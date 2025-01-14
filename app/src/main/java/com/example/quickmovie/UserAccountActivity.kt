package com.example.quickmovie

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import java.util.*


class UserAccountActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userName: TextView
    private lateinit var userEmail: TextView
    private lateinit var bookingHistoryContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://quickmovie-490e5-default-rtdb.asia-southeast1.firebasedatabase.app/")

        // Initialize Views
        userName = findViewById(R.id.userName)
        userEmail = findViewById(R.id.userEmail)
        bookingHistoryContainer = findViewById(R.id.bookingHistoryContainer)

        // Get current user
        val currentUser = auth.currentUser
        if (currentUser != null) {
            fetchUserProfile(currentUser)
        } else {
            Toast.makeText(this, "No user is signed in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchUserProfile(user: FirebaseUser) {
        // Retrieve user data from Firebase Realtime Database
        val userRef = database.reference.child("users").child(user.email?.replace(".", "_") ?: "unknown")
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userProfile = dataSnapshot.getValue(UserProfile::class.java)

                userProfile?.let {
                    // Update UI with user data
                    userName.text = it.name
                    userEmail.text = it.email

                    // Load booking history (if any)
                    loadBookingHistoryWithQR(user)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@UserAccountActivity, "Failed to load user profile", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadBookingHistoryWithQR(user: FirebaseUser) {
        val bookingHistoryRef = database.reference.child("users").child(user.email?.replace(".", "_") ?: "unknown").child("history")
        bookingHistoryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                bookingHistoryContainer.removeAllViews()

                // Check if there are booking history records
                if (dataSnapshot.exists()) {
                    for (bookingSnapshot in dataSnapshot.children) {
                        val booking = bookingSnapshot.getValue(Booking::class.java)
                        val qrCodeData = bookingSnapshot.child("qr_code_data").value.toString()

                        booking?.let {
                            val bookingItem = LinearLayout(this@UserAccountActivity)
                            bookingItem.orientation = LinearLayout.VERTICAL

                            // Movie details
                            val bookingText = TextView(this@UserAccountActivity)
                            bookingText.text = "Movie: ${it.movie}, Time: ${it.time}, Seats: ${it.seats}, Total Price: RM ${it.total_price}"

                            // QR code ImageView
                            val qrCodeImageView = ImageView(this@UserAccountActivity)
                            val qrCodeBitmap = generateQRCode(qrCodeData)
                            qrCodeImageView.setImageBitmap(qrCodeBitmap)

                            // Add views to container
                            bookingItem.addView(bookingText)
                            bookingItem.addView(qrCodeImageView)
                            bookingHistoryContainer.addView(bookingItem)
                        }
                    }
                } else {
                    val noHistory = TextView(this@UserAccountActivity)
                    noHistory.text = "No booking history found."
                    bookingHistoryContainer.addView(noHistory)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@UserAccountActivity, "Failed to load booking history", Toast.LENGTH_SHORT).show()
            }
        })
    }

    data class UserProfile(
        val name: String = "",
        val email: String = "",
    )

    data class Booking(
        val movie: String = "",
        val time: String = "",
        val seats: String = "",
        val total_price: Double = 0.0
    )

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
}
