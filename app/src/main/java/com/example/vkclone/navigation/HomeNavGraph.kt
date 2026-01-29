package com.example.vkclone.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.vkclone.domain.FeedPost

fun NavGraphBuilder.homeNavGraph(
    navHostController: NavHostController,
    onNewsFeedScreen: @Composable () -> Unit,
    onCommentsScreen: @Composable (FeedPost) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            onNewsFeedScreen()
        }
        composable(Screen.Comments.route) {
            Log.d("HomeNavGraph", "${it.arguments}")
            val feedPost = remember {
                navHostController.previousBackStackEntry?.savedStateHandle?.get<FeedPost>(Screen.KEY_FEED_POST)
            } ?: throw RuntimeException("Arg is null")
            onCommentsScreen(feedPost)
        }
    }
}