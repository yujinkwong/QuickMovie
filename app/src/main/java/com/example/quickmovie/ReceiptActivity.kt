package com.example.quickmovie

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiptActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paymentoption)

        // Retrieve data from PaymentActivity
        val selectedSeats = intent.getStringExtra("SELECTED_SEATS") ?: "No seats selected"
        val finalTotalPrice = intent.getDoubleExtra("FINAL_TOTAL_PRICE", 0.0)
        val paymentMethod = intent.getStringExtra("PAYMENT_METHOD") ?: "Unknown"

        // Set receipt details
        findViewById<TextView>(R.id.selectedSeats).text = "Seats: $selectedSeats"
        findViewById<TextView>(R.id.tv_payment_details).text = "Total Price: RM %.2f".format(finalTotalPrice)


        // QR Code Placeholder
        val qrCodeImageView: ImageView = findViewById(R.id.iv_qr_code)
        qrCodeImageView.setImageResource(R.drawable.tng) // Replace with actual QR logic

        // Done button
        findViewById<Button>(R.id.btn_done).setOnClickListener {
            finish() // Close the receipt screen
        }
    }
}
