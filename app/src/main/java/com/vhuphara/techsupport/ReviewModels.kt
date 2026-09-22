package com.vhuphara.techsupport

// Data sent to the API when submitting a review
data class ReviewRequest(
    val customer_id: Int,
    val booking_id: Int,
    val rating: Int,
    val comment: String
)

// A single review returned by the API
data class Review(
    val review_id: Int,
    val customer_id: Int,
    val booking_id: Int,
    val rating: Int,
    val comment: String,
    val created_at: String
)

// Response returned after submitting a review
data class ReviewResponse(
    val success: Boolean,
    val message: String,
    val review: Review?
)

// Response returned when retrieving reviews
data class ReviewsResponse(
    val success: Boolean,
    val message: String,
    val count: Int,
    val reviews: List<Review>
)