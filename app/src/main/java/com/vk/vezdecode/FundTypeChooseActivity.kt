package com.vk.vezdecode

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.vk.vezdecode.model.FundType
import com.vk.vezdecode.vo.FundView
import java.lang.IllegalStateException

class FundTypeChooseActivity: AppCompatActivity() {

    private lateinit var fundView: FundView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fund_type_choose_activity)

        fundView = ApplicationState.FundCreationState.fundView ?: FundView()
        ApplicationState.FundCreationState.fundView = fundView
    }

    fun onFundTypeButtonClicked(view: View) {
        val fundType = when(view.id) {
            R.id.regularFundCardView -> FundType.REGULAR
            R.id.targetFundCardView -> FundType.TARGET
            else -> throw IllegalStateException()
        }
        fundView.type = fundType

        startActivity(intent)
    }
}