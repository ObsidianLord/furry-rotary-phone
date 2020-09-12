package com.vk.vezdecode

import com.vk.vezdecode.vo.FundView

object ApplicationState {
    object CurrentFundState {
        var currentFundMoney: Long? = null
        var fundView: FundView? = null
    }

    object FundCreationState {
        var fundView: FundView? = null
    }
}