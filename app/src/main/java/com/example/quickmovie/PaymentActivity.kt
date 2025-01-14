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
        val selectedSeats = intent.getStringExtra("SELECTED_SEATS") ?: "No seats selected"
        val finalTotalPrice = intent.getDoubleExtra("FINAL_TOTAL_PRICE", 0.0)

        // Initialize views
        val paymentDetailsTextView: TextView = findViewById(R.id.tv_payment_details)
        val tngButton: Button = findViewById(R.id.btn_tng)
        val duitNowButton: Button = findViewById(R.id.btn_duitnow)
        val creditCardButton: Button = findViewById(R.id.btn_credit_card)
        val backButton: Button = findViewById(R.id.btn_back_to_food)

        // Set total price
        paymentDetailsTextView.text = "Total Price: RM %.2f".format(finalTotalPrice)

        // Payment method buttons
        tngButton.setOnClickListener { handlePaymentMethod("TNG eWallet", selectedSeats, finalTotalPrice) }
        duitNowButton.setOnClickListener { handlePaymentMethod("DuitNow", selectedSeats, finalTotalPrice) }
        creditCardButton.setOnClickListener { handlePaymentMethod("Credit/Debit Card", selectedSeats, finalTotalPrice) }

        // Back button to return to FoodActivity
        backButton.setOnClickListener { finish() }
    }

    private fun handlePaymentMethod(method: String, seats: String, price: Double) {
        // Show a Toast message for payment success
        Toast.makeText(this, "Payment of RM %.2f with $method successful for seats: $seats".format(price), Toast.LENGTH_LONG).show()

        // Wait for a brief moment before navigating back to the DashboardActivity
        Handler().postDelayed({
            navigateToDashboard()  // Redirect to the DashboardActivity
        }, 2000) // Wait for 2 seconds before navigating
    }

    private fun navigateToDashboard() {
        // Intent to navigate back to DashboardActivity
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)

        // Close PaymentActivity so it is no longer in the activity stack
        finish()
    }
}
