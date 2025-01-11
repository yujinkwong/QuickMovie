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

    // Declare the variables for quantity and total price
    private var popcornQuantity = 0
    private var drinkQuantity = 0
    private var totalPrice = 0.00

    // Declare the views
    private lateinit var popcornQuantityTextView: TextView
    private lateinit var drinkQuantityTextView: TextView
    private lateinit var totalPriceTextView: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food) // Ensure food.xml layout is used

        // Initialize the views
        popcornQuantityTextView = findViewById(R.id.tv_quantity_popcorn)
        drinkQuantityTextView = findViewById(R.id.tv_quantity_drink)
        totalPriceTextView = findViewById(R.id.tv_total_price)

        // Set click listeners for Popcorn buttons
        findViewById<Button>(R.id.btn_add_popcorn).setOnClickListener {
            updateQuantity(true, "popcorn")
        }
        findViewById<Button>(R.id.btn_minus_popcorn).setOnClickListener {
            updateQuantity(false, "popcorn")
        }

        // Set click listeners for Drink buttons
        findViewById<Button>(R.id.btn_add_drink).setOnClickListener {
            updateQuantity(true, "drink")
        }
        findViewById<Button>(R.id.btn_minus_drink).setOnClickListener {
            updateQuantity(false, "drink")
        }

        // Set Total Price button functionality to navigate to PaymentActivity
        totalPriceTextView.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("TOTAL_PRICE", totalPrice) // Pass total price to PaymentActivity
            startActivity(intent)
        }
    }

    // Method to update quantity and total price
    private fun updateQuantity(isAdding: Boolean, item: String) {
        when (item) {
            "popcorn" -> {
                if (isAdding) {
                    popcornQuantity++
                } else if (popcornQuantity > 0) {
                    popcornQuantity--
                }
                popcornQuantityTextView.text = popcornQuantity.toString()
            }
            "drink" -> {
                if (isAdding) {
                    drinkQuantity++
                } else if (drinkQuantity > 0) {
                    drinkQuantity--
                }
                drinkQuantityTextView.text = drinkQuantity.toString()
            }
        }

        // Recalculate total price
        totalPrice = (popcornQuantity * popcornPrice) + (drinkQuantity * drinkPrice)
        totalPriceTextView.text = "Total Price: RM %.2f".format(totalPrice)
    }
}
