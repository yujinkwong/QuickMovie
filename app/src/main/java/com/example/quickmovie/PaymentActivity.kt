package com.example.quickmovie

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class PaymentActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paymentoption)

        // Retrieve data from the previous activity
        val selectedMovie = intent.getStringExtra("SELECTED_MOVIE") ?: "No movie selected"
        val selectedLocation = intent.getStringExtra("SELECTED_LOCATION") ?: "No location selected"
        val selectedTime = intent.getStringExtra("SELECTED_TIME") ?: "No time selected"
        val selectedSeats = intent.getStringExtra("SELECTED_SEATS") ?: "No seats selected"
        val finalTotalPrice = intent.getDoubleExtra("FINAL_TOTAL_PRICE", 0.0)

        // Initialize the TextView for displaying payment details
        val paymentDetailsTextView: TextView = findViewById(R.id.tv_payment_details)

        // Create a string with all the details
        val movieDetails = "Movie: $selectedMovie\nLocation: $selectedLocation\nTime: $selectedTime\nSeats: $selectedSeats"
        paymentDetailsTextView.text = "Total Price: RM %.2f\n\n$movieDetails".format(finalTotalPrice)

        // Payment method buttons
        val tngButton: Button = findViewById(R.id.btn_tng)
        val duitNowButton: Button = findViewById(R.id.btn_duitnow)
        val creditCardButton: Button = findViewById(R.id.btn_credit_card)

        tngButton.setOnClickListener {
            handlePaymentMethod("TNG eWallet", selectedSeats, finalTotalPrice, selectedMovie, selectedTime, selectedLocation)
        }

        duitNowButton.setOnClickListener {
            handlePaymentMethod("DuitNow", selectedSeats, finalTotalPrice, selectedMovie, selectedTime, selectedLocation)
        }

        creditCardButton.setOnClickListener {
            handlePaymentMethod("Credit/Debit Card", selectedSeats, finalTotalPrice, selectedMovie, selectedTime, selectedLocation)
        }
    }

    private fun handlePaymentMethod(method: String, seats: String, price: Double, movie: String, time: String, location: String) {
        // Show a Toast message for payment success
        Toast.makeText(this, "Payment of RM %.2f with $method successful for seats: $seats".format(price), Toast.LENGTH_LONG).show()

        // Wait for a brief moment before navigating to ReceiptActivity
        Handler().postDelayed({
            navigateToReceipt(movie, time, location, seats, price)  // Redirect to ReceiptActivity
        }, 2000) // Wait for 2 seconds before navigating
    }

    private fun navigateToReceipt(movie: String, time: String, location: String, seats: String, price: Double) {
        // Intent to navigate to ReceiptActivity
        val intent = Intent(this, ReceiptActivity::class.java).apply {
            val userEmail = intent.getStringExtra("USER_EMAIL") ?: "No email provided"
            putExtra("USER_EMAIL", userEmail)
            putExtra("SELECTED_MOVIE", movie)
            putExtra("SELECTED_TIME", time)
            putExtra("SELECTED_LOCATION", location)
            putExtra("SELECTED_SEATS", seats)
            putExtra("FINAL_TOTAL_PRICE", price)
        }
        startActivity(intent)

        // Close PaymentActivity so it is no longer in the activity stack
        finish()
    }
}
