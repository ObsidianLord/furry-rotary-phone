package com.vk.vezdecode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vk.vezdecode.Utils.datesToDifferenceText
import com.vk.vezdecode.vo.FundView
import kotlinx.android.synthetic.main.fund_post_activity.*
import kotlinx.android.synthetic.main.snippet.*
import kotlinx.android.synthetic.main.snippet.nameTextView
import java.util.*

class FundPostActivity : AppCompatActivity() {

    private lateinit var fundView: FundView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fund_post_activity)

        fundView = ApplicationState.FundCreationState.fundView ?: throw IllegalStateException()

        nameTextView.text = fundView.name
        authorTextView.text =
            resources.getString(R.string.post_author_format).format(fundView.author)

        fundEndsInTextView.text = fundView.fundEndsDate?.let { fundEndsDate ->
            resources.getString(R.string.fund_until_date_format)
                .format(datesToDifferenceText(Date(), fundEndsDate))
        } ?: ""

        fundedTextView.text = resources.getString(R.string.snippet_funded_format).format(Constants.RUSSIAN_PRICE_FORMAT.format(ApplicationState.CurrentFundState.currentFundMoney), Constants.RUSSIAN_PRICE_FORMAT.format(fundView.price))

        descriptionTextView.text = fundView.description
    }
}