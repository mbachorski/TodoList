package pl.mbachorski.todolist

import android.app.Application
import com.google.firebase.FirebaseApp
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.mbachorski.todolist.authentication.di.authenticationModule
import pl.mbachorski.todolist.di.activityModule
import timber.log.Timber
import timber.log.Timber.DebugTree


class TodoListApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()

        initFirebase()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            // declare used Android context
            androidContext(applicationContext)

            modules(activityModule)
            modules(authenticationModule)
        }

        // Koin is lazy so in order to track Activity dependency get() has to be called here to
        // create instance of ActivityProvider as soon as application is created. Seems like hack,
        // but no better solution found for now.
        val activityProvider: ActivityProvider = get()
    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}