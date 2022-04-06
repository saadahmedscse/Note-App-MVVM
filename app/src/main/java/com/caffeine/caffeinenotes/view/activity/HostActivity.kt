package com.caffeine.caffeinenotes.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.caffeine.caffeinenotes.R

class HostActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        navController = findNavController(R.id.nav_controller)
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }

    fun updateCount(count : Int){
        this.count = count
    }

    override fun onBackPressed() {
        if (count == 0){
            finish()
        }
        else {
            super.onBackPressed()
        }
    }
}