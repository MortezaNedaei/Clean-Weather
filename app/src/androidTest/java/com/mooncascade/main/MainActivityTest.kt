package com.mooncascade.main

import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.mooncascade.R
import com.mooncascade.presentation.ui.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@SmallTest
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun activityLaunches() {
    }

    @Test
    fun testNavGraph() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        runOnUiThread {
            navController.setGraph(R.navigation.nav_graph)
        }
    }
}
