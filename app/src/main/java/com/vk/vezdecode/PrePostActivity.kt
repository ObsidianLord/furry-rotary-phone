package com.vk.vezdecode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vk.vezdecode.vo.FundView
import kotlinx.android.synthetic.main.pre_post_activity.*
import kotlinx.android.synthetic.main.snippet.*
import java.lang.IllegalStateException

class PrePostActivity : AppCompatActivity() {

    private lateinit var fundView: FundView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pre_post_activity)

        fundView = ApplicationState.FundCreationState.fundView ?: throw IllegalStateException()

        toolbar.title = fundView.author?.let { it.split(" ")[0] }

        nameTextView.text = fundView.name
        // TODO
        shortDescriptionTextView.text = resources.getString(R.string.snippet_short_description_format).format(fundView.name, 5)

        fundedTextView.text = resources.getString(R.string.snippet_funded_format).format("0 Р", fundView.price.toString() + " Р")
    }
}