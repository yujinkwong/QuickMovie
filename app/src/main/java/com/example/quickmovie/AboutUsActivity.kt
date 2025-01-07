package com.example.quickmovie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class AboutUsActivity : AppCompatActivity() { // Change class name to AboutusActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aboutus)
    }

    // Method to open Facebook page when Facebook button is clicked
    fun openFacebook(view: View) {
        val facebookUrl = "https://www.facebook.com/profile.php?id=100007254340301"  // Replace with your Facebook page URL
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl))
        startActivity(intent)
    }

    // Method to open Instagram page when Instagram button is clicked
    fun openInstagram(view: View) {
        val instagramUrl = "https://www.instagram.com/choon_yu12/"  // Replace with your Instagram profile URL
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl))
        startActivity(intent)
    }
}
