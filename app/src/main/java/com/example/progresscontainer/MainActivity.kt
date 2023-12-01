package com.example.progresscontainer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var progressContainer: ProgressContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressContainer = findViewById(R.id.progressContainer)
        progressContainer.state = ProgressContainer.State.Loading
        Handler(Looper.getMainLooper()).postDelayed({
            progressContainer.state = ProgressContainer.State.Notice("Произошла непредвиденная ошибка")
        }, 3000)
    }
}
