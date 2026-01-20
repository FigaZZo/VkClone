package com.example.vkclone.data

import com.example.vkclone.R
import com.example.vkclone.domain.FeedPost
import com.example.vkclone.domain.StatisticItem
import com.example.vkclone.domain.StatisticType
import com.example.vkclone.domain.VkRepository

object RepositoryImpl: VkRepository {
    override fun getVkPosts(): List<FeedPost> {
        return List(100) {
            FeedPost(
                it,
                "Group name $it",
                R.drawable.profile_photo,
                "14:00",
                R.string.post_text,
                R.drawable.meme,
                statistics = listOf(
                    StatisticItem(type = StatisticType.VIEWS, 966),
                    StatisticItem(type = StatisticType.SHARES, 7),
                    StatisticItem(type = StatisticType.COMMENTS, 8),
                    StatisticItem(type = StatisticType.LIKES, 27)
                )
            )
        }
    }

}