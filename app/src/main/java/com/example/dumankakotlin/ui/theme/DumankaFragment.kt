package com.example.dumankakotlin

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.example.dumankakotlin.databinding.FragmentDumankaBinding
import com.example.dumankakotlin.ui.theme.DumankaHelperClass
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class DumankaFragment : Fragment(R.layout.fragment_dumanka) {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    lateinit var binding: FragmentDumankaBinding
    lateinit var list1: ArrayList<EditText>
    var list2 = ArrayList<EditText>(5)
    var mykeyboard2: MyKeyboard2? = null
    var rowx: Int = 0
    lateinit var wordtocheck: String
    lateinit var profilebtn: Button
    lateinit var switchBtn: Button
    lateinit var vpager: ViewPager2
    var animationView: LottieAnimationView? = null
    var animationView1: LottieAnimationView? = null
    var list: ArrayList<String>? = null
    lateinit var pathReference: StorageReference
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    lateinit var mAuth: FirebaseAuth
    lateinit var user: FirebaseUser
    var startflag: Boolean = true
    var storage: FirebaseStorage? = null
    var guesswasmade = false
    lateinit var storageReference: StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        guesswasmade = false
        mAuth = FirebaseAuth.getInstance()
        user = mAuth.getCurrentUser()!!
        database =
            FirebaseDatabase.getInstance("https://dumankakotlin-5d76b-default-rtdb.europe-west1.firebasedatabase.app/")
        myRef = database.getReference("users")
        storage = FirebaseStorage.getInstance("gs://dumankakotlin-5d76b.appspot.com")

        binding = FragmentDumankaBinding.inflate(layoutInflater)
        profilebtn = requireActivity().findViewById(R.id.profilebtn)
        switchBtn = requireActivity().findViewById(R.id.switchbtn)
        vpager = requireActivity().findViewById(R.id.viewpager)
        animationView = binding.animationView
        animationView1 = binding.animationView3
        DumankaHelperClass.initialiseFileReader(requireContext())

        val textword = binding.textword
        list1 = ArrayList()
        list1.add(binding.text1d)
        list1.add(binding.text2d)
        list1.add(binding.text3d)
        list1.add(binding.text4d)
        list1.add(binding.text5d)
        list1.add(binding.text6d)
        list1.add(binding.text7d)
        list1.add(binding.text8d)
        list1.add(binding.text9d)
        list1.add(binding.text10d)
        list1.add(binding.text11d)
        list1.add(binding.text12d)
        list1.add(binding.text13d)
        list1.add(binding.text14d)
        list1.add(binding.text15d)
        list1.add(binding.text16d)
        list1.add(binding.text17d)
        list1.add(binding.text18d)
        list1.add(binding.text19d)
        list1.add(binding.text20d)
        list1.add(binding.text21d)
        list1.add(binding.text22d)
        list1.add(binding.text23d)
        list1.add(binding.text24d)
        list1.add(binding.text25d)
        list2.add(binding.text1d)
        list2.add(binding.text1d)
        list2.add(binding.text1d)
        list2.add(binding.text1d)
        list2.add(binding.text1d)
        list1.forEach {
            it.isFocusable = false
        }



        binding.start1.setOnClickListener(View.OnClickListener {
            profilebtn.isEnabled = false
            switchBtn.isEnabled = false
            vpager.isUserInputEnabled = false
            vpager.requestTransform()
            this.list = DumankaHelperClass.returnList()
            wordtocheck = list!!.random().toString()


            var textView: TextView = binding.textView3
            textView.isVisible = false
            if (user.uid == "znZsrNf4oeTD6Ahmrecm92IDRjM2") {
                textView.isVisible = true
                textView.setText(wordtocheck)
            }

            for(i in 0..24){
                list1[i].setBackgroundResource(R.drawable.border2)
                list1[i].setText("")
            }

            binding.start1.isVisible = false
            rowx = 0
            startdumanka(list1, textword, 0)
        })

        return binding.root
    }

    fun startdumanka(list1: ArrayList<EditText>, textword: EditText, row: Int) {
        // guesswasmade = false
        if (row == 0) {
            mykeyboard2 = binding.keyboard2
            mykeyboard2!!.setVisibility(View.VISIBLE)
        }
        val j = row * 5
        for (i in 0..4) {
            list2[i] = list1[i + j]
        }
        val ic = textword.onCreateInputConnection(EditorInfo())
        mykeyboard2!!.setInputConnection(ic)
        mykeyboard2!!.setActivated(true)
        textword.setText("")
        textword.setBackgroundColor(Color.TRANSPARENT)
        textword.isCursorVisible = false
        textword.setTextColor(Color.TRANSPARENT)
        textword.imeOptions = EditorInfo.IME_ACTION_GO
        textword.setImeActionLabel("actionGO", EditorInfo.IME_ACTION_GO)
        textword.requestFocus()
        textword.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            //Log.v("first_fragment1", "$actionId ")
            if (actionId == EditorInfo.IME_ACTION_GO || event.keyCode == KeyEvent.KEYCODE_ENTER) {
                val s = textword.text.toString()
                if (s.length == 5) {
                    rowx++
                    val result: Array<String?> = DumankaHelperClass.checkword(s, wordtocheck)
                    if (rowx != 5) {
                        if (!accessResult(result, list2)) {
                            startdumanka(list1, textword, rowx)
                        }
                    }
                    if (rowx == 5 && !accessResult(result, list2)) {
                        dialogloss()
                    }
                    return@OnEditorActionListener false
                } else {
                    Toast.makeText(
                        this.context,
                        "Моля, попълнете всички кутийки!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            false
        })

        textword.addTextChangedListener(object : TextWatcher {
            var text = charArrayOf(' ', ' ', ' ', ' ', ' ')
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                textword.requestFocus()
                for (i in 0..4) {
                    list2[i].isFocusable = true
                    list2[i].requestFocus()
                    list2[i].setText("")
                    list2[i].clearFocus()
                    list2[i].isFocusable = false
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                textword.requestFocus()
            }

            override fun afterTextChanged(s: Editable) {
                text = textword.text.toString().toCharArray()
                var i: Int
                i = 0
                while (i < text.size) {
                    list2[i].setText(text[i].toString())
                    i++
                }
                textword.requestFocus()
            }
        })
    }


    fun accessResult(s: Array<String?>, list1: ArrayList<EditText>): Boolean {
        for (i in 0..4) {
            if (s[i] === "зелена") {
                list1[i].setBackgroundResource(R.drawable.bordergreen)
            } else if (s[i] === "жълта") {
                list1[i].setBackgroundResource(R.drawable.borderyellow)
            } else list1[i].setBackgroundResource(R.drawable.bordergray)
        }
        return if (s[0] === "зелена" && s[1] === "зелена" && s[2] === "зелена" && s[3] === "зелена" && s[4] === "зелена") {
            mykeyboard2!!.setVisibility(View.INVISIBLE)
            myRef.child(user.getUid()).child("words").setValue(ServerValue.increment(+1))
            guesswasmade = true
            vpager.isUserInputEnabled = true

            animationView!!.setMinAndMaxProgress(0.0f, 1.0f)
            animationView1!!.setMinAndMaxProgress(0.0f, 1.0f)
            animationView!!.bringToFront()
            animationView!!.speed = 0.3.toFloat()
            animationView1!!.bringToFront()
            animationView1!!.speed = 0.4.toFloat()
            animationView!!.repeatCount = 0
            animationView1!!.repeatCount = 0
            animationView!!.playAnimation()
            animationView1!!.playAnimation()
            val builder = AlertDialog.Builder(this.context)
            val dialogView = layoutInflater.inflate(R.layout.dialog2w, null)
            list!!.remove(wordtocheck)
            val buttonnewgame = dialogView.findViewById<Button>(R.id.buttonnewgame)
            val guessedwords = dialogView.findViewById<TextView>(R.id.guessedwords)
            builder.setView(dialogView)
            myRef.child(user.getUid()).get()
                .addOnCompleteListener(object : OnCompleteListener<DataSnapshot?> {
                    override fun onComplete(task: Task<DataSnapshot?>) {
                        val dataSnapshot: DataSnapshot? = task.getResult()
                        val test1 = "Отгатнати думи: " + java.lang.String.valueOf(
                            dataSnapshot!!.child("words").getValue()
                        )
                        guessedwords.text = test1
                    }
                })
            val alertDialog = builder.create()
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                profilebtn!!.isEnabled = true
                switchBtn!!.isEnabled = true
                alertDialog.show()
            }, 1500)
            binding.start1.isVisible = true
            buttonnewgame.setOnClickListener {
                animationView!!.setMinAndMaxProgress(0.0f, 0.0f)
                animationView1!!.setMinAndMaxProgress(0.0f, 0.0f)
                binding.start1!!.performClick()
                alertDialog.dismiss()
            }

            true
        } else false
    }

    fun dialogloss() {
        val builder1 = AlertDialog.Builder(this.context)
        val dialogView: View = LayoutInflater.from(this.context).inflate(R.layout.dialog1l, null)
        val buttonnewgame = dialogView.findViewById<Button>(R.id.buttonnewgame)
        val header = dialogView.findViewById<TextView>(R.id.header)
        val content1 = dialogView.findViewById<TextView>(R.id.content1)
        val content2 = dialogView.findViewById<TextView>(R.id.content2)
        vpager.isUserInputEnabled = true
        builder1.setView(dialogView)
        val alertDialog = builder1.create()
        header.text = "О, не!"
        content1.text = "Не успяхте да отгатнете думата!"
        var wordAtLoss = "Думата беше: $wordtocheck"
        content2.text = wordAtLoss
        mykeyboard2!!.setVisibility(View.INVISIBLE)

        buttonnewgame.setOnClickListener {
            binding.start1.performClick()
            alertDialog.dismiss()
        }
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            alertDialog.show()
            profilebtn.isEnabled = true
            switchBtn.isEnabled = true
            binding.start1.isVisible = true
        }, 500)
    }


    companion object {

        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): DumankaFragment {
            val fragment = DumankaFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onPause() {
        super.onPause()
        if (guesswasmade) {
            DumankaHelperClass.onPauseDumanka()
            guesswasmade = false
        }
    }

}

