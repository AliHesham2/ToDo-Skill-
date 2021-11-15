package com.example.todo.view.add

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.model.TodoDao

class AddFragmentViewModelFactory(
    private val dataSource: TodoDao, private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddFragmentViewModel::class.java)) {
            return AddFragmentViewModel(dataSource) as T
        }
        throw IllegalArgumentException(application.resources.getString(R.string.UN_KNOWN_CLASS))
    }
}
