package com.uzwak8xllc.xchange

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.pow
import kotlin.math.round

fun swap(v1: TextView, v2: TextView) {
    val temp = v1.text
    v1.text = v2.text
    v2.text = temp
}

fun dForm(n: Double, e: Int = 2) = round(n * 10.0.pow(e.toDouble())) / 10.0.pow(e.toDouble())

var direction = ""

var curr_in = Currency.ERROR
var curr_out = Currency.ERROR

var rate = 42.0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        update()

        curr_in = Currency.RUBLE.findByCode(inCode.text.toString())
        curr_out = Currency.FRANC.findByCode(outCode.text.toString())

        rate = curr_in.xRate / curr_out.xRate

        currentRate.text = "${dForm(rate, 5)}"

        input.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //input.setText("${dForm(input.text.toString().toDouble())}")
                return
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calc()
            }


        })

        // val result = URL("https://api.exchangeratesapi.io/latest?base=USD").readText()
        // output.text = result

        interchange.setOnClickListener(::interswap)

        inSign.setOnClickListener(::goChoose)
        outSign.setOnClickListener(::goChoose)
        inCode.setOnClickListener(::goChoose)
        outCode.setOnClickListener(::goChoose)

    }

    private fun interswap(v: View) {
        swap(inSign, outSign)
        swap(inCode, outCode)

        val temp = curr_in
        curr_in = curr_out
        curr_out = temp

        rate = 1 / rate
        currentRate.text = "${dForm(rate, 5)}"

        calc()
    }

    private fun goChoose(v: View) {
        direction = if (v == inSign || v == inCode) "from" else "to"

        startActivity(Intent(this, ChooseActivity::class.java))
    }

    private fun calc() {
        if (input.text.toString() == "") return

         output.text = "${dForm(input.text.toString().toDouble() * currentRate.text.toString().toDouble())}"
    }

    private fun update() {
        if (curr_in != Currency.ERROR && curr_out != Currency.ERROR) {
            inSign.text = curr_in.sign
            inCode.text = curr_in.code

            outSign.text = curr_out.sign
            outCode.text = curr_out.code
        } else {
            errorMsg.text = getString(R.string.CurrencyError)
        }
    }
}