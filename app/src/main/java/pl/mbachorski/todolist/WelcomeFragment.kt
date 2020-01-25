package pl.mbachorski.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class WelcomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(FirebaseAuth.getInstance()) {
            if (this.currentUser != null) {
                findNavController().navigate(R.id.action_welcomeFragment_to_dashboardFragment)
            } else {
                findNavController().navigate(R.id.action_welcomeFragment_to_authenticationFragment)
            }
        }
    }
}
