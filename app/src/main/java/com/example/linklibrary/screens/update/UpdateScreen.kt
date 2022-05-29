package com.example.linklibrary.screens.update

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.linklibrary.components.*
import com.example.linklibrary.model.MBook
import com.example.linklibrary.navigation.AppScreens
import com.google.firebase.firestore.FirebaseFirestore

//var title=""
//var note=""
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun UpdateScreen(navController: NavController,context: Context= LocalContext.current){
    val isList= remember {
        mutableStateOf(true)
    }
    Scaffold(topBar = {
        LinkLibraryAppBar(title = "linkLibrary update screen", navController = navController)
    }){

      EnterTextForm(navController = navController, book = MBook(), context = context, isList = isList){
          Toast.makeText(context,"onenter",Toast.LENGTH_SHORT).show()
      }


    }
}
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun EnterTextForm(context: Context,
                  isList:MutableState<Boolean>,
                  navController: NavController,
                  book:MBook,
                  onEnter: (String) -> Unit = {}) {

    val listOfBooks = remember{
        mutableStateOf(mutableListOf<MBook>())
    }


    Column {
        val enterTitleState = rememberSaveable { mutableStateOf("") }
        val enterLinkState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val validTitle = remember(enterTitleState.value) {
            enterTitleState.value.trim().isNotEmpty()
        }


        EnterFields(
            enterTitleState,
            validTitle,
            onEnter,
           // book,
            listOfBooks,
            keyboardController,
            enterLinkState,
          //  validLink,
          //  list
        )

        ComponentButton(navController = navController, text = "save all", onClick = {
            isList.value=true
    saveToFirebase(
        listData = listOfBooks.value,
        navController = navController,
        context = context,
    isList = isList.value, data = book)
    navController.navigate(AppScreens.ActionChoice.name)
})
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(items = listOfBooks.value ) {it ->

                    NoteRow(note =it.notes!! ,
                        title= it.title!!,
                       book=it,
                        onNoteClicked =  {
                         isList.value=false

                        saveToFirebase(
                            isList = isList.value,
                            data=MBook(title = it.title, notes = it.notes),
                            navController = navController,
                             context = context, listData = listOfBooks.value)

                    })
                }

            }

        }






        }
var title=""
var note=""

@ExperimentalComposeUiApi
@Composable
private fun EnterFields(
    enterTitleState: MutableState<String>,
    validTitle: Boolean,
    onEnter: (String) -> Unit,
    listOfBooks: MutableState<MutableList<MBook>>,
    keyboardController: SoftwareKeyboardController?,
    enterLinkState: MutableState<String>,
) {
    InputField(valueState = enterTitleState,
        labelId = "enter title",
        enabled = true,
        onAction = KeyboardActions {
            if (!validTitle) return@KeyboardActions
            onEnter(enterTitleState.value.trim())
         title =enterTitleState.value
            enterTitleState.value = ""
           keyboardController?.hide()
            Log.d("Test", "EnterFields1:link  listOfBooks.value : ${listOfBooks.value}")
            Log.d("Test", "EnterFields1:link  t : $title")


        })

    InputField(valueState = enterLinkState,
        labelId = "enter link",
        enabled = true,
        onAction = KeyboardActions {
           if (title=="") return@KeyboardActions
            onEnter(enterLinkState.value.trim())
            note = enterLinkState.value
           listOfBooks.value.add(MBook(title = title, notes = note))
            Log.d("Test", "EnterFields1:link  t : $title")
            Log.d("Test", "EnterFields:link  listOfBooks.value : ${listOfBooks.value}")
            enterLinkState.value = ""
            title=""
            note=""
          keyboardController?.hide()

        })
}


fun  saveToFirebase(
    listData:List<MBook>,
    data:MBook, navController: NavController, context:Context,
    isList: Boolean
) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")
    when  {
      isList ->
            for (i in listData.indices)
                if (data.toString().isNotEmpty()) {
                    dbCollection.add(listData[i])
                        .addOnSuccessListener { documentRef ->
                            val docId = documentRef.id
                            dbCollection.document(docId)
                                .update(hashMapOf("id" to docId) as Map<String, Any>)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        navController.popBackStack()
                                        Toast.makeText(context,"saved",Toast.LENGTH_SHORT).show()
                                    }
                                }.addOnFailureListener {
                                    Log.w("Error", "SaveToFirebase:  Error updating doc",it )
                                }
                        }
                }else { Toast.makeText(context,"not saved",Toast.LENGTH_SHORT).show() }

        !isList -> {

            if (data.toString().isNotEmpty()) {
                dbCollection.add(data)
                    .addOnSuccessListener { documentRef ->
                        val docId = documentRef.id
                        dbCollection.document(docId)
                            .update(hashMapOf("id" to docId) as Map<String, Any>)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                   // navController.popBackStack()
                                    Toast.makeText(context,"saved",Toast.LENGTH_SHORT).show()
                                }
                            }.addOnFailureListener {
                                Log.w("Error", "SaveToFirebase:  Error updating doc",it )
                            }
                    }
            }else { Toast.makeText(context,"not saved",Toast.LENGTH_SHORT).show() }
        }
        else -> {}
    }
}


