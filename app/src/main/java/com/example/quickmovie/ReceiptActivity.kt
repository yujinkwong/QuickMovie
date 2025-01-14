package com.example.quickmovie

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiptActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paymentoption)

        // Retrieve data from PaymentActivity
        val selectedSeats = intent.getStringExtra("SELECTED_SEATS") ?: "No seats selected"
        val finalTotalPrice = intent.getDoubleExtra("FINAL_TOTAL_PRICE", 0.0)

        // Set receipt details
        findViewById<TextView>(R.id.tv_seat_details).text = "Seats: $selectedSeats"
        findViewById<TextView>(R.id.tv_total_price).text = "Total Price: RM %.2f".format(finalTotalPrice)
    }
}
