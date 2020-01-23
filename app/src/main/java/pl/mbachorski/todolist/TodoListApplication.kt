package pl.mbachorski.todolist

import android.app.Application
import com.google.firebase.FirebaseApp

class TodoListApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}