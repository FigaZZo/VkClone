package com.example.vkclone.domain

interface VkRepository {
    fun getVkPosts(): List<FeedPost>
}