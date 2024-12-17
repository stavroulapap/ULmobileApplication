package com.example.ul_project1

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : Fragment() {

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
            phoneLayout.error = "Please enter a valid 10-digit phone number"
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



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val nameLayout = view.findViewById<TextInputLayout>(R.id.editFullName)
        val nameEditText = view.findViewById<TextInputEditText>(R.id.editName)
        val emailLayout = view.findViewById<TextInputLayout>(R.id.editValidEmail)
        val emailEditText = view.findViewById<TextInputEditText>(R.id.editEmail)
        val phoneLayout = view.findViewById<TextInputLayout>(R.id.editPhoneNumber)
        val phoneEditText = view.findViewById<TextInputEditText>(R.id.editPhone)
        val passwordLayout = view.findViewById<TextInputLayout>(R.id.editStrongPassword)
        val passwordEditText = view.findViewById<TextInputEditText>(R.id.editPassword)
        val registerButton = view.findViewById<Button>(R.id.button)

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()

            // Check if name, email, phone, and password are valid
            val isNameValid = validateName(name, nameLayout)
            val isEmailValid = validateEmail(email, emailLayout)
            val isPasswordValid = validatePassword(password, passwordLayout)
            val isPhoneValid = validatePhone(phone, phoneLayout)

            if (isNameValid && isEmailValid && isPasswordValid && isPhoneValid) {
                val credentialsManager = CredentialsManager()

                val isRegistered = credentialsManager.register(email, password)
                if (isRegistered) {
                    Log.d("RegisterFragment", "Registration successful")
                    loadFragment(LoginFragment())
                } else {
                    emailLayout.error = "This email is already registered"
                }
            }
        }

        val loginNowLabel = view.findViewById<TextView>(R.id.loginLabel)
        loginNowLabel.setOnClickListener {
            Log.d("Onboarding", "Login now pressed")
            loadFragment(LoginFragment())
        }
        return view
    }

    private fun loadFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_container_view, fragment) // Replace the current Fragment with the new one
            addToBackStack(null) // added to back stack to allow navigation back
            commit()
        }
    }
}
