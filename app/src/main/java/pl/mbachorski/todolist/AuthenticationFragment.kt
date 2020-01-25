package pl.mbachorski.todolist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.inject
import pl.mbachorski.todolist.authentication.AuthenticationCallback
import pl.mbachorski.todolist.authentication.AuthenticationService


class AuthenticationFragment : Fragment() {
    private val authenticationService: AuthenticationService by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.authentication_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.loginUsingGoogle).setOnClickListener {
            authenticationService.login(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        authenticationService.handleAuthenticationResult(
            requestCode,
            resultCode,
            data,
            object : AuthenticationCallback {
                override fun onSuccess() {
                    findNavController().navigate(R.id.action_authenticationFragment_to_dashboardFragment)
                }

                override fun onFailure(errorrCode: Int) {
                    activity?.let { Toast.makeText(it, "Error: $errorrCode", Toast.LENGTH_LONG) }
                }
            }
        )
    }
}
