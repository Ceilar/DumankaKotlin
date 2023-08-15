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


class MyKeyboard2 @JvmOverloads constructor(
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
    var button10: Button? = null
    var button11: Button? = null
    var button12: Button? = null
    var button13: Button? = null
    var button14: Button? = null
    var button15: Button? = null
    var button16: Button? = null
    var button17: Button? = null
    var button18: Button? = null
    var button19: Button? = null
    var button20: Button? = null
    var button21: Button? = null
    var button22: Button? = null
    var button23: Button? = null
    var button24: Button? = null
    var button25: Button? = null
    var button26: Button? = null
    var button27: Button? = null
    var button28: Button? = null
    var button29: Button? = null
    var button30: Button? = null
    var button31: Button? = null
    var button32: Button? = null
    var buttonDelete: Button? = null
    var buttonEnter: Button? = null
    private val keyValues = SparseArray<String>()
    private var inputConnection: InputConnection? = null
    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.keyboard2, this, true)
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
        button10 = findViewById<View>(R.id.button_10) as Button
        button10!!.setOnClickListener(this)
        button11 = findViewById<View>(R.id.button_11) as Button
        button11!!.setOnClickListener(this)
        button12 = findViewById<View>(R.id.button_12) as Button
        button12!!.setOnClickListener(this)
        button13 = findViewById<View>(R.id.button_13) as Button
        button13!!.setOnClickListener(this)
        button14 = findViewById<View>(R.id.button_14) as Button
        button14!!.setOnClickListener(this)
        button15 = findViewById<View>(R.id.button_15) as Button
        button15!!.setOnClickListener(this)
        button16 = findViewById<View>(R.id.button_16) as Button
        button16!!.setOnClickListener(this)
        button17 = findViewById<View>(R.id.button_17) as Button
        button17!!.setOnClickListener(this)
        button18 = findViewById<View>(R.id.button_18) as Button
        button18!!.setOnClickListener(this)
        button19 = findViewById<View>(R.id.button_19) as Button
        button19!!.setOnClickListener(this)
        button20 = findViewById<View>(R.id.button_20) as Button
        button20!!.setOnClickListener(this)
        button21 = findViewById<View>(R.id.button_21) as Button
        button21!!.setOnClickListener(this)
        button22 = findViewById<View>(R.id.button_22) as Button
        button22!!.setOnClickListener(this)
        button24 = findViewById<View>(R.id.button_24) as Button
        button24!!.setOnClickListener(this)
        button25 = findViewById<View>(R.id.button_25) as Button
        button25!!.setOnClickListener(this)
        button26 = findViewById<View>(R.id.button_26) as Button
        button26!!.setOnClickListener(this)
        button27 = findViewById<View>(R.id.button_27) as Button
        button27!!.setOnClickListener(this)
        button28 = findViewById<View>(R.id.button_28) as Button
        button28!!.setOnClickListener(this)
        button29 = findViewById<View>(R.id.button_29) as Button
        button29!!.setOnClickListener(this)
        button30 = findViewById<View>(R.id.button_30) as Button
        button30!!.setOnClickListener(this)
        button31 = findViewById<View>(R.id.button_31) as Button
        button31!!.setOnClickListener(this)
        buttonDelete = findViewById<View>(R.id.button_delete) as Button
        buttonDelete!!.setOnClickListener(this)
        buttonEnter = findViewById<View>(R.id.button_enter) as Button
        buttonEnter!!.setOnClickListener(this)
        keyValues.put(R.id.button_1, "я")
        keyValues.put(R.id.button_2, "в")
        keyValues.put(R.id.button_3, "е")
        keyValues.put(R.id.button_4, "р")
        keyValues.put(R.id.button_5, "т")
        keyValues.put(R.id.button_6, "ъ")
        keyValues.put(R.id.button_7, "у")
        keyValues.put(R.id.button_8, "и")
        keyValues.put(R.id.button_9, "о")
        keyValues.put(R.id.button_10, "п")
        keyValues.put(R.id.button_11, "ч")
        keyValues.put(R.id.button_12, "а")
        keyValues.put(R.id.button_13, "с")
        keyValues.put(R.id.button_14, "д")
        keyValues.put(R.id.button_15, "ф")
        keyValues.put(R.id.button_16, "г")
        keyValues.put(R.id.button_17, "х")
        keyValues.put(R.id.button_18, "й")
        keyValues.put(R.id.button_19, "к")
        keyValues.put(R.id.button_20, "л")
        keyValues.put(R.id.button_21, "ш")
        keyValues.put(R.id.button_22, "щ")
        keyValues.put(R.id.button_24, "з")
        keyValues.put(R.id.button_25, "ь")
        keyValues.put(R.id.button_26, "ц")
        keyValues.put(R.id.button_27, "ж")
        keyValues.put(R.id.button_28, "б")
        keyValues.put(R.id.button_29, "н")
        keyValues.put(R.id.button_30, "м")
        keyValues.put(R.id.button_31, "ю")
        keyValues.put(R.id.button_enter, "\n")
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
                "я" -> inputConnection!!.commitText("я", 1)
              /*  "я" -> inputConnection!!.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_0
                    )
                )*/
                "в" -> inputConnection!!.commitText("в", 1)
                "е" -> inputConnection!!.commitText("е", 1)
                "р" -> inputConnection!!.commitText("р", 1)
                "т" -> inputConnection!!.commitText("т", 1)
                "ъ" -> inputConnection!!.commitText("ъ", 1)
                "у" -> inputConnection!!.commitText("у", 1)
                "и" -> inputConnection!!.commitText("и", 1)
                "о" -> inputConnection!!.commitText("о", 1)
                "п" -> inputConnection!!.commitText("п", 1)
                "ч" -> inputConnection!!.commitText("ч", 1)
                "а" -> inputConnection!!.commitText("а", 1)
                "с" -> inputConnection!!.commitText("с", 1)
                "д" -> inputConnection!!.commitText("д", 1)
                "ф" -> inputConnection!!.commitText("ф", 1)
                "г" -> inputConnection!!.commitText("г", 1)
                "х" -> inputConnection!!.commitText("х", 1)
                "й" -> inputConnection!!.commitText("й", 1)
                "к" -> inputConnection!!.commitText("к", 1)
                "л" -> inputConnection!!.commitText("л", 1)
                "ш" -> inputConnection!!.commitText("ш", 1)
                "щ" -> inputConnection!!.commitText("щ", 1)
                "з" -> inputConnection!!.commitText("з", 1)
                "ь" -> inputConnection!!.commitText("ь", 1)
                "ц" -> inputConnection!!.commitText("ц", 1)
                "ж" -> inputConnection!!.commitText("ж", 1)
                "б" -> inputConnection!!.commitText("б", 1)
                "н" -> inputConnection!!.commitText("н", 1)
                "м" -> inputConnection!!.commitText("м", 1)
                "ю" -> inputConnection!!.commitText("ю", 1)
            }
        }
    }

    fun setInputConnection(ic: InputConnection?) {
        inputConnection = ic
    }
}
