package com.example.deloitte_android_test.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = "",
    var hasbadge: Boolean = false,
) {

    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "CATALOGUE",
                icon = Icons.Filled.Search,
                route = Destinations.Main.route
            ),
            BottomNavigationItem(
                label = "WISHLIST",
                icon = Icons.Filled.Favorite,
                route = Destinations.WishList.route,
                hasbadge = true
            ),
            BottomNavigationItem(
                label = "BASKET",
                icon = Icons.Filled.ShoppingCart,
                route = Destinations.Basket.route,
                hasbadge = true
            ),
        )
    }


}