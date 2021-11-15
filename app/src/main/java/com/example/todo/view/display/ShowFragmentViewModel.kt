package com.example.todo.view.display

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.model.Todo
import com.example.todo.model.TodoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowFragmentViewModel(private val dataSource: TodoDao) :ViewModel() {

     private var _data = MutableLiveData<List<Todo>>()
             val data: LiveData<List<Todo>>
                 get() = _data

    private var _noData =  MutableLiveData<Boolean>()
    val  noData:LiveData<Boolean>
        get() = _noData


    init {
        requestToGetTodos()
     }

    private fun requestToGetTodos(){
        viewModelScope.launch(Dispatchers.IO) {
            getTodoItems()
        }
    }
     private suspend fun getTodoItems(){
             val x  = dataSource.getAllData()
         if(x.isNotEmpty()){
             whenSuccess(x)
         }else{
             noData()
         }
     }

    private suspend fun whenSuccess(todos: List<Todo>) {
        withContext(Dispatchers.Main){
            _data.value = todos
            _noData.value = false
        }
    }
    private suspend fun noData(){
        withContext(Dispatchers.Main){
            _noData.value = true
        }
    }
    fun itemIDToDelete(itemPosition: Int) {
        val data = _data.value?.get(itemPosition)
        viewModelScope.launch {
            deleteTodoItem(data!!.id)
        }
    }
    private suspend fun deleteTodoItem(id: Int) {
        dataSource.delete(id)
        whenDeleted(id)
    }
    private suspend fun whenDeleted(itemID: Int) {
        withContext(Dispatchers.Main){
            _data.value =  _data.value!!.filter {
                it.id != itemID
            }
        }
    }

}