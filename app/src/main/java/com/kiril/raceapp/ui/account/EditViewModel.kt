package com.kiril.raceapp.ui.account


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiril.raceapp.ui.util.ErrorUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(private val supabaseClient: SupabaseClient) : ViewModel() {

    private val _updateSuccess = MutableLiveData<Boolean>()
    val updateSuccess: LiveData<Boolean> get() = _updateSuccess

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun updateUser(email: String, password: String, avatarUri: String? = null) {
        if (email.isEmpty() || (password.isEmpty() && avatarUri == null)) {
            _errorMessage.value = "Email and password must not be empty"
            return
        }

        viewModelScope.launch {
            try {
                supabaseClient.auth.updateUser {
                    this.email = email
                    if (password.isNotBlank()) {
                        this.password = password
                    }
                }
                _updateSuccess.value = true
            } catch (e: Exception) {
                val errorMessage = ErrorUtils.extractErrorMessage(e.message)
                _errorMessage.value = errorMessage
            }
        }
    }

}
