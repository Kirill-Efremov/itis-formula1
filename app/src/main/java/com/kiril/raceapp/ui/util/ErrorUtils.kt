package com.kiril.raceapp.ui.util

object ErrorUtils {
    fun extractErrorMessage(message: String?): String {
        return when {
            message?.contains("Password should be at least 6 characters") == true -> "Password should be at least 6 characters"
            message?.contains("Unable to validate email address: invalid format") == true -> "Unable to validate email address: invalid format"
            message?.contains("User already registered") == true -> "User already registered"
            else -> "An error occurred, please try again later"
        }
    }
}