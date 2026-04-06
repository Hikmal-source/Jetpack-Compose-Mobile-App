package com.hikmal.hikmalmobile
import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegistrationViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()
    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()
    private val _emailError = MutableStateFlow(false)
    val emailError: StateFlow<Boolean> = _emailError.asStateFlow()
    private val _passwordError = MutableStateFlow(false)
    val passwordError: StateFlow<Boolean> = _passwordError.asStateFlow()
    private val _confirmPasswordError = MutableStateFlow(false)
    val confirmPasswordError: StateFlow<Boolean> = _confirmPasswordError.asStateFlow()



    fun updateEmail(newEmail: String) {
        _email.value = newEmail
        _emailError.value = newEmail.isNotEmpty() && !isValidEmail(newEmail)
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
        _passwordError.value = newPassword.isNotEmpty() && !isValidPassword(newPassword)

        if (_confirmPassword.value.isNotEmpty()) {
            _confirmPasswordError.value = _confirmPassword.value != _password.value
        }
    }
    fun updateConfirmPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
        _confirmPasswordError.value = newConfirmPassword.isNotEmpty() &&
                newConfirmPassword != _password.value
    }
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }
    fun passwordStrength(password: String): String {
        val score = listOf(
            password.length >= 8,
            password.any { it.isDigit() },
            password.any { it.isUpperCase() },
            password.any { it.isLowerCase() },
            password.any { it.isLetterOrDigit() }
        ).count { it }

        return when (score) {
            0, 1 -> "Very Weak"
            2 -> "Weak"
            3 -> "Medium"
            4 -> "Strong"
            else -> "Very Strong"
        }
    }
    fun checkSubmit(): Boolean {
        return _email.value.isNotEmpty() &&
                _password.value.isNotEmpty() &&
                _confirmPassword.value.isNotEmpty() &&
                !_emailError.value &&
                !_passwordError.value &&
                !_confirmPasswordError.value
    }
    fun submitRegistration() {
        if (checkSubmit()) {
            println("Registration successful for: ${_email.value}")
            clearSensitiveData()
        }
    }
    private fun clearSensitiveData() {
        _email.value = ""
        _password.value = ""
        _confirmPassword.value = ""
        _emailError.value = false
        _passwordError.value = false
        _confirmPasswordError.value = false
    }
}

