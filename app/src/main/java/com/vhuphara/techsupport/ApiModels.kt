package com.vhuphara.techsupport


// =========================================================
// AUTHENTICATION MODELS
// =========================================================

// ---------------------------------------------------------
// Login request
// Data sent from Android to the REST API
// ---------------------------------------------------------

data class LoginRequest(
    val email: String,
    val password: String
)


// ---------------------------------------------------------
// Registration request
// Data sent when creating a new account
// ---------------------------------------------------------

data class RegisterRequest(
    val full_name: String,
    val email: String,
    val phone_number: String,
    val address: String,
    val password: String
)


// ---------------------------------------------------------
// Logged-in / authenticated user
// ---------------------------------------------------------

data class AuthUser(
    val user_id: Int,
    val full_name: String,
    val email: String,
    val phone_number: String?,
    val address: String?
)


// ---------------------------------------------------------
// Authentication response
// Used for registration/authentication responses
// ---------------------------------------------------------

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val token: String?,
    val user: AuthUser?
)


// ---------------------------------------------------------
// Login user
// Information returned by the login API
// ---------------------------------------------------------

data class User(
    val user_id: Int,
    val full_name: String,
    val email: String,
    val phone_number: String?,
    val address: String?
)


// ---------------------------------------------------------
// Login response
// Matches POST /api/auth/login
// ---------------------------------------------------------

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String?,
    val user: User?,
    val error: String?
)



// =========================================================
// SERVICE MODELS
// =========================================================

data class Service(
    val service_id: Int,
    val service_name: String,
    val category: String,
    val description: String,
    val price: Double,
    val duration: Int,
    val image_url: String?,
    val active_status: Boolean
)


data class ServicesResponse(
    val success: Boolean,
    val message: String,
    val count: Int,
    val services: List<Service>
)



// =========================================================
// BOOKING MODELS
// =========================================================

// Data sent when creating a booking

data class BookingRequest(
    val customer_id: Int,
    val service_id: Int,
    val booking_date: String,
    val booking_time: String,
    val address: String,
    val problem_description: String
)


// Booking information

data class Booking(
    val booking_id: Int,
    val customer_id: Int,
    val service_id: Int,
    val service_name: String?,
    val category: String?,
    val price: Double?,
    val booking_date: String,
    val booking_time: String,
    val address: String,
    val problem_description: String,
    val status: String,
    val created_at: String
)


// Response after creating a booking

data class BookingResponse(
    val success: Boolean,
    val message: String,
    val booking: Booking
)


// Customer booking list response

data class CustomerBookingsResponse(
    val success: Boolean,
    val message: String,
    val count: Int,
    val bookings: List<Booking>
)


// Booking status response

data class BookingStatusResponse(
    val success: Boolean,
    val message: String,
    val booking: Booking
)


// Request used to update booking status

data class StatusRequest(
    val status: String
)