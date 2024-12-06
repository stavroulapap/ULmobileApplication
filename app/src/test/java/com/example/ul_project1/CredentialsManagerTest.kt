package com.example.ul_project1

import org.junit.Assert.*
import org.junit.Test

class CredentialsManagerTest {

///////////////////for email

    @Test
    fun givenEmptyEmail_thenReturnFalse() {
        val credentialsManager = CredentialsManager()
        val email = ""
        val result = credentialsManager.isEmailValid(email)
        assertFalse(result)
    }

    @Test
    fun givenWrongEmailFormat_thenReturnFalse() {
        val credentialsManager = CredentialsManager()
        val email = "not an email"
        val result = credentialsManager.isEmailValid(email)
        assertFalse(result)
    }

    @Test
    fun givenProperEmailFormat_thenReturnTrue() {
        val credentialsManager = CredentialsManager()
        val email = "example@email.com"
        val result = credentialsManager.isEmailValid(email)
        assertTrue(result)
    }

////////////////for password

    @Test
    fun givenEmptyPassword_thenReturnError() {
        val credentialsManager = CredentialsManager()
        val emptyPassword = ""
        val result = credentialsManager.isPasswordValid(emptyPassword)
        assertFalse("Password is empty, should return false", result)
    }

    @Test
    fun givenNonEmptyPassword_thenReturnSuccess() {
        val credentialsManager = CredentialsManager()
        val nonEmptyPassword = "Abc123@#"
        val result = credentialsManager.isPasswordValid(nonEmptyPassword)
        assertTrue("Password is valid, should return true", result)
    }


    /////////////for login
    @Test
    fun givenWrongCredentials_thenReturnFalse() {
        val credentialsManager = CredentialsManager()
        val email = "Not an user"
        val password = "Not a password"
        val result = credentialsManager.login(email, password)
        assertFalse(result)
    }

    @Test
    fun givenEmptyCredentials_thenReturnFalse() {
        val credentialsManager = CredentialsManager()
        val email = ""
        val password = ""
        val result = credentialsManager.login(email, password)
        assertFalse(result)
    }

    @Test
    fun givenFixedCredentials_thenReturnTrue() {
        val credentialsManager = CredentialsManager()
        val email = "test@gmail.com"
        //val email = "test"
        val password = "123456"
        val result = credentialsManager.login(email, password)
        assertTrue(result)
    }

    //given proper credentials,when user registers, then create account
    @Test
    fun givenProperCredentials_whenUserRegisters_thenCreateAccount() {
        val credentialsManager = CredentialsManager()
        val email = "another@te.st"
        val password = "1234b#"
        credentialsManager.register(email, password)
        //credentialsManager.register("Fullname",email,"600 600 600", password )

        val isLoginSuccess = credentialsManager.login(email, password)
        assertTrue(isLoginSuccess)
    }

    //given already used email, when user registers, then return error
    //given already used email with different casing, when user registers, then return error
    //given used email with different casing, when user logs in, then return success
}