package cn.dozyx.demo.shortcut

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_shortcut.setOnClickListener {
            startActivity(Intent(this, ShortcutActivity::class.java))
        }
        btn_dynamic.setOnClickListener {
            startActivity(Intent(this, DynamicShortcutActivity::class.java))
        }
        addShortcut()
    }

    private fun addShortcut() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            addDynamicShortcut()
            addLauncherShortcut()
        }
    }

    private fun addLauncherShortcut() {
        val builder = ShortcutInfoCompat.Builder(this, "launcher")
        val intent = Intent(this, ShortcutLauncherActivity::class.java)
        intent.action = Intent.ACTION_VIEW// action 不能为 null
        builder.setIntent(intent)
        builder.setShortLabel("launcher")
        builder.setIcon(IconCompat.createWithResource(this, R.drawable.anime))
        ShortcutManagerCompat.addDynamicShortcuts(this, listOf(builder.build()))
    }

    private fun addDynamicShortcut() {
        val builder = ShortcutInfoCompat.Builder(this, "dynamic")
        val intent = Intent(this, DynamicShortcutActivity::class.java)
        intent.action = Intent.ACTION_VIEW// action 不能为 null
        builder.setIntent(intent)
        builder.setShortLabel("dynamic")
        builder.setIcon(IconCompat.createWithResource(this, R.drawable.anime))
        ShortcutManagerCompat.addDynamicShortcuts(this, listOf(builder.build()))
    }
}