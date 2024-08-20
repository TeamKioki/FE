package com.umc6th.kioki.tutorial

data class MenuItem(
    val imageRes: Int,
    val name: String,
    val price: Int,
    val count: Int = 1
)

enum class MenuOption(val price: Int) {
    LARGE_SET(11_500),
    SET(10_800),
    SINGLE(9300)
}

data class SideMenu(
    val imageRes: Int,
    val name: String,
    val price: Int,
    val isSelected: Boolean = false
)

data class DrinkMenu(
    val imageRes: Int,
    val name: String,
    val price: Int,
    val isSelected: Boolean = false
)