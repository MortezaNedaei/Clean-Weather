package com.mooncascade.home

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.mooncascade.R
import com.mooncascade.launchFragmentInHiltContainer
import com.mooncascade.presentation.ui.home.HomeFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@SmallTest
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Test
    fun launchFragmentAndVerifyUI() {

        launchFragmentInHiltContainer<HomeFragment>()

        onView(withId(R.id.tv_next_days_forecasts))
            .check(
                matches(
                    withText(
                        ApplicationProvider.getApplicationContext<Context>()
                            .getString(R.string.tv_next_days)
                    )
                )
            );
    }
}
