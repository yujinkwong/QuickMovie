package com.example.quickmovie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class FoodActivity : ComponentActivity() {

    // Prices for items
    private val popcornPrice = 5.00
    private val drinkPrice = 2.50

    // Declare variables for quantities and total prices
    private var popcornQuantity = 0
    private var drinkQuantity = 0
    private var foodTotalPrice = 0.00

    // Declare views
    private lateinit var popcornQuantityTextView: TextView
    private lateinit var drinkQuantityTextView: TextView
    private lateinit var totalPriceButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food) // Ensure food.xml layout is used

        // Get selected data from the intent
        val selectedMovie = intent.getStringExtra("SELECTED_MOVIE") ?: "Unknown Movie"
        val selectedLocation = intent.getStringExtra("SELECTED_LOCATION") ?: "Unknown Location"
        val selectedTime = intent.getStringExtra("SELECTED_TIME") ?: "Unknown Time"
        val selectedSeats = intent.getStringExtra("SELECTED_SEATS") ?: ""
        val seatTotalPrice = intent.getDoubleExtra("SEAT_TOTAL_PRICE", 0.0)

        // Initialize views
        popcornQuantityTextView = findViewById(R.id.tv_quantity_popcorn)
        drinkQuantityTextView = findViewById(R.id.tv_quantity_drink)
        totalPriceButton = findViewById(R.id.tv_total_price)

        // Update total price button initially
        updateTotalPrice(seatTotalPrice)

        // Set click listeners for popcorn buttons
        findViewById<Button>(R.id.btn_add_popcorn).setOnClickListener {
            updateQuantity(isAdding = true, item = "popcorn")
            updateTotalPrice(seatTotalPrice)
        }
        findViewById<Button>(R.id.btn_minus_popcorn).setOnClickListener {
            updateQuantity(isAdding = false, item = "popcorn")
            updateTotalPrice(seatTotalPrice)
        }

        // Set click listeners for drink buttons
        findViewById<Button>(R.id.btn_add_drink).setOnClickListener {
            updateQuantity(isAdding = true, item = "drink")
            updateTotalPrice(seatTotalPrice)
        }
        findViewById<Button>(R.id.btn_minus_drink).setOnClickListener {
            updateQuantity(isAdding = false, item = "drink")
            updateTotalPrice(seatTotalPrice)
        }

        // Set up total price button to navigate to PaymentActivity
        totalPriceButton.setOnClickListener {
            val finalTotalPrice = seatTotalPrice + foodTotalPrice
            val intent = Intent(this, PaymentActivity::class.java).apply {
                // Pass all necessary data to PaymentActivity
                putExtra("SELECTED_MOVIE", selectedMovie)
                putExtra("SELECTED_LOCATION", selectedLocation)
                putExtra("SELECTED_TIME", selectedTime)
                putExtra("SELECTED_SEATS", selectedSeats)
                putExtra("FINAL_TOTAL_PRICE", finalTotalPrice)
            }
            startActivity(intent)
        }
    }

    // Method to update quantity and total price
    private fun updateQuantity(isAdding: Boolean, item: String) {
        when (item) {
            "popcorn" -> {
                if (isAdding) popcornQuantity++ else if (popcornQuantity > 0) popcornQuantity--
                popcornQuantityTextView.text = popcornQuantity.toString()
            }
            "drink" -> {
                if (isAdding) drinkQuantity++ else if (drinkQuantity > 0) drinkQuantity--
                drinkQuantityTextView.text = drinkQuantity.toString()
            }
        }
        foodTotalPrice = (popcornQuantity * popcornPrice) + (drinkQuantity * drinkPrice)
    }

    // Method to update the total price displayed on the button
    private fun updateTotalPrice(seatTotalPrice: Double) {
        val finalPrice = seatTotalPrice + foodTotalPrice
        totalPriceButton.text = "Total Price: RM %.2f".format(finalPrice)
    }
}
