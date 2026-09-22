package com.vhuphara.techsupport

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RewardsActivity : AppCompatActivity() {

    private lateinit var reviewInput: EditText
    private lateinit var submitReviewButton: Button
    private lateinit var backButton: Button
    private lateinit var pointsText: TextView

    private var selectedRating = 0
    private var customerId = 0

    // Current test booking.
    // This can later be replaced with a selected booking ID.
    private val bookingId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_rewards)

        pointsText = findViewById(R.id.pointsText)
        reviewInput = findViewById(R.id.reviewInput)
        submitReviewButton = findViewById(R.id.submitReviewButton)
        backButton = findViewById(R.id.backButton)

        // Get the logged-in customer's ID
        val sharedPreferences =
            getSharedPreferences("VhupharaTechSupport", MODE_PRIVATE)

        customerId = sharedPreferences.getInt("user_id", 0)

        if (customerId == 0) {

            Toast.makeText(
                this,
                "Please log in again.",
                Toast.LENGTH_LONG
            ).show()

            finish()
            return
        }

        // Display current reward points.
        // This is currently a prototype value.
        pointsText.text = "Reward Points: 100"

        // Star 1
        findViewById<TextView>(R.id.star1).setOnClickListener {
            selectedRating = 1
            updateStars()
        }

        // Star 2
        findViewById<TextView>(R.id.star2).setOnClickListener {
            selectedRating = 2
            updateStars()
        }

        // Star 3
        findViewById<TextView>(R.id.star3).setOnClickListener {
            selectedRating = 3
            updateStars()
        }

        // Star 4
        findViewById<TextView>(R.id.star4).setOnClickListener {
            selectedRating = 4
            updateStars()
        }

        // Star 5
        findViewById<TextView>(R.id.star5).setOnClickListener {
            selectedRating = 5
            updateStars()
        }

        // Submit review
        submitReviewButton.setOnClickListener {
            submitReview()
        }

        // Back
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun updateStars() {

        val stars = listOf(
            findViewById<TextView>(R.id.star1),
            findViewById<TextView>(R.id.star2),
            findViewById<TextView>(R.id.star3),
            findViewById<TextView>(R.id.star4),
            findViewById<TextView>(R.id.star5)
        )

        stars.forEachIndexed { index, star ->

            if (index < selectedRating) {
                star.text = "★"
            } else {
                star.text = "☆"
            }
        }
    }

    private fun submitReview() {

        // Check rating
        if (selectedRating == 0) {

            Toast.makeText(
                this,
                "Please select a rating",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        // Get review text
        val comment = reviewInput.text.toString().trim()

        // Check review text
        if (comment.isEmpty()) {

            Toast.makeText(
                this,
                "Please write a review",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        // Check customer session
        if (customerId == 0) {

            Toast.makeText(
                this,
                "User session not found. Please log in again.",
                Toast.LENGTH_LONG
            ).show()

            return
        }

        // Create review request
        val reviewRequest = ReviewRequest(
            customer_id = customerId,
            booking_id = bookingId,
            rating = selectedRating,
            comment = comment
        )

        // Prevent duplicate submissions while request is running
        submitReviewButton.isEnabled = false

        // Send review to REST API
        RetrofitClient.apiService
            .submitReview(reviewRequest)
            .enqueue(object : Callback<ReviewResponse> {

                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {

                    submitReviewButton.isEnabled = true

                    if (
                        response.isSuccessful &&
                        response.body()?.success == true
                    ) {

                        Toast.makeText(
                            this@RewardsActivity,
                            "Review submitted successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Clear review field
                        reviewInput.text.clear()

                        // Reset rating
                        selectedRating = 0
                        updateStars()

                    } else {

                        Toast.makeText(
                            this@RewardsActivity,
                            "Failed to submit review",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<ReviewResponse>,
                    t: Throwable
                ) {

                    submitReviewButton.isEnabled = true

                    Toast.makeText(
                        this@RewardsActivity,
                        "Connection error: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}