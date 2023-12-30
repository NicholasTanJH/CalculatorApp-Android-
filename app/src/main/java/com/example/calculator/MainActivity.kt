package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var lastNumeric = false
    private var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    //view = clicked button
    fun onDigit(view: View) {
        //add something
        tvInput?.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        tvInput?.text = ""
        lastDot = false
        lastNumeric = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        tvInput?.text?.let {
            if ((lastNumeric && !gotOperator(it.toString()))) {
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    private fun gotOperator(text: String): Boolean {
        return if (text.startsWith("-")) {
            false
        } else {
            text.contains("/") ||
                    text.contains("*") ||
                    text.contains("+") ||
                    text.contains("-")
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvNum = tvInput?.text.toString()
            var prefix = ""
            try {
                if (tvNum.startsWith("-")) {
                    prefix = "-"
                    tvNum = tvNum.substring(1)
                }
                if (tvNum.contains("-")) {
                    val tvValue = tvNum.split("-")
                    var value1 = tvValue[0]
                    val value2 = tvValue[1]
                    if (prefix.isNotEmpty()) {
                        value1 = prefix + value1
                    }
                    tvInput?.text =
                        removeZeroAfterDot((value1.toDouble() - value2.toDouble()).toString())
                }
                if (tvNum.contains("+")) {
                    val tvValue = tvNum.split("+")
                    var value1 = tvValue[0]
                    val value2 = tvValue[1]
                    if (prefix.isNotEmpty()) {
                        value1 = prefix + value1
                    }
                    tvInput?.text =
                        removeZeroAfterDot((value1.toDouble() + value2.toDouble()).toString())
                }
                if (tvNum.contains("*")) {
                    val tvValue = tvNum.split("*")
                    var value1 = tvValue[0]
                    val value2 = tvValue[1]
                    if (prefix.isNotEmpty()) {
                        value1 = prefix + value1
                    }
                    tvInput?.text =
                        removeZeroAfterDot((value1.toDouble() * value2.toDouble()).toString())
                }
                if (tvNum.contains("/")) {
                    val tvValue = tvNum.split("/")
                    var value1 = tvValue[0]
                    val value2 = tvValue[1]
                    if (prefix.isNotEmpty()) {
                        value1 = prefix + value1
                    }
                    tvInput?.text =
                        removeZeroAfterDot((value1.toDouble() / value2.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace() //Logcat?
            }
        }
    }

    private fun removeZeroAfterDot(value: String): String {
        return if (value.contains(".0")) {
            value.substringBefore(".0")
        } else {
            value
        }
    }
}