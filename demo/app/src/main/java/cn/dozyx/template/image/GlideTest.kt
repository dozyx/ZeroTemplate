package cn.dozyx.template.image

import android.graphics.Bitmap
import cn.dozyx.template.R
import cn.dozyx.template.base.BaseTestActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_base_test.*
import timber.log.Timber

class GlideTest : BaseTestActivity() {

    override fun initActions() {
        addAction("normal"){
            Glide.with(this).load(PICTURE_URL_2).placeholder(R.drawable.image_loading).into(image_view)
        }

        addAction("rxjava"){
            val subscribe = Observable.create<Bitmap> {
                val target = Glide.with(this)
                    .asBitmap()
                    .load(PICTURE_URL_2)
                    .apply(RequestOptions().override(200).centerCrop())
                    .submit()
                it.onNext(target.get())
                it.onComplete()
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Timber.d("load bitmap success $it")
                    image_view.setImageBitmap(it)
                }
        }
    }

    companion object {
        private const val PICTURE_URL = "https://img-cms.pchome.net/article/1k0/bh/36/o1dzur-1hvc.jpg"
        private const val PICTURE_URL_2 = "https://i.ytimg.com/vi/nCUdCORG2zA/hq720.jpg?sqp=-oaymwEZCNAFEJQDSFXyq4qpAwsIARUAAIhCGAFwAQ==&rs=AOn4CLCorjYzsi4P4pU5O7QAwcKvg27T-A"
    }
}
