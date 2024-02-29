package com.example.deloitte_android_test.presentation.navigation

sealed class Destinations(val route: String) {
    data object Main : Destinations("main_route")
    data object Details : Destinations("details_route")
    data object WishList : Destinations("wish_list_route")
    data object Basket : Destinations("basket_route")
}
