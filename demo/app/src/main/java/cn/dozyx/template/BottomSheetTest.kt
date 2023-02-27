package cn.dozyx.template

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BottomSheetTest :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_bottom_sheet)
        hashMapOf<String, Int>().withDefault { 1 }
    }
}