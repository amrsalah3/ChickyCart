package com.narify.ecommerce

import android.app.Application
import com.narify.netdetect.NetDetect
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        NetDetect.init(this)

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        // For testing/developing cases
/*        val db = Firebase.firestore
        db.firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false)
            .build()

        db.useEmulator("10.0.2.2", 8080)
        Firebase.auth.useEmulator("10.0.2.2", 9099)*/

    }
}