package com.example.quickmovie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

class SelectTimeActivity : ComponentActivity() {

    private lateinit var timeButton1: Button
    private lateinit var timeButton2: Button
    private lateinit var timeButton3: Button
    private lateinit var timeButton4: Button
    private lateinit var proceedButton: Button

    private var selectedTimeButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selecttime)

        // Initialize time buttons
        timeButton1 = findViewById(R.id.btn_10am)
        timeButton2 = findViewById(R.id.btn_3pm)
        timeButton3 = findViewById(R.id.btn_5pm)
        timeButton4 = findViewById(R.id.btn_12pm)
        proceedButton = findViewById(R.id.proceedButton1)

        // Set click listeners for time buttons
        timeButton1.setOnClickListener { onTimeButtonClicked(timeButton1) }
        timeButton2.setOnClickListener { onTimeButtonClicked(timeButton2) }
        timeButton3.setOnClickListener { onTimeButtonClicked(timeButton3) }
        timeButton4.setOnClickListener { onTimeButtonClicked(timeButton4) }

        // Set click listener for the proceed button
        proceedButton.setOnClickListener {
            if (selectedTimeButton != null) {
                // Navigate to the Food page
                val intent = Intent(this, FoodActivity::class.java)
                startActivity(intent)
            } else {
                // Show a message if no time is selected
                Toast.makeText(this, "Please select a time before proceeding.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onTimeButtonClicked(clickedButton: Button) {
        if (clickedButton == selectedTimeButton) {
            return
        }

        // Reset the previously selected button, if any
        selectedTimeButton?.let {
            it.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        }

        // Highlight the selected button
        clickedButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))

        // Update the currently selected button
        selectedTimeButton = clickedButton
    }
}
