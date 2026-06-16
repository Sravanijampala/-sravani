package com.example.bulkyo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bulkyo.screens.*

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // --- AUTH & ONBOARDING ---
        composable(route = Screen.Splash.route) {
            SplashScreen(onNavigateToNext = {
                navController.navigate(Screen.Onboarding1.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(route = Screen.AppIntroduction.route) { PlaceholderScreen("App Introduction") { navController.popBackStack() } }
        composable(route = Screen.Onboarding1.route) {
            OnboardingPagerScreen(onFinish = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Onboarding1.route) { inclusive = true }
                }
            })
        }
        composable(route = Screen.Onboarding2.route) { PlaceholderScreen("Onboarding 2") { navController.popBackStack() } }
        composable(route = Screen.Onboarding3.route) { PlaceholderScreen("Onboarding 3") { navController.popBackStack() } }
        
        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.HomeDashboard.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onForgotPassword = { navController.navigate(Screen.ForgotPassword.route) },
                onSignUp = { navController.navigate(Screen.SignUp.route) }
            )
        }
        composable(route = Screen.SignUp.route) {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate(Screen.HomeDashboard.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onLogin = { navController.navigate(Screen.Login.route) }
            )
        }
        composable(route = Screen.ForgotPassword.route) { PlaceholderScreen("Forgot Password") { navController.popBackStack() } }
        composable(route = Screen.OtpVerification.route) { PlaceholderScreen("OTP Verification") { navController.popBackStack() } }
        composable(route = Screen.CreateProfile.route) { PlaceholderScreen("Create Profile") { navController.popBackStack() } }

        // --- DASHBOARD & MAIN ---
        composable(route = Screen.HomeDashboard.route) {
            ModernHomeDashboard(
                onSearch = { navController.navigate(Screen.Search.route) },
                onCategoryClick = { navController.navigate(Screen.Categories.route) },
                onItemClick = { id -> 
                    when(id) {
                        "ai_assistant" -> navController.navigate(Screen.AIAssistant.route)
                        "reports" -> navController.navigate(Screen.Reports.route)
                        "leaderboard" -> navController.navigate(Screen.Leaderboard.route)
                        else -> navController.navigate(Screen.ItemDetails.createRoute(id))
                    }
                },
                onProfileClick = { navController.navigate(Screen.UserProfile.route) },
                onCartClick = { navController.navigate(Screen.Cart.route) },
                onNotificationsClick = { navController.navigate(Screen.Notifications.route) },
                onSavedClick = { navController.navigate(Screen.Favorites.route) }
            )
        }
        composable(route = Screen.Search.route) { 
            SearchScreen(
                onBack = { navController.popBackStack() },
                onItemClick = { itemId -> navController.navigate(Screen.ItemDetails.createRoute(itemId)) }
            ) 
        }
        composable(route = Screen.Categories.route) {
            CategoryListScreen(
                onCategoryClick = { category -> navController.navigate(Screen.CategoryDetails.createRoute(category)) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = Screen.CategoryDetails.route) { backStackEntry ->
            val catId = backStackEntry.arguments?.getString("categoryId") ?: ""
            CategoryDetailsScreen(
                categoryName = catId,
                onBack = { navController.popBackStack() },
                onItemClick = { itemId -> navController.navigate(Screen.ItemDetails.createRoute(itemId)) }
            )
        }
        composable(route = Screen.ItemDetails.route) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            ItemDetailsScreen(
                itemId = itemId,
                onBack = { navController.popBackStack() },
                onAddToCart = { navController.navigate(Screen.Cart.route) }
            )
        }

        // --- COMMUNICATION ---
        composable(route = Screen.Notifications.route) { NotificationsScreen(onBack = { navController.popBackStack() }) }
        composable(route = Screen.Messages.route) { 
            ChatListScreen(
                onChatClick = { chatId -> navController.navigate(Screen.IndividualChat.createRoute(chatId)) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = Screen.ChatList.route) { 
            ChatListScreen(
                onChatClick = { chatId -> navController.navigate(Screen.IndividualChat.createRoute(chatId)) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = Screen.IndividualChat.route) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            IndividualChatScreen(chatId = chatId, onBack = { navController.popBackStack() })
        }
        composable(route = Screen.VoiceCall.route) { PlaceholderScreen("Voice Call") { navController.popBackStack() } }
        composable(route = Screen.VideoCall.route) { PlaceholderScreen("Video Call") { navController.popBackStack() } }

        // --- USER PROFILE & SETTINGS ---
        composable(route = Screen.UserProfile.route) {
            UserProfileScreen(
                onEditProfile = { navController.navigate(Screen.EditProfile.route) },
                onSettings = { navController.navigate(Screen.Settings.route) },
                onLogout = { navController.navigate(Screen.LogoutConfirmation.route) },
                onOrdersClick = { navController.navigate(Screen.OrderHistory.route) },
                onPaymentClick = { navController.navigate(Screen.PaymentMethods.route) },
                onReviewsClick = { navController.navigate(Screen.Reviews.route) },
                onFeedbackClick = { navController.navigate(Screen.FeedbackForm.route) }
            )
        }
        composable(route = Screen.EditProfile.route) { PlaceholderScreen("Edit Profile") { navController.popBackStack() } }
        composable(route = Screen.Settings.route) { SettingsScreen(onBack = { navController.popBackStack() }) }
        composable(route = Screen.PrivacySettings.route) { PlaceholderScreen("Privacy Settings") { navController.popBackStack() } }
        composable(route = Screen.SecuritySettings.route) { PlaceholderScreen("Security Settings") { navController.popBackStack() } }
        composable(route = Screen.ChangePassword.route) { PlaceholderScreen("Change Password") { navController.popBackStack() } }

        // --- SUPPORT ---
        composable(route = Screen.HelpCenter.route) { PlaceholderScreen("Help Center") { navController.popBackStack() } }
        composable(route = Screen.FAQ.route) { PlaceholderScreen("FAQ") { navController.popBackStack() } }
        composable(route = Screen.ContactSupport.route) { PlaceholderScreen("Contact Support") { navController.popBackStack() } }
        composable(route = Screen.FeedbackForm.route) { 
            FeedbackFormScreen(onBack = { navController.popBackStack() }) 
        }

        // --- SHOPPING & ORDERS ---
        composable(route = Screen.Favorites.route) { 
            FavoritesScreen(
                onBack = { navController.popBackStack() },
                onItemClick = { itemId -> navController.navigate(Screen.ItemDetails.createRoute(itemId)) }
            ) 
        }
        composable(route = Screen.RecentActivity.route) { PlaceholderScreen("Recent Activity") { navController.popBackStack() } }
        composable(route = Screen.Reviews.route) { 
            ReviewsScreen(
                onBack = { navController.popBackStack() },
                onAddReview = { navController.navigate(Screen.AddReview.route) }
            ) 
        }
        composable(route = Screen.AddReview.route) { 
            AddReviewScreen(onBack = { navController.popBackStack() }) 
        }
        composable(route = Screen.Wishlist.route) { PlaceholderScreen("Wishlist") { navController.popBackStack() } }
        composable(route = Screen.Cart.route) {
            CartScreen(
                onCheckout = { navController.navigate(Screen.Checkout.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = Screen.Checkout.route) { 
            CheckoutScreen(
                onAddPayment = { navController.navigate(Screen.PaymentMethods.route) },
                onBack = { navController.popBackStack() },
                onOrderPlaced = {
                    navController.navigate(Screen.PaymentSuccess.route) {
                        popUpTo(Screen.HomeDashboard.route) { inclusive = false }
                    }
                }
            )
        }
        composable(route = Screen.AddressManagement.route) { PlaceholderScreen("Address Management") { navController.popBackStack() } }
        composable(route = Screen.PaymentMethods.route) { 
            AddPaymentDetailsScreen(
                onBack = { navController.popBackStack() },
                onSuccess = { navController.popBackStack() }
            )
        }
        composable(route = Screen.PaymentSuccess.route) { 
            PaymentSuccessScreen(onContinue = {
                navController.navigate(Screen.HomeDashboard.route) {
                    popUpTo(Screen.HomeDashboard.route) { inclusive = true }
                }
            })
        }
        composable(route = Screen.OrderSummary.route) { PlaceholderScreen("Order Summary") { navController.popBackStack() } }
        composable(route = Screen.OrderTracking.route) { TrackingScreen { navController.popBackStack() } }
        composable(route = Screen.OrderHistory.route) { OrderHistoryScreen { navController.popBackStack() } }
        composable(route = Screen.Coupons.route) { PlaceholderScreen("Coupons") { navController.popBackStack() } }

        // --- BUSINESS & MEMBERSHIP ---
        composable(route = Screen.SubscriptionPlans.route) { PlaceholderScreen("Subscription Plans") { navController.popBackStack() } }
        composable(route = Screen.MembershipDashboard.route) { PlaceholderScreen("Membership Dashboard") { navController.popBackStack() } }
        composable(route = Screen.AnalyticsOverview.route) { ReportsScreen { navController.popBackStack() } }
        composable(route = Screen.Reports.route) { ReportsScreen { navController.popBackStack() } }
        composable(route = Screen.Calendar.route) { PlaceholderScreen("Calendar") { navController.popBackStack() } }
        composable(route = Screen.ScheduleManagement.route) { PlaceholderScreen("Schedule Management") { navController.popBackStack() } }
        composable(route = Screen.BookingScreen.route) { PlaceholderScreen("Booking Screen") { navController.popBackStack() } }
        composable(route = Screen.BookingConfirmation.route) { PlaceholderScreen("Booking Confirmation") { navController.popBackStack() } }

        // --- TOOLS & MEDIA ---
        composable(route = Screen.LocationMap.route) { PlaceholderScreen("Location Map") { navController.popBackStack() } }
        composable(route = Screen.GPSTracking.route) { PlaceholderScreen("GPS Tracking") { navController.popBackStack() } }
        composable(route = Screen.FileUpload.route) { PlaceholderScreen("File Upload") { navController.popBackStack() } }
        composable(route = Screen.DocumentViewer.route) { PlaceholderScreen("Document Viewer") { navController.popBackStack() } }
        composable(route = Screen.MediaGallery.route) { PlaceholderScreen("Media Gallery") { navController.popBackStack() } }
        composable(route = Screen.CameraIntegration.route) { PlaceholderScreen("Camera Integration") { navController.popBackStack() } }
        composable(route = Screen.QRScanner.route) { PlaceholderScreen("QR Scanner") { navController.popBackStack() } }

        // --- AI & COMMUNITY ---
        composable(route = Screen.AIAssistant.route) { AIAssistantScreen { navController.popBackStack() } }
        composable(route = Screen.VoiceAssistant.route) { PlaceholderScreen("Voice Assistant") { navController.popBackStack() } }
        composable(route = Screen.CommunityFeed.route) { PlaceholderScreen("Community Feed") { navController.popBackStack() } }
        composable(route = Screen.PostCreation.route) { PlaceholderScreen("Post Creation") { navController.popBackStack() } }
        composable(route = Screen.Leaderboard.route) { LeaderboardScreen { navController.popBackStack() } }
        composable(route = Screen.Achievements.route) { PlaceholderScreen("Achievements") { navController.popBackStack() } }
        composable(route = Screen.ReferralProgram.route) { PlaceholderScreen("Referral Program") { navController.popBackStack() } }

        // --- SYSTEM ---
        composable(route = Screen.AdminDashboard.route) { PlaceholderScreen("Admin Dashboard") { navController.popBackStack() } }
        composable(route = Screen.LogoutConfirmation.route) { PlaceholderScreen("Logout Confirmation") { navController.popBackStack() } }
        composable(route = Screen.SuccessThankYou.route) { PlaceholderScreen("Success Thank You") { navController.popBackStack() } }
    }
}
