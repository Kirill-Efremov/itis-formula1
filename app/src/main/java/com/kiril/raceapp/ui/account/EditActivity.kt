package com.kiril.raceapp.ui.account

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
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import javax.inject.Inject

@AndroidEntryPoint
class EditActivity : AppCompatActivity() {
    @Inject
    lateinit var supabaseClient: SupabaseClient
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var updateButton : MaterialButton
    private val editViewModel: EditViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val user = supabaseClient.auth.currentUserOrNull()

        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        updateButton  = findViewById(R.id.register_button)


        updateButton .text = "Update"

        emailInput.setText(user?.email)
        updateButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            editViewModel.updateUser(email, password)
        }
        editViewModel.updateSuccess.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        editViewModel.errorMessage.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }
//    private fun update(imageUri: Uri) {
//        val email = emailInput.text.toString()
//        val password = passwordInput.text.toString()
//
//
//
//        lifecycleScope.launch {
//            supabaseClient.auth.updateUser {
//                this.email = email
//                if (password.isNotBlank()) this.password = password
////                data = buildJsonObject {
////                    put("avatar", randomUUID)
////                }
//            }
//        }.invokeOnCompletion {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//
//
//    }
}