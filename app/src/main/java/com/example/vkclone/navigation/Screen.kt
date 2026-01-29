package com.example.vkclone.navigation

sealed class Screen(
    val route: String
) {
    object NewsFeed : Screen(ROUTE_NEWS_FEED)
    object Favorite : Screen(ROUTE_FAVOURITE)
    object Profile : Screen(ROUTE_PROFILE)
    object Home : Screen(ROUTE_HOME)
    object Comments : Screen(ROUTE_COMMENTS)

    companion object {
        const val KEY_FEED_POST = "feed_post_key"
        private const val ROUTE_NEWS_FEED = "news_feed"
        private const val ROUTE_FAVOURITE = "favourite"
        private const val ROUTE_PROFILE = "profile"
        private const val ROUTE_HOME = "home"
        private const val ROUTE_COMMENTS = "comments"
    }
}