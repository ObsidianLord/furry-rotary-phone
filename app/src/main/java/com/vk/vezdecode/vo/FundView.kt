package com.vk.vezdecode.vo

import com.vk.vezdecode.model.FundEndsCondition
import com.vk.vezdecode.model.FundType
import java.time.LocalDate

data class FundView(
    var type: FundType? = null,

    val thumbnailFilePath: String? = null,
    val name: String? = null,
    val price: Int? = null,
    val goal: String? = null,
    val description: String? = null,
    val accountPaymentName: String? = null,
    val author: String? = null,

    var fundEndsCondition: FundEndsCondition? = null,
    var fundEndsDate: LocalDate? = null
)