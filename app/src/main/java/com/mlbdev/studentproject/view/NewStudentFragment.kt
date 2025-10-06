package com.mlbdev.studentproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mlbdev.studentproject.R
import com.mlbdev.studentproject.databinding.FragmentNewStudentBinding

class NewStudentFragment : Fragment() {
    private lateinit var  binding: FragmentNewStudentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewStudentBinding.inflate(inflater)
        return binding.root
    }
}