package cn.dozyx.template.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

/**
 * Create by dozyx on 2019/7/2
 */
abstract class LifeCycleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("LifeCycleActivity.onCreate")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.d("LifeCycleActivity.onRestart")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("LifeCycleActivity.onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("LifeCycleActivity.onResume")
    }

    override fun onPause() {
        Timber.d("LifeCycleActivity.onPause")
        super.onPause()
    }

    override fun onStop() {
        Timber.d("LifeCycleActivity.onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Timber.d("LifeCycleActivity.onDestroy")
        super.onDestroy()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Timber.d("LifeCycleActivity.onRestoreInstanceState")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("LifeCycleActivity.onSaveInstanceState")
    }

}
