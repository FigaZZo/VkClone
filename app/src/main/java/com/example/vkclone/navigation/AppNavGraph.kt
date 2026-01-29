package com.example.vkclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vkclone.domain.FeedPost

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    onNewsFeedScreen: @Composable () -> Unit,
    onCommentsScreen: @Composable (FeedPost) -> Unit,
    onFavoriteScreen: @Composable () -> Unit,
    onProfileScreen: @Composable () -> Unit,
) {
    NavHost(navController = navHostController, startDestination = Screen.Home.route) {
        homeNavGraph(navHostController, onNewsFeedScreen, onCommentsScreen)
        composable(Screen.Favorite.route) {
            onFavoriteScreen()
        }
        composable(Screen.Profile.route) {
            onProfileScreen()
        }
    }
}