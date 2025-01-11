package com.example.quickmovie

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity

class PaymentActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paymentoption) // Ensure payment.xml layout is used

        // Retrieve the total price passed from FoodActivity
        val totalPrice = intent.getDoubleExtra("TOTAL_PRICE", 0.0)

        // Find the total price TextView and display the total price
        val totalPricesValueTextView: TextView = findViewById(R.id.totalPricesValue)
        totalPricesValueTextView.text = "RM %.2f".format(totalPrice)

        // Example: Handle TNG button click
        findViewById<TextView>(R.id.tng).setOnClickListener {
            // Add your payment processing logic for TNG here
        }

        // Example: Handle DuitNow button click
        findViewById<TextView>(R.id.duitnow).setOnClickListener {
            // Add your payment processing logic for DuitNow here
        }
    }
}
