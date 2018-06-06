package co.androidbaseappkotlinmvvm

import android.support.v4.content.ContextCompat
import android.graphics.drawable.ColorDrawable
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AppTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun testToolbarDesign() {
        //check toolbar is displayed
        onView(withId(R.id.action_bar)).check(matches(isDisplayed()))
    }
}