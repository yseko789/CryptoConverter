package com.yseko.cryptoconverter

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavBar(
    val route: String,
    val title: String,
    val icon: Int
){
    object Search: BottomNavBar(
        route = "search",
        title="Search",
        icon = R.drawable.ic_baseline_currency_bitcoin_24
    )
    object Converter: BottomNavBar(
        route = "converter",
        title="Converter",
        icon = R.drawable.ic_baseline_compare_arrows_24
    )

}
