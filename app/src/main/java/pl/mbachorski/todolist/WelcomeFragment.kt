package pl.mbachorski.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class WelcomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        findNavController().navigate(R.id.dashboardFragment)
        findNavController().navigate(R.id.authenticationFragment)
    }
}
