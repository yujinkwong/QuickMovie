package com.example.quickmovie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.ComponentActivity

class AboutUsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aboutus) // Loads the aboutus.xml layout

        // Add click listener for the menu button
        findViewById<ImageButton>(R.id.menubaraboutus).setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish() // Close AboutUsActivity to avoid stacking multiple activities
        }
    }

    // Method to open Facebook page when Facebook button is clicked
    fun openFacebook(view: View) {
        val facebookUrl = "https://www.facebook.com/profile.php?id=61571831437481"  // Replace with your Facebook page URL
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl))
        startActivity(intent)
    }


}
