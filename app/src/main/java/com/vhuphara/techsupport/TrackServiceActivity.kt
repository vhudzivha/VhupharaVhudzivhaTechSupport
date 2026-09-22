package com.vhuphara.techsupport

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrackServiceActivity : AppCompatActivity() {

    private lateinit var etBookingId: EditText
    private lateinit var btnCheckStatus: Button

    private lateinit var tvBookingId: TextView
    private lateinit var tvService: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvProblem: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvBack: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_track_service)

        etBookingId = findViewById(R.id.etBookingId)
        btnCheckStatus = findViewById(R.id.btnCheckStatus)

        tvBookingId = findViewById(R.id.tvBookingId)
        tvService = findViewById(R.id.tvService)
        tvDate = findViewById(R.id.tvDate)
        tvTime = findViewById(R.id.tvTime)
        tvAddress = findViewById(R.id.tvAddress)
        tvProblem = findViewById(R.id.tvProblem)
        tvStatus = findViewById(R.id.tvStatus)
        tvBack = findViewById(R.id.tvTrackBack)

        btnCheckStatus.setOnClickListener {

            val bookingIdText = etBookingId.text.toString().trim()

            if (bookingIdText.isEmpty()) {
                etBookingId.error = "Enter your booking ID"
                etBookingId.requestFocus()
                return@setOnClickListener
            }

            val bookingId = bookingIdText.toIntOrNull()

            if (bookingId == null || bookingId <= 0) {
                etBookingId.error = "Enter a valid booking ID"
                etBookingId.requestFocus()
                return@setOnClickListener
            }

            checkBookingStatus(bookingId)
        }

        tvBack.setOnClickListener {
            finish()
        }
    }

    private fun checkBookingStatus(bookingId: Int) {

        btnCheckStatus.isEnabled = false

        Toast.makeText(
            this,
            "Checking booking status...",
            Toast.LENGTH_SHORT
        ).show()

        RetrofitClient.apiService.getBookingStatus(bookingId)
            .enqueue(object : Callback<BookingStatusResponse> {

                override fun onResponse(
                    call: Call<BookingStatusResponse>,
                    response: Response<BookingStatusResponse>
                ) {

                    btnCheckStatus.isEnabled = true

                    if (response.isSuccessful && response.body()?.success == true) {

                        val booking = response.body()?.booking

                        if (booking != null) {

                            tvBookingId.text =
                                "Booking ID: #${booking.booking_id}"

                            tvService.text =
                                "Service: ${booking.service_name ?: "IT Service"}"

                            tvDate.text =
                                "Date: ${booking.booking_date}"

                            tvTime.text =
                                "Time: ${booking.booking_time}"

                            tvAddress.text =
                                "Address: ${booking.address}"

                            tvProblem.text =
                                "Problem: ${booking.problem_description}"

                            tvStatus.text =
                                "Status: ${booking.status}"

                        } else {

                            Toast.makeText(
                                this@TrackServiceActivity,
                                "Booking information not found.",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    } else {

                        Toast.makeText(
                            this@TrackServiceActivity,
                            "Booking not found.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<BookingStatusResponse>,
                    t: Throwable
                ) {

                    btnCheckStatus.isEnabled = true

                    Toast.makeText(
                        this@TrackServiceActivity,
                        "Connection error: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}