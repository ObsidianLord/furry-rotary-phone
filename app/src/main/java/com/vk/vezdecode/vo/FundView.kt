package com.vk.vezdecode.vo

import com.vk.vezdecode.model.FundEndsCondition
import com.vk.vezdecode.model.FundType
import java.time.LocalDate

data class FundView(
    var type: FundType? = null,

    var thumbnailFilePath: String? = null,
    var name: String? = null,
    var price: Int? = null,
    var goal: String? = null,
    var description: String? = null,
    var accountPaymentName: String? = null,
    var author: String? = null,

    var fundEndsCondition: FundEndsCondition? = null,
    var fundEndsDate: LocalDate? = null
)