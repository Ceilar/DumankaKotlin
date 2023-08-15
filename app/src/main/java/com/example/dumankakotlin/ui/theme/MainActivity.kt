package com.example.dumankakotlin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Switch
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
    lateinit var gso: GoogleSignInOptions
    var gsc: GoogleSignInClient? = null
    lateinit var switchBtn: Switch
    lateinit var wordbtn: Button
    lateinit var startbtn: Button
    lateinit var mathbtn: Button
    lateinit var vpager: ViewPager2
    lateinit var equations: Equations
    var viewPagerAdapter: ViewPagerAdapter? = null
    lateinit var profilebtn: Button
    lateinit var myPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var guesswasmade = false
    var startflag = false
    var storage: FirebaseStorage? = null
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    lateinit var mAuth: FirebaseAuth
    lateinit var user: FirebaseUser
    var flag1 = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this, gso)
        mAuth = FirebaseAuth.getInstance()
        user = mAuth.getCurrentUser()!!
        database =
            FirebaseDatabase.getInstance("https://dumankakotlin-5d76b-default-rtdb.europe-west1.firebasedatabase.app/")
        myRef = database.getReference("users")
        storage = FirebaseStorage.getInstance("gs://dumankakotlin-5d76b.appspot.com")
        profilebtn = findViewById<Button>(R.id.profilebtn)
        switchBtn = findViewById<Switch>(R.id.switchbtn)
        wordbtn = findViewById<Button>(R.id.wordbtn)
        mathbtn = findViewById<Button>(R.id.mathbtn)
        vpager = findViewById<ViewPager2>(R.id.viewpager)
        startbtn = findViewById<Button>(R.id.start1)
        startbtn.isVisible = false
        flag1 = true
        guesswasmade = false
        equations = Equations()
        viewPagerAdapter = ViewPagerAdapter(this)
        myPreferences = getSharedPreferences(MY_PREFS, MODE_PRIVATE)
        startflag = true
        editor = getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit()

        val switch_status = myPreferences.getBoolean(SWITCH_STATUS, false)
        switchBtn.setChecked(switch_status)
        if (switch_status) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        switchBtn.setOnClickListener(View.OnClickListener {
            if (switchBtn.isChecked()) {
                editor.putBoolean(SWITCH_STATUS, true)
                editor.apply()
                switchBtn.setChecked(true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                editor.putBoolean(SWITCH_STATUS, false)
                editor.apply()
                switchBtn.setChecked(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })

        profilebtn.setEnabled(true)
        profilebtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)
        })

        vpager.setAdapter(viewPagerAdapter)
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