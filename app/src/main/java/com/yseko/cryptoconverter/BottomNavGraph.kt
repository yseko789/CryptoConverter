package com.yseko.cryptoconverter

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = BottomNavBar.Search.route
    ){
        composable(route=BottomNavBar.Search.route){
            SearchScreen()
        }
        composable(route=BottomNavBar.Converter.route){
            ConverterScreen()
        }
    }
}