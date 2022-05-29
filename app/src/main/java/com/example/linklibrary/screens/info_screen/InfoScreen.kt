package com.example.linklibrary.screens.info_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.linklibrary.components.LinkLibraryAppBar
import com.example.linklibrary.model.MBook
import com.example.linklibrary.navigation.AppScreens
import com.example.linklibrary.screens.home.HomeScreenViewModel
import com.example.linklibrary.screens.home.SwipeAbleItemCell
import com.example.ssjetpackcomposeswipeableview.SwipeDirection
import com.google.firebase.auth.FirebaseAuth
import java.util.stream.Collectors

@ExperimentalMaterialApi
@Composable
fun InfoScreen(navController: NavController,viewModel:HomeScreenViewModel= hiltViewModel()){

    val listOfBooks = remember {
        mutableStateOf(listOf<MBook>())
    }
    val currentUser = FirebaseAuth.getInstance().currentUser

    if (!viewModel.dataFromDb.value.data.isNullOrEmpty()) {
        listOfBooks.value = viewModel.dataFromDb.value.data!!.toList().filter { mBook ->
            mBook.id == currentUser?.uid.toString()
        }
}
    Scaffold( topBar = {
        LinkLibraryAppBar(title = "Links",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController){
            navController.navigate(AppScreens.HomeScreen.name)
        }
    }) {

       Column() {
           LazyColumn(
               modifier = Modifier
                   .fillMaxSize()
                   .background(color = Color.Green.copy(alpha = 0.3f))
                   .fillMaxHeight(),

               contentPadding = PaddingValues(25.dp),


               ) {


               listOfBooks.value = viewModel.dataFromDb.value.data!!
               val listOfBooks2 = viewModel.dataFromDb.value.data!!

               listOfBooks.value = listOfBooks.value.stream()
                   .distinct()
                   .collect(Collectors.toList())

               items(items = listOfBooks.value) { item ->

                   SwipeAbleItemCell(
                       number = listOfBooks.value.size,
                       onEditClick = {
                           HomeScreenViewModel.linkList.value =
                               listOfBooks2.filter { mBook -> mBook.title == item.title }
                           navController.navigate(AppScreens.DetailScreen.name + "/${item.title}")
                       },
                       onDeleteClicked = {
                           for (i in listOfBooks2.indices) {
                               if (item.title == listOfBooks2[i].title)
                                   viewModel.delNote(listOfBooks2[i])
                           }
                           viewModel.getAllBooksFromDatabase()
                       },
                       swipeDirection = SwipeDirection.BOTH,
                       title = item.title!!
                   )
               }


           }


       }}}