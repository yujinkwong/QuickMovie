package com.example.quickmovie

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

class SeatSelectionActivity : ComponentActivity() {

    // Constants
    private val seatPrice = 15.0 // Price for one seat

    // Variables to track selected seats
    private val selectedSeats = mutableListOf<String>()
    private var totalPrice = 0.0

    // Views
    private lateinit var selectedSeatsTextView: TextView
    private lateinit var totalPriceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seat) // Loads the seat.xml layout

        // Initialize the views
        selectedSeatsTextView = findViewById(R.id.selectedSeats)
        totalPriceTextView = findViewById(R.id.totalPrice)

        // Set click listeners for all seat buttons
        val seatButtons = listOf(
            "A1", "A2", "A3", "A4", "A5", "B1", "B2", "B3", "B4", "B5"
        )

        seatButtons.forEach { seatId ->
            val button: Button = findViewById(resources.getIdentifier(seatId, "id", packageName))
            button.setOnClickListener { onSeatClick(seatId, button) }
        }
    }

    // Function to handle seat click events
    private fun onSeatClick(seatId: String, button: Button) {
        val greenColor = ContextCompat.getColor(this, R.color.green) // Get green color
        val redColor = ContextCompat.getColor(this, R.color.red) // Get red color

        if (selectedSeats.contains(seatId)) {
            // Deselect the seat (remove it from the list)
            selectedSeats.remove(seatId)
            button.setBackgroundColor(greenColor) // Change to green (deselected state)
        } else {
            // Select the seat (add it to the list)
            selectedSeats.add(seatId)
            button.setBackgroundColor(redColor) // Change to red (selected state)
        }

        // Update the UI with the selected seats and total price
        updateUI()
    }

    // Function to update the selected seats and total price
    private fun updateUI() {
        // Display the selected seats
        selectedSeatsTextView.text = "Selected Seats: ${selectedSeats.joinToString(", ")}"

        // Calculate and display the total price
        totalPrice = selectedSeats.size * seatPrice
        totalPriceTextView.text = "Total Price: RM %.2f".format(totalPrice)
    }
}
