package com.mlbdev.studentproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mlbdev.studentproject.model.Student

class DetailViewModel(app:Application):AndroidViewModel(app) {
    val studentLD = MutableLiveData<Student>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    //copas dari list view jadi pakai volley trs ntar di find
    fun fetch(id:String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://www.jsonkeeper.com/b/LLMW"
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                //sukses
                val sType = object: TypeToken<List<Student>>() {}.type
                val result = Gson().fromJson<List<Student>>(it, sType) //sukses kalau sumber data berbentuk array dan dalamnya ada banyak object
                //ini diconvert dari list of student ke array list
                val arrStudent = result as ArrayList<Student>
                val student = arrStudent.find { it.id == id }
                if (student != null) {
                    studentLD.value = student
                } else {
                    // kalau id tidak ditemukan
                    studentLD.value = null
                    android.util.Log.e("DetailViewModel", "Student dengan id=$id tidak ditemukan")
                }

            },
            {
                //failed

            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
}