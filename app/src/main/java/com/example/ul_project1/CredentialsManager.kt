package com.example.ul_project1
//import android.util.Patterns
import android.util.Log

class CredentialsManager {

//    val credentialsMap = mutableMapOf(
//        Pair("Key", "Value")
//    )

    private val emailPattern = (("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + //format for email
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"))

    fun isEmailValid(email: String): Boolean {
        return Regex(emailPattern).matches(email)
    }

//    fun isEmailValid(email: String): Boolean {
//        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
//    }

    fun isPasswordValid(password: String): Boolean {
        if (password.isBlank()) {
            Log.e("CredentialsManager", "Password is empty") // show error in Logcat
            return false
        }
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=]).{8,}$" //the password has at least one number,lowercase,uppercase,special char,8 digits
        return Regex(passwordPattern).matches(password)
    }


    fun login(email: String, password: String): Boolean {
            return email == "test@gmail.com" && password == "1234"
//    fun register(fullname,email,phone,password: String){
//        credentialsMap.put(email,password)
//    }

    }
}
