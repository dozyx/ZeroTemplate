package cn.dozyx.template.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ActionProvider
import androidx.core.view.MenuItemCompat
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.activity_toolbar.*
import timber.log.Timber

class ToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)
        setSupportActionBar(toolbar)
//        setupActionBarCustomView()
    }

    private fun setupActionBarCustomView() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
    //            setHomeAsUpIndicator(android.R.drawable.arrow_up_float)

            val button = Button(this@ToolbarActivity)
            button.text = "custom"
            button.setTextColor(Color.BLUE)
            setDisplayShowCustomEnabled(true)
            customView = button
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.test, menu)
        val item = menu?.findItem(R.id.action_settings)
//        item?.actionView = LayoutInflater.from(this).inflate(R.layout.menu_action_view, null)// 设置 action view 之后将没办法自动响应 onOptionsItemSelected
        MenuItemCompat.setActionProvider(item, CustomActionProvider(this));
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Timber.d("ToolbarActivity.onOptionsItemSelected $item")
        return super.onOptionsItemSelected(item)
    }
}

class CustomActionProvider(context: Context) : ActionProvider(context) {
    override fun onCreateActionView(): View {
        return LayoutInflater.from(context).inflate(R.layout.menu_action_view, null)
    }

    override fun onPerformDefaultAction(): Boolean {
        Timber.d("CustomActionProvider.onPerformDefaultAction")
        return super.onPerformDefaultAction()
    }
}

