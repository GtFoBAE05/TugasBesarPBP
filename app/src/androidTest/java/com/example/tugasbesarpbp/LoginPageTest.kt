package com.example.tugasbesarpbp


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginPageTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginPage::class.java)

    @Test
    fun loginPageTest() {
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
        textInputEditText.perform(replaceText("dini"), closeSoftKeyboard())

        val materialButton2 = onView(
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
        materialButton2.perform(click())

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

        val materialButton3 = onView(
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
        materialButton3.perform(click())
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
}
