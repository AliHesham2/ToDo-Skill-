package com.example.todo.view.edit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.model.TodoDao

class EditFragmentViewModelFactory(private val todo: Int, private val dataSource: TodoDao, private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditFragmentViewModel::class.java)) {
            return EditFragmentViewModel(todo,dataSource,application) as T
        }
        throw IllegalArgumentException(application.resources.getString(R.string.UN_KNOWN_CLASS))
    }
}
