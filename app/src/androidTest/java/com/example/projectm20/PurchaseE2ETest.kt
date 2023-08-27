package com.example.projectm20

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PurchaseE2ETest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun testPurchaseFlow() {
        // Step 1: Login Screen
        // Entering the username
        composeTestRule.onNodeWithContentDescription("user-name").performTextInput("standard_user")
        // Entering the password
        composeTestRule.onNodeWithContentDescription("password").performTextInput("secret_sauce")
        // Clicking the login button
        composeTestRule.onNodeWithContentDescription("login-button").performClick()


        // Step 2: Showcase Screen and Item Selection
        composeTestRule.onNodeWithText("Sauce Labs Backpack").performClick()

        // Step 3: Add item to the cart
        composeTestRule.onNodeWithText("Add to cart").performClick()

        // Step 4: Go back to Showcase Screen and add second item
        composeTestRule.onNodeWithContentDescription("Back to products").performClick()

        composeTestRule.onNodeWithText("Sauce Labs Bike Light").performClick()

        composeTestRule.onNodeWithText("Add to cart").performClick()

        // Step 5: Go to Cart Screen and start purchase flow
        composeTestRule.onNodeWithContentDescription("Cart").performClick()

        // Assert quantity displayed by hard coding
        val numberOfItemsInCart = 2
        // Replace below line with actual assertion
        composeTestRule.onNodeWithText("Cart Quantity: $numberOfItemsInCart").assertIsDisplayed()


        composeTestRule.onNodeWithText("Checkout").performClick()

        // Step 6: Address Screen
        // Fill in First Name
        composeTestRule.onNodeWithContentDescription("First Name").performTextInput("Standard")

        // Fill in Last Name
        composeTestRule.onNodeWithContentDescription("Last Name").performTextInput("user")

        // Fill in Zip/Postal Code
        composeTestRule.onNodeWithContentDescription("Zip/Postal Code").performTextInput("123456")

        // Proceed to Confirm Purchase Screen
        composeTestRule.onNodeWithText("Continue").performClick()

        // Step 7: Confirm Purchase Screen
        composeTestRule.onNodeWithText("Finish").performClick()
        // Assert expected behavior

        // Verify the order status on the Confirm Purchase Screen
        val confirmPurchaseNode = composeTestRule.onNodeWithText("Thank you for your order! your order has been dispatched, and will arrive just as fast as the pony can get there!")

        val expectedMessage = "Thank you for your order! your order has been dispatched, and will arrive just as fast as the pony can get there!"
        val actualMessage = confirmPurchaseNode

        // Check if the actual message matches the expected message
        if (expectedMessage.equals(actualMessage)) {
            println("Purchase was successful")
        } else {
            println("Purchase failed")
        }



    }
}

