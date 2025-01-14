package com.example.quickmovie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

class SeatSelectionActivity : ComponentActivity() {

    private val seatPrice = 15.0 // Price for one seat
    private val selectedSeats = mutableListOf<String>() // List to store selected seats

    private lateinit var selectedSeatsTextView: TextView
    private lateinit var totalPriceTextView: TextView
    private lateinit var proceedButton: Button // Proceed button
    private var totalPrice = 0.0 // Total price for selected seats

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seat)

        // Retrieve data from the previous activity
        val selectedTime = intent.getStringExtra("SELECTED_TIME") ?: "Unknown Time"

        // Initialize views
        selectedSeatsTextView = findViewById(R.id.selectedSeats)
        totalPriceTextView = findViewById(R.id.totalPrice)
        proceedButton = findViewById(R.id.proceedButton) // Initialize the proceed button

        // Set up seat buttons and click listeners
        val seatButtons = listOf("A1", "A2", "A3", "A4", "A5", "B1", "B2", "B3", "B4", "B5")
        seatButtons.forEach { seatId ->
            val button: Button = findViewById(resources.getIdentifier(seatId, "id", packageName))
            button.setOnClickListener { onSeatClick(seatId, button) }
        }

        // Proceed to FoodActivity when proceed button is clicked
        proceedButton.setOnClickListener {
            if (selectedSeats.isNotEmpty()) {
                val intent = Intent(this, FoodActivity::class.java)
                intent.putExtra("SELECTED_TIME", selectedTime)
                intent.putExtra("SELECTED_SEATS", selectedSeats.joinToString(", "))
                intent.putExtra("SEAT_TOTAL_PRICE", totalPrice)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please select at least one seat before proceeding.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Handles seat button clicks
    private fun onSeatClick(seatId: String, button: Button) {
        val greenColor = ContextCompat.getColor(this, R.color.green)
        val redColor = ContextCompat.getColor(this, R.color.red)

        if (selectedSeats.contains(seatId)) {
            // Deselect the seat
            selectedSeats.remove(seatId)
            button.setBackgroundColor(greenColor) // Set to green
        } else {
            // Select the seat
            selectedSeats.add(seatId)
            button.setBackgroundColor(redColor) // Set to red
        }

        updateUI()
    }

    // Updates the selected seats and total price UI
    private fun updateUI() {
        // Update the selected seats text
        selectedSeatsTextView.text = "Selected Seats: ${selectedSeats.joinToString(", ")}"

        // Calculate and update the total price
        totalPrice = selectedSeats.size * seatPrice
        totalPriceTextView.text = "Total Price: RM %.2f".format(totalPrice)
    }
}
