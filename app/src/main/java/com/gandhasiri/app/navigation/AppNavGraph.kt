package com.gandhasiri.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gandhasiri.app.presentation.auth.LoginScreen
import com.gandhasiri.app.presentation.auth.RegisterScreen
import com.gandhasiri.app.presentation.home.HomeScreen
import com.gandhasiri.app.presentation.splash.SplashScreen
import com.gandhasiri.app.presentation.tree_register.TreeRegisterScreen
import com.gandhasiri.app.presentation.ai.AiAssistantScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.TreeRegister.route) {
            TreeRegisterScreen(navController)
        }
        composable(Screen.TreeMap.route) {
            com.gandhasiri.app.presentation.home.TreeMapScreen(navController)
        }
        composable(
            route = Screen.TreeDetails.route,
            arguments = listOf(androidx.navigation.navArgument("treeId") {
                type = androidx.navigation.NavType.StringType
            })
        ) { backStackEntry ->
            val treeId = backStackEntry.arguments?.getString("treeId") ?: ""
            com.gandhasiri.app.presentation.tree_details.TreeDetailsScreen(navController, treeId)
        }
        composable(Screen.AiAssistant.route) {
            AiAssistantScreen(navController)
        }
        composable(Screen.Profile.route) {
            com.gandhasiri.app.presentation.profile.ProfileScreen(navController)
        }
    }
}
