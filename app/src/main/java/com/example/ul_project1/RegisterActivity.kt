package com.example.ul_project1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.util.Patterns
import android.widget.Button

class RegisterActivity : AppCompatActivity() {

    private fun validateName(name: String, nameLayout: TextInputLayout): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            nameLayout.error = "Name cannot be empty"
            isValid = false
        } else {
            nameLayout.error = null
        }

        return isValid
    }

    private fun validateEmail(email: String, emailLayout: TextInputLayout): Boolean {
        var isValid = true

        if (email.isEmpty()) {
            emailLayout.error = "Email cannot be empty"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.error = "Please enter a valid email address. Example: example@gmail.com"
            isValid = false
        } else {
            emailLayout.error = null
        }

        return isValid
    }

    private fun validatePhone(phone: String, phoneLayout: TextInputLayout): Boolean {
        var isValid = true

        if (phone.isEmpty()) {
            phoneLayout.error = "Phone number cannot be empty"
            isValid = false
        } else if (!phone.matches(Regex("^[0-9]{10}$"))) {
            phoneLayout.error = "Please enter a valid 10-digit phone number "
            isValid = false
        } else {
            phoneLayout.error = null
        }

        return isValid
    }

    private fun validatePassword(password: String, passwordLayout: TextInputLayout): Boolean {
        var isValid = true

        if (password.isEmpty()) {
            passwordLayout.error = "Password cannot be empty"
            isValid = false
        } else if (password.length < 6) {
            passwordLayout.error = "Password must be at least 6 characters long"
            isValid = false
        } else {
            passwordLayout.error = null
        }

        return isValid
    }

    private fun navigateToLoginActivity() {
        val goToLoginIntent = Intent(this, LoginActivity::class.java)
        startActivity(goToLoginIntent)
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //FOR THE ERROR MESSAGES
        val nameLayout = findViewById<TextInputLayout>(R.id.editFullName)
        val nameEditText = findViewById<TextInputEditText>(R.id.editName)
        val emailLayout = findViewById<TextInputLayout>(R.id.editValidEmail)
        val emailEditText = findViewById<TextInputEditText>(R.id.editEmail)
        val phoneLayout = findViewById<TextInputLayout>(R.id.editPhoneNumber)
        val phoneEditText = findViewById<TextInputEditText>(R.id.editPhone)
        val passwordLayout = findViewById<TextInputLayout>(R.id.editStrongPassword)
        val passwordEditText = findViewById<TextInputEditText>(R.id.editPassword)
        val registerButton = findViewById<Button>(R.id.button)

        registerButton.setOnClickListener {  //the button NEXT in register Layout
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()

            //check if email password name phone are valid
            val isNameValid = validateName(name, nameLayout)
            val isEmailValid = validateEmail(email, emailLayout)
            val isPasswordValid = validatePassword(password, passwordLayout)
            val isPhoneValid = validatePhone(phone, phoneLayout)


            if (isNameValid && isEmailValid && isPasswordValid && isPhoneValid) {
                val credentialsManager = CredentialsManager()

                val isRegistered = credentialsManager.register(email, password)
                if (isRegistered) {
                    Log.d("RegisterActivity", "Registration successful")
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    emailLayout.error = "This email is already registered"
                }
            }
        }


        val loginNowLabel = findViewById<TextView>(R.id.loginLabel)
        loginNowLabel.setOnClickListener {
            Log.d("Onboarding", "Login now pressed")
            navigateToLoginActivity()
        }
    }
}