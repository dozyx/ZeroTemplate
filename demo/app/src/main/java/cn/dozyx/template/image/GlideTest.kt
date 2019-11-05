package cn.dozyx.template.image

import android.os.Bundle
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_glide_test.*

class GlideTest : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Glide.with(this).load(PICTURE_URL).placeholder(R.drawable.image_loading).into(image)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_glide_test
    }

    companion object {
        private const val PICTURE_URL = "https://img-cms.pchome.net/article/1k0/bh/36/o1dzur-1hvc.jpg"
    }
}
