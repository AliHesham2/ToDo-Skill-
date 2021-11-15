package com.example.todo.view.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.model.Todo
import com.example.todo.model.TodoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddFragmentViewModel(private val dataSource:TodoDao) :ViewModel() {

    private var _navigate =  MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean>
        get() = _navigate

     fun getItem(name: String, date: String,currentDate :String) {
         val dataItem = Todo(0,name,currentDate,date)
         viewModelScope.launch {
          addItem(dataItem)
         }
     }

    private suspend  fun addItem(todo: Todo){
        dataSource.insert(todo)
        navigate()
    }
    private suspend fun navigate(){
        withContext(Dispatchers.Main){
            _navigate.value = true
        }
    }

}