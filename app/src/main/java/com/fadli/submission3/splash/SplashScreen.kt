package com.fadli.submission3.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.fadli.submission3.databinding.ActivitySplashScreenBinding
import com.fadli.submission3.ui.home.MainActivity
import com.fadli.submission3.util.Constanta.TIME_SPLASH


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private lateinit var _binding: ActivitySplashScreenBinding
    private lateinit var _viewModel: SplashScreenViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _viewModel = SplashScreenViewModel(application)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                _viewModel.getChangeTheme().observe(this@SplashScreen) { isDarkModeActive ->
                    if (isDarkModeActive) {
                        toMain()
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        toMain()
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }, TIME_SPLASH)


    }
    private fun toMain() {
        val intent = Intent(this@SplashScreen, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}