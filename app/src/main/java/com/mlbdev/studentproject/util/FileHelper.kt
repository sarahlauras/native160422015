package com.mlbdev.studentproject.util

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class FileHelper (val context: Context){
    val folderName = "myfolder"
    val fileName = "mydata.txt"

    // function untuk bikin file baru/load file jika sudah ada
    private fun getFile(): File {
        val dir = File(context.filesDir, folderName)
        if (!dir.exists()) {
            dir.mkdirs() // bikin folder jika folder belum ada
        }
        return File(dir, fileName)
    }

    fun writeToFile(data:String){ //apa yang ingin ditulis
        try{
            val file = getFile() //memanggil yang atas
            //append = false -> data baru dilanjutkan
            //appeng = true -> data nari mereplace data lama
            FileOutputStream(file, false).use{//method use
                    output -> output.write(data.toByteArray())
            }
        }catch(e:IOException){
            e.printStackTrace()
        }
    }

    fun readFromFile(): String { //menyesuaikan seberapa banyak iterasi
        return try { //kalau tidak error, maka yang direturn isi file
            // kalau error, maka yang direturn errornya
            val file = getFile()
            file.bufferedReader().useLines { lines ->
                lines.joinToString("\n")
            }
        } catch (e: IOException) {
            e.printStackTrace().toString()
        }
    }

    fun deleteFile(): Boolean{
        return getFile().delete()
    }

    fun getFilePath():String{
        return getFile().absolutePath
    }
}
