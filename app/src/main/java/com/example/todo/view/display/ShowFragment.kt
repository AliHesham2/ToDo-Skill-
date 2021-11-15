package com.example.todo.view.display

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.adapter.Adapter
import com.example.todo.databinding.FragmentShowBinding
import com.example.todo.model.TodoDatabase

class ShowFragment : Fragment() {
    private lateinit var binding : FragmentShowBinding
    private lateinit var viewModel: ShowFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Initialization
         binding = FragmentShowBinding.inflate(inflater)
        val application = requireNotNull(activity).application
        val dataSource = TodoDatabase.getInstance(application).todoDao
        val viewModelFactory = ShowFragmentViewModelFactory(dataSource,application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ShowFragmentViewModel::class.java)
        binding.lifecycleOwner = this
        binding.data = viewModel

            //swap left to delete
       val itemTouchHelperCallback =  object :ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ){
           override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return  false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.itemIDToDelete(viewHolder.adapterPosition)
            }

        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.todoData)

        //observer
        viewModel.noData.observe(this.viewLifecycleOwner,{
            if(it){hideItems()}else{showItems()}
        })

        //Adapter
        binding.todoData.adapter = Adapter (Adapter.OnClickListener{
            this.findNavController().navigate(ShowFragmentDirections.actionShowFragmentToEditFragment(it.id))
        })

        //OnScroll
        binding.todoData.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    binding.floatingActionButton.hide()
                }else{
                    binding.floatingActionButton.show()
                }
            }
        })

        //OnClickListeners
        binding.floatingActionButton.setOnClickListener {
            this.findNavController().navigate(ShowFragmentDirections.actionShowFragmentToAddFragment())
        }


        return binding.root
    }


    private fun hideItems(){
        binding.noItems.visibility  = VISIBLE
        binding.todoData.visibility = GONE
    }
    private fun showItems(){
        binding.noItems.visibility  = GONE
        binding.todoData.visibility = VISIBLE
    }

}