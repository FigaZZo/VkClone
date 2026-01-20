package com.example.vkclone.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.vkclone.data.RepositoryImpl
import com.example.vkclone.domain.GetVkPostUseCase
import com.example.vkclone.domain.FeedPost
import com.example.vkclone.domain.StatisticItem
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel: ViewModel() {
    private val getVkPostUseCase = GetVkPostUseCase(RepositoryImpl)
    private val initialList = getVkPostUseCase()

    private val _posts = MutableStateFlow(initialList)
    val posts = _posts.asLiveData()

    fun updateWatched(post: FeedPost, item: StatisticItem) {
        val oldStatistics = post.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        val newPost = post.copy(statistics = newStatistics)
        val newList = _posts.value.toMutableList()
        newList.replaceAll() {
            if (it.id == newPost.id) {
                newPost
            } else {
                it
            }
        }
        _posts.value = newList
    }

    fun remove(post: FeedPost) {
        val newList = _posts.value.toMutableList()
        newList.removeIf { it.id == post.id }
        _posts.value = newList
    }
}