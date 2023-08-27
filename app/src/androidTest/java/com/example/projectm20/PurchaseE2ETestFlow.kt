package com.example.projectm20

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PurchaseE2ETestFlow {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testPurchaseFlow() {
        login()
        selectItemAndAddToCart("Sauce Labs Backpack")
        selectItemAndAddToCart("Sauce Labs Bike Light")
        verifyCartQuantity(2)
        startCheckout()
        fillAddress()
        confirmPurchase()
        verifyOrderSuccess()
    }

    private fun login() {
        // Login actions
        composeTestRule.onNodeWithContentDescription("user-name").performTextInput("standard_user")
        composeTestRule.onNodeWithContentDescription("password").performTextInput("secret_sauce")
        composeTestRule.onNodeWithContentDescription("login-button").performClick()
    }

    private fun selectItemAndAddToCart(itemName: String) {
        // Item selection and cart addition actions
        composeTestRule.onNodeWithText(itemName).performClick()
        composeTestRule.onNodeWithText("Add to cart").performClick()
        composeTestRule.onNodeWithContentDescription("Back to products").performClick()
    }

    private fun verifyCartQuantity(expectedQuantity: Int) {
        // Cart quantity verification
//        composeTestRule.onNodeWithText("Cart Quantity: $expectedQuantity").assertIsDisplayed()
        val cartQuantityNode = composeTestRule.onNodeWithText("Cart Quantity: $expectedQuantity")
        cartQuantityNode.assertIsDisplayed()
    }

    private fun startCheckout() {
        // Start checkout action
        composeTestRule.onNodeWithContentDescription("Cart").performClick()
        composeTestRule.onNodeWithText("Checkout").performClick()
    }

    private fun fillAddress() {
        // Address filling actions
        composeTestRule.onNodeWithContentDescription("First Name").performTextInput("Standard")
        composeTestRule.onNodeWithContentDescription("Last Name").performTextInput("user")
        composeTestRule.onNodeWithContentDescription("Zip/Postal Code").performTextInput("123456")
        composeTestRule.onNodeWithText("Continue").performClick()
    }

    private fun confirmPurchase() {
        // Confirm purchase action
        composeTestRule.onNodeWithText("Finish").performClick()
    }

    private fun verifyOrderSuccess() {
        val expectedMessage = "Thank you for your order! your order has been dispatched, and will arrive just as fast as the pony can get there!"
        val confirmPurchaseNode = composeTestRule.onNodeWithText(expectedMessage)

        // Use the assertion to check if the node is displayed
        confirmPurchaseNode.assertIsDisplayed()

        println("Purchase was successful")
    }

}

