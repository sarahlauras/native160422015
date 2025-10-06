package com.mlbdev.studentproject.viewmodel

import android.app.Application
import android.util.Log
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
import com.mlbdev.studentproject.util.FileHelper

class ListViewModel(application: Application):AndroidViewModel(application) {
    //harus menyiapkan live data berbentuk list
    //LD = variable Live Data
    val studentsLD = MutableLiveData<ArrayList<Student>>() //live data ini bisa diubah" (mutable)
    val errorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue:RequestQueue? = null

    fun refresh(){ //biasanya taruh volley di sini
        loadingLD.value = true //progress bar muncul
        errorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://www.jsonkeeper.com/b/LLMW"
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                //sukses
                val sType = object: TypeToken<List<Student>>() {}.type
                val result = Gson().fromJson<List<Student>>(it, sType) //it adalah sumber data dari volley
                //sukses kalau sumber data berbentuk array dan dalamnya ada banyak object
                //ini diconvert dari list of student ke array list
                studentsLD.value = result as ArrayList<Student>
                loadingLD.value = false

                //simpan juga ke file
                val filehelper = FileHelper(getApplication())
                val jsonstring = Gson().toJson(result)
                filehelper.writeToFile(jsonstring)
                Log.d("print_file", jsonstring) //menampilkan yang disimpan

                //baca json string dari file
                val hasil = filehelper.readFromFile()
                val listStudent = Gson().fromJson<List<Student>>(hasil, sType)
                Log.d("print_file", listStudent.toString()) //menampilkan yang dari file ke string
            },
            {
                //failed
                errorLD.value = true
                loadingLD.value = false
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun testSaveFile(){
        val filehelper = FileHelper(getApplication())
        filehelper.writeToFile("hello world")
        val content = filehelper.readFromFile()
        Log.d("print file ", content)
        Log.d("print file ", filehelper.getFilePath())
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}