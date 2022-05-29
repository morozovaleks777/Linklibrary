package com.example.linklibrary.repository

import android.util.Log
import androidx.navigation.NavController
import com.example.linklibrary.data.DataOrException
import com.example.linklibrary.model.MBook
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import android.widget.Toast

import androidx.core.content.ContextCompat.startActivity

import com.example.linklibrary.MainActivity

import android.content.Intent

import androidx.annotation.NonNull
import androidx.core.content.ContextCompat

import com.google.android.gms.tasks.OnCompleteListener




class FireRepository @Inject constructor(
    private val queryBook: Query
) {
    suspend fun getAllBooksFromDatabase(): DataOrException<List<MBook>, Boolean, Exception> {
        val dataOrException = DataOrException<List<MBook>, Boolean, Exception>()

        try {
            dataOrException.loading = true
            dataOrException.data =  queryBook.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(MBook::class.java)!!
            }
            if (!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false


        }catch (exception: FirebaseFirestoreException){
            dataOrException.e = exception
        }
        return dataOrException

    }

fun removeSection(book:MBook){
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")
    book.title?.let {
        dbCollection.document(it).delete().addOnCompleteListener(){}
    }
}

   fun deleteFromFirebase(book: MBook) {


        val db = FirebaseFirestore.getInstance()
        val dbCollection = db.collection("books")

//        if (book.toString().isNotEmpty()){

        book.id?.let {
            dbCollection.document(it).delete()




               .addOnSuccessListener {
     //                  documentRef ->
//                    val docId = documentRef.javaClass
//                    dbCollection.document(docId)
//                        .update(hashMapOf("id" to docId) as Map<String, Any>)
//                        .addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                navController.popBackStack()
//                            }
//
//
//                        }.addOnFailureListener {
//                            Log.w("Error", "SaveToFirebase:  Error updating doc", it)
//                        }

                }
        }


//        }else {
//
//
//
//        }


    }










}
