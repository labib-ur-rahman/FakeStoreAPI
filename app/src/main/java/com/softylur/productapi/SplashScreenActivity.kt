package com.softylur.productapi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.softylur.productapi.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val upFromBottom = AnimationUtils.loadAnimation(this, R.anim.animation1)

        binding.logoImageView.animation = upFromBottom
        binding.appNameTextView.animation = upFromBottom

        // Delay the splash screen for 3 seconds, then move to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Start the home page activity
            startActivity(Intent(this, HomePageActivity::class.java))
            // Close this activity
            finish()
        }, 3000) // 3 seconds delay

    }
}