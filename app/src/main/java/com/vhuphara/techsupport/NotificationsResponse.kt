package com.vhuphara.techsupport

data class NotificationsResponse(
    val success: Boolean,
    val message: String,
    val count: Int,
    val notifications: List<Notification>
)

data class Notification(
    val notification_id: Int,
    val user_id: Int,
    val title: String,
    val message: String,
    val type: String,
    val read_status: Boolean,
    val created_at: String
)