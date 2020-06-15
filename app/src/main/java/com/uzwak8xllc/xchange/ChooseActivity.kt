package com.uzwak8xllc.xchange

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.iterator

import kotlinx.android.synthetic.main.activity_choose.*

var error_msg = ""


class ChooseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        toFrom.text = if (direction == "from") getString(R.string.choose_in) else getString(R.string.choose_out)

        for (row in table) {
            row.setOnClickListener(::setRate)
        }
        Log.d("SET_RATE", "Rows are now clickable")
    }


    private fun setRate(v: View) {
        v.background = getDrawable(R.color.colorPrimary)
        val from = direction == "from"
        Log.d("SET_RATE", "Got direction: $direction")
        val curr = getCurrencyById(v.id)

        Log.d("SET_RATE", "Looking for last currency: ...")
        val last = Currency.IR_RIAL.findByCode(if (from) curr_in.code else curr_out.code)

        Log.d("SET_RATE", "Currency change: ${last.name} to ${curr.name}")

        if (from) {
            curr_in = curr
            rate = rate * last.xRate / curr.xRate
        } else {
            curr_out = curr
            rate = rate * curr.xRate / last.xRate
        }
        Log.d("set rate", "Currency set")

        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun getCurrencyById(ident: Int): Currency {
        return when (ident) {
            R.id.CAN_DOLLAR -> Currency.CAN_DOLLAR
            R.id.DOLLAR -> Currency.DOLLAR
            R.id.EURO -> Currency.EURO
            R.id.RUBLE -> Currency.RUBLE
            R.id.POUND -> Currency.POUND
            R.id.SHEKEL -> Currency.SHEKEL
            R.id.SWISS_FRANC -> Currency.FRANC
            R.id.IR_RIAL -> Currency.IR_RIAL
            else -> {
                error_msg = "Unidentified currency error, please try again"
                Currency.ERROR
            }
        }
    }
}