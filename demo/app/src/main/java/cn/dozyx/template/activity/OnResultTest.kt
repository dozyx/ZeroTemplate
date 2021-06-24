package cn.dozyx.template.activity

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.UriUtils
import cn.dozyx.template.base.BaseShowResultActivity
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

import java.io.File

/**
 * Create by dozyx on 2019/5/9
 */
class OnResultTest : BaseTestActivity() {
    override fun initActions() {
        addButton("pick photo"){
            val intent1 = Intent(Intent.ACTION_PICK)
            intent1.type = "image/*"
            startActivityForResult(intent1, 1)
        }
        addButton("清理"){
            clearResult()
        }
        addButton("相册"){
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2)
        }
        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.CreateDocument()) { uri ->
                Timber.d("ResultContracts $uri")
            }
        addButton("ResultContracts") {
            resultLauncher.launch("test")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("test", 1)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //        appendResult(savedInstanceState.getInt("test") + "");
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val config = LogUtils.getConfig()
        config.stackDeep = 20
        LogUtils.d("stack")
        data?.data?.let {
            val file = UriUtils.uri2File(it)
            appendResult("$requestCode  &  $resultCode  & ${it.path} &  ${file.absolutePath} & ${file.exists()}")
        }
    }
}
