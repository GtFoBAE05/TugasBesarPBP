package com.example.tugasbesarpbp

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test

class ActivityTest {
    class AddEditActivityTest {

        @Rule
        @JvmField
        var mActivityScenarioRule = ActivityScenarioRule(AddEditActivity::class.java)

        @Test
        fun addEditActivityTest() {
            val materialButton = Espresso.onView(
                Matchers.allOf(
                    withId(R.id.btn_save), ViewMatchers.withText("Simpan"),
                    childAtPosition(
                        Matchers.allOf(
                            withId(R.id.ll_button),
                            childAtPosition(
                                ViewMatchers.withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                1
                            )
                        ),
                        1
                    ),
                    ViewMatchers.isDisplayed()
                )
            )
            materialButton.perform(ViewActions.click())
            Espresso.onView(ViewMatchers.isRoot()).perform(waitFor(3000))
            val textInputEditText = Espresso.onView(
                Matchers.allOf(
                    withId(R.id.et_nama),
                    childAtPosition(
                        childAtPosition(
                            withId(R.id.layout_nama),
                            0
                        ),
                        0
                    )
                )
            )
            textInputEditText.perform(
                ViewActions.scrollTo(),
                ViewActions.replaceText("1"),
                ViewActions.closeSoftKeyboard()
            )
            val materialButton2 = Espresso.onView(
                Matchers.allOf(
                    withId(R.id.btn_save), ViewMatchers.withText("Simpan"),
                    childAtPosition(
                        Matchers.allOf(
                            withId(R.id.ll_button),
                            childAtPosition(
                                ViewMatchers.withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                1
                            )
                        ),
                        1
                    ),
                    ViewMatchers.isDisplayed()
                )
            )
            materialButton2.perform(ViewActions.click())
            Espresso.onView(ViewMatchers.isRoot()).perform(waitFor(3000))
            val textInputEditText2 = Espresso.onView(
                Matchers.allOf(
                    withId(R.id.et_npm),
                    childAtPosition(
                        childAtPosition(
                            withId(R.id.layout_npm),
                            0
                        ),
        }