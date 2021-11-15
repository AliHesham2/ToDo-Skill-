package com.example.todo.view.display

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.model.TodoDao

class ShowFragmentViewModelFactory(private val dataSource: TodoDao, private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShowFragmentViewModel::class.java)) {
            return ShowFragmentViewModel(dataSource) as T
        }
        throw IllegalArgumentException(application.resources.getString(R.string.UN_KNOWN_CLASS))
    }
}
