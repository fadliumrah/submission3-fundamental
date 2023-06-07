package com.fadli.submission3.ui.themes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fadli.submission3.databinding.ActivityChangeThemesBinding
import androidx.appcompat.app.AppCompatDelegate


class ChangeThemes : AppCompatActivity() {
    private lateinit var _binding: ActivityChangeThemesBinding
    private lateinit var _viewModel: ThemesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChangeThemesBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _viewModel = ThemesViewModel(application)

        _binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            _viewModel.saveChangeTheme(isChecked)
        }


        _viewModel.getChangeTheme().observe(this) { isDarkModeActive->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                _binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                _binding.switchTheme.isChecked = false
            }
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}