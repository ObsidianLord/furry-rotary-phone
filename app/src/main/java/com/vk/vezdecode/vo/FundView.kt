package com.vk.vezdecode.vo

import android.graphics.Bitmap
import com.vk.vezdecode.model.FundEndsCondition
import com.vk.vezdecode.model.FundType
import java.time.LocalDate
import java.util.*

data class FundView(
    var type: FundType? = null,

    var thumbnailFilePath: String? = null,
    var name: String? = null,
    var price: Long? = null,
    var goal: String? = null,
    var description: String? = null,
    var accountPaymentName: String? = null,
    var author: String? = null,
    var image: Bitmap? = null,

    var fundEndsCondition: FundEndsCondition? = null,
    var fundEndsDate: Date? = null
)