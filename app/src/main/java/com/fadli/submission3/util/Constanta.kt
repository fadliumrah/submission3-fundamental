package com.fadli.submission3.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.fadli.submission3.R

object Constanta {

    val TAB_TITLES = intArrayOf(R.string.followers, R.string.following)

    const val TIME_SPLASH = 1000L

    const val API_URL = "https://api.github.com"

    const val GITHUB_TOKEN = "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"

    const val EXTRA_USER = "EXTRA_USER"

    val THEME_KEY = booleanPreferencesKey("THEME_KEY")
}