package com.vhuphara.techsupport

import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RewardsReviewsActivity : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var reviewInput: android.widget.EditText
    private lateinit var submitButton: Button
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_rewards_reviews)

        ratingBar = findViewById(R.id.ratingBar)
        reviewInput = findViewById(R.id.reviewInput)
        submitButton = findViewById(R.id.submitReviewButton)
        backButton = findViewById(R.id.backButton)

        // Submit review
        submitButton.setOnClickListener {

            val rating = ratingBar.rating
            val review = reviewInput.text.toString().trim()

            if (rating == 0f) {
                Toast.makeText(
                    this,
                    "Please select a rating",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (review.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter a review",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            Toast.makeText(
                this,
                "Review submitted successfully",
                Toast.LENGTH_SHORT
            ).show()

            reviewInput.text.clear()
            ratingBar.rating = 0f
        }

        // Back button
        backButton.setOnClickListener {
            finish()
        }
    }
}