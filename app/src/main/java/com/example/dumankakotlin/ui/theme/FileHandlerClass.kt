package com.example.dumankakotlin.ui.theme

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

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
}