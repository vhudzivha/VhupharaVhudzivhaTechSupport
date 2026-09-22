package com.vhuphara.techsupport

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    // ==========================================
    // AUTHENTICATION
    // ==========================================

    // LOGIN
    @POST("api/auth/login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>


    // ==========================================
    // SERVICES
    // ==========================================

    // GET all available services
    @GET("api/services")
    fun getServices(): Call<ServicesResponse>


    // ==========================================
    // BOOKINGS
    // ==========================================

    // CREATE a new booking
    @POST("api/bookings")
    fun createBooking(
        @Body booking: BookingRequest
    ): Call<BookingResponse>


    // GET bookings belonging to a customer
    @GET("api/bookings/customer/{customerId}")
    fun getCustomerBookings(
        @Path("customerId") customerId: Int
    ): Call<CustomerBookingsResponse>


    // GET the status of a specific booking
    @GET("api/bookings/{bookingId}/status")
    fun getBookingStatus(
        @Path("bookingId") bookingId: Int
    ): Call<BookingStatusResponse>


    // UPDATE the status of a specific booking
    @PUT("api/bookings/{bookingId}/status")
    fun updateBookingStatus(
        @Path("bookingId") bookingId: Int,
        @Body status: StatusRequest
    ): Call<BookingStatusResponse>


    // ==========================================
    // NOTIFICATIONS
    // ==========================================

    // GET notifications belonging to a user
    @GET("api/notifications/{userId}")
    fun getNotifications(
        @Path("userId") userId: Int
    ): Call<NotificationsResponse>


    // ==========================================
    // MESSAGES
    // ==========================================

    // GET messages for a booking
    @GET("api/messages/booking/{bookingId}")
    fun getBookingMessages(
        @Path("bookingId") bookingId: Int
    ): Call<MessagesResponse>


    // SEND a new message
    @POST("api/messages")
    fun sendMessage(
        @Body message: MessageRequest
    ): Call<MessageResponse>


    // ==========================================
    // REVIEWS
    // ==========================================

    // GET reviews for a booking
    @GET("api/reviews/booking/{bookingId}")
    fun getBookingReviews(
        @Path("bookingId") bookingId: Int
    ): Call<ReviewsResponse>


    // SUBMIT a new review
    @POST("api/reviews")
    fun submitReview(
        @Body review: ReviewRequest
    ): Call<ReviewResponse>
}