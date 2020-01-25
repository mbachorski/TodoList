package pl.mbachorski.todolist.authentication

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import pl.mbachorski.todolist.MainActivity
import timber.log.Timber

private const val RC_SIGN_IN = 12345

class FirebaseAuthenticationService(private val activity: Activity?) : AuthenticationService {

    override fun login(fragment: Fragment) {
        Timber.d("Login with firebase")

        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        fragment.startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun logout() {
        Timber.d("Logout with firebase")
        activity?.let {
            AuthUI.getInstance()
                .signOut(activity.applicationContext)
                .addOnCompleteListener {
                    // user is now signed out
                    activity.startActivity(Intent(activity, MainActivity::class.java))
                    activity.finish()
                }
        }
    }

    override fun handleAuthenticationResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        authenticationCallback: AuthenticationCallback
    ) {
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                Timber.d("Firebase authentication successful, user: ${user?.displayName}")
                authenticationCallback.onSuccess()

            } else {
                Timber.d("Firebase authentication failure ${response?.error?.errorCode}")

                // TODO: translate error code to translated message for user
                authenticationCallback.onFailure(response?.error?.errorCode ?: 0)
            }
        }
    }
}