package com.zerofate.template.justfortest

import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.dozeboy.android.core.utli.log.ZLog

/**
 * 没有任何意义的Activity，可能以后会加入很多乱七八糟的东西
 */
class MeaninglessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meaningless)
        findViewById<Button>(R.id.button).setOnClickListener {
            val result = BluetoothAdapter.getDefaultAdapter().enable()
            val resultDiscovery = BluetoothAdapter.getDefaultAdapter().startDiscovery()
        }
    }


    companion object {

        private val TAG = "MeaninglessActivity"
    }


}
