package com.example.vkclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.homeNavGraph(
    onNewsFeedScreen: @Composable () -> Unit,
    onCommentsScreen: @Composable () -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            onNewsFeedScreen()
        }
        composable(Screen.Comments.route) {
            onCommentsScreen()
        }
    }
}