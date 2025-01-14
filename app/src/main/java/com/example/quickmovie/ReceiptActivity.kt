package com.example.quickmovie

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ReceiptActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receipt)

        // Initialize Firebase Authentication and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Retrieve data from the intent
        val selectedMovie = intent.getStringExtra("SELECTED_MOVIE") ?: "No movie selected"
        val selectedTime = intent.getStringExtra("SELECTED_TIME") ?: "No time selected"
        val selectedSeats = intent.getStringExtra("SELECTED_SEATS") ?: "No seats selected"
        val finalTotalPrice = intent.getDoubleExtra("FINAL_TOTAL_PRICE", 0.0)
        val userEmail = auth.currentUser?.email ?: "No email available"

        // Display ticket details in the TextViews
        findViewById<TextView>(R.id.tv_email1).text = "Email: $userEmail"
        findViewById<TextView>(R.id.tv_movie_details1).text = "Movie: $selectedMovie"
        findViewById<TextView>(R.id.tv_time_details1).text = "Time: $selectedTime"
        findViewById<TextView>(R.id.tv_seat_details).text = "Seats: $selectedSeats"
        findViewById<TextView>(R.id.tv_total_price).text = "Total Price: RM %.2f".format(finalTotalPrice)

        // Generate the QR code
        val qrCodeData = "Movie: $selectedMovie, Time: $selectedTime, Seats: $selectedSeats, Price: RM $finalTotalPrice"
        val qrCodeBitmap = generateQRCode(qrCodeData)

        // Set QR code image
        val qrCodeImageView: ImageView = findViewById(R.id.iv_qr_code)
        qrCodeImageView.setImageBitmap(qrCodeBitmap)

        // Set up the "Done" button to save the data to Firebase and navigate to the Dashboard
        findViewById<Button>(R.id.btn_done).setOnClickListener {
            // Save booking history to Firebase
            saveBookingHistoryToFirebase(selectedMovie, selectedTime, selectedSeats, finalTotalPrice, userEmail, qrCodeData)
            // Navigate to the Dashboard
            navigateToDashboard()
        }
    }

    // Function to generate QR code from a string
    private fun generateQRCode(data: String): Bitmap {
        val writer = MultiFormatWriter()
        val hints = HashMap<EncodeHintType, Any>()
        hints[EncodeHintType.MARGIN] = 1 // Optional: Set margin for QR code

        val bitMatrix: BitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 200, 200, hints)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }

    // Function to save booking details and QR code data to Firebase Realtime Database
    private fun saveBookingHistoryToFirebase(selectedMovie: String, selectedTime: String, selectedSeats: String, totalPrice: Double, userEmail: String, qrCodeData: String) {
        val userRef = database.child("users").child(userEmail.replace(".", "_"))
        val historyRef = userRef.child("history").push()

        // Save booking details including QR code data
        val bookingDetails = mapOf(
            "movie" to selectedMovie,
            "time" to selectedTime,
            "seats" to selectedSeats,
            "total_price" to totalPrice,
            "qr_code_data" to qrCodeData
        )

        historyRef.setValue(bookingDetails)
    }

    // Function to navigate to the Dashboard
    private fun navigateToDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()  // Finish the ReceiptActivity so the user can't go back to it
    }
}
