package com.mlbdev.studentproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mlbdev.studentproject.R
import com.mlbdev.studentproject.databinding.FragmentStudentDetailBinding
import com.mlbdev.studentproject.model.Student
import com.mlbdev.studentproject.viewmodel.DetailViewModel

class StudentDetailFragment : Fragment() {
    private lateinit var binding: FragmentStudentDetailBinding
    private lateinit var viewmodel: DetailViewModel
    private lateinit var student: Student

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = StudentDetailFragmentArgs.fromBundle(requireArguments()).id

        viewmodel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewmodel.fetch(id)

        observeViewModel()
    }

    fun observeViewModel(){
        viewmodel.studentLD.observe(viewLifecycleOwner) { it ->
            it?.let {
                binding.txtID.setText(it.id)
                binding.txtName.setText(it.name)
                binding.txtBoD.setText(it.bod)
                binding.txtPhone.setText(it.phone)
            }
        }
    }
}