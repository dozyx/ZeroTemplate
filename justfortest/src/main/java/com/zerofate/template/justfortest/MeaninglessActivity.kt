package com.zerofate.template.justfortest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.dozeboy.android.core.utli.log.ZLog
import com.tencent.mmkv.MMKV



/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
class MeaninglessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meaningless)
        ZLog.d("onCreate: ")
        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent();
            intent.setPackage("")
        }
        val rootDir = MMKV.initialize(this)
        ZLog.d("mmkv root: $rootDir")
        val kv = MMKV.defaultMMKV()

        kv.encode("bool", true)
        val bValue = kv.decodeBool("bool")
        ZLog.d(bValue)
        kv.encode("int", Integer.MIN_VALUE)
        val iValue = kv.decodeInt("int")
        ZLog.d(iValue)
        kv.encode("string", "Hello from mmkv")
        val str = kv.decodeString("string")
        ZLog.d(str)
    }


    companion object {

        private val TAG = "MeaninglessActivity"
    }


}
