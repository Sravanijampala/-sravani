import time
import unittest
from appium import webdriver
from appium.options.android import UiAutomator2Options
from appium.webdriver.common.appiumby import AppiumBy

class BulkyoAppiumTests(unittest.TestCase):
    
    @classmethod
    def setUpClass(cls):
        """
        Setup capabilities and initialize the Appium driver session.
        App: Bulkyo (com.example.bulkyo)
        """
        options = UiAutomator2Options()
        options.platform_name = "Android"
        options.device_name = "Android_Emulator"
        options.automation_name = "UiAutomator2"
        options.app_package = "com.example.bulkyo"
        options.app_activity = "com.example.bulkyo.MainActivity"
        options.no_reset = False
        options.new_command_timeout = 300
        
        # Connect to the local Appium server (usually running on port 4723)
        print("Connecting to Appium Server...")
        cls.driver = webdriver.Remote("http://127.0.0.1:4723", options=options)
        cls.driver.implicitly_wait(10)

    @classmethod
    def tearDownClass(cls):
        """
        Close session after all tests complete.
        """
        if cls.driver:
            print("Terminating Appium Driver Session...")
            cls.driver.quit()

    def test_01_splash_screen_flow(self):
        """
        Test Case ID: TC-001 / TC-003
        Verifies the App starts, splash screen displays the main launcher button,
        and clicking 'Get Started' advances to onboarding.
        """
        print("Executing: test_01_splash_screen_flow")
        
        # Wait for the splash screen button (using Jetpack Compose contentDescription or text)
        # In Compose, components can be found by accessibility labels, text content, or xpath.
        get_started_btn = self.driver.find_element(
            by=AppiumBy.ANDROID_UIAUTOMATOR,
            value='new UiSelector().text("Get Started")'
        )
        self.assertIsNotNone(get_started_btn, "Get Started button should be visible on Splash screen")
        
        # Click button to advance to onboarding
        get_started_btn.click()
        time.sleep(1.5)

    def test_02_onboarding_swipe_flow(self):
        """
        Test Case ID: TC-006 to TC-013
        Tests the Onboarding Pager screens. Taps the skip/finish buttons.
        """
        print("Executing: test_02_onboarding_swipe_flow")
        
        # Locate the onboarding bottom button ("Skip to Login" / "Get Started")
        action_btn = self.driver.find_element(
            by=AppiumBy.ANDROID_UIAUTOMATOR,
            value='new UiSelector().textContains("Get Started")'
        )
        
        # If not visible directly, look for Skip button
        if not action_btn:
            action_btn = self.driver.find_element(
                by=AppiumBy.ANDROID_UIAUTOMATOR,
                value='new UiSelector().textContains("Skip")'
            )
            
        self.assertIsNotNone(action_btn, "Action button on Onboarding should be present")
        action_btn.click()
        time.sleep(1.5)

    def test_03_login_validation_flow(self):
        """
        Test Case ID: TC-017 / TC-018
        Verifies validation logic on empty fields.
        """
        print("Executing: test_03_login_validation_flow")
        
        # Click login button directly to trigger error
        login_btn = self.driver.find_element(
            by=AppiumBy.ANDROID_UIAUTOMATOR,
            value='new UiSelector().text("Login")'
        )
        login_btn.click()
        time.sleep(1)
        
        # Verify validation error is displayed
        error_text = self.driver.find_element(
            by=AppiumBy.ANDROID_UIAUTOMATOR,
            value='new UiSelector().text("Please enter both email and password")'
        )
        self.assertIsNotNone(error_text, "Validation error message should appear for empty fields")

    def test_04_successful_login_flow(self):
        """
        Test Case ID: TC-020
        Verifies login with valid credentials navigates to the Home Dashboard.
        """
        print("Executing: test_04_successful_login_flow")
        
        # Enter credentials
        email_field = self.driver.find_element(
            by=AppiumBy.ANDROID_UIAUTOMATOR,
            value='new UiSelector().text("Email")'
        )
        email_field.send_keys("testuser@bulkyo.com")
        
        password_field = self.driver.find_element(
            by=AppiumBy.ANDROID_UIAUTOMATOR,
            value='new UiSelector().text("Password")'
        )
        password_field.send_keys("SecurePass123")
        
        # Click login
        login_btn = self.driver.find_element(
            by=AppiumBy.ANDROID_UIAUTOMATOR,
            value='new UiSelector().text("Login")'
        )
        login_btn.click()
        time.sleep(2)
        
        # Verify transition to Home Dashboard by searching for Search bar placeholder
        search_prompt = self.driver.find_element(
            by=AppiumBy.ANDROID_UIAUTOMATOR,
            value='new UiSelector().textContains("Search")'
        )
        self.assertIsNotNone(search_prompt, "User should be redirected to the Home Dashboard upon login")

    def test_05_ecommerce_flow(self):
        """
        Test Case ID: TC-056 to TC-058
        Verifies searching for an item, entering details page, adjusting quantity,
        adding it to the Cart, and viewing Cart.
        """
        print("Executing: test_05_ecommerce_flow")
        
        # Click Search tab / bar
        search_prompt = self.driver.find_element(
            by=AppiumBy.ANDROID_UIAUTOMATOR,
            value='new UiSelector().textContains("Search")'
        )
        search_prompt.click()
        time.sleep(1.5)
        
        # Input Search item
        search_input = self.driver.find_element(
            by=AppiumBy.ANDROID_UIAUTOMATOR,
            value='new UiSelector().text("Search items...")'
        )
        search_input.send_keys("Catering Buffet")
        time.sleep(1.5)
        
        # Click on the matching card
        item_card = self.driver.find_element(
            by=AppiumBy.ANDROID_UIAUTOMATOR,
            value='new UiSelector().textContains("Catering")'
        )
        item_card.click()
        time.sleep(2)
        
        # Check details screen and click '+' button twice
        plus_btn = self.driver.find_element(
            by=AppiumBy.ANDROID_UIAUTOMATOR,
            value='new UiSelector().text("+")'
        )
        plus_btn.click()
        plus_btn.click()
        time.sleep(0.5)
        
        # Add to cart
        add_to_cart_btn = self.driver.find_element(
            by=AppiumBy.ANDROID_UIAUTOMATOR,
            value='new UiSelector().text("Add to Cart")'
        )
        add_to_cart_btn.click()
        time.sleep(2)
        
        # Verify in Cart Screen
        checkout_btn = self.driver.find_element(
            by=AppiumBy.ANDROID_UIAUTOMATOR,
            value='new UiSelector().text("Proceed to Checkout")'
        )
        self.assertIsNotNone(checkout_btn, "Should be on Cart Screen showing 'Proceed to Checkout'")

if __name__ == "__main__":
    unittest.main()
