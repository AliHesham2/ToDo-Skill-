package com.example.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.CustomCardViewBinding
import com.example.todo.model.Todo

class Adapter (private val onClickListener: OnClickListener) :
    ListAdapter<Todo, Adapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: CustomCardViewBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Todo) {
            binding.data = data
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        return ViewHolder(CustomCardViewBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data= getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(data)
        }
        holder.bind(data)
    }

    class OnClickListener(val clickListener: (data: Todo) -> Unit) {
        fun onClick(data: Todo) = clickListener(data)
    }

}