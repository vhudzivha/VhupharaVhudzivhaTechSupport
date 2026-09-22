package com.vhuphara.techsupport

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var etRegisterFullName: EditText
    private lateinit var etRegisterEmail: EditText
    private lateinit var etRegisterPhone: EditText
    private lateinit var etRegisterAddress: EditText
    private lateinit var etRegisterPassword: EditText
    private lateinit var etRegisterConfirmPassword: EditText

    private lateinit var btnRegister: Button
    private lateinit var btnBackToLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Connect XML fields
        etRegisterFullName = findViewById(R.id.etRegisterFullName)
        etRegisterEmail = findViewById(R.id.etRegisterEmail)
        etRegisterPhone = findViewById(R.id.etRegisterPhone)
        etRegisterAddress = findViewById(R.id.etRegisterAddress)
        etRegisterPassword = findViewById(R.id.etRegisterPassword)
        etRegisterConfirmPassword = findViewById(R.id.etRegisterConfirmPassword)

        btnRegister = findViewById(R.id.btnRegister)
        btnBackToLogin = findViewById(R.id.btnBackToLogin)

        // Create account
        btnRegister.setOnClickListener {
            registerUser()
        }

        // Back to login
        btnBackToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerUser() {

        val fullName = etRegisterFullName.text.toString().trim()
        val email = etRegisterEmail.text.toString().trim()
        val phone = etRegisterPhone.text.toString().trim()
        val address = etRegisterAddress.text.toString().trim()
        val password = etRegisterPassword.text.toString()
        val confirmPassword = etRegisterConfirmPassword.text.toString()

        // Check empty fields
        if (fullName.isEmpty() ||
            email.isEmpty() ||
            phone.isEmpty() ||
            address.isEmpty() ||
            password.isEmpty() ||
            confirmPassword.isEmpty()
        ) {
            Toast.makeText(
                this,
                "Please complete all fields",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Check password length
        if (password.length < 6) {
            Toast.makeText(
                this,
                "Password must contain at least 6 characters",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Check passwords
        if (password != confirmPassword) {
            Toast.makeText(
                this,
                "Passwords do not match",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Save account temporarily on the device
        val preferences = getSharedPreferences(
            "VhupharaUser",
            MODE_PRIVATE
        )

        preferences.edit()
            .putString("fullName", fullName)
            .putString("email", email)
            .putString("phone", phone)
            .putString("address", address)
            .putString("password", password)
            .apply()

        Toast.makeText(
            this,
            "Account created successfully",
            Toast.LENGTH_SHORT
        ).show()

        // Go to Login screen
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("registeredEmail", email)
        startActivity(intent)
        finish()
    }
}