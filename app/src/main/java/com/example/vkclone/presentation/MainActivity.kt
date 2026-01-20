package com.example.vkclone.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.vkclone.ui.theme.VkCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkCloneTheme(dynamicColor = false) {
                Scaffold(
                    bottomBar = { BottomNavigationBar() },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    val viewmodel = ViewModelProvider(this)[MainViewModel::class]
                    val posts = viewmodel.posts.observeAsState(listOf())
                    LazyColumn(modifier = Modifier.padding(innerPadding)) {
                        items(
                            items = posts.value,
                            key = { it.id }
                        ) { post ->
                            SwipablePostCard(
                                post,
                                onPressStatistics = { post, item ->
                                    viewmodel.updateWatched(post, item)
                                },
                                onDeletePost = { post ->
                                    viewmodel.remove(post)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Favourite,
        NavigationItem.Profile
    )
    var selectedItem = remember {
        mutableStateOf(NavigationItem.Home.titleResId)
    }
    NavigationBar() { 
        items.forEach {item ->
            NavigationBarItem(
                selected = item.titleResId == selectedItem.value,
                onClick = { selectedItem.value = item.titleResId },
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(text = stringResource(id = item.titleResId)) },
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VkCloneTheme {
        Greeting("Android")
    }
}