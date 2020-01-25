package pl.mbachorski.todolist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.ext.android.inject
import pl.mbachorski.todolist.authentication.AuthenticationService


class MainActivity : AppCompatActivity() {

    private val authenticationService: AuthenticationService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                authenticationService.logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
