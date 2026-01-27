package com.example.vkclone.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vkclone.domain.FeedPost
import com.example.vkclone.navigation.AppNavGraph
import com.example.vkclone.navigation.NavigationState
import com.example.vkclone.navigation.rememberNavigationState
import com.example.vkclone.presentation.mainscreen.HomeScreen
import com.example.vkclone.presentation.mainscreen.LaunchCommentScreen
import com.example.vkclone.ui.theme.VkCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navigationState = rememberNavigationState()

            VkCloneTheme(dynamicColor = false) {
                var topBarChangeable = remember {
                    mutableStateOf<@Composable () -> Unit>({})
                }
                var post = remember {
                    mutableStateOf<FeedPost?>(null)
                }
                Scaffold(
                    topBar = topBarChangeable.value,
                    bottomBar = { BottomNavigationBar(navigationState) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppNavGraph(
                        navigationState.navHostController,
                        onNewsFeedScreen = {
                            HomeScreen(
                                innerPadding,
                                setTopBar = { topBarChangeable.value = it },
                                onPressComment = {
                                    post.value = it
                                    navigationState.navigateToComments()
                                }
                            )
                        },
                        onCommentsScreen = {
                            LaunchCommentScreen(
                                innerPadding,
                                post.value ?: error("Wrong post sent"),
                                setTopBar = { topBarChangeable.value = it },
                                onBackPressed = { navigationState.navHostController.popBackStack() }
                            )
                        },
                        onFavoriteScreen = { TextCounter("hello favorite") { topBarChangeable.value = it } },
                        onProfileScreen = { TextCounter("hey profile") { topBarChangeable.value = it } },
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navigationState: NavigationState
) {
    Log.d("BottomNavigationBar", "RECOMPOSITION")
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Favourite,
        NavigationItem.Profile
    )

    val backStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    NavigationBar() {
        items.forEach { item ->
            var selectedItem = backStackEntry?.destination?.hierarchy?.any {
                it.route == item.screen.route
            } ?: false
            NavigationBarItem(
                selected = selectedItem,
                onClick = { navigationState.navigateTo(item.screen.route) },
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(text = stringResource(id = item.titleResId)) },
            )
        }
    }
}

@Composable
private fun TextCounter(
    name: String,
    setTopBar: (@Composable () -> Unit) -> Unit
) {
    Log.d("TextCounter", "RECOMPOSITION")

    var count by rememberSaveable {
        mutableIntStateOf(0)
    }

    setTopBar {}

    Text(
        modifier = Modifier.clickable { count++ },
        text = "$name Count: $count",
        color = Color.Black
    )
}