package com.example.linklibrary.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linklibrary.data.DataOrException
import com.example.linklibrary.model.MBook
import com.example.linklibrary.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: FireRepository):ViewModel(

) {
    val dataFromDb: MutableState<DataOrException<List<MBook>, Boolean, Exception>>
            = mutableStateOf(DataOrException(listOf(), true,Exception("")))

    private fun getAllBooksFromDatabase() {
        viewModelScope.launch {
            dataFromDb.value.loading = true
            dataFromDb.value = repository.getAllBooksFromDatabase()
            if (!dataFromDb.value.data.isNullOrEmpty()) dataFromDb.value.loading = false
        }
        Log.d("GET", "getAllBooksFromDatabase: ${dataFromDb.value.data?.toList().toString()}")

    }
    init{getAllBooksFromDatabase()}
companion object{
    var linkList= mutableStateOf(listOf<MBook>())
     var link= mutableStateOf(String())
}
}