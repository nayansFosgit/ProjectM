package com.example.projectm20

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class PurchaseE2ETestFlowScenario {

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
        waitForScreenTransition {
            // Login actions
            composeTestRule.onNodeWithContentDescription("user-name").performTextInput("standard_user")
            composeTestRule.onNodeWithContentDescription("password").performTextInput("secret_sauce")
            composeTestRule.onNodeWithContentDescription("login-button").performClick()

            // Log the screen after transition
            logScreen("Showcase Screen")
        }
    }

    private fun selectItemAndAddToCart(itemName: String) {
        waitForScreenTransition {
            // Item selection and cart addition actions
            composeTestRule.onNodeWithText(itemName).performClick()
            composeTestRule.onNodeWithText("Add to cart").performClick()
            composeTestRule.onNodeWithContentDescription("Back to products").performClick()

            // Log the screen after transition
            logScreen("Showcase Screen")
        }
    }

    private fun verifyCartQuantity(expectedQuantity: Int) {
        waitForScreenTransition {
            // Cart quantity verification
            val cartQuantityNode = composeTestRule.onNodeWithText("Cart Quantity: $expectedQuantity")
            cartQuantityNode.assertIsDisplayed()

            // Log the screen after transition
            logScreen("Cart Screen")
        }
    }

    private fun startCheckout() {
        waitForScreenTransition {
            // Start checkout action
            composeTestRule.onNodeWithContentDescription("Cart").performClick()
            composeTestRule.onNodeWithText("Checkout").performClick()

            // Log the screen after transition
            logScreen("Address Screen")
        }
    }

    private fun fillAddress() {
        waitForScreenTransition {
            // Address filling actions
            composeTestRule.onNodeWithContentDescription("First Name").performTextInput("Standard")
            composeTestRule.onNodeWithContentDescription("Last Name").performTextInput("user")
            composeTestRule.onNodeWithContentDescription("Zip/Postal Code").performTextInput("123456")
            composeTestRule.onNodeWithText("Continue").performClick()

            // Log the screen after transition
            logScreen("Confirm Purchase Screen")
        }
    }

    private fun confirmPurchase() {
        waitForScreenTransition {
            // Confirm purchase action
            composeTestRule.onNodeWithText("Finish").performClick()

            // Log the screen after transition
            logScreen("Order Success Screen")
        }
    }

    private fun verifyOrderSuccess() {
        waitForScreenTransition {
            val expectedMessage = "Thank you for your order! your order has been dispatched, and will arrive just as fast as the pony can get there!"
            val confirmPurchaseNode = composeTestRule.onNodeWithText(expectedMessage)

            // Assertion to check if the node is displayed
            confirmPurchaseNode.assertIsDisplayed()

            println("Purchase was successful")
        }
    }

    private fun waitForScreenTransition(action: () -> Unit) {
        action.invoke()
        // Wait for Compose to become idle
        composeTestRule.waitForIdle()

        // Semaphore to wait for screen transition completion
        if (!ScreenUtils.screenTransitionSemaphore.tryAcquire(5, TimeUnit.SECONDS)) {
            // Handle case where the screen transition is taking too long (no network, etc.)
            throw Exception("Screen transition took too long. Check your network connection.")
        }
    }

    private fun logScreen(screenName: String) {
        // Log the screen transition
        println("Transitioned to: $screenName")
    }

    private object ScreenUtils {
        val screenTransitionSemaphore = Semaphore(0)
    }
}
