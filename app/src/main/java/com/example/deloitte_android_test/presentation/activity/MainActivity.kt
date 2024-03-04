package com.example.deloitte_android_test.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.deloitte_android_test.presentation.navigation.BottomNavigationItem
import com.example.deloitte_android_test.presentation.navigation.NavGraph
import com.example.deloitte_android_test.presentation.viewmodel.BadgeCountViewModel
import com.example.deloitte_android_test.ui.theme.DeloitteandroidtestTheme
import com.example.deloitte_android_test.utils.DataHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeloitteandroidtestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    BottomNavigationBar()

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    viewModel: BadgeCountViewModel = hiltViewModel()
) {


    var badgeCount by remember {
        mutableStateOf(Pair(0, 0))
    }

    val handler by viewModel.badgeCount.collectAsStateWithLifecycle(
        initialValue = DataHandler.SUCCESS(
            Pair(0, 0)
        )
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.getItemCount()
    }

    when (handler) {
        is DataHandler.LOADING -> {}

        is DataHandler.SUCCESS -> {
            handler.data?.let {
                badgeCount = it
            }

        }

        is DataHandler.ERROR -> {}

    }

    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                BottomNavigationItem().bottomNavigationItems()
                    .forEachIndexed { index, navigationItem ->

                        NavigationBarItem(
                            selected = index == navigationSelectedItem,
                            label = {
                                Text(navigationItem.label)
                            },
                            icon = {

                                BadgedBox(
                                    badge = {
                                        if (index == 1) {
                                            Badge {
                                                Text(badgeCount.first.toString())
                                            }
                                        }
                                        if (index == 2) {
                                            Badge {
                                                Text(badgeCount.second.toString())
                                            }
                                        }
                                    }) {
                                    Icon(
                                        navigationItem.icon,
                                        contentDescription = navigationItem.label
                                    )
                                }
                                Icon(
                                    navigationItem.icon,
                                    contentDescription = navigationItem.label
                                )

                            },
                            onClick = {
                                navigationSelectedItem = index
                                navController.navigate(navigationItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
            }
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues = paddingValues)
        )
    }


}

