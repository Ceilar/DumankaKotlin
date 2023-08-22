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
import com.example.dumankakotlin.ui.theme.FileHandlerClass
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


class LoginActivity : AppCompatActivity() {
    //var gso: GoogleSignInOptions? = null
    private var gsc: GoogleSignInClient? = null
    private lateinit var googleBtn: Button
    private var firebaseAuth: FirebaseAuth? = null
    private var rootNode: FirebaseDatabase? = null
    private var reference: DatabaseReference? = null
    private var storage: FirebaseStorage? = null

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
                val email = firebaseUser.email
                rootNode =
                    FirebaseDatabase.getInstance("https://dumankakotlin-5d76b-default-rtdb.europe-west1.firebasedatabase.app/")
                reference = rootNode!!.getReference("users")
                if (authResult.additionalUserInfo!!.isNewUser) {
                    if (FileHandlerClass.createNewFile(firebaseUser.uid)) {
                        val builder = AlertDialog.Builder(this@LoginActivity)
                        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
                        val dialogView = LayoutInflater.from(this@LoginActivity)
                            .inflate(R.layout.logindialog, viewGroup, false)
                        val regbtn = dialogView.findViewById<Button>(R.id.registrationbtn)
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
                        Toast.makeText(
                            this@LoginActivity,
                            "Проверете интернет връзката си, след което опитайте да се регистрирате отново!",
                            Toast.LENGTH_SHORT
                        ).show()
                        firebaseAuth!!.currentUser?.delete()
                        finish()
                        recreate()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Успешно влизане!", Toast.LENGTH_SHORT)
                        .show()
                    navigateToSecondActivity()
                }
            }
            .addOnFailureListener { }
    }

    private fun navigateToSecondActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}