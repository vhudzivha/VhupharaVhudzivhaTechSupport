package com.vhuphara.techsupport

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsActivity : AppCompatActivity() {

    private lateinit var notificationsContainer: LinearLayout
    private lateinit var tvBack: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_notifications)

        notificationsContainer =
            findViewById(R.id.notificationsContainer)

        tvBack =
            findViewById(R.id.tvBack)

        // ==============================
        // BACK BUTTON
        // ==============================

        tvBack.setOnClickListener {
            finish()
        }

        // ==============================
        // GET LOGGED-IN USER ID
        // ==============================

        val sharedPreferences =
            getSharedPreferences(
                "VhupharaTechSupport",
                MODE_PRIVATE
            )

        val userId =
            sharedPreferences.getInt("user_id", 0)

        // Check that a user is logged in
        if (userId == 0) {

            Toast.makeText(
                this,
                "Please log in again.",
                Toast.LENGTH_LONG
            ).show()

            finish()

            return
        }

        // ==============================
        // LOAD NOTIFICATIONS
        // ==============================

        loadNotifications(userId)
    }

    private fun loadNotifications(userId: Int) {

        RetrofitClient.apiService
            .getNotifications(userId)
            .enqueue(object : Callback<NotificationsResponse> {

                override fun onResponse(
                    call: Call<NotificationsResponse>,
                    response: Response<NotificationsResponse>
                ) {

                    if (
                        response.isSuccessful &&
                        response.body() != null
                    ) {

                        val notificationResponse =
                            response.body()!!

                        displayNotifications(
                            notificationResponse.notifications
                        )

                    } else {

                        Toast.makeText(
                            this@NotificationsActivity,
                            "Failed to load notifications.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<NotificationsResponse>,
                    t: Throwable
                ) {

                    Toast.makeText(
                        this@NotificationsActivity,
                        "Connection error: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun displayNotifications(
        notifications: List<Notification>
    ) {

        notificationsContainer.removeAllViews()

        // ==============================
        // EMPTY STATE
        // ==============================

        if (notifications.isEmpty()) {

            val emptyMessage =
                TextView(this)

            emptyMessage.text =
                "No notifications yet."

            emptyMessage.textSize = 16f

            emptyMessage.setPadding(
                20,
                40,
                20,
                40
            )

            notificationsContainer.addView(
                emptyMessage
            )

            return
        }

        // ==============================
        // DISPLAY NOTIFICATIONS
        // ==============================

        for (notification in notifications) {

            val notificationView =
                TextView(this)

            notificationView.text =
                "${notification.title}\n\n" +
                        "${notification.message}\n\n" +
                        "Type: ${notification.type}\n" +
                        "Date: ${notification.created_at}"

            notificationView.textSize = 16f

            notificationView.setPadding(
                20,
                20,
                20,
                20
            )

            notificationsContainer.addView(
                notificationView
            )
        }
    }
}