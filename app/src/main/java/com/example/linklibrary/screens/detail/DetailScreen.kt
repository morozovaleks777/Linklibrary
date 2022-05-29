package com.example.linklibrary.screens.detail

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.linklibrary.components.LinkLibraryAppBar
import com.example.linklibrary.components.NoteRow
import com.example.linklibrary.model.MBook
import com.example.linklibrary.navigation.AppScreens
import com.example.linklibrary.screens.home.HomeScreenViewModel
import com.example.ssjetpackcomposeswipeableview.SwipeDirection
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun DetailScreen(navController: NavController,viewModel: HomeScreenViewModel = hiltViewModel(),
                 swipeDirection: SwipeDirection){

    val squareSize = if (swipeDirection == SwipeDirection.BOTH) 60.dp else 120.dp
    val swipeAbleState = rememberSwipeableState(initialValue = 0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = when(swipeDirection) {
        SwipeDirection.LEFT -> mapOf(0f to 0, -sizePx to 1)
        SwipeDirection.RIGHT -> mapOf(0f to 0, sizePx to 1)
        else -> mapOf(0f to 0, sizePx to 1, -sizePx to 2)
    }

    
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


                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)

                ) {

                    // listOfLinks= viewModel.dataFromDb.value.data!!

                    items(items = HomeScreenViewModel.linkList.value) { item ->

                        NoteRow(
//                                Modifier
//                                    .swipeable(
//                                        state = swipeAbleState,
//                                        anchors = anchors,
//                                        thresholds = { _, _ ->
//                                            FractionalThreshold(0.3f)
//                                        },
//                                        orientation = Orientation.Horizontal
//                                    )
//                                    .offset {
//                                        IntOffset(
//                                            swipeAbleState.offset.value.roundToInt(), 0
//                                        )
//                                    },

                                title = item.title!!,
                                note = item.notes!!,
                                onNoteClicked = {
                                    navController.navigate(AppScreens.WebViewScreen.name)
                                    HomeScreenViewModel.link.value = item.notes!!
                                })
                        }

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






