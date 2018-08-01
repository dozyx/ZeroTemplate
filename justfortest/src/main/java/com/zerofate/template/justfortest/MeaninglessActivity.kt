package com.zerofate.template.justfortest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
class MeaninglessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meaningless)
    }


    companion object {

        private val TAG = "MeaninglessActivity"
    }


}
