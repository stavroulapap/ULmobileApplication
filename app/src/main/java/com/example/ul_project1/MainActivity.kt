package com.example.ul_project1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.util.Log
import android.content.Intent
import android.util.Patterns
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.checkRemember)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //FOR THE ERROR MESSAGES
        val emailLayout = findViewById<TextInputLayout>(R.id.editEmail)
        val emailEditText = findViewById<TextInputEditText>(R.id.email)

        val passwordLayout = findViewById<TextInputLayout>(R.id.editPassword)
        val passwordEditText = findViewById<TextInputEditText>(R.id.password)

        val registerButton = findViewById<Button>(R.id.buttonLabel)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            var isValid = true

            //EMAIL
            if (email.isEmpty()) {
                emailLayout.error = "Email cannot be empty"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailLayout.error = "Please enter a valid email address. Example: example@gmail.com"
            } else {
                emailLayout.error = null
            }

            //PASSWORD
            if (password.isEmpty()) {
                passwordLayout.error = "Password cannot be empty"
                isValid = false
            } else if (password.length < 6) {
                passwordLayout.error = "Password must be at least 6 characters long"
                isValid = false
            } else {
                passwordLayout.error = null
            }

            //EVERYTHING IS VALID
            if (isValid) {
                Log.d("RegisterActivity", "All fields are valid. Proceeding...")
            }

        }

        //CHANGE TO THE OTHER LAYOUT
        val registerNowLabel= findViewById<TextView>(R.id.registerNowLabel)

        registerNowLabel.setOnClickListener{
            Log.d("Onboarding","Login now pressed")
            val goToRegisterIntent=Intent(this, RegisterActivity::class.java)
            startActivity(goToRegisterIntent)
        }

        //set/remove error from email field layout
//        editEmail.error="Oh no"
//        editEmail.error.isErrorEnabled=false
//
//        //get value from email field
//        findViewById<TextInputEditText>(R.id.editEmail).text.toString()
    }

}