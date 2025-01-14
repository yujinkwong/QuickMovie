package com.example.quickmovie

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
                    loadBookingHistory(user)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@UserAccountActivity, "Failed to load user profile", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadBookingHistory(user: FirebaseUser) {
        val bookingHistoryRef = database.reference.child("bookingHistory").child(user.email?.replace(".", "_") ?: "unknown")
        bookingHistoryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                bookingHistoryContainer.removeAllViews()

                // Check if there are booking history records
                if (dataSnapshot.exists()) {
                    for (bookingSnapshot in dataSnapshot.children) {
                        val booking = bookingSnapshot.getValue(Booking::class.java)
                        booking?.let {
                            val bookingItem = TextView(this@UserAccountActivity)
                            bookingItem.text = "Movie: ${it.movieName}, Date: ${it.date}"
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
        val movieName: String = "",
        val date: String = ""
    )
}
