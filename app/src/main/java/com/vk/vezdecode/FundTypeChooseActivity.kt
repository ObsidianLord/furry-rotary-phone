package com.vk.vezdecode

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vk.vezdecode.model.FundType
import com.vk.vezdecode.vo.FundView
import kotlinx.android.synthetic.main.fund_type_choose_activity.*


class FundTypeChooseActivity: AppCompatActivity() {

    private lateinit var fundView: FundView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fund_type_choose_activity)

        toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })

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

        val intent = Intent(this, FundInfoEditActivity::class.java)
        startActivity(intent)
    }
}