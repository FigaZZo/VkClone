package com.example.vkclone.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatisticItem(
    val type: StatisticType,
    val count: Int = 0
): Parcelable

@Parcelize
enum class StatisticType: Parcelable {
    VIEWS, COMMENTS, SHARES, LIKES
}