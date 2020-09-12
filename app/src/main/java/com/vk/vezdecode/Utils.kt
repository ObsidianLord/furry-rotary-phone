package com.vk.vezdecode

import java.util.*

object Utils {
    fun datesToDifferenceText(fromDate: Date, toDate: Date): String {
        val untilDiffMillis = (toDate.time - fromDate.time)

        val daysUntilEnd = untilDiffMillis / 1000 / 60 / 60 / 24
        val hoursUntilEnd = untilDiffMillis / 1000 / 60 / 60
        val minutesUntilEnd = untilDiffMillis / 1000 / 60

        return when {
            daysUntilEnd > 0 -> "$daysUntilEnd дней"
            hoursUntilEnd > 0 -> "$hoursUntilEnd часов"
            minutesUntilEnd > 0 -> "$minutesUntilEnd минут"

            (daysUntilEnd + hoursUntilEnd + minutesUntilEnd) < 0 -> ""
            else -> ""
        }
    }
}