package com.uzwak8xllc.xchange


import android.widget.TextView

enum class Currency(val full: String, val sign: String, val code: String, val xRate: Double) {
    CAN_DOLLAR("Canadian Dollar", "\$", "CAD", 51.0),
    DOLLAR("United States dollar", "\$", "USD", 74.0),
    EURO("Euro", "€", "EUR", 78.44),
    FRANC("Swiss Franc", "₣", "CHF", 73.34),
    IR_RIAL("Iranian rial", ".ر.ا", "IRR", 0.001656),
    POUND("Great Britain pound sterling", "£", "GBP", 87.68),
    RUBLE("Russian Federation ruble", "₽", "RUR", 1.00),
    SHEKEL("New Israeli shekel", "₪", "ILS", 20.03),

    ERROR("Invalid currency entered", "~", "ERR", 0.0);

    fun getRate(inCode: TextView, outCode: TextView): Double {
        val inc = inCode.text.toString()
        val outc = outCode.text.toString()
        var rate1 = 0.0

        for (curr in values()) if (curr.code == inc) rate1 = curr.xRate

        if (rate1 == 0.0) return 0.0

        for (curr in values()) if (curr.code == outc) return curr.xRate / rate1

        return 0.0
    }

    fun findByCode(wcode: String): Currency {
        for (curr in values()) if (curr.code == wcode) return curr

        return ERROR
    }

    fun findBySign(wsign: String): Currency {
        for (curr in values()) if (curr.sign == wsign) {

            return curr
        }

        return ERROR
    }

    fun findByFull(wstr: String): Currency {
        for (curr in values()) if (curr.full == wstr) {

            return curr
        }

        return ERROR
    }
}