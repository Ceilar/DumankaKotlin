package com.example.dumankakotlin.ui.theme

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException


object DumankaHelperClass{
    lateinit var pathReference: StorageReference
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    lateinit var mAuth: FirebaseAuth
    lateinit var user: FirebaseUser
    lateinit var storageReference: StorageReference
    var storage: FirebaseStorage? = null
    var list: ArrayList<String>? = null

    fun initialiseFileReader(context: Context) {
        Log.v("32131","sadsa")
        mAuth = FirebaseAuth.getInstance()
        user = mAuth.getCurrentUser()!!
        database =
            FirebaseDatabase.getInstance("https://dumankakotlin-5d76b-default-rtdb.europe-west1.firebasedatabase.app/")
        myRef = database.getReference("users")
        storage = FirebaseStorage.getInstance("gs://dumankakotlin-5d76b.appspot.com")
        fetchfile(context = context)
    }
    private fun fetchfile(context: Context): File? {
        storageReference = storage!!.reference
        val str: String
        str = "words/" + user.uid + ".txt"
        pathReference = storageReference.child(str)
        try {
            val localFile = File.createTempFile(user.getUid(), ".txt")
            pathReference.getFile(localFile)
                .addOnSuccessListener(object : OnSuccessListener<FileDownloadTask.TaskSnapshot?> {
                    override fun onSuccess(taskSnapshot: FileDownloadTask.TaskSnapshot?) {
                        readFile(localFile, context = context)
                    }
                })
            return localFile
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
    private fun readFile(file: File?,context: Context) {
        var content = ""
        val sbuffer = StringBuffer()
        list = ArrayList()
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader(file))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        if (file != null) {
            try {
                while (reader!!.readLine().also { content = it } != null) {
                    sbuffer.append(content)
                    list!!.add(content)
                }
                reader.close()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else Toast.makeText(
            context,
            "Проверете интернет връзката си или изчакайте момент",
            Toast.LENGTH_SHORT
        ).show()
    }
    fun returnList(): ArrayList<String>? {
        return this.list
    }
    fun checkword(s: String, textword: String?): Array<String?> {
        val input: CharArray
        val file: CharArray
        val result = arrayOfNulls<String>(5)
        result[0] = "сива"
        result[1] = "сива"
        result[2] = "сива"
        result[3] = "сива"
        result[4] = "сива"
        input = s.toCharArray()
        file = textword!!.toCharArray()
        for (j in 0..4) {
            if (input[j] == file[j]) {
                result[j] = "зелена"
                input[j] = '.'
                file[j] = '.'
            }
        }
        for (j in 0..4) {
            if (input[j] != '.') {
                for (i in 0..4) {
                    if (input[j] == file[i]) {
                        result[j] = "жълта"
                        input[j] = ','
                        file[i] = ','
                    }
                }
            }
        }

        return result
    }

}