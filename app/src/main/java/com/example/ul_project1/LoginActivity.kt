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

class LoginActivity : AppCompatActivity() {

    private fun validateEmail(email: String, emailLayout: TextInputLayout): Boolean {
        var isValid = true

        if (email.isEmpty()) {
            emailLayout.error = "Email cannot be empty"
            isValid=false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.error = "Please enter a valid email address. Example: example@gmail.com"
            isValid= false
        } else {
            emailLayout.error = null
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
            isValid= false
        } else {
            passwordLayout.error = null
        }

        return isValid
    }

    private fun navigateToRegistration() {
        val goToRegisterIntent = Intent(this, RegisterActivity::class.java)
        startActivity(goToRegisterIntent)
        finish()
    }


    private fun navigateToCentralActivityWithStaticCredentials(
        email: String,
        password: String,
        emailLayout: TextInputLayout,
        passwordLayout: TextInputLayout
    ): Boolean {
        var isValid = true

        if (email != "test@gmail.com") {
            emailLayout.error = "Incorrect email"
            isValid = false
        } else {
            emailLayout.error = null
        }

        if (password != "123456") {
            passwordLayout.error = "Incorrect password"
            isValid = false
        } else {
            passwordLayout.error = null
        }

        return isValid
    }

    private fun navigateToSampleActivity() {
        val intent = Intent(this, SampleActivity::class.java)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.checkRemember)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //FOR THE ERROR MESSAGES (email,password)
        val emailLayout = findViewById<TextInputLayout>(R.id.editEmail)
        val emailEditText = findViewById<TextInputEditText>(R.id.email)
        val passwordLayout = findViewById<TextInputLayout>(R.id.editPassword)
        val passwordEditText = findViewById<TextInputEditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.buttonLabel)

        loginButton.setOnClickListener { //the button NEXT in login Layout
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()


            //check if email and password are valid
            val isEmailValid = validateEmail(email, emailLayout)
            val isPasswordValid = validatePassword(password, passwordLayout)

//            if (navigateToCentralActivityWithStaticCredentials(email, password, emailLayout, passwordLayout)) { //with static credentials navigate to CentralActivity->Hello World
//                val intent = Intent(this, CentralActivity::class.java)
//                startActivity(intent)
//            }

            if (isEmailValid && isPasswordValid) {
                Log.d("RegisterActivity", "All fields are valid. Proceeding...")
                val credentialsManager = CredentialsManager()

                //login layout(the user has already valid credentials and tries to do login)
                if (credentialsManager.login(email, password)) {
                    Log.d("MainActivity", "Login successful")
                    val intent = Intent(this, CentralActivity::class.java) //goes to CentralActivity
                    startActivity(intent)
                    // finish()  we put it in comments because we want again to register with the same email(we want the history)
                } else {
                    emailLayout.error = "Invalid email or password"
                }
            }
        }


        val registerNowLabel = findViewById<TextView>(R.id.registerNowLabel)
        registerNowLabel.setOnClickListener { //the label "register now" in login Layout
            Log.d("Onboarding", "Register now pressed")

            //for the following one of those must be without comments
            //navigateToRegistration() //by pressing the registerNowLabel it goes to the RegisterActivity
            navigateToSampleActivity() //by pressing the registerNowLabel it goes to the SampleActivity with the switch button which you press and changes fragments
        }
    }
}
