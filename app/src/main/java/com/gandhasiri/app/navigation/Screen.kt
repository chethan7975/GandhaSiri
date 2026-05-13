package com.gandhasiri.app.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object TreeRegister : Screen("tree_register")
    object TreeList : Screen("tree_list")
    object TreeDetails : Screen("tree_details/{treeId}") {
        fun createRoute(treeId: String) = "tree_details/$treeId"
    }
    object TreeMap : Screen("tree_map")
    object AiAssistant : Screen("ai_assistant")
    object Profile : Screen("profile")
}
