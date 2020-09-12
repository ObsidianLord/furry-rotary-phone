package com.vk.vezdecode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vk.vezdecode.vo.FundView

class FundPostActivity : AppCompatActivity() {

    private lateinit var fundView: FundView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fund_post_activity)

        fundView = ApplicationState.FundCreationState.fundView ?: throw IllegalStateException()
    }
}