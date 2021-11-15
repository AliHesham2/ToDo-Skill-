package com.example.todo.view.edit

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.model.Todo
import com.example.todo.model.TodoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditFragmentViewModel(private val todoID: Int, private val dataSource: TodoDao, private val application: Application) : ViewModel() {

    private var _data = MutableLiveData<Todo>()
    val data: LiveData<Todo>
        get() = _data

    private var _navigate =  MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean>
        get() = _navigate

    init {
        requestTodoItem()
    }

    private fun requestTodoItem(){
        viewModelScope.launch(Dispatchers.IO) {
            getTodoItem()
        }
    }

    private suspend  fun getTodoItem(){
       val todoData = dataSource.getDataWithId(todoID)
        whenSuccess(todoData)
    }


    private suspend fun whenSuccess(todos: Todo) {
        withContext(Dispatchers.Main){
            _data.value = todos
        }
    }


    fun update(name: String, deadline: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateItem(Todo(todoID,name,_data.value!!.date,deadline))
        }
    }


    private suspend fun updateItem (todo:Todo){
        dataSource.update(todo)
        whenUpdated()
    }

    private suspend fun whenUpdated(){
        withContext(Dispatchers.Main){
            _navigate.value = true
        }
    }

}