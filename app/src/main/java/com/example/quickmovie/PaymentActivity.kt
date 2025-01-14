package com.example.quickmovie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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
        tngButton.setOnClickListener {
            navigateToReceipt(selectedSeats, finalTotalPrice, "TNG eWallet")
        }
        duitNowButton.setOnClickListener {
            navigateToReceipt(selectedSeats, finalTotalPrice, "DuitNow")
        }
        creditCardButton.setOnClickListener {
            navigateToReceipt(selectedSeats, finalTotalPrice, "Credit/Debit Card")
        }

        // Back button to return to the previous page
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun navigateToReceipt(selectedSeats: String, totalPrice: Double, paymentMethod: String) {
        // Navigate to ReceiptActivity
        val intent = Intent(this, ReceiptActivity::class.java)
        intent.putExtra("SELECTED_SEATS", selectedSeats)
        intent.putExtra("FINAL_TOTAL_PRICE", totalPrice)
        intent.putExtra("PAYMENT_METHOD", paymentMethod)
        startActivity(intent)
    }
}
