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

class MessagesActivity : AppCompatActivity() {

    private lateinit var messagesTextView: TextView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button
    private lateinit var backButton: Button

    // Current booking used by the messaging screen.
    // This can later be replaced with a selected booking ID.
    private val bookingId = 1

    private var senderId = 0

    // Current backend test receiver.
    // Keep this until the technician/user selection is implemented.
    private val receiverId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        messagesTextView = findViewById(R.id.messagesTextView)
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)
        backButton = findViewById(R.id.backButton)

        // Get the logged-in user's ID
        val sharedPreferences =
            getSharedPreferences("VhupharaTechSupport", MODE_PRIVATE)

        senderId = sharedPreferences.getInt("user_id", 0)

        if (senderId == 0) {
            Toast.makeText(
                this,
                "Please log in again.",
                Toast.LENGTH_LONG
            ).show()

            finish()
            return
        }

        loadMessages()

        sendButton.setOnClickListener {
            sendMessage()
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun loadMessages() {

        messagesTextView.text = "Loading messages..."

        RetrofitClient.apiService
            .getBookingMessages(bookingId)
            .enqueue(object : Callback<MessagesResponse> {

                override fun onResponse(
                    call: Call<MessagesResponse>,
                    response: Response<MessagesResponse>
                ) {

                    if (response.isSuccessful && response.body() != null) {

                        val result = response.body()!!

                        if (result.messages.isEmpty()) {

                            messagesTextView.text =
                                "No messages yet."

                        } else {

                            val builder = StringBuilder()

                            for (message in result.messages) {

                                builder.append("Message\n")
                                builder.append(message.message)
                                builder.append("\n\n")
                                builder.append("Date: ")
                                builder.append(message.sent_at)
                                builder.append("\n\n")
                            }

                            messagesTextView.text = builder.toString()
                        }

                    } else {

                        messagesTextView.text =
                            "Unable to load messages."

                        Toast.makeText(
                            this@MessagesActivity,
                            "Failed to load messages",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<MessagesResponse>,
                    t: Throwable
                ) {

                    messagesTextView.text =
                        "Connection error. Please check your internet connection."

                    Toast.makeText(
                        this@MessagesActivity,
                        "Connection error: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun sendMessage() {

        val messageText = messageInput.text.toString().trim()

        if (messageText.isEmpty()) {

            Toast.makeText(
                this,
                "Please enter a message",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (senderId == 0) {

            Toast.makeText(
                this,
                "User session not found. Please log in again.",
                Toast.LENGTH_LONG
            ).show()

            return
        }

        val messageRequest = MessageRequest(
            booking_id = bookingId,
            sender_id = senderId,
            receiver_id = receiverId,
            message = messageText
        )

        sendButton.isEnabled = false

        RetrofitClient.apiService
            .sendMessage(messageRequest)
            .enqueue(object : Callback<MessageResponse> {

                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {

                    sendButton.isEnabled = true

                    if (response.isSuccessful && response.body() != null) {

                        Toast.makeText(
                            this@MessagesActivity,
                            "Message sent successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        messageInput.text.clear()

                        // Refresh the conversation
                        loadMessages()

                    } else {

                        Toast.makeText(
                            this@MessagesActivity,
                            "Failed to send message",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<MessageResponse>,
                    t: Throwable
                ) {

                    sendButton.isEnabled = true

                    Toast.makeText(
                        this@MessagesActivity,
                        "Connection error: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}