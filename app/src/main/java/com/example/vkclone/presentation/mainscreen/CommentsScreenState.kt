package com.example.vkclone.presentation.mainscreen

import com.example.vkclone.domain.Comment
import com.example.vkclone.domain.FeedPost

sealed class CommentsScreenState {
    object Initial: CommentsScreenState()

    data class Comments(val post: FeedPost, val comments: List<Comment>): CommentsScreenState()
}