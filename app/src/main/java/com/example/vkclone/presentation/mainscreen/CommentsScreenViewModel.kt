package com.example.vkclone.presentation.mainscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.vkclone.data.RepositoryImpl
import com.example.vkclone.domain.FeedPost
import com.example.vkclone.domain.GetCommentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class CommentsScreenViewModel(
    post: FeedPost
): ViewModel() {
    private val getCommentsUseCase = GetCommentsUseCase(RepositoryImpl)
    private val initialComments = getCommentsUseCase()

    private val _commentsScreenState = MutableStateFlow<CommentsScreenState>(CommentsScreenState.Initial)
    val commentsScreenState = _commentsScreenState.asLiveData()

    init {
        loadComments(post)
        Log.d("CommentsScreenViewModel", initialComments.joinToString(" "))
    }

    fun loadComments(post: FeedPost) {
        _commentsScreenState.value = CommentsScreenState.Comments(post, initialComments)
    }
}