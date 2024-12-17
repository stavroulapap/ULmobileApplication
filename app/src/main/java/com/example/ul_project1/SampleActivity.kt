package com.example.ul_project1

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class SampleActivity : AppCompatActivity() {

    private lateinit var switchButton: Button
    private lateinit var credentialsManager: CredentialsManager
    private var isRegisterFragmentVisible = false //which fragment is active



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        credentialsManager = CredentialsManager()
        //we use the button to change the fragment
        switchButton = findViewById(R.id.switch_button)

        switchButton.setOnClickListener {
            if (isRegisterFragmentVisible) {
                loadFragment(LoginFragment()) //if RegisterFragment is active, load LoginFragment
            } else {
                loadFragment(RegisterFragment()) //if LoginFragment is active, load RegisterFragment
            }
        }
        //at the beginning we load the LoginFragment
        loadFragment(LoginFragment())
    }


    fun getCredentialsManager(): CredentialsManager {
        return credentialsManager
    }

     fun loadFragment(fragment: Fragment) {
        //supportFragmentManager to change fragments
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, fragment)
            addToBackStack(null)
        }
        //update the flag in which fragment is active
        isRegisterFragmentVisible = fragment is RegisterFragment
    }
}
