package com.vhuphara.techsupport

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class ServicesActivity : AppCompatActivity() {

    private lateinit var servicesContainer: LinearLayout
    private lateinit var tvLoading: TextView
    private lateinit var tvBack: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_services)

        // ==========================================
        // CONNECT XML VIEWS
        // ==========================================

        servicesContainer = findViewById(R.id.servicesContainer)
        tvLoading = findViewById(R.id.tvLoading)
        tvBack = findViewById(R.id.tvServicesBack)


        // ==========================================
        // BACK BUTTON
        // ==========================================

        tvBack.setOnClickListener {
            finish()
        }


        // ==========================================
        // LOAD SERVICES FROM REST API
        // ==========================================

        loadServices()
    }


    // ==========================================
    // LOAD SERVICES
    // ==========================================

    private fun loadServices() {

        tvLoading.visibility = View.VISIBLE
        tvLoading.text = "Loading IT services..."

        servicesContainer.removeAllViews()


        RetrofitClient.apiService
            .getServices()
            .enqueue(object : Callback<ServicesResponse> {

                override fun onResponse(
                    call: Call<ServicesResponse>,
                    response: Response<ServicesResponse>
                ) {

                    tvLoading.visibility = View.GONE


                    if (response.isSuccessful) {

                        val servicesResponse = response.body()


                        if (
                            servicesResponse != null &&
                            servicesResponse.success
                        ) {

                            val services =
                                servicesResponse.services


                            if (services.isEmpty()) {

                                showEmptyMessage()

                                return
                            }


                            displayServices(services)

                        } else {

                            Toast.makeText(
                                this@ServicesActivity,
                                servicesResponse?.message
                                    ?: "No services available.",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    } else {

                        val errorMessage =
                            response.errorBody()
                                ?.string()
                                ?: "Unable to load services."

                        Toast.makeText(
                            this@ServicesActivity,
                            "Server error: $errorMessage",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }


                override fun onFailure(
                    call: Call<ServicesResponse>,
                    t: Throwable
                ) {

                    tvLoading.visibility = View.GONE


                    Toast.makeText(
                        this@ServicesActivity,
                        "Unable to connect to the server.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }


    // ==========================================
    // DISPLAY SERVICES
    // ==========================================

    private fun displayServices(
        services: List<Service>
    ) {

        servicesContainer.removeAllViews()


        for (service in services) {

            // Only display active services
            if (!service.active_status) {
                continue
            }


            // ======================================
            // SERVICE CARD
            // ======================================

            val card = LinearLayout(this)

            card.orientation =
                LinearLayout.VERTICAL

            card.setPadding(
                24,
                24,
                24,
                24
            )

            card.setBackgroundColor(
                Color.WHITE
            )

            card.elevation = 6f


            val cardParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

            cardParams.setMargins(
                0,
                0,
                0,
                20
            )

            card.layoutParams = cardParams


            // ======================================
            // SERVICE NAME
            // ======================================

            val name = TextView(this)

            name.text = service.service_name

            name.textSize = 19f

            name.setTextColor(
                Color.rgb(18, 59, 93)
            )

            name.setTypeface(
                null,
                Typeface.BOLD
            )

            name.setPadding(
                0,
                0,
                0,
                8
            )


            // ======================================
            // CATEGORY
            // ======================================

            val category = TextView(this)

            category.text =
                "Category: ${service.category}"

            category.textSize = 14f

            category.setTextColor(
                Color.rgb(96, 112, 128)
            )

            category.setPadding(
                0,
                0,
                0,
                8
            )


            // ======================================
            // DESCRIPTION
            // ======================================

            val description = TextView(this)

            description.text =
                service.description

            description.textSize = 14f

            description.setTextColor(
                Color.rgb(70, 80, 90)
            )

            description.setPadding(
                0,
                0,
                0,
                12
            )


            // ======================================
            // PRICE
            // ======================================

            val price = TextView(this)

            price.text =
                String.format(
                    Locale.US,
                    "Estimated price: R%.2f",
                    service.price
                )

            price.textSize = 15f

            price.setTextColor(
                Color.rgb(40, 125, 50)
            )

            price.setTypeface(
                null,
                Typeface.BOLD
            )

            price.setPadding(
                0,
                0,
                0,
                8
            )


            // ======================================
            // DURATION
            // ======================================

            val duration = TextView(this)

            duration.text =
                "Estimated duration: ${service.duration} minutes"

            duration.textSize = 14f

            duration.setTextColor(
                Color.rgb(96, 112, 128)
            )


            // ======================================
            // ADD VIEWS TO CARD
            // ======================================

            card.addView(name)
            card.addView(category)
            card.addView(description)
            card.addView(price)
            card.addView(duration)


            // ======================================
            // ADD CARD TO SCREEN
            // ======================================

            servicesContainer.addView(card)
        }


        // If all services were inactive
        if (servicesContainer.childCount == 0) {

            showEmptyMessage()
        }
    }


    // ==========================================
    // EMPTY SERVICES MESSAGE
    // ==========================================

    private fun showEmptyMessage() {

        servicesContainer.removeAllViews()


        val emptyMessage = TextView(this)

        emptyMessage.text =
            "No IT services are currently available."

        emptyMessage.textSize = 16f

        emptyMessage.setTextColor(
            Color.rgb(70, 80, 90)
        )

        emptyMessage.setPadding(
            20,
            40,
            20,
            40
        )

        servicesContainer.addView(
            emptyMessage
        )
    }
}