package com.vhuphara.techsupport

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var cardBookService: LinearLayout
    private lateinit var cardServices: LinearLayout
    private lateinit var cardTrackService: LinearLayout
    private lateinit var cardNotifications: LinearLayout
    private lateinit var cardMessages: LinearLayout
    private lateinit var cardRewards: LinearLayout
    private lateinit var cardSettings: LinearLayout
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // ==========================================
        // CONNECT HOME SCREEN CARDS
        // ==========================================

        cardBookService = findViewById(R.id.cardBookService)
        cardServices = findViewById(R.id.cardServices)
        cardTrackService = findViewById(R.id.cardTrackService)
        cardNotifications = findViewById(R.id.cardNotifications)
        cardMessages = findViewById(R.id.cardMessages)
        cardRewards = findViewById(R.id.cardRewards)
        cardSettings = findViewById(R.id.cardSettings)
        btnLogout = findViewById(R.id.btnLogout)


        // ==========================================
        // BOOK A SERVICE
        // ==========================================

        cardBookService.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    BookServiceActivity::class.java
                )
            )
        }


        // ==========================================
        // IT SERVICES
        // ==========================================

        cardServices.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ServicesActivity::class.java
                )
            )
        }


        // ==========================================
        // TRACK MY SERVICE
        // ==========================================

        cardTrackService.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    TrackServiceActivity::class.java
                )
            )
        }


        // ==========================================
        // NOTIFICATIONS
        // ==========================================

        cardNotifications.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    NotificationsActivity::class.java
                )
            )
        }


        // ==========================================
        // MESSAGES
        // ==========================================

        cardMessages.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    MessagesActivity::class.java
                )
            )
        }


        // ==========================================
        // REWARDS & REVIEWS
        // ==========================================

        cardRewards.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RewardsReviewsActivity::class.java
                )
            )
        }


        // ==========================================
        // SETTINGS
        // ==========================================

        cardSettings.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    SettingsActivity::class.java
                )
            )
        }


        // ---------------------------------------------------------
// LOGOUT
// ---------------------------------------------------------
        btnLogout.setOnClickListener {

            // Clear saved login information
            val sharedPreferences =
                getSharedPreferences(
                    "VhupharaTechSupport",
                    MODE_PRIVATE
                )

            sharedPreferences.edit()
                .clear()
                .apply()

            Toast.makeText(
                this,
                "Logged out successfully",
                Toast.LENGTH_SHORT
            ).show()

            val intent = Intent(
                this,
                LoginActivity::class.java
            )

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

            finish()
        }
    }
}