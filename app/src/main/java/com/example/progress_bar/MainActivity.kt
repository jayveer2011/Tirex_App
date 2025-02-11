package com.example.progress_bar

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var progressStatus = 0
    private val totalChunks = 1000
    private var sendChunks = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get references to the ProgressBar and TextView
        val simpleProgressBar = findViewById<ProgressBar>(R.id.simpleProgressBar)
        simpleProgressBar.visibility = View.VISIBLE
        val textView = findViewById<TextView>(R.id.textView)

        // Set the maximum value for the ProgressBar (total chunks)
        simpleProgressBar.max = totalChunks

        // Start a background thread to simulate progress updates
        Thread {
            while (sendChunks < totalChunks) {
                // Update the number of chunks sent
                sendChunks += 8
                // Calculate the progress percentage
                progressStatus = (sendChunks * 100) / totalChunks

                // Run the UI updates on the main thread
                runOnUiThread { // Update the ProgressBar with the new progress
                    simpleProgressBar.progress = sendChunks

                    // Update the TextView with the percentage of progress
                    textView.text = "$progressStatus%"

                    // Log progress for debugging purposes
                    Log.d("Process", "Progress: $progressStatus%")
                    Log.d("Process", "Sent Chunks: $sendChunks")
                }

                // Simulate a delay for each progress update
                try {
                    Thread.sleep(200)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }
}