package cn.dozyx.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.dozeboy.android.sample.navigation.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val host = supportFragmentManager
                .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        setupBottomNavMenu(host.navController)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        findViewById<BottomNavigationView>(R.id.navigation)?.let { bottomNavView ->
            NavigationUI.setupWithNavController(bottomNavView, navController)
        }
    }
}
