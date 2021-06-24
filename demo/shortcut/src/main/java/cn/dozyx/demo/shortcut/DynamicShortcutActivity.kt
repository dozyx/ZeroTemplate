package cn.dozyx.demo.shortcut

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dynamic_shortcut.*
import timber.log.Timber

class DynamicShortcutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_shortcut)
        Timber.d("DynamicShortActivity.onCreate")

        btn_shortcut.setOnClickListener {
            startActivity(Intent(this, ShortcutActivity::class.java))
        }
    }

    override fun onDestroy() {
        Timber.d("DynamicShortActivity.onDestroy")
        super.onDestroy()
    }
}