package com.example.deloitte_android_test.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.deloitte_android_test.presentation.screen.DetailsScreen
import com.example.deloitte_android_test.presentation.screen.MainScreen



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
            MainScreen { id ->
                navController.navigate(Destinations.Details.route + "/${id}")
            }
        }
        composable(
            route = Destinations.Details.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { navBackStackEntry ->

            val id = navBackStackEntry.arguments?.getString("id")
            id?.let {
                DetailsScreen(id = it, onShowMainScreen = {
                    navController.navigate(Destinations.Main.route)
                })
            }

        }
    }
}



