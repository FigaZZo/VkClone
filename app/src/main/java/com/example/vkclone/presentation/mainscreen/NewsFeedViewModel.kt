package com.example.vkclone.presentation.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.vkclone.data.RepositoryImpl
import com.example.vkclone.domain.FeedPost
import com.example.vkclone.domain.GetVkPostUseCase
import com.example.vkclone.domain.StatisticItem
import kotlinx.coroutines.flow.MutableStateFlow

class NewsFeedViewModel: ViewModel() {
    private val getVkPostUseCase = GetVkPostUseCase(RepositoryImpl)
    private val initialList = getVkPostUseCase()
    private val _newsFeedScreenState = MutableStateFlow<NewsFeedScreenState>(NewsFeedScreenState.Initial)
    val newsFeedScreenState = _newsFeedScreenState.asLiveData()

    init {
        getPosts()
    }

    fun getPosts() {
        val newState = NewsFeedScreenState.FeedPosts(initialList)
        _newsFeedScreenState.value = newState
    }

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
        when (_newsFeedScreenState.value) {
            is NewsFeedScreenState.Initial -> {
                error("Wrong screen state")
            }

            is NewsFeedScreenState.FeedPosts -> {
                val newList =
                    (_newsFeedScreenState.value as NewsFeedScreenState.FeedPosts).posts.toMutableList()
                newList.replaceAll() {
                    if (it.id == newPost.id) {
                        newPost
                    } else {
                        it
                    }
                }
                _newsFeedScreenState.value = NewsFeedScreenState.FeedPosts(newList)
            }
        }
    }

    fun remove(post: FeedPost) {
        when (_newsFeedScreenState.value) {
            is NewsFeedScreenState.Initial -> {
                error("Wrong screen state")
            }

            is NewsFeedScreenState.FeedPosts -> {
                val newList =
                    (_newsFeedScreenState.value as NewsFeedScreenState.FeedPosts).posts.toMutableList()
                newList.removeIf { it.id == post.id }
                _newsFeedScreenState.value = NewsFeedScreenState.FeedPosts(newList)
            }
        }

    }
}