package com.example.quickmovie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MenuActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        // Functionality for menu_Home to navigate back to DashboardActivity
        findViewById<Button>(R.id.menu_Home).setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish() // Closes MenuActivity to prevent going back to it on pressing back
        }

        // Functionality for menu_Profile to navigate to the SeatActivity
        findViewById<Button>(R.id.menu_Profile).setOnClickListener {
            val intent = Intent(this, SeatSelectionActivity::class.java)
            startActivity(intent)
        }

        // menu_Setting has no functionality
        findViewById<Button>(R.id.menu_AboutUs).setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }
    }
}
