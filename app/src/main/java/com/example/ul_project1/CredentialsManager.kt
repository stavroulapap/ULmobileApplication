package com.example.ul_project1
//import android.util.Patterns
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CredentialsManager {

    object Data {  //map for email and password for each user(valid credentials from registerActivity->LoginAct->CentralAct)
        val credentialsMap = mutableMapOf(
            Pair("test1@gmail.com", "123456")
        )
    }

    private val emailPattern = (("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"))

    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=]).{6,}$" //the password has at least one number,lowercase,uppercase,special char,6 digits

    fun isEmailValid(email: String): Boolean {
        return Regex(emailPattern).matches(email)
    }

//    fun isEmailValid(email: String): Boolean {
//        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
//    }

    fun isPasswordValid(password: String): Boolean {
        if (password.isBlank()) {
            Log.e("CredentialsManager", "Password is empty")
            return false
        }
        return Regex(passwordPattern).matches(password)
    }

    //registration for the new user
    fun register(email: String, password: String): Boolean {
        val normalizedEmail = email.lowercase()

        //we check if the email already exists
        if (Data.credentialsMap.containsKey(normalizedEmail)) {
            return false //if the email already exists, return false
        }

        //if the email doesnt exist we add it with the password
        Data.credentialsMap[normalizedEmail] = password
        return true
    }


    fun login(email: String, password: String): Boolean {
        val normalizedEmail = email.lowercase()

        // we check if the email exists and the password is correct
        return Data.credentialsMap[normalizedEmail] == password
        //return email == "test@gmail.com" && password == "Sp1234_#"--->for default
    }

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    fun login() {
        _isLoggedIn.value = true
    }

    fun logout() {
        _isLoggedIn.value = false
    }
}


