package com.example.quickmovie

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paymentoption) // Assuming paymentoption.xml is the layout

        // Retrieve the total price from the Intent
        val totalPrice = intent.getDoubleExtra("TOTAL_PRICE", 0.0)

        // Display the total price in the corresponding TextView
        val totalPricesValueTextView = findViewById<TextView>(R.id.totalPricesValue)
        totalPricesValueTextView.text = "RM $totalPrice"
    }
}

