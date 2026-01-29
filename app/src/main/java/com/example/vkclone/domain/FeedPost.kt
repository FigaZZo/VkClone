package com.example.vkclone.domain

import android.os.Parcelable
import com.example.vkclone.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedPost(
    val id: Int,
    val groupName: String = "Group name",
    val groupProfile: Int = R.drawable.profile_photo,
    val postTime: String = "14:00",
    val postText: Int = R.string.post_text,
    val imageLink: Int = R.drawable.meme,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(type = StatisticType.VIEWS, 966),
        StatisticItem(type = StatisticType.SHARES, 7),
        StatisticItem(type = StatisticType.COMMENTS, 8),
        StatisticItem(type = StatisticType.LIKES, 27)
    )
): Parcelable