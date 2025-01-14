package com.example.quickmovie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MenuActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        // Navigate to DashboardActivity
        findViewById<Button>(R.id.menu_Home).setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish() // Close MenuActivity to prevent going back to it
        }

        // Navigate to ProfileActivity
        findViewById<Button>(R.id.menu_Profile).setOnClickListener {
            val intent = Intent(this, UserAccountActivity::class.java)
            startActivity(intent)
            finish() // Close MenuActivity to prevent going back to it
        }

        // Navigate to AboutUsActivity
        findViewById<Button>(R.id.menu_AboutUs).setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
            finish() // Close MenuActivity to prevent going back to it
        }

        // Navigate to FeedbackActivity
        findViewById<Button>(R.id.menu_Feedback).setOnClickListener {
            val intent = Intent(this, FeedbackActivity::class.java)
            startActivity(intent)
            finish() // Close MenuActivity to prevent going back to it
        }

        // Log out functionality
        findViewById<Button>(R.id.menu_LogOut).setOnClickListener {
            val googleSignInClient = GoogleSignIn.getClient(
                this,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
            )

            // Sign out from Firebase
            FirebaseAuth.getInstance().signOut()

            // Revoke access to allow account selection
            googleSignInClient.revokeAccess().addOnCompleteListener {
                // Navigate to login page
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }

        // Set up the Messenger button
        findViewById<ImageButton>(R.id.messenger).setOnClickListener {
            openFacebook(it)
        }
    }

    // Method to open the Facebook Messenger link
    fun openFacebook(view: android.view.View) {
        val facebookUrl = "https://www.facebook.com/messages/t/61571831437481"  // Replace with your correct link

        // Try opening Facebook Messenger app
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("fb://messaging/61571831437481"))
            startActivity(intent)
        } catch (e: Exception) {
            // If Messenger is not installed, open the link in a browser
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl))
            startActivity(intent)
        }
    }
}
