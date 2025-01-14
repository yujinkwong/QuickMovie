package com.example.quickmovie

import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

class SelectTimeActivity3 : ComponentActivity() {

    private lateinit var timeButton1: Button
    private lateinit var timeButton2: Button
    private lateinit var timeButton3: Button
    private lateinit var timeButton4: Button
    private lateinit var proceedButton: Button
    private lateinit var webView: WebView

    private var selectedTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selecttime)

        // Initialize buttons and WebView
        timeButton1 = findViewById(R.id.btn_10am)
        timeButton2 = findViewById(R.id.btn_3pm)
        timeButton3 = findViewById(R.id.btn_5pm)
        timeButton4 = findViewById(R.id.btn_12pm)
        proceedButton = findViewById(R.id.proceedButton1)
        webView = findViewById(R.id.moviePoster)

        // Set up the WebView to load YouTube video
        val youtubeUrl = "https://www.youtube.com/embed/-FmWuCgJmxo"
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.loadUrl(youtubeUrl)

        // Set click listeners for time buttons
        timeButton1.setOnClickListener { selectTime("10:00 AM", timeButton1) }
        timeButton2.setOnClickListener { selectTime("3:00 PM", timeButton2) }
        timeButton3.setOnClickListener { selectTime("5:00 PM", timeButton3) }
        timeButton4.setOnClickListener { selectTime("12:00 PM", timeButton4) }

        // Proceed to seat selection
        proceedButton.setOnClickListener {
            if (selectedTime != null) {
                val intent = Intent(this, SeatSelectionActivity::class.java)
                intent.putExtra("SELECTED_TIME", selectedTime)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please select a time before proceeding.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun selectTime(time: String, button: Button) {
        // Reset previously selected button
        listOf(timeButton1, timeButton2, timeButton3, timeButton4).forEach {
            it.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        }

        // Highlight the selected button and update the selected time
        button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
        selectedTime = time
    }
}
