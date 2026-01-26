package com.example.vkclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    onNewsFeedScreen: @Composable () -> Unit,
    onFavoriteScreen: @Composable () -> Unit,
    onProfileScreen: @Composable () -> Unit,
) {
    NavHost(navHostController, Screen.NewsFeed.route) {
        composable(Screen.NewsFeed.route) {
            onNewsFeedScreen()
        }
        composable(Screen.Favorite.route) {
            onFavoriteScreen()
        }
        composable(Screen.Profile.route) {
            onProfileScreen()
        }
    }
}