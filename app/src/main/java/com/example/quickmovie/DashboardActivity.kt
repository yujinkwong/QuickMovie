package com.example.quickmovie

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.viewpager2.widget.ViewPager2

class DashboardActivity : ComponentActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ImageCarouselAdapter
    private val handler = Handler(Looper.getMainLooper())
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the content view to the layout file
        setContentView(R.layout.dashboard)

        // Initialize ViewPager2
        viewPager = findViewById(R.id.viewPager)

        // Sample images (replace with your drawable resource IDs)
        val images = listOf(
            R.drawable.sonic3,
            R.drawable.venomposter,
            R.drawable.wicked
        )

        // Set up the adapter
        adapter = ImageCarouselAdapter(images)
        viewPager.adapter = adapter

        // Start auto-scroll
        startAutoScroll(images.size)
    }

    private fun startAutoScroll(totalItems: Int) {
        val runnable = object : Runnable {
            override fun run() {
                if (currentPosition == totalItems) {
                    currentPosition = 0
                }
                viewPager.setCurrentItem(currentPosition++, true)
                handler.postDelayed(this, 3000) // Change slides every 3 seconds
            }
        }

        handler.post(runnable)

        // Optional: Pause auto-scroll when the user interacts with the ViewPager
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPosition = position
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove any pending callbacks to avoid memory leaks
        handler.removeCallbacksAndMessages(null)
    }
}