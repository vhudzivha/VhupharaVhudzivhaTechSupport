package com.vhuphara.techsupport

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BookingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Open the API-connected booking screen
        val intent = Intent(this, BookServiceActivity::class.java)
        startActivity(intent)

        // Close this activity so the user does not return to the old booking screen
        finish()
    }
}