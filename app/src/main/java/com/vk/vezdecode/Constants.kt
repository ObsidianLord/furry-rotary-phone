package com.vk.vezdecode

import android.widget.AdapterView
import android.widget.Spinner
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object Constants {
    const val RUBLE_CHARACTER = '₽'

    val RUSSIAN_PRICE_FORMAT = DecimalFormat(
        "#,### $RUBLE_CHARACTER",
        DecimalFormatSymbols(Locale.forLanguageTag("RU")).apply {
            groupingSeparator = ' '
        }
    )

    const val PICK_IMAGE = 1
}