package com.example.vkclone.presentation.mainscreen

import com.example.vkclone.domain.Comment
import com.example.vkclone.domain.FeedPost

sealed class NewsFeedScreenState {
    object Initial: NewsFeedScreenState()

    data class FeedPosts(val posts: List<FeedPost>): NewsFeedScreenState()
}