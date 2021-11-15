package com.example.todo.view.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.adapter.Adapter
import com.example.todo.model.Todo

@BindingAdapter("todoItems")
fun todoItems(recyclerView: RecyclerView, data: List<Todo>?) {
    val adapter = recyclerView.adapter as Adapter
    adapter.submitList(data)
}


@BindingAdapter("todoItemName")
fun todoItemName(txt:TextView,name:String?) {
       txt.text = name ?: ""
}

@BindingAdapter("todoCreatedDate")
fun todoCreatedDate(txt:TextView,date:String?) {
    txt.text = date ?: ""
}

@BindingAdapter("todoDeadLine")
fun todoDeadLine(txt:TextView,deadline:String?) {
    txt.text = deadline ?: ""
}
