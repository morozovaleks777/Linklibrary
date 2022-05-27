package com.example.linklibrary.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.linklibrary.components.LinkLibraryAppBar
import com.example.linklibrary.navigation.AppScreens
import com.example.linklibrary.screens.home.HomeScreenViewModel

@Composable
fun WebViewScreen(navController: NavController){
    Scaffold(topBar = {
        LinkLibraryAppBar(title = "webView",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController){
            navController.navigate(AppScreens.HomeScreen.name)
        }
    }) {
        LoadWebUrl(url = HomeScreenViewModel.link.value.toString())
    }

    
}

@Composable
fun LoadWebUrl(url: String) {

    AndroidView(factory = {
        WebView(it).apply {
            webViewClient = WebViewClient()

            loadUrl(url)
        }
    })
}
