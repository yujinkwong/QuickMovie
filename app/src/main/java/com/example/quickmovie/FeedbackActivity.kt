package com.example.quickmovie

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class FeedbackActivity : ComponentActivity() {

    private lateinit var feedbackInput: EditText
    private lateinit var submitFeedbackButton: Button
    private val database = FirebaseDatabase.getInstance("https://quickmovie-490e5-default-rtdb.asia-southeast1.firebasedatabase.app/")
        .getReference("feedback")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feedback)

        feedbackInput = findViewById(R.id.feedbackInput)
        submitFeedbackButton = findViewById(R.id.submitFeedbackButton)

        submitFeedbackButton.setOnClickListener {
            val feedbackText = feedbackInput.text.toString().trim()
            if (feedbackText.isNotEmpty()) {
                saveFeedbackToDatabase(feedbackText)
            } else {
                Toast.makeText(this, "Please enter your feedback", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveFeedbackToDatabase(feedback: String) {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null) {
            val userEmail = user.email ?: "Anonymous"
            val timestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).format(Date())

            // Generate a unique key for each feedback
            val feedbackId = database.push().key ?: UUID.randomUUID().toString()

            val feedbackData = mapOf(
                "userEmail" to userEmail,
                "comment" to feedback,
                "timestamp" to timestamp
            )

            database.child(feedbackId).setValue(feedbackData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show()
                    feedbackInput.text.clear()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to submit feedback: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }
}
