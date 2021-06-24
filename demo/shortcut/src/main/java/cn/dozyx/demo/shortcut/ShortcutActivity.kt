package cn.dozyx.demo.shortcut

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class ShortcutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shortcut)
        Timber.d("ShortcutActivity.onCreate")
    }

    override fun onDestroy() {
        Timber.d("ShortcutActivity.onDestroy")
        super.onDestroy()
    }
}