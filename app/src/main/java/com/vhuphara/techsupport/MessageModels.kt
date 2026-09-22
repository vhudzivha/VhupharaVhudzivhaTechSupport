package com.vhuphara.techsupport

// Represents one message returned by the API
data class Message(
    val message_id: Int,
    val booking_id: Int,
    val sender_id: Int,
    val receiver_id: Int,
    val message: String,
    val sent_at: String,
    val read_status: Boolean
)

// Response returned when getting messages
data class MessagesResponse(
    val success: Boolean,
    val message: String,
    val count: Int,
    val messages: List<Message>
)

// Data sent when creating a new message
data class MessageRequest(
    val booking_id: Int,
    val sender_id: Int,
    val receiver_id: Int,
    val message: String
)

// Response returned after sending a message
data class MessageResponse(
    val success: Boolean,
    val message: String,
    val createdMessage: Message?
)