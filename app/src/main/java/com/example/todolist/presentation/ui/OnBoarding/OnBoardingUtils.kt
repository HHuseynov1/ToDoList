package com.example.todolist.presentation.ui.OnBoarding

import android.content.Context
import androidx.core.content.edit
import com.example.todolist.util.Constants.ONBOARDING_SHARED_PREF

class OnBoardingUtils(private val context: Context) {
    fun isOnboardingCompleted(): Boolean {
        return context.getSharedPreferences(ONBOARDING_SHARED_PREF, Context.MODE_PRIVATE)
            .getBoolean("complated", false)
    }

    fun setOnboardingCompleted() {
        context.getSharedPreferences(ONBOARDING_SHARED_PREF, Context.MODE_PRIVATE)
            .edit {
                putBoolean("complated", true)
            }
    }
}