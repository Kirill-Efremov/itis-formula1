package com.kiril.raceapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.kiril.raceapp.R
import com.kiril.raceapp.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val registerViewModel: RegisterViewModel by viewModels()

    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var registerButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        registerButton = findViewById(R.id.register_button)

        registerButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            registerViewModel.register(email, password)
        }
        registerViewModel.registrationSuccess.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        registerViewModel.errorMessage.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }
}