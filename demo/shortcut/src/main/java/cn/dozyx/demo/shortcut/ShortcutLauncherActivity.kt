package cn.dozyx.demo.shortcut

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ShortcutLauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, DynamicShortcutActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
        finish()
    }
}