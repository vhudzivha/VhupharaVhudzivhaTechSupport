package com.vhuphara.techsupport

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var btnCreateAccount: MaterialButton
    private lateinit var tvBack: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        // ==========================================
        // CONNECT XML FIELDS
        // ==========================================

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnCreateAccount = findViewById(R.id.btnCreateAccount)
        tvBack = findViewById(R.id.tvBack)


        // ==========================================
        // LOGIN BUTTON
        // ==========================================

        btnLogin.setOnClickListener {

            val email = etEmail.text?.toString()?.trim() ?: ""
            val password = etPassword.text?.toString() ?: ""


            // ==========================================
            // VALIDATE EMAIL
            // ==========================================

            if (email.isEmpty()) {

                etEmail.error = "Enter your email address"
                etEmail.requestFocus()

                return@setOnClickListener
            }


            // ==========================================
            // VALIDATE PASSWORD
            // ==========================================

            if (password.isEmpty()) {

                etPassword.error = "Enter your password"
                etPassword.requestFocus()

                return@setOnClickListener
            }


            // ==========================================
            // DISABLE BUTTON WHILE LOGGING IN
            // ==========================================

            btnLogin.isEnabled = false
            btnLogin.text = "LOGGING IN..."


            // ==========================================
            // CREATE LOGIN REQUEST
            // ==========================================

            val loginRequest = LoginRequest(
                email = email,
                password = password
            )


            // ==========================================
            // SEND LOGIN REQUEST TO REST API
            // ==========================================

            RetrofitClient.apiService
                .login(loginRequest)
                .enqueue(object : Callback<LoginResponse> {


                    // ==========================================
                    // SERVER RESPONSE
                    // ==========================================

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {

                        // Enable button again
                        btnLogin.isEnabled = true
                        btnLogin.text = "LOGIN"


                        // ==========================================
                        // SUCCESSFUL HTTP RESPONSE
                        // ==========================================

                        if (response.isSuccessful) {

                            val loginResponse = response.body()


                            // ==========================================
                            // CHECK API LOGIN SUCCESS
                            // ==========================================

                            if (
                                loginResponse != null &&
                                loginResponse.success
                            ) {

                                // ==========================================
                                // SAVE LOGIN INFORMATION
                                // ==========================================

                                val sharedPreferences =
                                    getSharedPreferences(
                                        "VhupharaTechSupport",
                                        MODE_PRIVATE
                                    )


                                sharedPreferences.edit()
                                    .putString(
                                        "auth_token",
                                        loginResponse.token ?: ""
                                    )
                                    .putInt(
                                        "user_id",
                                        loginResponse.user?.user_id ?: 0
                                    )
                                    .putString(
                                        "user_name",
                                        loginResponse.user?.full_name ?: ""
                                    )
                                    .putString(
                                        "user_email",
                                        loginResponse.user?.email ?: email
                                    )
                                    .putString(
                                        "user_phone",
                                        loginResponse.user?.phone_number ?: ""
                                    )
                                    .putString(
                                        "user_address",
                                        loginResponse.user?.address ?: ""
                                    )
                                    .apply()


                                // ==========================================
                                // LOGIN SUCCESS MESSAGE
                                // ==========================================

                                Toast.makeText(
                                    this@LoginActivity,
                                    "Login successful!",
                                    Toast.LENGTH_SHORT
                                ).show()


                                // ==========================================
                                // OPEN MAIN DASHBOARD
                                // ==========================================

                                val intent = Intent(
                                    this@LoginActivity,
                                    MainActivity::class.java
                                )


                                // Prevent returning to login screen
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK


                                startActivity(intent)

                                finish()


                            } else {

                                // ==========================================
                                // API LOGIN FAILED
                                // ==========================================

                                Toast.makeText(
                                    this@LoginActivity,
                                    loginResponse?.message
                                        ?: "Invalid email or password",
                                    Toast.LENGTH_LONG
                                ).show()
                            }


                        } else {

                            // ==========================================
                            // HTTP ERROR
                            // ==========================================

                            val errorMessage =
                                response.errorBody()
                                    ?.string()
                                    ?.takeIf { it.isNotBlank() }
                                    ?: "HTTP ${response.code()}"


                            Toast.makeText(
                                this@LoginActivity,
                                "Login failed: $errorMessage",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }


                    // ==========================================
                    // NETWORK ERROR
                    // ==========================================

                    override fun onFailure(
                        call: Call<LoginResponse>,
                        t: Throwable
                    ) {

                        btnLogin.isEnabled = true
                        btnLogin.text = "LOGIN"


                        Toast.makeText(
                            this@LoginActivity,
                            "Unable to connect to the server: ${t.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }


        // ==========================================
        // CREATE ACCOUNT BUTTON
        // ==========================================

        btnCreateAccount.setOnClickListener {

            val intent = Intent(
                this,
                RegisterActivity::class.java
            )

            startActivity(intent)
        }


        // ==========================================
        // BACK BUTTON
        // ==========================================

        tvBack.setOnClickListener {

            finish()
        }
    }
}