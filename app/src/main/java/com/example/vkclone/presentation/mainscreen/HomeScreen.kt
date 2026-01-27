package com.example.vkclone.presentation.mainscreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.vkclone.domain.FeedPost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    setTopBar: (@Composable () -> Unit) -> Unit,
    onPressComment: (FeedPost) -> Unit,
) {
    val viewmodel = ViewModelProvider(LocalViewModelStoreOwner.current!!)[NewsFeedViewModel::class]
    LaunchNewsFeed(
        innerPadding,
        viewmodel,
        onPressComment,
        setTopBar
    )
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
                    SweepableNewsFeedCard(
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