package com.umc6th.kioki.tutorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umc6th.kioki.R

class TutorialViewModel : ViewModel() {
    private val sideMenuItems = listOf(
        SideMenu(R.drawable.fries_regular, "프렌치프라이(R)", 0),
        SideMenu(R.drawable.fries_large, "프렌치프라이(L)", 500),
        SideMenu(R.drawable.cheesestick, "치즈스틱", 400),
        SideMenu(R.drawable.hashbrown, "해쉬브라운", 0),
        SideMenu(R.drawable.nugget_four, "너겟킹 4조각", 100),
        SideMenu(R.drawable.onion_ring, "어니언링", 300),
        SideMenu(R.drawable.shaking_fries, "쉐이킹프라이", 300),
        SideMenu(R.drawable.cheese_ball, "치즈볼", 400),
        SideMenu(R.drawable.nuggat_wing, "너겟윙", 300),
        SideMenu(R.drawable.coleslaw, "코울슬로", 0),
        SideMenu(R.drawable.corn_salad, "콘샐러드", 0)
    )
    private val drinkMenuItems = listOf(
        SideMenu(R.drawable.coke_regular, "코카콜라(R)", 0),
        SideMenu(R.drawable.coke_large, "코카콜라(L)", 200),
        SideMenu(R.drawable.deep_choco, "핫)기라델리 딥초코 교환", 1300),
        SideMenu(R.drawable.cokezero_regular, "코카콜라 제로(R)", 0),
        SideMenu(R.drawable.cokezero_large, "코카콜라 제로(L)", 200),
        SideMenu(R.drawable.sprite_regular, "스프라이트(R)", 0),
        SideMenu(R.drawable.sprite_large, "스프라이트(L)", 200),
        SideMenu(R.drawable.spritezero_regular, "스프라이트 제로(R)", 0),
        SideMenu(R.drawable.spritezero_large, "스프라이트 제로(L)", 200),
        SideMenu(R.drawable.americano, "아메리카노", 0),
        SideMenu(R.drawable.orange, "미닛메이드 오렌지", 800),
        SideMenu(R.drawable.water, "순수(미네랄워터)", 0)
    )


    private val _currentScreen = MutableLiveData<TutorialScreen>()
    val currentScreen: LiveData<TutorialScreen> = _currentScreen

    private val _sideMenus = MutableLiveData<List<SideMenu>>(sideMenuItems)
    val sideMenus: LiveData<List<SideMenu>> = _sideMenus

    private val _drinkMenus = MutableLiveData<List<SideMenu>>(drinkMenuItems)
    val drinkMenus: LiveData<List<SideMenu>> = _drinkMenus

    private val _selectedMenu = MutableLiveData<MenuItem>()
    val selectedMenuItem: LiveData<MenuItem> = _selectedMenu

    private val _selectedOption = MutableLiveData<MenuOption>()
    val selectedOption: LiveData<MenuOption> = _selectedOption

    private val _selectedSideMenu = MutableLiveData<SideMenu>()
    val selectedSideMenu: LiveData<SideMenu> = _selectedSideMenu

    private val _selectedDrinkMenu = MutableLiveData<SideMenu>()
    val selectedDrinkMenu: LiveData<SideMenu> = _selectedDrinkMenu

    private val _selectedTotalMenu = MutableLiveData<List<MenuItem>>(emptyList())
    val selectedTotalMenu: LiveData<List<MenuItem>> = _selectedTotalMenu

    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int> = _totalPrice

    val totalOrderPrice = MutableLiveData<Int>(0)

    fun setScreen(screen: TutorialScreen) {
        _currentScreen.value = screen
    }

    fun setMenuItem(menuItem: MenuItem) {
        _selectedMenu.value = menuItem
    }

    fun setCheckedOption(option: MenuOption) {
        _selectedOption.value = option
        updateTotalPrice()
    }

    fun selectSideMenu(position: Int) {
        val currentList = _sideMenus.value?.mapIndexed { index, sideMenu ->
            sideMenu.copy(isSelected = index == position) // 선택된 사이드 메뉴를 true로 설정
        }
        _sideMenus.value = currentList!!
        _selectedSideMenu.value = currentList[position]
        updateTotalPrice()
    }

    fun selectDrinkMenu(position: Int) {
        val currentList = _drinkMenus.value?.mapIndexed { index, drinkMenu ->
            drinkMenu.copy(isSelected = index == position) // 선택된 음료를 true로 설정
        }
        _drinkMenus.value = currentList!!
        _selectedDrinkMenu.value = currentList[position]
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val menuItemPrice = _selectedOption.value?.price ?: 0
        val sidePrice = _sideMenus.value?.filter { it.isSelected }?.sumOf { it.price } ?: 0
        val drinkPrice = _drinkMenus.value?.filter { it.isSelected }?.sumOf { it.price } ?: 0
        _totalPrice.value = menuItemPrice + sidePrice + drinkPrice
    }

    fun addTotalMenu() {
        _selectedTotalMenu.value = _selectedTotalMenu.value?.plus(
            MenuItem(
                imageRes = _selectedMenu.value!!.imageRes,
                name = _selectedMenu.value!!.name,
                price = _totalPrice.value!!,
                count = 1
            )
        )
        _totalPrice.value = 0
    }

    fun removeSelectedMenu(index: Int) {
        val currentList = _selectedTotalMenu.value?.toMutableList()
        currentList?.removeAt(index)
        _selectedTotalMenu.value = currentList?: emptyList()
    }

    fun setOrderPrice(orderPrice: Int) {
        totalOrderPrice.value = orderPrice
    }
}

sealed class TutorialScreen {
    data object Main : TutorialScreen()
    data object StepOne : TutorialScreen()
    data object StepTwo : TutorialScreen()
}
