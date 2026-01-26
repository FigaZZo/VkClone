package com.example.vkclone.presentation.mainscreen

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.vkclone.domain.FeedPost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    innerPadding: PaddingValues,
    setTopBar: (@Composable () -> Unit) -> Unit,
) {
    var post: FeedPost? by remember {
        mutableStateOf(null)
    }

    if (post == null) {
        val viewmodel = ViewModelProvider(LocalViewModelStoreOwner.current!!)[NewsFeedViewModel::class]
        LaunchNewsFeed(
            innerPadding,
            viewmodel,
            {
                post = it
            },
            setTopBar
        )
    } else {
        val viewmodel = ViewModelProvider(
            LocalViewModelStoreOwner.current!!,
            CommentsScreenViewModelFactory(post!!)
        )[CommentsScreenViewModel::class]
        LaunchCommentScreen(
            innerPadding,
            viewmodel,
            post!!,
            {
                post = null
            },
            setTopBar
        )
    }
}

@Composable
fun LaunchNewsFeed(
    innerPadding: PaddingValues,
    viewmodel: NewsFeedViewModel,
    onPressComment: (FeedPost) -> Unit,
    setTopBar: (@Composable () -> Unit) -> Unit,
) {
    val screenState = viewmodel.newsFeedScreenState.observeAsState(NewsFeedScreenState.Initial)

    setTopBar {}

    when (screenState.value) {
        is NewsFeedScreenState.FeedPosts -> {
            val posts = (screenState.value as NewsFeedScreenState.FeedPosts).posts
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(
                    items = posts,
                    key = { it.id }
                ) { post ->
                    SwipablePostCard(
                        post,
                        onPressStatistics = { post, item ->
                            viewmodel.updateWatched(post, item)
                        },
                        onPressComment = onPressComment,
                        onDeletePost = { post ->
                            viewmodel.remove(post)
                        }
                    )
                }
            }
        }

        is NewsFeedScreenState.Initial -> {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchCommentScreen(
    innerPadding: PaddingValues,
    viewmodel: CommentsScreenViewModel,
    post: FeedPost,
    onBackPressed: () -> Unit,
    setTopBar: (@Composable () -> Unit) -> Unit,
) {
    val screenState = viewmodel.commentsScreenState.observeAsState(CommentsScreenState.Initial)

    setTopBar {
        TopAppBar(
            title = {
                Text(text = "Comments for FeedPost Id: ${post.id}")
            },
            navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(
                        imageVector = Icons.Filled.ChevronLeft,
                        contentDescription = null
                    )
                }
            }
        )
    }

    when (screenState.value) {
        is CommentsScreenState.Comments -> {
            val comments = (screenState.value as CommentsScreenState.Comments).comments
            Log.d("CommentScreenState", comments.joinToString(" "))

            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 72.dp
                )
            ) {
                items(
                    items = comments,
                    key = { it.id }
                ) { comment ->
                    CommentItem(comment)
                }
            }
        }

        CommentsScreenState.Initial -> {

        }
    }
}