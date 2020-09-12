package com.vk.vezdecode

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.vk.vezdecode.model.FundType
import com.vk.vezdecode.vo.FundView
import kotlinx.android.synthetic.main.fund_info_edit_activity.*
import kotlinx.android.synthetic.main.funds_activity.*
import java.lang.IllegalStateException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.util.*

class FundInfoEditActivity : AppCompatActivity() {

    private lateinit var fundView: FundView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fund_info_edit_activity)

        fundView = ApplicationState.FundCreationState.fundView ?: throw IllegalStateException()

        toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })

        run {
            when (fundView.type) {
                FundType.TARGET -> {
                    headerToolbar.title = "Целевой сбор"
                    authorLayout.visibility = View.INVISIBLE
                }

                FundType.REGULAR -> {
                    headerToolbar.title = "Регулярный сбор"
                    authorLayout.visibility = View.VISIBLE
                }
            }
        }

        fun createTextWatcher(editText: EditText): TextWatcher {
            var textBefore: String? = null

            return object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int, count: Int, after: Int
                ) {
                    textBefore = s?.toString()
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    beforeInfoEditTextChange(editText, textBefore)
                }

                override fun afterTextChanged(s: Editable) {
                }
            }
        }

        listOf(nameEditText, priceEditText, goalEditText, descriptionEditText)
            .forEach { editText -> editText.addTextChangedListener(createTextWatcher(editText)) }
    }

    fun beforeInfoEditTextChange(editText: EditText, textBefore: String?) {
        val text = editText.text?.toString()
        if (text.isNullOrEmpty()) {
            return
        }

        if (text.isBlank()) {
            editText.setText("")
        }

        when (editText.id) {
            R.id.nameEditText -> fundView.name = text
            R.id.priceEditText -> {
                if (text.matches(Regex("\\d+"))) {
                    fundView.price = text.toInt()
                    /*editText.setText(priceFormat.format(price))*/
                    return
                }

                /*try {
                    price = priceFormat.parse(text)!!.toInt()
                } catch (ex: ParseException) {
                    editText.setText(textBefore)
                }*/
            }
            R.id.goalEditText -> fundView.goal = text
            R.id.descriptionEditText -> fundView.description = text
        }
    }

    fun onButtonNextClick(view: View) {
        val intent = Intent(this, AdditionalActivity::class.java)
        startActivity(intent)
    }
}