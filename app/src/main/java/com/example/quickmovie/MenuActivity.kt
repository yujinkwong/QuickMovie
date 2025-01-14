package com.example.quickmovie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MenuActivity : ComponentActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        // Navigate to DashboardActivity
        findViewById<Button>(R.id.menu_Home).setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish() // Close MenuActivity to prevent going back to it
        }
        // Navigate to SeatSelectionActivity (Profile)
        findViewById<Button>(R.id.menu_Detail).setOnClickListener {
            val intent = Intent(this, SelectTimeActivity::class.java)
            startActivity(intent)
        }
        // Navigate to SeatSelectionActivity (Profile)
        findViewById<Button>(R.id.menu_Seat).setOnClickListener {
            val intent = Intent(this, SeatSelectionActivity::class.java)
            startActivity(intent)
        }

        // Navigate to AboutUsActivity
        findViewById<Button>(R.id.menu_AboutUs).setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }

        // Navigate to FoodActivity
        findViewById<Button>(R.id.menu_Food).setOnClickListener {
            val intent = Intent(this, FoodActivity::class.java)
            startActivity(intent)
        }
    }
}
