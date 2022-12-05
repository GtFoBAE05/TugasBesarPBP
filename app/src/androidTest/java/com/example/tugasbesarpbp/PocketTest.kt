package com.example.tugasbesarpbp


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PocketTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(SplashScreen::class.java)

    @Test
    fun pocketTest() {
        val textInputEditText = onView(
            allOf(
                withId(R.id.tietUsernameLogin),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editTextUsernameLogin),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("boni"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.tietPasswordLogin),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldPasswordLogin),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("123"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.btnLogin), withText("LOGIN"),
                childAtPosition(
                    allOf(
                        withId(R.id.mainLayout),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.menu_pocket), withContentDescription("Pocket"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomnav),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton2 = onView(
            allOf(
                withId(R.id.addPocketButton), withText("ADD POCKET"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.pocketFrame),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton3 = onView(
            allOf(
                withId(R.id.btnAddPocket), withText("ADD POCKET"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.addPocketFragment),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val appCompatEditText = onView(
            allOf(
                withId(R.id.etPocketName),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.addPocketFragment),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("Saham"), closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton4 = onView(
            allOf(
                withId(R.id.btnAddPocket), withText("ADD POCKET"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.addPocketFragment),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val prefixSuffixEditText = onView(
            allOf(
                withId(R.id.etPocketBalance),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.addPocketFragment),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        prefixSuffixEditText.perform(replaceText("10"), closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton5 = onView(
            allOf(
                withId(R.id.btnAddPocket), withText("ADD POCKET"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.addPocketFragment),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val recyclerView = onView(
            allOf(
                withId(R.id.rvPocketPage),
                childAtPosition(
                    withClassName(`is`("android.widget.RelativeLayout")),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))
        onView(isRoot()).perform(waitFor(3000))

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.editPocketName), withText("Saham"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText(""))
        onView(isRoot()).perform(waitFor(3000))

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.editPocketName),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val prefixSuffixEditText2 = onView(
            allOf(
                withId(R.id.editPocketBalance), withText("10.0"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        prefixSuffixEditText2.perform(replaceText(""))
        onView(isRoot()).perform(waitFor(3000))

        val prefixSuffixEditText3 = onView(
            allOf(
                withId(R.id.editPocketBalance),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        prefixSuffixEditText3.perform(closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton6 = onView(
            allOf(
                withId(R.id.buttonUpdatePocketDetailPage), withText("UPDATE"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton6.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.editPocketName),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(replaceText("Nikah"), closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton7 = onView(
            allOf(
                withId(R.id.buttonUpdatePocketDetailPage), withText("UPDATE"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton7.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val prefixSuffixEditText4 = onView(
            allOf(
                withId(R.id.editPocketBalance),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        prefixSuffixEditText4.perform(replaceText("99"), closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton8 = onView(
            allOf(
                withId(R.id.buttonUpdatePocketDetailPage), withText("UPDATE"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton8.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val recyclerView2 = onView(
            allOf(
                withId(R.id.rvPocketPage),
                childAtPosition(
                    withClassName(`is`("android.widget.RelativeLayout")),
                    0
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, click()))
        onView(isRoot()).perform(waitFor(3000))

        val materialButton9 = onView(
            allOf(
                withId(R.id.buttonDeletePocketDetailPage), withText("DELETE"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton9.perform(click())
        onView(isRoot()).perform(waitFor(3000))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    fun waitFor(delay:Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for " + delay + " milliseconds"
            }

            override fun perform(uiController: UiController?, view: View?) {
                uiController!!.loopMainThreadForAtLeast(delay)
            }
        }
    }
}
