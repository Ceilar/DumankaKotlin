package com.example.dumankakotlin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class LoginActivity : AppCompatActivity() {
    //var gso: GoogleSignInOptions? = null
    var gsc: GoogleSignInClient? = null
    lateinit var googleBtn: Button
    private var firebaseAuth: FirebaseAuth? = null
    var rootNode: FirebaseDatabase? = null
    var reference: DatabaseReference? = null
    var storageReference: StorageReference? = null
    var storage: FirebaseStorage? = null
    var localFile: File? = null
    var pathReference: StorageReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this, gso)
        gsc!!.revokeAccess()

        firebaseAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance("gs://dumankakotlin-5d76b.appspot.com")

        googleBtn = findViewById(R.id.googlebtn)
        googleBtn.setOnClickListener(View.OnClickListener {
            val signInIntent = gsc!!.signInIntent
            startActivityForResult(signInIntent, 1000)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            } catch (e: ApiException) {
                Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth!!.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                val firebaseUser = firebaseAuth!!.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser!!.email
                rootNode = FirebaseDatabase.getInstance("https://dumankakotlin-5d76b-default-rtdb.europe-west1.firebasedatabase.app/")
                reference = rootNode!!.getReference("users")
                if (authResult.additionalUserInfo!!.isNewUser) {
                    createnewfile(firebaseUser.uid)
                    val builder = AlertDialog.Builder(this@LoginActivity)
                    val viewGroup = findViewById<ViewGroup>(android.R.id.content)
                    val dialogView = LayoutInflater.from(this@LoginActivity)
                        .inflate(R.layout.logindialog, viewGroup, false)
                    val regbtn = dialogView.findViewById<Button>(R.id.registrationbtn)
                    //TextView header = dialogView.findViewById(R.id.header2);
                    val name1 = dialogView.findViewById<EditText>(R.id.name)
                    builder.setView(dialogView)
                    val alertDialog = builder.create()
                    regbtn.setOnClickListener {
                        val username = name1.text.toString()
                        val user = UserHelperClass(email, username, uid, 0, 0)
                        reference!!.child(uid).setValue(user)
                        Toast.makeText(
                            this@LoginActivity,
                            "Успешна регистрация!",
                            Toast.LENGTH_SHORT
                        ).show()
                        val imm =
                            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.toggleSoftInput(
                            InputMethodManager.HIDE_IMPLICIT_ONLY,
                            0
                        )
                        navigateToSecondActivity()
                        alertDialog.dismiss()
                    }
                    alertDialog.show()
                } else {
                    Toast.makeText(this@LoginActivity, "Успешно влизане!", Toast.LENGTH_SHORT)
                        .show()
                    navigateToSecondActivity()
                }
            }
            .addOnFailureListener { }
    }

    fun navigateToSecondActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun createnewfile(uid: String) {
        storageReference = storage!!.reference
        pathReference = storageReference!!.child("words/words.txt")
        val SIZE = (512 * 512).toLong()
        pathReference!!.getBytes(SIZE).addOnSuccessListener { bytes ->
            val riversRef = storageReference!!.child("words/$uid.txt")
            val uploadTask = riversRef.putBytes(bytes!!)
            uploadTask.addOnFailureListener { }.addOnSuccessListener { }
        }.addOnFailureListener { }
    }
}