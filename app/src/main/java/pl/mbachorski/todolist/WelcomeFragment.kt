package pl.mbachorski.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.inject
import pl.mbachorski.todolist.authentication.AuthenticationService

class WelcomeFragment : Fragment() {

    private val authenticationService: AuthenticationService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (authenticationService.isLoggedIn()) {
            findNavController().navigate(R.id.action_welcomeFragment_to_dashboardFragment)
        } else {
            findNavController().navigate(R.id.action_welcomeFragment_to_authenticationFragment)
        }
    }
}
