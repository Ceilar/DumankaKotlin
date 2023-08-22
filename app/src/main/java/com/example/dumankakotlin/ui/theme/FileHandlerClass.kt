package com.example.dumankakotlin.ui.theme

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream

object FileHandlerClass {

    private var firebaseAuth: FirebaseAuth? = null
    var rootNode: FirebaseDatabase? = null
    var reference: DatabaseReference? = null
    var storageReference: StorageReference? = null
    var storage: FirebaseStorage? = null
    var localFile: File? = null
    var pathReference: StorageReference? = null


    fun createNewFile(uid: String):Boolean {
        var flag1 = true
        firebaseAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance("gs://dumankakotlin-5d76b.appspot.com")
        rootNode =
            FirebaseDatabase.getInstance("https://dumankakotlin-5d76b-default-rtdb.europe-west1.firebasedatabase.app/")
        reference = rootNode!!.getReference("users")
        storageReference = storage!!.reference
        pathReference = storageReference!!.child("words/words.txt")
        //val firebaseUser = firebaseAuth!!.currentUser
        val SIZE = (512 * 512).toLong()
        pathReference!!.getBytes(SIZE).addOnSuccessListener { bytes ->
            val riversRef = storageReference!!.child("words/$uid.txt")
            val uploadTask = riversRef.putBytes(bytes!!)
            uploadTask.addOnFailureListener {flag1 = false}.addOnSuccessListener {flag1 = true }
        }.addOnFailureListener {flag1 = false}
        return flag1
    }
    fun onPauseDumanka(list: ArrayList<String>?){
        try {
            //Toast.makeText(MainActivity.this, "TEST1", Toast.LENGTH_LONG).show();
            Log.v("readingfile", "Onstop")
            val uploadfile = File.createTempFile("test", "txt")
            val writer = BufferedWriter(FileWriter(uploadfile))
            for (i in list!!.indices) {
                writer.write(

                    list[i].trimIndent() + "\n"

                )
            }
            writer.close()
            Log.v("readingfile", "File procheten")
            DumankaHelperClass.storageReference = DumankaHelperClass.storage!!.getReference()
            val str: String
            str = "words/" + DumankaHelperClass.user.getUid() + ".txt"
            DumankaHelperClass.pathReference = DumankaHelperClass.storageReference.child(str)
            try {
                //Toast.makeText(MainActivity.this, "TEST2", Toast.LENGTH_LONG).show();
                val inputStream: InputStream = FileInputStream(uploadfile)
                val task: UploadTask = DumankaHelperClass.pathReference.putStream(inputStream)
                task.addOnFailureListener(object : OnFailureListener {
                    override fun onFailure(e: Exception) {}
                }).addOnCompleteListener(object :
                    OnCompleteListener<UploadTask.TaskSnapshot?> {
                    override fun onComplete(task: Task<UploadTask.TaskSnapshot?>) {
                        Log.v("readingfile", "File zapisan")
                    }
                })
            } finally {
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}