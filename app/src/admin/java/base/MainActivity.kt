package base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.syriamarket.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view_admin)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_main_host_fragment_admin) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
    }
}