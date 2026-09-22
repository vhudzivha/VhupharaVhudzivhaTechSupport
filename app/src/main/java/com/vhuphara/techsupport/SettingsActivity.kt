package com.vhuphara.techsupport

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {

    private lateinit var switchNotifications: Switch
    private lateinit var switchDarkMode: Switch
    private lateinit var btnSaveSettings: Button
    private lateinit var btnBack: Button

    private val preferencesName = "VhupharaTechSupportSettings"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        // Connect XML components
        switchNotifications = findViewById(R.id.switchNotifications)
        switchDarkMode = findViewById(R.id.switchDarkMode)
        btnSaveSettings = findViewById(R.id.btnSaveSettings)
        btnBack = findViewById(R.id.btnBack)

        // Load saved settings
        loadSettings()

        // Save settings
        btnSaveSettings.setOnClickListener {

            saveSettings()

            Toast.makeText(
                this,
                "Settings saved successfully",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Back button
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadSettings() {

        val preferences = getSharedPreferences(
            preferencesName,
            MODE_PRIVATE
        )

        val notificationsEnabled =
            preferences.getBoolean(
                "notifications_enabled",
                true
            )

        val darkModeEnabled =
            preferences.getBoolean(
                "dark_mode_enabled",
                false
            )

        // Restore switch states
        switchNotifications.isChecked = notificationsEnabled
        switchDarkMode.isChecked = darkModeEnabled

        // Apply saved appearance
        applyDarkMode(darkModeEnabled)
    }

    private fun saveSettings() {

        val notificationsEnabled =
            switchNotifications.isChecked

        val darkModeEnabled =
            switchDarkMode.isChecked

        val preferences = getSharedPreferences(
            preferencesName,
            MODE_PRIVATE
        )

        preferences.edit()
            .putBoolean(
                "notifications_enabled",
                notificationsEnabled
            )
            .putBoolean(
                "dark_mode_enabled",
                darkModeEnabled
            )
            .apply()

        // Apply selected appearance
        applyDarkMode(darkModeEnabled)
    }

    private fun applyDarkMode(enabled: Boolean) {

        if (enabled) {

            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )

        } else {

            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }
}