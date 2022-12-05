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
class BillTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(SplashScreen::class.java)

    @Test
    fun billTest() {
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

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.menu_bill), withContentDescription("Bill"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomnav),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton2 = onView(
            allOf(
                withId(R.id.AddBillButton), withText("ADD BILL"),
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
        materialButton2.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton3 = onView(
            allOf(
                withId(R.id.btnAddBillButton), withText("ADD BILL"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.tietAddBillName),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldAddBillName),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("Listrik"), closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton4 = onView(
            allOf(
                withId(R.id.btnAddBillButton), withText("ADD BILL"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.tietAddBillDate),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldAddBillDate),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("1/12/2022"), closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton5 = onView(
            allOf(
                withId(R.id.btnAddBillButton), withText("ADD BILL"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.tietAddBillAmount),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldAddBillAmount),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(replaceText("10"), closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton6 = onView(
            allOf(
                withId(R.id.btnAddBillButton), withText("ADD BILL"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton6.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val recyclerView = onView(
            allOf(
                withId(R.id.rv_bill),
                childAtPosition(
                    withClassName(`is`("android.widget.LinearLayout")),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.tietDetailBillName), withText("Listrik"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldDetailBillName),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(replaceText(""))
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.tietDetailBillName),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldDetailBillName),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText7.perform(closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText8 = onView(
            allOf(
                withId(R.id.tietDetailBillDate), withText("1/12/2022"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldDetailBillDate),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText8.perform(replaceText(""))
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText9 = onView(
            allOf(
                withId(R.id.tietDetailBillDate),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldDetailBillDate),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText9.perform(closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText10 = onView(
            allOf(
                withId(R.id.tietDetailBillAmount), withText("10.0"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldDetailBillAmount),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText10.perform(replaceText(""))
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText11 = onView(
            allOf(
                withId(R.id.tietDetailBillAmount),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldDetailBillAmount),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText11.perform(closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton7 = onView(
            allOf(
                withId(R.id.btnUpdateBill), withText("UPDATE BILL"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        4
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton7.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText12 = onView(
            allOf(
                withId(R.id.tietDetailBillName),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldDetailBillName),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText12.perform(replaceText("Air"), closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton8 = onView(
            allOf(
                withId(R.id.btnUpdateBill), withText("UPDATE BILL"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        4
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton8.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText13 = onView(
            allOf(
                withId(R.id.tietDetailBillDate),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldDetailBillDate),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText13.perform(replaceText("22/12/2022"), closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton9 = onView(
            allOf(
                withId(R.id.btnUpdateBill), withText("UPDATE BILL"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        4
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton9.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText14 = onView(
            allOf(
                withId(R.id.tietDetailBillAmount),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.outlinedTextFieldDetailBillAmount),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText14.perform(replaceText("50"), closeSoftKeyboard())
        onView(isRoot()).perform(waitFor(3000))

        val materialButton10 = onView(
            allOf(
                withId(R.id.btnUpdateBill), withText("UPDATE BILL"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        4
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton10.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val recyclerView2 = onView(
            allOf(
                withId(R.id.rv_bill),
                childAtPosition(
                    withClassName(`is`("android.widget.LinearLayout")),
                    1
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, click()))
        onView(isRoot()).perform(waitFor(3000))

        val materialButton11 = onView(
            allOf(
                withId(R.id.btnDeleteBill), withText("DELETE BILL"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        4
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton11.perform(click())
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
