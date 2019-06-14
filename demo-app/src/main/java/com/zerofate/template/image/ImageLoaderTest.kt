package com.zerofate.template.image

import android.os.Bundle

import com.dozeboy.core.base.BaseActivity
import com.squareup.picasso.Picasso
import com.zerofate.template.R
import kotlinx.android.synthetic.main.image_activity_loader.*

/**
 * Create by timon on 2019/5/28
 */
private val IMAGE_URLS = arrayOf("https://img-cms.pchome.net/article/1k0/bh/36/o1dzur-1hvc.jpg", "https://img-cms.pchome.net/article/1k0/bh/36/o1dzus-dmd.jpg", "https://img-cms.pchome.net/article/1k0/bh/36/o1dzus-ji3.jpg")

class ImageLoaderTest : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.image_activity_loader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_start_load.setOnClickListener {
            Picasso.get().load(IMAGE_URLS[0]).into(image_load0)
            Picasso.get().load(IMAGE_URLS[1]).into(image_load1)
            Picasso.get().load(IMAGE_URLS[2]).into(image_load2)
        }
    }
}
