package cn.dozyx.template

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.test_night_mode.*

class NightModeTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val configuration = Configuration()
        configuration.uiMode = Resources.getSystem().configuration.uiMode
        val newContext = createConfigurationContext(configuration)

        window.setBackgroundDrawable(ContextCompat.getDrawable(newContext, R.drawable.window_bg))
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        setContentView(R.layout.test_night_mode)

        view_custom_night_mode.setBackgroundColor(
            ContextCompat.getColor(newContext, R.color.night_mode)
        )
    }
}