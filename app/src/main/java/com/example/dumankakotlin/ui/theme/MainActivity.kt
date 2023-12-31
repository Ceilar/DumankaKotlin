package com.example.dumankakotlin

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {
    private lateinit var gso: GoogleSignInOptions
    private var gsc: GoogleSignInClient? = null
    private lateinit var switchBtn: Switch
    private lateinit var wordbtn: Button
    private lateinit var startbtn: Button
    private lateinit var mathbtn: Button
    private lateinit var vpager: ViewPager2
    private lateinit var equations: Equations
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private lateinit var hintbtn: Button
    private lateinit var videolayoutbtn: LinearLayout
    private lateinit var profilebtn: Button
    private lateinit var myPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var guesswasmade = false
    private var startflag = false
    private var storage: FirebaseStorage? = null
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private var flag1 = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.currentUser!!
        database =
            FirebaseDatabase.getInstance("https://dumankakotlin-5d76b-default-rtdb.europe-west1.firebasedatabase.app/")
        myRef = database.getReference("users")
        storage = FirebaseStorage.getInstance("gs://dumankakotlin-5d76b.appspot.com")

        profilebtn = findViewById(R.id.profilebtn)
        hintbtn = findViewById(R.id.hintbutton)
        switchBtn = findViewById(R.id.switchbtn)
        wordbtn = findViewById(R.id.wordbtn)
        mathbtn = findViewById(R.id.mathbtn)
        vpager = findViewById(R.id.viewpager)
        startbtn = findViewById(R.id.start1)
        startbtn.isVisible = false
        flag1 = true
        guesswasmade = false
        equations = Equations()
        viewPagerAdapter = ViewPagerAdapter(this)
        myPreferences = getSharedPreferences(MY_PREFS, MODE_PRIVATE)
        startflag = true
        editor = getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit()

        val switchStatus = myPreferences.getBoolean(SWITCH_STATUS, false)
        switchBtn.isChecked = switchStatus
        if (switchStatus) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        switchBtn.setOnClickListener(View.OnClickListener {
            if (switchBtn.isChecked) {
                editor.putBoolean(SWITCH_STATUS, true)
                editor.apply()
                switchBtn.isChecked = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                editor.putBoolean(SWITCH_STATUS, false)
                editor.apply()
                switchBtn.isChecked = false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })

        profilebtn.isEnabled = true
        profilebtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)
        })

        hintbtn.setOnClickListener {
            val builder1 = AlertDialog.Builder(this)
            val dialogView: View = LayoutInflater.from(this).inflate(R.layout.videowindow, null)
            builder1.setView(dialogView)
            val alertDialog = builder1.create()
            val videoLayout: LinearLayout = dialogView.findViewById(R.id.videolayout)
            val video1: VideoView = dialogView.findViewById(R.id.videoView)
            video1.setVideoURI(
                Uri.parse("android.resource://"
                    + packageName + "/" + R.raw.dumankavideo4))

            videoLayout.setOnClickListener{
                if(alertDialog.isShowing){
                    alertDialog.dismiss()
                }
            }
            video1.requestFocus()
            video1.start()
            alertDialog.show()
            video1.setOnCompletionListener {
                alertDialog.dismiss()
            }

        }

        checkUserName()
        vpager.adapter = viewPagerAdapter
        vpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 1) {
                    mathbtn.setBackgroundResource(R.drawable.selected)
                    wordbtn.setBackgroundResource(R.drawable.notselected)
                } else if (position == 0) {
                    mathbtn.setBackgroundResource(R.drawable.notselected)
                    wordbtn.setBackgroundResource(R.drawable.selected)
                }
            }
        })
    }
    private fun checkUserName(){
        val firebaseUser = firebaseAuth.currentUser
        myRef.child(firebaseUser!!.uid).get().addOnCompleteListener { task ->
            val dataSnapshot = task.result
            val username = dataSnapshot.child("name").value.toString()
            if(username==""){
                firebaseAuth.currentUser?.delete()
                finish()
                val intent1 = Intent(this@MainActivity,LoginActivity::class.java)
                startActivity(intent1)
            }

        }
    }
    override fun recreate() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    companion object {
        private const val MY_PREFS = "switch_prefs"
        private const val SWITCH_STATUS = "switch_status"
    }
}