package com.example.quickmovie

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.quickmovie.ui.theme.QuickMovieTheme
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class DashboardActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var dotsIndicator: DotsIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the layout for DashboardActivity using XML layout
        setContentView(R.layout.dashboard)

        // Initialize the ViewPager and DotsIndicator
        viewPager = findViewById(R.id.carouselViewPager)
        dotsIndicator = findViewById(R.id.dotsIndicator)

        // Array of image resources for the carousel
        val imageResources = intArrayOf(
            R.drawable.sonic3,  // Replace with actual image resource IDs
            R.drawable.sinchan,
            R.drawable.wicked
        )

        // Set the adapter for the ViewPager
        val imageAdapter = ImageAdapter(this, imageResources)
        viewPager.adapter = imageAdapter

        // Link the DotsIndicator to the ViewPager
        dotsIndicator.setViewPager(viewPager)

        // Set the content using Jetpack Compose (Optional, if you need Compose UI)
        setContent {
            QuickMovieTheme {
                // Your Compose UI content (optional)
                // Example: SignInScreenWithXML { signInWithGoogle() }
            }
        }
    }
}
