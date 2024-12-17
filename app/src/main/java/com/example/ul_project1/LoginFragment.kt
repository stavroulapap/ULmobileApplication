package com.example.ul_project1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.util.Patterns
import android.widget.Button
import android.widget.TextView

class LoginFragment : Fragment() {

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


    private fun navigateToCentralActivity(email: String, password: String,emailLayout: TextInputLayout) {
        val credentialsManager = (activity as SampleActivity).getCredentialsManager()
        if (credentialsManager.login(email, password)) {
            Log.d("LoginFragment", "Login successful")
            val intent = Intent(activity, CentralActivity::class.java)
            startActivity(intent)
        } else {
            Log.d("LoginFragment", "Invalid credentials")
            emailLayout.error = "Invalid email or password"
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val emailLayout = view.findViewById<TextInputLayout>(R.id.editEmail)
        val emailEditText = view.findViewById<TextInputEditText>(R.id.email)
        val passwordLayout = view.findViewById<TextInputLayout>(R.id.editPassword)
        val passwordEditText = view.findViewById<TextInputEditText>(R.id.password)
        val loginButton = view.findViewById<Button>(R.id.buttonLabel)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            val isEmailValid = validateEmail(email, emailLayout)
            val isPasswordValid = validatePassword(password, passwordLayout)

            if (isEmailValid && isPasswordValid) {
                    navigateToCentralActivity(email,password,emailLayout)
            }
        }

        val registerNowLabel = view.findViewById<TextView>(R.id.registerNowLabel)
        registerNowLabel.setOnClickListener {
            Log.d("LoginFragment", "Register now pressed")
            loadFragment(RegisterFragment())
        }
        return view
    }

    private fun loadFragment(fragment: Fragment) {
        //supportFragmentManager to change Fragment
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_container_view, fragment) // Replace the current Fragment with the new one
            addToBackStack(null) // added to back stack to allow navigation back
            commit()
        }
    }
}

