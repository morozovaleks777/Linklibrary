package com.example.linklibrary.navigation

import java.lang.IllegalArgumentException

enum class AppScreens {
    DetailScreen,
    HomeScreen,
    SplashScreen,
    LoginScreen,
    ActionChoice,
    UpdateScreen,
    WebViewScreen,
    InfoScreen
    ;
    companion object{
        fun fromRoute(route:String?):AppScreens=when(route?.substringBefore("/")){
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            HomeScreen.name -> HomeScreen
           // SearchScreen.name -> SearchScreen
            DetailScreen.name -> DetailScreen
            UpdateScreen.name -> UpdateScreen
           WebViewScreen.name -> WebViewScreen
            ActionChoice.name ->ActionChoice
            InfoScreen.name ->InfoScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }

}