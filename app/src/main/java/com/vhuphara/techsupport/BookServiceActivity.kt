package com.vhuphara.techsupport

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Locale

class BookServiceActivity : AppCompatActivity() {

    private lateinit var spinnerService: Spinner
    private lateinit var etProblem: EditText
    private lateinit var etBookingDate: EditText
    private lateinit var etBookingTime: EditText
    private lateinit var etAddress: EditText
    private lateinit var btnSubmitBooking: MaterialButton
    private lateinit var tvBack: TextView

    // Store the actual services returned by the API
    private var serviceList: List<Service> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_book_service)

        // =========================================================
        // CONNECT XML FIELDS
        // =========================================================

        spinnerService = findViewById(R.id.spinnerService)
        etProblem = findViewById(R.id.etProblem)
        etBookingDate = findViewById(R.id.etDate)
        etBookingTime = findViewById(R.id.etTime)
        etAddress = findViewById(R.id.etAddress)
        btnSubmitBooking = findViewById(R.id.btnSubmitBooking)
        tvBack = findViewById(R.id.tvBack)

        // =========================================================
        // BACK BUTTON
        // =========================================================

        tvBack.setOnClickListener {
            finish()
        }

        // =========================================================
        // LOAD LOGGED-IN USER INFORMATION
        // =========================================================

        loadUserInformation()

        // =========================================================
        // LOAD SERVICES FROM REST API
        // =========================================================

        loadServices()

        // =========================================================
        // DATE PICKER
        // =========================================================

        etBookingDate.setOnClickListener {

            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->

                    val date = String.format(
                        Locale.US,
                        "%04d-%02d-%02d",
                        selectedYear,
                        selectedMonth + 1,
                        selectedDay
                    )

                    etBookingDate.setText(date)
                },
                year,
                month,
                day
            ).show()
        }

        // =========================================================
        // TIME PICKER
        // =========================================================

        etBookingTime.setOnClickListener {

            val calendar = Calendar.getInstance()

            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->

                    val time = String.format(
                        Locale.US,
                        "%02d:%02d",
                        selectedHour,
                        selectedMinute
                    )

                    etBookingTime.setText(time)
                },
                hour,
                minute,
                true
            ).show()
        }

        // =========================================================
        // SUBMIT BOOKING
        // =========================================================

        btnSubmitBooking.setOnClickListener {
            submitBooking()
        }
    }

    // =============================================================
    // LOAD USER INFORMATION
    // =============================================================

    private fun loadUserInformation() {

        val sharedPreferences = getSharedPreferences(
            "VhupharaTechSupport",
            MODE_PRIVATE
        )

        val userId = sharedPreferences.getInt(
            "user_id",
            0
        )

        val userAddress = sharedPreferences.getString(
            "user_address",
            ""
        ) ?: ""

        // If the logged-in user has an address,
        // automatically place it into the address field.
        if (userAddress.isNotEmpty()) {
            etAddress.setText(userAddress)
        }

        // Make sure a valid user is logged in.
        if (userId == 0) {

            Toast.makeText(
                this,
                "Please log in again.",
                Toast.LENGTH_LONG
            ).show()

            finish()
        }
    }

    // =============================================================
    // LOAD SERVICES FROM REST API
    // =============================================================

    private fun loadServices() {

        btnSubmitBooking.isEnabled = false

        RetrofitClient.apiService
            .getServices()
            .enqueue(object : Callback<ServicesResponse> {

                override fun onResponse(
                    call: Call<ServicesResponse>,
                    response: Response<ServicesResponse>
                ) {

                    if (
                        response.isSuccessful &&
                        response.body()?.success == true
                    ) {

                        serviceList =
                            response.body()?.services ?: emptyList()

                        if (serviceList.isEmpty()) {

                            Toast.makeText(
                                this@BookServiceActivity,
                                "No services are currently available.",
                                Toast.LENGTH_LONG
                            ).show()

                            return
                        }

                        setupServiceSpinner()

                        btnSubmitBooking.isEnabled = true

                    } else {

                        Toast.makeText(
                            this@BookServiceActivity,
                            "Could not load services.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<ServicesResponse>,
                    t: Throwable
                ) {

                    Toast.makeText(
                        this@BookServiceActivity,
                        "Connection error: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    // =============================================================
    // SET UP SERVICE SPINNER
    // =============================================================

    private fun setupServiceSpinner() {

        val serviceNames = mutableListOf<String>()

        // First item
        serviceNames.add("Select a service")

        // Add services returned by API
        for (service in serviceList) {

            serviceNames.add(
                service.service_name
            )
        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            serviceNames
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinnerService.adapter = adapter
    }

    // =============================================================
    // SUBMIT BOOKING
    // =============================================================

    private fun submitBooking() {

        // ---------------------------------------------------------
        // GET LOGGED-IN USER ID
        // ---------------------------------------------------------

        val sharedPreferences = getSharedPreferences(
            "VhupharaTechSupport",
            MODE_PRIVATE
        )

        val customerId = sharedPreferences.getInt(
            "user_id",
            0
        )

        // ---------------------------------------------------------
        // CHECK USER LOGIN
        // ---------------------------------------------------------

        if (customerId == 0) {

            Toast.makeText(
                this,
                "Your session has expired. Please log in again.",
                Toast.LENGTH_LONG
            ).show()

            return
        }

        // ---------------------------------------------------------
        // GET FORM VALUES
        // ---------------------------------------------------------

        val servicePosition =
            spinnerService.selectedItemPosition

        val problem =
            etProblem.text.toString().trim()

        val date =
            etBookingDate.text.toString().trim()

        val time =
            etBookingTime.text.toString().trim()

        val address =
            etAddress.text.toString().trim()

        // ---------------------------------------------------------
        // VALIDATE SERVICE
        // ---------------------------------------------------------

        if (
            servicePosition <= 0 ||
            servicePosition > serviceList.size
        ) {

            Toast.makeText(
                this,
                "Please select a service.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        // ---------------------------------------------------------
        // VALIDATE PROBLEM
        // ---------------------------------------------------------

        if (problem.isEmpty()) {

            etProblem.error =
                "Describe the IT problem or service you need"

            etProblem.requestFocus()

            return
        }

        // ---------------------------------------------------------
        // VALIDATE DATE
        // ---------------------------------------------------------

        if (date.isEmpty()) {

            etBookingDate.error =
                "Select a booking date"

            etBookingDate.requestFocus()

            return
        }

        // ---------------------------------------------------------
        // VALIDATE TIME
        // ---------------------------------------------------------

        if (time.isEmpty()) {

            etBookingTime.error =
                "Select a booking time"

            etBookingTime.requestFocus()

            return
        }

        // ---------------------------------------------------------
        // VALIDATE ADDRESS
        // ---------------------------------------------------------

        if (address.isEmpty()) {

            etAddress.error =
                "Enter the service address"

            etAddress.requestFocus()

            return
        }

        // ---------------------------------------------------------
        // GET ACTUAL SERVICE FROM API LIST
        // ---------------------------------------------------------

        val selectedService =
            serviceList[servicePosition - 1]

        val serviceId =
            selectedService.service_id

        // ---------------------------------------------------------
        // CREATE BOOKING REQUEST
        // ---------------------------------------------------------

        val bookingRequest = BookingRequest(

            customer_id = customerId,

            service_id = serviceId,

            booking_date = date,

            booking_time = time,

            address = address,

            problem_description = problem
        )

        // ---------------------------------------------------------
        // DISABLE BUTTON
        // ---------------------------------------------------------

        btnSubmitBooking.isEnabled = false

        btnSubmitBooking.text = "SUBMITTING..."

        // ---------------------------------------------------------
        // SEND BOOKING TO REST API
        // ---------------------------------------------------------

        RetrofitClient.apiService
            .createBooking(bookingRequest)
            .enqueue(object : Callback<BookingResponse> {

                override fun onResponse(
                    call: Call<BookingResponse>,
                    response: Response<BookingResponse>
                ) {

                    btnSubmitBooking.isEnabled = true

                    btnSubmitBooking.text =
                        "SUBMIT BOOKING"

                    if (
                        response.isSuccessful &&
                        response.body()?.success == true
                    ) {

                        Toast.makeText(
                            this@BookServiceActivity,
                            "Booking created successfully!",
                            Toast.LENGTH_LONG
                        ).show()

                        // Return to dashboard
                        finish()

                    } else {

                        val errorMessage =
                            response.errorBody()
                                ?.string()
                                ?: response.body()?.message
                                ?: "Booking could not be created."

                        Toast.makeText(
                            this@BookServiceActivity,
                            errorMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<BookingResponse>,
                    t: Throwable
                ) {

                    btnSubmitBooking.isEnabled = true

                    btnSubmitBooking.text =
                        "SUBMIT BOOKING"

                    Toast.makeText(
                        this@BookServiceActivity,
                        "Connection error: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}