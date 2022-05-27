package com.example.linklibrary.screens.home

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.linklibrary.components.LinkLibraryAppBar
import com.example.linklibrary.components.NoteRow
import com.example.linklibrary.model.MBook
import com.example.linklibrary.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import java.util.stream.Collectors

@Composable
fun HomeScreen(navController: NavController,
               viewModel: HomeScreenViewModel = hiltViewModel() ){

var listLinks=emptyList <MBook>()
    var listOfBooks :List<MBook>
    val currentUser = FirebaseAuth.getInstance().currentUser

    if (!viewModel.dataFromDb.value.data.isNullOrEmpty()) {
        listOfBooks = viewModel.dataFromDb.value.data!!.toList().filter { mBook ->
            mBook.id == currentUser?.uid.toString()
        }

        Log.d("Books", "HomeContent: ${listOfBooks.toString()}")
    }
    Scaffold(topBar = {
        LinkLibraryAppBar(title = "linkLibrary", navController = navController)}){

        LazyColumn(

            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
          listOfBooks= viewModel.dataFromDb.value.data!!


           val listOfBooks2= viewModel.dataFromDb.value.data!!



           listOfBooks= listOfBooks.stream()
                .distinct()
                .collect(Collectors.toList())

            items(items = listOfBooks) { item ->

                        NoteRow(
                            note = "",
                            title = item.title!!,
                            onNoteClicked = {
HomeScreenViewModel.linkList.value=listOfBooks2.filter { mBook -> mBook.title ==item.title}

                                navController.navigate(AppScreens.DetailScreen.name + "/${item.title}")

                                            },
                            list = listLinks
                        )
                    }
                }

            }

        }









