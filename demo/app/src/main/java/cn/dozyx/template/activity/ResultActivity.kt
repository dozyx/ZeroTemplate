package cn.dozyx.template.activity

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.UriUtils
import cn.dozyx.template.base.BaseShowResultActivity

import java.io.File

/**
 * Create by timon on 2019/5/9
 */
class ResultActivity : BaseShowResultActivity() {
    override fun getButtonText(): Array<String> {
        return arrayOf("pick photo", "清理", "相册")
    }

    override fun onButton1() {
        val intent1 = Intent(Intent.ACTION_PICK)
        intent1.type = "image/*"
        startActivityForResult(intent1, 1)
    }

    override fun onButton2() {
        setText("")
    }

    override fun onButton3() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2)
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
        val config = LogUtils.getConfig()
        config.stackDeep = 20
        LogUtils.d("stack")
        data?.data?.let {
            val file = UriUtils.uri2File(it)
            appendResult("$requestCode  &  $resultCode  & ${it.path} &  ${file.absolutePath} & ${file.exists()}")
        }
    }
}
