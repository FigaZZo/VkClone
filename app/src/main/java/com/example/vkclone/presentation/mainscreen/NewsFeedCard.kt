package com.example.vkclone.presentation.mainscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxDefaults
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vkclone.R
import com.example.vkclone.domain.FeedPost
import com.example.vkclone.domain.StatisticItem
import com.example.vkclone.domain.StatisticType
import com.example.vkclone.ui.theme.VkCloneTheme
import kotlinx.coroutines.delay

val testFeedPost = FeedPost(
    -1,
    "Group name -1",
    R.drawable.profile_photo,
    "14: 00",
    R.string.post_text,
    R.drawable.meme,
    statistics = listOf(
        StatisticItem(type = StatisticType.VIEWS, 966),
        StatisticItem(type = StatisticType.SHARES, 7),
        StatisticItem(type = StatisticType.COMMENTS, 8),
        StatisticItem(type = StatisticType.LIKES, 27)
    )
)

val testFunction1 = fun(a: FeedPost, b: StatisticItem) {}
val testFunction2 = fun(a: FeedPost) {}

@Preview
@Composable
fun NewsFeedCardLightTheme() {
    VkCloneTheme(dynamicColor = false) {
        NewsFeedCard(testFeedPost, testFunction1, testFunction2)
    }
}

@Preview
@Composable
fun NewsFeedCardDarkTheme() {
    VkCloneTheme(true, false) {
        NewsFeedCard(testFeedPost, testFunction1, testFunction2)
    }
}

@Composable
fun SweepableNewsFeedCard(
    post: FeedPost,
    onPressStatistics: (FeedPost, StatisticItem) -> Unit,
    onDeletePost: (FeedPost) -> Unit,
    onPressComment: (FeedPost) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val swipeState = rememberSwipeToDismissBoxState(
        SwipeToDismissBoxValue.Settled,
        positionalThreshold = SwipeToDismissBoxDefaults.positionalThreshold,
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(isRemoved) {
        if (isRemoved) {
            delay(500)
            onDeletePost(post)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = 500),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            swipeState,
            { DeleteBackground(swipeState) },
        ) {
            NewsFeedCard(post, onPressStatistics, onPressComment)
        }
    }
}

@Composable
private fun DeleteBackground(
    swipeDismissState: SwipeToDismissBoxState
) {
    val (colorStart, colorEnd) = if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        Color.LightGray to Color.Red
    } else Color.Transparent to Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .clip(RoundedCornerShape(10.dp))
            .drawBehind {
                drawRect(lerp(colorStart, colorEnd, swipeDismissState.progress))
            },
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
private fun NewsFeedCard(
    post: FeedPost,
    onPressStatistics: (FeedPost, StatisticItem) -> Unit,
    onPressComment: (FeedPost) -> Unit
) {
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondary),
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .padding(8.dp)
        ) {
            HeaderPost(post)
            Spacer(Modifier.height(10.dp))
            Text(
                text = stringResource(post.postText),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.height(10.dp))
            Image(
                painter = painterResource(post.imageLink),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(5.dp))
            StatisticsPost(post, onPressStatistics, onPressComment)
        }
    }
}

@Composable
private fun HeaderPost(post: FeedPost) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(post.groupProfile),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .height(60.dp)
        )
        Spacer(Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = post.groupName,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = post.postTime,
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = null,
//            modifier = Modifier.height(60.dp)
        )
    }
}

@Composable
private fun StatisticsPost(
    post: FeedPost,
    onPressStatistics: (FeedPost, StatisticItem) -> Unit,
    onPressComment: (FeedPost) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        post.statistics.forEach {
            when (it.type) {
                StatisticType.SHARES -> Row(modifier = Modifier.weight(0.5f)) {
                    IconWithText(
                        Icons.Outlined.Share,
                        it,
                    ) {
                        onPressStatistics(post, it)
                    }
                }
                StatisticType.COMMENTS -> Row(modifier = Modifier.weight(0.5f)) {
                    IconWithText(
                        Icons.AutoMirrored.Filled.Comment,
                        it,
                    ) {
                        onPressComment(post)
                    }
                }
                StatisticType.LIKES -> Row(modifier = Modifier.weight(0.5f)) {
                    IconWithText(
                        Icons.Outlined.FavoriteBorder,
                        it,
                    ) {
                        onPressStatistics(post, it)
                    }
                }

                StatisticType.VIEWS -> Row(modifier = Modifier.weight(1.5f)) {
                    IconWithText(
                        Icons.Filled.RemoveRedEye,
                        it,
                    ) {
                        onPressStatistics(post, it)
                    }
                }
            }
        }
    }
}

@Composable
private fun IconWithText(
    imageVector: ImageVector,
    item: StatisticItem,
    onPressStatistics: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(true){
                onPressStatistics()
            }
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
        )
        Text(
            text = item.count.toString(),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}