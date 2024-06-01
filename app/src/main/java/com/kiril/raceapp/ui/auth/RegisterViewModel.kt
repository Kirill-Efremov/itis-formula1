package com.kiril.raceapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiril.raceapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val supabaseClient: SupabaseClient) :
    ViewModel() {

    private val _registrationSuccess = MutableLiveData<Boolean>()
    val registrationSuccess: LiveData<Boolean> get() = _registrationSuccess

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun register(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Email and password must not be empty"
            return
        }


        viewModelScope.launch {
            try {
                supabaseClient.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }
                _registrationSuccess.value = true
            } catch (e: Exception) {
                val errorMessage = extractErrorMessage(e.message)
                _errorMessage.value = errorMessage
            }
        }
    }

    private fun extractErrorMessage(message: String?): String {

        return when {

            message?.contains("Password should be at least 6 characters") == true -> "Password should be at least 6 characters"
            message?.contains("Unable to validate email address: invalid format") == true -> "Unable to validate email address: invalid format"
            message?.contains("User already registered") == true -> "User already registered"
            else -> "Registration error, please try to register later"
        }
    }
}
