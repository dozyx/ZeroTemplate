package com.zerofate.template.justfortest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_meaningless.*


/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
class MeaninglessActivity : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meaningless)
        edit.requestFocus()
        SPUtils.getInstance().put("111","1112")
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    companion object {

        private val TAG = "MeaninglessActivity"
    }


}
