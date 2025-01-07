package com.example.quickmovie

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

class SelectTimeActivity : ComponentActivity() {

    // Declare the time buttons with actual IDs from your layout
    private lateinit var timeButton1: Button // This represents the button for "5:00 PM"
    private lateinit var timeButton2: Button // This represents the button for "3:00 PM"
    private lateinit var timeButton3: Button // This represents the button for "12:00 PM"
    private lateinit var timeButton4: Button // This represents the button for "10:00 AM"

    // Declare a variable to keep track of the currently selected button
    private var selectedTimeButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selecttime)

        // Initialize the time buttons using their correct IDs from the layout
        timeButton1 = findViewById(R.id.btn_10am) // This button corresponds to 5:00 PM
        timeButton2 = findViewById(R.id.btn_3pm) // This button corresponds to 3:00 PM
        timeButton3 = findViewById(R.id.btn_5pm) // This button corresponds to 12:00 PM
        timeButton4 = findViewById(R.id.btn_12pm) // This button corresponds to 10:00 AM

        // Set click listeners for each time button
        timeButton1.setOnClickListener { onTimeButtonClicked(timeButton1) }
        timeButton2.setOnClickListener { onTimeButtonClicked(timeButton2) }
        timeButton3.setOnClickListener { onTimeButtonClicked(timeButton3) }
        timeButton4.setOnClickListener { onTimeButtonClicked(timeButton4) }
    }

    // Function to handle the button click event
    private fun onTimeButtonClicked(clickedButton: Button) {
        // If the clicked button is already selected, do nothing
        if (clickedButton == selectedTimeButton) {
            return
        }

        // Reset the previously selected button if any
        selectedTimeButton?.let {
            // Reset the previously selected button back to its original state
            it.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        }

        // Set the new selected button to green
        clickedButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))

        // Update the currently selected button
        selectedTimeButton = clickedButton
    }
}
