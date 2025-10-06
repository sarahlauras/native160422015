package com.mlbdev.studentproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mlbdev.studentproject.R
import com.mlbdev.studentproject.databinding.FragmentStudentListBinding
import com.mlbdev.studentproject.viewmodel.ListViewModel

class StudentListFragment : Fragment() {
    private lateinit var binding: FragmentStudentListBinding
    private lateinit var viewmodel:ListViewModel
    //recycler view butuh adapter
    private val studentListAdapter = StudentListAdapter(arrayListOf()) //sudah punya array list adapter
    //pakai update di student list adapter untuk mengganti data student list adapter

    override fun onCreateView(//untuk load layout
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }
    //ga butuh companion_object karena ga pake fragment_manager untuk kirim data

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {//dipanggil setelah sukses load, oprek UI di sini
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProvider(this).get(ListViewModel::class.java) //cara nge init view model
        //function refresh untuk load data hardcode
        viewmodel.refresh() //load/fetch data

        //testing file
//        viewmodel.testSaveFile()

        //set up recycler view
        //kalau sedang ada di activity butuh context tinggal panggil this, kalau fragment gabisa pakai this tapi pakai this.context
        binding.recViewStudent.layoutManager = LinearLayoutManager(context)
        binding.recViewStudent.adapter = studentListAdapter

        //swipe refresh
        binding.refreshLayout.setOnRefreshListener {
            binding.recViewStudent.visibility = View.GONE
            binding.txtError.visibility = View.GONE
            binding.progressLoad.visibility = View.VISIBLE
            viewmodel.refresh()
            binding.refreshLayout.isRefreshing = false
        }
        //observe (subscribe) terhadap 3 variable, maka buat functionnya
        observeViewModel()
    }

    fun observeViewModel(){
        //ada 3 variable yang mau di subs

        //observe - live data - arrayList student
        viewmodel.studentsLD.observe(viewLifecycleOwner, Observer {
            studentListAdapter.updateStudentList(it)
        })

        //observe - live data - errorLD
        //This observe “loading” livedata. For now its just use dummy data
        viewmodel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.txtError.text = "Something went wrong! when load student data"
                binding.txtError.visibility = View.VISIBLE
                binding.recViewStudent.visibility = View.INVISIBLE
            } else {
                binding.recViewStudent.visibility = View.VISIBLE
                binding.txtError.visibility = View.INVISIBLE
                binding.progressLoad.visibility = View.INVISIBLE //menyembunyikan recycle view
            }
        })


    }

}
