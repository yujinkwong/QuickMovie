package com.example.quickmovie

import android.os.Bundle
import androidx.activity.ComponentActivity

class SeatSelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seat) // Loads the seat.xml layout
    }
}
