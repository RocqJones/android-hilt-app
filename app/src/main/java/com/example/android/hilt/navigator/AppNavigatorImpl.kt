package com.example.android.hilt.navigator

import androidx.fragment.app.FragmentActivity
import com.example.android.hilt.R
import com.example.android.hilt.ui.ButtonsFragment
import com.example.android.hilt.ui.LogsFragment
import javax.inject.Inject

/**
 * Navigator implementation.
 *
 * AppNavigatorImpl depends on a FragmentActivity. Because an AppNavigator instance is provided in
 * the Activity container , FragmentActivity is already available as a predefined binding.
 */
class AppNavigatorImpl @Inject constructor(private val activity: FragmentActivity) : AppNavigator {

    override fun navigateTo(screen: Screens) {
        val fragment = when (screen) {
            Screens.BUTTONS -> ButtonsFragment()
            Screens.LOGS -> LogsFragment()
        }

        activity.supportFragmentManager.beginTransaction().replace(
            R.id.main_container, fragment
        ).addToBackStack(
            fragment::class.java.canonicalName
        ).commit()
    }
}
