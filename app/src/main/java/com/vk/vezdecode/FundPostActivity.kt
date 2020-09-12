package com.vk.vezdecode

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vk.vezdecode.Utils.datesToDifferenceText
import com.vk.vezdecode.vo.FundView
import kotlinx.android.synthetic.main.fund_post_activity.*
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit

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

        fundedTextView1.text = resources.getString(R.string.snippet_funded_format).format(Constants.RUSSIAN_PRICE_FORMAT.format(ApplicationState.CurrentFundState.currentFundMoney ?: 0), Constants.RUSSIAN_PRICE_FORMAT.format(fundView.price))

        descriptionTextView.text = fundView.description


        val qwe = Executors.newScheduledThreadPool(1, object: ThreadFactory {
            override fun newThread(r: Runnable): Thread {
                return Thread(r)
            }
        })

        var i = 0
        qwe.scheduleAtFixedRate({
            if (i < 100) {
                i += 1
                updateProgress(i)
            }
        }, 0L, 200, TimeUnit.MILLISECONDS)
    }


    fun updateProgress(i: Int) {
        runOnUiThread {
            progressBar.progress = i
            fatCardProgress.layoutParams.width = (fatMaxCardProgress.width * i / 100).toInt()

            val testPaint = Paint()
            testPaint.set(textViewLeft.getPaint())
            val availableWidth = textViewLeft.width - textViewLeft.getPaddingLeft() - textViewLeft.getPaddingRight() - testPaint.measureText(textViewLeft.text.toString())
            Log.d("MY WIDTH", availableWidth.toString())

            if (availableWidth < 0) {
                textViewRight.visibility = View.VISIBLE
                textViewLeft.visibility = View.INVISIBLE
            } else {
                textViewLeft.visibility = View.VISIBLE
                textViewRight.visibility = View.INVISIBLE
            }


            if ((fatMaxCardProgress.width * i / 100) + referenceValueTop.width >= fatMaxCardProgress.width) {
                referenceValueInPlace.visibility = View.INVISIBLE
                referenceValueTop.visibility = View.VISIBLE
            } else {
                referenceValueInPlace.visibility = View.VISIBLE
                referenceValueTop.visibility = View.INVISIBLE

            }


            fatCardProgress.requestLayout()
        }
    }
}