package com.example.vkclone.domain

data class FeedPost(
    val id: Int,
    val groupName: String,
    val groupProfile: Int,
    val postTime: String,
    val postText: Int,
    val imageLink: Int,
    val statistics: List<StatisticItem>
)