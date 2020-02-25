import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import co.androidbaseappkotlinmvvm.MovieMainActivity
import co.androidbaseappkotlinmvvm.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MovieAppTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MovieMainActivity>(MovieMainActivity::class.java)

    @Test
    fun initApp() {
        //check main activity is displayed
        onView(withId(R.id.main_activity)).check(matches(isDisplayed()))
    }
}