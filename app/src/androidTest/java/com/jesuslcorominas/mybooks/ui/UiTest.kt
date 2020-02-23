package com.jesuslcorominas.mybooks.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.jesuslcorominas.mybooks.R
import com.jesuslcorominas.mybooks.model.server.GoogleBooks
import com.jesuslcorominas.mybooks.ui.main.MainActivity
import com.jesuslcorominas.mybooks.utils.fromJson
import com.jesuslcorominas.mybooks.utils.withDrawable
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.get

class UiTest : KoinTest {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @get:Rule
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_COARSE_LOCATION"
        )

    // region Test search, navigate to detail and save as favourite
    @Test
    fun clickABookNavigatesToDetail() {
        activityTestRule.launchActivity(null)

        performSearch()
        performClickOnSearchButton()
        performClickOnRecyclerViewSecondItem()

        checkHasNavigatedToDetail()

        checkIsNotFavourite()
        saveAsFavourite()
        checkIsFavourite()
    }

    private fun performSearch() = onView(withId(R.id.editTextSearch))
        .perform(replaceText("El nombre del viento"))

    private fun performClickOnSearchButton() = onView(withId(R.id.imageButtonSearch))
        .perform(click())

    private fun performClickOnRecyclerViewSecondItem() =
        onView(withId(R.id.recyclerViewItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                2,
                click()
            )
        )

    private fun checkHasNavigatedToDetail() = onView(withId(R.id.toolbarDetail))
        .check(matches(hasDescendant(withText("El nombre del viento"))))

    private fun checkIsNotFavourite() = onView(withId(R.id.fabFavourite))
        .check(matches(isDisplayed()))
        .check(matches(withDrawable(R.drawable.ic_favourite_off, tint = R.color.white)))

    private fun saveAsFavourite() = onView(withId(R.id.fabFavourite))
        .perform(click())

    private fun checkIsFavourite() = onView(withId(R.id.fabFavourite))
        .check(matches(isDisplayed()))
        .check(matches(withDrawable(R.drawable.ic_favourite_on, tint = R.color.white)))

    private fun backToMain() = Espresso.pressBack()
    // endregion

    // region Set up
    @Before
    fun setUp() {
        enqueueList()
        enqueueDetail()

        val resource = OkHttp3IdlingResource.create("OkHttp", get<GoogleBooks>().okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    private fun enqueueList() {
        enqueue("elnombredelviento_list.json")
    }

    private fun enqueueDetail() {
        enqueue("elnombredelviento_detail.json")
    }

    private fun enqueue(jsonFile: String) {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson(
                ApplicationProvider.getApplicationContext(),
                jsonFile
            )
        )
    }

    // endregion
}