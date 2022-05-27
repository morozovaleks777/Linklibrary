package com.example.linklibrary.screens.update

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.linklibrary.components.InputField
import com.example.linklibrary.components.LinkLibraryAppBar
import com.example.linklibrary.components.NoteRow
import com.example.linklibrary.model.MBook
import com.google.firebase.firestore.FirebaseFirestore

@ExperimentalComposeUiApi
@Composable
fun UpdateScreen(navController: NavController){
    Scaffold(topBar = {
        LinkLibraryAppBar(title = "linkLibrary update screen", navController = navController)
    }){

      SearchForm(navController = navController, book = MBook())


    }
}
@ExperimentalComposeUiApi
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    hint: String = "Enter",
    navController: NavController,
   // book:MBook= MBook("","",""),
     book:MBook,
    onEnter: (String) -> Unit = {

    }) {

    val listOfBooks = remember{
        mutableStateOf(listOf<MBook>())
    }



    Column {
        val enterLinkState = rememberSaveable { mutableStateOf("") }
        val enterLinkState2 = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(enterLinkState.value) {
            enterLinkState.value.trim().isNotEmpty()
        }
        val valid1 = remember(enterLinkState2.value) {
            enterLinkState.value.trim().isNotEmpty()
        }
        val list= mutableListOf<MBook>()

        InputField(valueState = enterLinkState,
            labelId = "enter title",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onEnter(enterLinkState.value.trim())
               // list.add(MBook(title = enterLinkState.value))

                book.title=enterLinkState.value
               // listOfBooks.value=list
                enterLinkState.value = ""
                keyboardController?.hide()
            })




        InputField(valueState = enterLinkState2,
            labelId = "enter link",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid1) return@KeyboardActions
                onEnter(enterLinkState2.value.trim())
                book.notes=enterLinkState2.value
                list.add(MBook(title = book.title, notes = book.notes))

                listOfBooks.value=list
                enterLinkState2.value = ""
                keyboardController?.hide()
            })

      //  val list: MutableList<String>? =null
       // list?.add(MBook(notes = enterLinkState.value))
//        listOfBooks.value=list

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(items = listOfBooks.value ) {it ->

                    NoteRow(note =it.notes!! , title= it.title!!, list = listOfBooks.value, onNoteClicked =  {


                        saveToFirebase(
                            book=book,
                            navController = navController,

                        )

                    })
                }

            }

        }






        }





fun saveToFirebase(book: MBook, navController: NavController) {


    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")

    if (book.toString().isNotEmpty()){
        dbCollection.add(book)
            .addOnSuccessListener { documentRef ->
                val docId = documentRef.id
                dbCollection.document(docId)
                    .update(hashMapOf("id" to docId) as Map<String, Any>)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.popBackStack()
                        }


                    }.addOnFailureListener {
                        Log.w("Error", "SaveToFirebase:  Error updating doc",it )
                    }

            }


    }else {



    }


}


