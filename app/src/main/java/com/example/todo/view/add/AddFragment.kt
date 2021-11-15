package com.example.todo.view.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.databinding.FragmentAddBinding
import com.example.todo.model.TodoDatabase
import com.example.todo.view.util.PopUpMsg
import java.text.SimpleDateFormat
import java.util.*


class AddFragment : Fragment() {
    private lateinit var binding : FragmentAddBinding
    private lateinit var viewModel: AddFragmentViewModel
    private val myCalendar: Calendar = Calendar.getInstance()
    private lateinit var format: SimpleDateFormat
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Initialization
        binding = FragmentAddBinding.inflate(inflater)
        val application = requireNotNull(activity).application
        val dataSource = TodoDatabase.getInstance(application).todoDao
        val viewModelFactory = AddFragmentViewModelFactory(dataSource,application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddFragmentViewModel::class.java)
        binding.lifecycleOwner = this
        format = SimpleDateFormat(this.resources.getString(R.string.DATE_FORMAT), Locale.US)
        val date = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
           myCalendar.set(Calendar.YEAR, year)
           myCalendar.set(Calendar.MONTH, month)
           myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
          setDateAsText()
        }

        //observers
        viewModel.navigate.observe(this.viewLifecycleOwner,{
            if(it){
                this.findNavController().navigate(AddFragmentDirections.actionAddFragmentToShowFragment())
            }
        })

        //ClickListeners
        binding.date.editText?.setOnClickListener {
                DatePickerDialog(
                    this.requireActivity(), date, myCalendar[Calendar.YEAR],
                    myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
                ).show()
        }

        binding.topAppBar.setNavigationOnClickListener {
            this.findNavController().popBackStack()
        }

        binding.submit.setOnClickListener {
            val  name = binding.name.editText?.text.toString()
            val  calendarDate = binding.date.editText?.text.toString()
            if(name.isEmpty() || calendarDate.isEmpty()){
                PopUpMsg.toastMsg(this.requireContext(),this.resources.getString(R.string.FILL_FIELDS))
            }else if(checkDateValidity()){
                PopUpMsg.toastMsg(this.requireContext(),this.resources.getString(R.string.INVALID_DATE))
            }else{
                viewModel.getItem(name.trim(),calendarDate,format.format(Date()))
            }
        }

        return binding.root
    }

    private fun setDateAsText() {
        binding.date.editText?.setText(format.format(myCalendar.time))
    }

    private fun checkDateValidity(): Boolean {
        return format.format(myCalendar.time) < format.format(Date())
    }


}