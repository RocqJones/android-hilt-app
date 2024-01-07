package com.example.android.hilt.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.hilt.R
import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Main activity of the application.
 *
 * Container for the Buttons & Logs fragments. This activity simply tracks clicks on buttons.
 *
 * Hilt needs to be aware of the Activity that hosts the Fragment in order to work. We'll need to use @AndroidEntryPoint.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //private lateinit var navigator: AppNavigator
    @Inject lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // navigator = (applicationContext as LogApplication).serviceLocator.provideNavigator(this)

        if (savedInstanceState == null) {
            navigator.navigateTo(Screens.BUTTONS)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()

        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }
}
