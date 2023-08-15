package com.example.dumankakotlin


import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso


class ProfileActivity : AppCompatActivity() {
    lateinit var name: TextView
    lateinit var email: TextView
    lateinit var words: TextView
    lateinit var equations: TextView
    var profilepic: ImageView? = null
    lateinit var logoutbtn: Button
    var mAuth: FirebaseAuth? = null
    var database: FirebaseDatabase? = null
    var myRef: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        name = findViewById(R.id.username)
        email = findViewById(R.id.email)
        profilepic = findViewById(R.id.profilepic)
        logoutbtn = findViewById(R.id.logoutbtn)
        words = findViewById(R.id.words)
        name.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG)
        email.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG)
        words.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG)
        equations = findViewById(R.id.equations)
        equations.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG)
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth!!.currentUser
        database =
            FirebaseDatabase.getInstance("https://dumankakotlin-5d76b-default-rtdb.europe-west1.firebasedatabase.app/")
        myRef = database!!.getReference("users")
        myRef!!.child(user!!.uid).get().addOnCompleteListener { task ->
            val dataSnapshot = task.result
            val username = "Име:  " + dataSnapshot.child("name").value.toString()
            val wordsguessed = "Отгатнати думи: " + dataSnapshot.child("words").value.toString()
            val equationsguessed =
                "Отгатнати уравнения: " + dataSnapshot.child("equations").value.toString()
            name.setText(username)
            equations.setText(equationsguessed)
            words.setText(wordsguessed)
        }
        val imgUri = user.photoUrl
        Picasso.get().load(imgUri).into(profilepic)
        email.setText(user.email)
        logoutbtn.setOnClickListener(View.OnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
}