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
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.example.dumankakotlin.databinding.FragmentReshavankaBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue


/**
 * A simple [Fragment] subclass.
 * Use the [Reshavanka.newInstance] factory method to
 * create an instance of this fragment.
 */
class Reshavanka : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    var mykeyboard: MyKeyboard? = null
    var rowx: Int = 0
    var list3 = ArrayList<EditText>()
    lateinit var equationToCheck: String
    lateinit var list1: ArrayList<EditText>
    lateinit var binding: FragmentReshavankaBinding
    lateinit var profilebtn: Button
    lateinit var switchBtn: Button
    lateinit var vpager: ViewPager2
    var animationView: LottieAnimationView? = null
    var animationView1: LottieAnimationView? = null
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    lateinit var mAuth: FirebaseAuth
    lateinit var user: FirebaseUser
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

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
        binding = FragmentReshavankaBinding.inflate(layoutInflater)
        profilebtn = requireActivity().findViewById(R.id.profilebtn)
        switchBtn = requireActivity().findViewById(R.id.switchbtn)
        vpager = requireActivity().findViewById(R.id.viewpager)
        animationView = binding.animationView1
        animationView1 = binding.animationView2
        mAuth = FirebaseAuth.getInstance()
        user = mAuth.currentUser!!
        database =
            FirebaseDatabase.getInstance("https://dumankakotlin-5d76b-default-rtdb.europe-west1.firebasedatabase.app/")
        myRef = database.getReference("users")

        var equations = Equations()
        equationToCheck = equations.returnEquation()
        val equation = binding.equation1
        list1 = ArrayList()
        list1.add(binding.text1r)
        list1.add(binding.text2r)
        list1.add(binding.text3r)
        list1.add(binding.text4r)
        list1.add(binding.text5r)
        list1.add(binding.text6r)
        list1.add(binding.text7r)
        list1.add(binding.text8r)
        list1.add(binding.text9r)
        list1.add(binding.text10r)
        list1.add(binding.text11r)
        list1.add(binding.text12r)
        list1.add(binding.text13r)
        list1.add(binding.text14r)
        list1.add(binding.text15r)
        list1.add(binding.text16r)
        list1.add(binding.text17r)
        list1.add(binding.text18r)
        list1.add(binding.text19r)
        list1.add(binding.text20r)
        list1.add(binding.text21r)
        list1.add(binding.text22r)
        list1.add(binding.text23r)
        list1.add(binding.text24r)
        list1.add(binding.text25r)
        list1.add(binding.text26r)
        list1.add(binding.text27r)
        list1.add(binding.text28r)
        list1.add(binding.text29r)
        list1.add(binding.text30r)
        list1.add(binding.text31r)
        list1.add(binding.text32r)
        list1.add(binding.text33r)
        list1.add(binding.text34r)
        list1.add(binding.text35r)
        list1.add(binding.text36r)
        list1.add(binding.text37r)
        list1.add(binding.text38r)
        list1.add(binding.text39r)
        list1.add(binding.text40r)
        list1.add(binding.text41r)
        list1.add(binding.text42r)

        list3.add(binding.text1r)
        list3.add(binding.text1r)
        list3.add(binding.text1r)
        list3.add(binding.text1r)
        list3.add(binding.text1r)
        list3.add(binding.text1r)
        list3.add(binding.text1r)


        binding.start1.setOnClickListener(View.OnClickListener {
            vpager.isUserInputEnabled = false
            profilebtn.isEnabled = false
            switchBtn.isEnabled = false
            vpager.requestTransform()
            equationToCheck = equations.returnEquation()
            for (i in 0..41) {
                list1[i].setBackgroundResource(R.drawable.border2)
                list1[i].setText("")
            }
            binding.start1.visibility = View.GONE
            rowx = 0
            startreshavanka(list1, equation, 0)
        })

        return binding.root
        //return inflater.inflate(R.layout.fragment_reshavanka, container, false)
    }

    private fun startreshavanka(list1: ArrayList<EditText>, equation: EditText, row: Int) {
        val j = row * 7
        for (i in 0..6) {
            list3[i] = list1[i + j]
        }
        if (row == 0) {
            mykeyboard = binding.keyboard
            mykeyboard!!.visibility = View.VISIBLE
        }
        val ic = equation.onCreateInputConnection(EditorInfo())
        mykeyboard!!.setInputConnection(ic)
        mykeyboard!!.isActivated = true
        equation.setText("")
        equation.setBackgroundColor(Color.TRANSPARENT)
        equation.isCursorVisible = false
        equation.setTextColor(Color.TRANSPARENT)
        equation.imeOptions = EditorInfo.IME_ACTION_GO
        equation.setImeActionLabel("actionGO", EditorInfo.IME_ACTION_GO)
        equation.requestFocus()
        equation.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO || event.keyCode == KeyEvent.KEYCODE_ENTER) {
                val s = equation.text.toString()
                if (s.length == 7) {
                    rowx++
                    val result1: Array<String?>
                    result1 = checkequation(s, equationToCheck)
                    if (rowx != 6) {
                        if (!accessResult1(result1, list3, equation)) {
                            startreshavanka(list1, equation, rowx)
                        }
                    }
                    if (rowx == 6 && !accessResult1(result1, list3, equation)) {
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
        equation.addTextChangedListener(object : TextWatcher {
            var text = charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' ')
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                equation.requestFocus()
                for (i in 0..6) {
                    list3[i].isFocusable = true
                    list3[i].requestFocus()
                    list3[i].setText("")
                    list3[i].clearFocus()
                    list3[i].isFocusable = false
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                equation.requestFocus()
            }

            override fun afterTextChanged(s: Editable) {
                text = equation.text.toString().toCharArray()
                var i: Int = 0
                while (i < text.size) {
                    list3[i].setText(text[i].toString())
                    i++
                }
                equation.requestFocus()
            }
        })
    }

    private fun checkequation(equationInput: String, equationToCheck: String?): Array<String?> {
        val input: CharArray
        val check: CharArray
        val result = arrayOfNulls<String>(7)
        result[0] = "сива"
        result[1] = "сива"
        result[2] = "сива"
        result[3] = "сива"
        result[4] = "сива"
        result[5] = "сива"
        result[6] = "сива"
        input = equationInput.toCharArray()
        check = equationToCheck!!.toCharArray()
        for (j in 0..6) {
            if (input[j] == check[j]) {
                result[j] = "зелена"
                input[j] = '.'
                check[j] = '.'
            }
        }
        for (j in 0..6) {
            if (input[j] != '.') {
                for (i in 0..6) {
                    if (input[j] == check[i]) {
                        result[j] = "жълта"
                        input[j] = ','
                        check[i] = ','
                    }
                }
            }
        }
        return result
    }

    fun accessResult1(s: Array<String?>, list1: ArrayList<EditText>, equation: EditText?): Boolean {
        for (i in 0..6) {
            if (s[i] === "зелена") {
                list1[i].setBackgroundResource(R.drawable.bordergreen)
            } else if (s[i] === "жълта") {
                list1[i].setBackgroundResource(R.drawable.borderyellow)
            } else list1[i].setBackgroundResource(R.drawable.bordergray)
        }
        return if (s[0] === "зелена" && s[1] === "зелена" && s[2] === "зелена" && s[3] === "зелена"
            && s[4] === "зелена" && s[5] === "зелена" && s[6] === "зелена"
        ) {
            myRef.child(user.uid).child("equations").setValue(ServerValue.increment(+1))
            vpager.isUserInputEnabled = true
            mykeyboard!!.visibility = View.INVISIBLE
            binding.start1.visibility = View.VISIBLE
            val builder = AlertDialog.Builder(this.context)
            val dialogView: View = layoutInflater.inflate(R.layout.dialog1w, null)
            val buttonnewgame = dialogView.findViewById<Button>(R.id.buttonnewgame)
            val equationsguessd = dialogView.findViewById<TextView>(R.id.equationsguessed)
            builder.setView(dialogView)
            myRef.child(user.uid).get()
                .addOnCompleteListener(object : OnCompleteListener<DataSnapshot?> {
                    override fun onComplete(task: Task<DataSnapshot?>) {
                        val dataSnapshot: DataSnapshot? = task.result
                        val test1 = "Отгатнати уравнения: " + java.lang.String.valueOf(
                            dataSnapshot!!.child("equations").value
                        )
                        equationsguessd.text = test1
                    }
                })
            val alertDialog = builder.create()
            buttonnewgame.setOnClickListener {
                animationView!!.setMinAndMaxProgress(0.0f, 0.0f)
                animationView1!!.setMinAndMaxProgress(0.0f, 0.0f)
                binding.start1.performClick()
                alertDialog.dismiss()
            }
            animationView!!.setMinAndMaxProgress(0.0f, 1.0f)
            animationView1!!.setMinAndMaxProgress(0.0f, 1.0f)
            animationView!!.bringToFront()
            animationView!!.speed = 0.3.toFloat()
            animationView1!!.bringToFront()
            animationView1!!.speed = 0.4.toFloat()
            animationView!!.repeatCount = 0
            animationView1!!.repeatCount = 0
            animationView1!!.playAnimation()
            animationView!!.playAnimation()
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                profilebtn.isEnabled = true
                switchBtn.isEnabled = true
                alertDialog.show()
            }, 1500)
            true
        } else false
    }

    private fun dialogloss() {
        val builder = AlertDialog.Builder(this.context)
        val dialogView: View = layoutInflater.inflate(R.layout.dialog1l, null)
        val buttonnewgame = dialogView.findViewById<Button>(R.id.buttonnewgame)
        val header = dialogView.findViewById<TextView>(R.id.header)
        val content1 = dialogView.findViewById<TextView>(R.id.content1)
        val content2 = dialogView.findViewById<TextView>(R.id.content2)
        vpager.isUserInputEnabled = true
        builder.setView(dialogView)
        val alertDialog = builder.create()

        header.text = "О, не!"
        content1.text = "Не успяхте да отгатнете уравнението!"
        var wordAtLoss = "Уравнението беше: $equationToCheck"
        content2.text = wordAtLoss
        mykeyboard!!.visibility = View.INVISIBLE

        buttonnewgame.setOnClickListener {
            binding.start1.performClick()
            alertDialog.dismiss()
        }
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            alertDialog.show()
            profilebtn.isEnabled = true
            switchBtn.isEnabled = true
            binding.start1.visibility = View.VISIBLE
        }, 500)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): Reshavanka {
            val fragment = Reshavanka()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}