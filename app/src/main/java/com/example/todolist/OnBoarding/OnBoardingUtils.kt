package com.example.todolist.OnBoarding

import android.content.Context
import androidx.core.content.edit

class OnBoardingUtils(private val context: Context) {
    fun isOnboardingCompleted(): Boolean {
        return context.getSharedPreferences("onboarding", Context.MODE_PRIVATE)
            .getBoolean("complated", false)
    }

    fun setOnboardingCompleted() {
        context.getSharedPreferences("onboarding", Context.MODE_PRIVATE)
            .edit {
                putBoolean("complated", true)
            }
    }
}