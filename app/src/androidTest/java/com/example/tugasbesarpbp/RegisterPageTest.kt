package com.example.tugasbesarpbp


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
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
class RegisterPageTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(RegisterPage::class.java)

    @Test
    fun registerPageTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.btnFromRegisterPage), withText("REGISTER"),
                childAtPosition(
                    allOf(
                        withId(R.id.mainLayoutRegisterPage),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText = onView(
            allOf(
                withId(R.id.tietUsernameRegister),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldUsernameUpdate),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("dini"), closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton2 = onView(
            allOf(
                withId(R.id.btnFromRegisterPage), withText("REGISTER"),
                childAtPosition(
                    allOf(
                        withId(R.id.mainLayoutRegisterPage),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.tietPasswordRegister),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldPasswordRegister),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("123"), closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton3 = onView(
            allOf(
                withId(R.id.btnFromRegisterPage), withText("REGISTER"),
                childAtPosition(
                    allOf(
                        withId(R.id.mainLayoutRegisterPage),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.tietEmailRegister),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldEmailRegister),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("dini"), closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton4 = onView(
            allOf(
                withId(R.id.btnFromRegisterPage), withText("REGISTER"),
                childAtPosition(
                    allOf(
                        withId(R.id.mainLayoutRegisterPage),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.tietEmailRegister), withText("dini"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldEmailRegister),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("dini@gmail.com"))
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.tietEmailRegister), withText("dini@gmail.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldEmailRegister),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton5 = onView(
            allOf(
                withId(R.id.btnFromRegisterPage), withText("REGISTER"),
                childAtPosition(
                    allOf(
                        withId(R.id.mainLayoutRegisterPage),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.tietDateRegister),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldDateRegister),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton6 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton6.perform(scrollTo(), click())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton7 = onView(
            allOf(
                withId(R.id.btnFromRegisterPage), withText("REGISTER"),
                childAtPosition(
                    allOf(
                        withId(R.id.mainLayoutRegisterPage),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        materialButton7.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.tietNoTelpRegister),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldNoTelpRegister),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText7.perform(replaceText("123"), closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton8 = onView(
            allOf(
                withId(R.id.btnFromRegisterPage), withText("REGISTER"),
                childAtPosition(
                    allOf(
                        withId(R.id.mainLayoutRegisterPage),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        materialButton8.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val materialTextView = onView(
            allOf(
                withId(com.example.awesomedialog.R.id.yesButton), withText("Okay"),
                childAtPosition(
                    allOf(
                        withId(R.id.mainLayout),
                        childAtPosition(
                            withClassName(`is`("android.widget.RelativeLayout")),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())
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
