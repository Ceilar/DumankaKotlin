package com.example.dumankakotlin

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout


class MyKeyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {
    var button1: Button? = null
    var button2: Button? = null
    var button3: Button? = null
    var button4: Button? = null
    var button5: Button? = null
    var button6: Button? = null
    var button7: Button? = null
    var button8: Button? = null
    var button9: Button? = null
    var button0: Button? = null
    var buttonDelete: Button? = null
    var buttonEnter: Button? = null
    var buttonPlus: Button? = null
    var buttonMinus: Button? = null
    var buttonMulti: Button? = null
    var buttonDivide: Button? = null
    var buttonEquals: Button? = null
    private val keyValues = SparseArray<String>()
    private var inputConnection: InputConnection? = null

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true)
        button1 = findViewById<View>(R.id.button_1) as Button
        button1!!.setOnClickListener(this)
        button2 = findViewById<View>(R.id.button_2) as Button
        button2!!.setOnClickListener(this)
        button3 = findViewById<View>(R.id.button_3) as Button
        button3!!.setOnClickListener(this)
        button4 = findViewById<View>(R.id.button_4) as Button
        button4!!.setOnClickListener(this)
        button5 = findViewById<View>(R.id.button_5) as Button
        button5!!.setOnClickListener(this)
        button6 = findViewById<View>(R.id.button_6) as Button
        button6!!.setOnClickListener(this)
        button7 = findViewById<View>(R.id.button_7) as Button
        button7!!.setOnClickListener(this)
        button8 = findViewById<View>(R.id.button_8) as Button
        button8!!.setOnClickListener(this)
        button9 = findViewById<View>(R.id.button_9) as Button
        button9!!.setOnClickListener(this)
        button0 = findViewById<View>(R.id.button_0) as Button
        button0!!.setOnClickListener(this)
        buttonDelete = findViewById<View>(R.id.button_delete) as Button
        buttonDelete!!.setOnClickListener(this)
        buttonEnter = findViewById<View>(R.id.button_enter) as Button
        buttonEnter!!.setOnClickListener(this)
        buttonPlus = findViewById<View>(R.id.button_plus) as Button
        buttonPlus!!.setOnClickListener(this)
        buttonMinus = findViewById<View>(R.id.button_minus) as Button
        buttonMinus!!.setOnClickListener(this)
        buttonDivide = findViewById<View>(R.id.button_divide) as Button
        buttonDivide!!.setOnClickListener(this)
        buttonMulti = findViewById<View>(R.id.button_multi) as Button
        buttonMulti!!.setOnClickListener(this)
        buttonEquals = findViewById<View>(R.id.button_equals) as Button
        buttonEquals!!.setOnClickListener(this)
        keyValues.put(R.id.button_1, "1")
        keyValues.put(R.id.button_2, "2")
        keyValues.put(R.id.button_3, "3")
        keyValues.put(R.id.button_4, "4")
        keyValues.put(R.id.button_5, "5")
        keyValues.put(R.id.button_6, "6")
        keyValues.put(R.id.button_7, "7")
        keyValues.put(R.id.button_8, "8")
        keyValues.put(R.id.button_9, "9")
        keyValues.put(R.id.button_0, "0")
        keyValues.put(R.id.button_enter, "\n")
        keyValues.put(R.id.button_plus, "+")
        keyValues.put(R.id.button_minus, "-")
        keyValues.put(R.id.button_divide, "/")
        keyValues.put(R.id.button_multi, "*")
        keyValues.put(R.id.button_equals, "=")
    }

    override fun onClick(view: View) {
        if (inputConnection == null) return
        if (view.id == R.id.button_delete) {
            inputConnection!!.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
        } else {
            val value = keyValues[view.id]
            when (value) {
                "\n" -> inputConnection!!.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_ENTER
                    )
                )

                "0" -> inputConnection!!.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_0
                    )
                )

                "1" -> inputConnection!!.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_1
                    )
                )

                "2" -> inputConnection!!.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_2
                    )
                )

                "3" -> inputConnection!!.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_3
                    )
                )

                "4" -> inputConnection!!.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_4
                    )
                )

                "5" -> inputConnection!!.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_5
                    )
                )

                "6" -> inputConnection!!.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_6
                    )
                )

                "7" -> inputConnection!!.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_7
                    )
                )

                "8" -> inputConnection!!.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_8
                    )
                )

                "9" -> inputConnection!!.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_9
                    )
                )

                "+" -> inputConnection!!.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_PLUS
                    )
                )

                "-" -> inputConnection!!.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_MINUS
                    )
                )

                "*" -> inputConnection!!.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_STAR
                    )
                )

                "/" -> inputConnection!!.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_SLASH
                    )
                )

                "=" -> inputConnection!!.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_EQUALS
                    )
                )
            }
        }
    }

    fun setInputConnection(ic: InputConnection?) {
        inputConnection = ic
    }
}