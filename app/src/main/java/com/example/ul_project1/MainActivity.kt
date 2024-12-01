package com.example.ul_project1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.util.Log
import android.content.Intent
import com.google.android.material.textfield.TextInputEditText

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