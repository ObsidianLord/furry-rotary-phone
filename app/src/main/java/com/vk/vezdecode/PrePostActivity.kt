package com.vk.vezdecode

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vk.vezdecode.Constants.RUSSIAN_PRICE_FORMAT
import com.vk.vezdecode.Utils.datesToDifferenceText
import com.vk.vezdecode.vo.FundView
import kotlinx.android.synthetic.main.fund_info_edit_activity.*
import kotlinx.android.synthetic.main.pre_post_activity.*
import kotlinx.android.synthetic.main.snippet.*
import java.lang.IllegalStateException
import java.time.LocalDateTime
import java.util.*

class PrePostActivity : AppCompatActivity() {

    private lateinit var fundView: FundView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pre_post_activity)

        run {
            fundView = ApplicationState.FundCreationState.fundView ?: throw IllegalStateException()

            ApplicationState.CurrentFundState.currentFundMoney = 0
            ApplicationState.CurrentFundState.fundView = fundView
        }

        toolbar.setNavigationOnClickListener { finish() }

        toolbar.title = fundView.author?.let { it.split(" ")[0] }

        nameTextView.text = fundView.name

        if (fundView.image != null) {
            thumbnailImageView.setImageBitmap(fundView.image)
        }

        val timeUntilString = fundView.fundEndsDate?.let { datesToDifferenceText(Date(), it) }
        shortDescriptionTextView.text = if (timeUntilString == null) {
            fundView.author
        } else {
            resources.getString(R.string.snippet_short_description_format)
                .format(fundView.author, timeUntilString)
        }

        fundedTextView.text = resources.getString(R.string.snippet_funded_format).format(RUSSIAN_PRICE_FORMAT.format(ApplicationState.CurrentFundState.currentFundMoney), RUSSIAN_PRICE_FORMAT.format(fundView.price))
    }

    fun onFundPostButtonClick(view: View) {
        val intent = Intent(this, FundFeedActivity::class.java)
        startActivity(intent)
    }
}