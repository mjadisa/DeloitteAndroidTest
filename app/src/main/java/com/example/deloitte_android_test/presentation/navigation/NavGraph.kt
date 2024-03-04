package com.example.deloitte_android_test.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.deloitte_android_test.presentation.screen.BasketScreen
import com.example.deloitte_android_test.presentation.screen.MainScreen
import com.example.deloitte_android_test.presentation.screen.WishListScreen


@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier

) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Main.route,
        modifier = modifier
    ) {

        composable(route = Destinations.Main.route) {
            MainScreen()
        }

        composable(
            route = Destinations.WishList.route
        ) {
            WishListScreen()
        }
        composable(
            route = Destinations.Basket.route
        ) {
            BasketScreen()
        }
    }
}



