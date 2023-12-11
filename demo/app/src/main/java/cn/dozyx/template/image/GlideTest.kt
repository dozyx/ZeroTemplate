package cn.dozyx.template.image

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import cn.dozyx.template.R
import cn.dozyx.template.base.BaseTestActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_base_test.*
import timber.log.Timber

class GlideTest : BaseTestActivity() {

    override fun initActions() {
        addAction("normal"){
            Glide.with(this).load(PICTURE_URL_2).placeholder(R.drawable.image_loading).into(ImageTarget(image_view))
        }
        addAction("normal1"){
            Glide.with(this).load(PICTURE_URL).placeholder(R.drawable.image_loading).into(ImageTarget(image_view))
        }

        addAction("picasso") {
            Picasso.Builder(this).build().load(PICTURE_URL_3).placeholder(R.drawable.image_loading)
                .into(image_view)
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

    private class ImageTarget(val iv: ImageView) : CustomTarget<Drawable>() {

        override fun onLoadStarted(placeholder: Drawable?) {
            super.onLoadStarted(placeholder)
            Timber.d("ImageTarget.onLoadStarted")
        }

        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            iv.setImageDrawable(resource)
            Timber.d("ImageTarget.onResourceReady")
        }

        override fun onLoadCleared(placeholder: Drawable?) {
            Timber.d("ImageTarget.onLoadCleared")
        }

        override fun onLoadFailed(errorDrawable: Drawable?) {
            super.onLoadFailed(errorDrawable)
            Timber.d("ImageTarget.onLoadFailed")
        }
    }

    companion object {
        private const val PICTURE_URL = "https://img-cms.pchome.net/article/1k0/bh/36/o1dzur-1hvc.jpg"
        private const val PICTURE_URL_2 = "https://i.ytimg.com/vi/nCUdCORG2zA/hq720.jpg?sqp=-oaymwEZCNAFEJQDSFXyq4qpAwsIARUAAIhCGAFwAQ==&rs=AOn4CLCorjYzsi4P4pU5O7QAwcKvg27T-A"
        private const val PICTURE_URL_3 = "https://scontent-hkg4-1.cdninstagram.com/v/t50.2886-16/271451356_286045790223902_3085395842748421433_n.mp4?_nc_ht=scontent-hkg4-1.cdninstagram.com&_nc_cat=102&_nc_ohc=GVts_XQIpTgAX_DVy5e&edm=AABBvjUBAAAA&ccb=7-4&oe=61DFFFF7&oh=00_AT9-g3PQLa5u9YKeuy8rupzacLjNXyUOiLz3cihTyNhtuA&_nc_sid=83d603"
    }
}
