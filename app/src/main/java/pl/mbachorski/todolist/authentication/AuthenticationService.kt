package pl.mbachorski.todolist.authentication

import android.content.Intent
import androidx.fragment.app.Fragment

interface AuthenticationService {

    fun isLoggedIn(): Boolean
    
    fun login(fragment: Fragment)

    fun logout()

    fun handleAuthenticationResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        authenticationCallback: AuthenticationCallback
    )
}

interface AuthenticationCallback {
    fun onSuccess()
    fun onFailure(errorrCode: Int)
}