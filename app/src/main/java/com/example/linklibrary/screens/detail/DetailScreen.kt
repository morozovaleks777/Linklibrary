package com.example.linklibrary.screens.detail

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.linklibrary.components.LinkLibraryAppBar
import com.example.linklibrary.components.NoteRow
import com.example.linklibrary.model.MBook
import com.example.linklibrary.navigation.AppScreens
import com.example.linklibrary.screens.home.HomeScreenViewModel

@Composable
fun DetailScreen(navController: NavController,viewModel: HomeScreenViewModel = hiltViewModel()){
    
var listOfLinks= emptyList<MBook>()

    Scaffold(topBar = {
        LinkLibraryAppBar(title = "Links",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController){
            navController.navigate(AppScreens.HomeScreen.name)
        }
    }) {

        Surface(modifier = Modifier
            .padding(3.dp)
            .fillMaxSize()) {
            Column(modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {



            LazyColumn(modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ){

                // listOfLinks= viewModel.dataFromDb.value.data!!

                    items(items = HomeScreenViewModel.linkList.value) { item ->

       NoteRow(
           title=item.title!!,
           note = item.notes!!,
           onNoteClicked = {
             navController.navigate(AppScreens.WebViewScreen.name)
               HomeScreenViewModel.link.value= item.notes!!
           },
           list = listOfLinks )}

                        
                        }





                }


            }


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






