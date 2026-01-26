package com.example.vkclone.domain

interface VkRepository {
    fun getVkPosts(): List<FeedPost>

    fun getComments(): List<Comment>
}