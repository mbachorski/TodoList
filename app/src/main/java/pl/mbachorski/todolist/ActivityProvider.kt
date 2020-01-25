package pl.mbachorski.todolist

import android.app.Activity
import android.app.Application
import android.os.Bundle

class ActivityProvider(application: Application) {

    var activeActivity: Activity? = null

    init {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityDestroyed(activity: Activity) {
                activeActivity = null
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                // NOOP
            }

            override fun onActivityStopped(activity: Activity) {
                // NOOP
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activeActivity = activity
            }

            override fun onActivityStarted(activity: Activity) {
                // NOOP
            }

            override fun onActivityPaused(activity: Activity?) {
                activeActivity = null
            }

            override fun onActivityResumed(activity: Activity?) {
                activeActivity = activity
            }
        })
    }
}