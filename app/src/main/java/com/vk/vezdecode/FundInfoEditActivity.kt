package com.vk.vezdecode

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.vk.vezdecode.Constants.PICK_IMAGE
import com.vk.vezdecode.Constants.RUBLE_CHARACTER
import com.vk.vezdecode.Constants.RUSSIAN_PRICE_FORMAT
import com.vk.vezdecode.model.FundType
import com.vk.vezdecode.vo.FundView
import kotlinx.android.synthetic.main.fund_info_edit_activity.*
import java.lang.IllegalStateException
import java.io.BufferedInputStream
import java.io.InputStream
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
        if (!fundView.name.isNullOrEmpty() && fundView.price != null) {
            val price = fundView.price ?: -1
            buttonNext.isEnabled = price > 0
        } else {
            buttonNext.isEnabled = false
        }


        if (fundView.image != null) {
            imageView.setImageBitmap(fundView.image)
            loadedImageLayout.visibility = View.VISIBLE
            requestingImageLayout.visibility = View.GONE
        }

        headerToolbar.setNavigationOnClickListener { finish() }

        run {
            when (fundView.type) {
                FundType.TARGET -> {
                    headerToolbar.title = resources.getString(R.string.targetFundTitle)
                    authorLayout.visibility = View.GONE
                }

                FundType.REGULAR -> {
                    headerToolbar.title = resources.getString(R.string.regularFundTitle)
                    authorLayout.visibility = View.VISIBLE
                    authorSpinner.onItemSelectedListener = createAuthorSpinnerItemSelectedListener()
                }
            }
        }

        listOf(nameEditText, priceEditText, goalEditText, descriptionEditText)
            .forEach { it.addTextChangedListener(createInfoEditTextWatcher(it)) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data!!.data != null) {
                val uri = data!!.data
                val inputStream: InputStream = contentResolver.openInputStream(uri!!)!!
                val bufferedInputStream = BufferedInputStream(inputStream);
                val bmp = BitmapFactory.decodeStream(bufferedInputStream);

                fundView.image = bmp

                imageView.setImageBitmap(bmp)
                loadedImageLayout.visibility = View.VISIBLE
                requestingImageLayout.visibility = View.GONE
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }

    fun onPickImageClick(view: View) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    fun onRemoveImageClick(view: View) {
        loadedImageLayout.visibility = View.GONE
        requestingImageLayout.visibility = View.VISIBLE
    }

    fun onInfoEditTextChanged(editText: EditText, textBefore: String?) {
        val text = editText.text?.toString()
        if (text.isNullOrEmpty()) {
            return
        }

        if (text.isBlank()) {
            editText.setText("")
            return
        }

        if (text == textBefore) {
            return
        }

        when (editText.id) {
            R.id.nameEditText -> fundView.name = text
            R.id.priceEditText -> {

                if (text.toString().contains(Regex("[^0-9 $RUBLE_CHARACTER]"))) {
                    editText.setText(textBefore)
                    return
                }

                if (text.length > 13) {
                    editText.setText(textBefore)
                    return
                }

                try {
                    fundView.price = RUSSIAN_PRICE_FORMAT.parse(text)!!.toLong()
                    editText.setText(RUSSIAN_PRICE_FORMAT.format(fundView.price!!))
                } catch (ex: ParseException) {
                    if (text.matches(Regex("^\\s*\\d+\\s*$"))) {
                        fundView.price = text.trim().toLong()
                        editText.setText(RUSSIAN_PRICE_FORMAT.format(fundView.price!!))
                    } else if (text.length < (textBefore?.length ?: 0)) {
                        if (textBefore!!.matches(Regex("\\d $RUBLE_CHARACTER"))) {
                            fundView.price = null
                            editText.text = null
                        } else {
                            editText.setText(textBefore)
                        }
                        return
                    }
                }

                editText.setSelection(editText.text.toString().length - 2)
            }
            R.id.goalEditText -> fundView.goal = text
            R.id.descriptionEditText -> fundView.description = text
        }

        run {
            val nameFilled = !fundView.name.isNullOrBlank()
            val priceFilled = (fundView.price != null)


            buttonNext.isEnabled = nameFilled && priceFilled
        }
    }

    fun onButtonNextClick(view: View) {

        val activityClass = when (fundView.type) {
            FundType.TARGET -> AdditionalActivity::class.java
            FundType.REGULAR -> PrePostActivity::class.java
            else -> throw IllegalStateException()
        }
        val intent = Intent(this, activityClass)

        startActivity(intent)
    }

    private fun createAuthorSpinnerItemSelectedListener() =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                fundView.author = resources.getStringArray(R.array.authorNames)[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

    private fun createInfoEditTextWatcher(editText: EditText) = object : TextWatcher {
        var textBefore: String? = null

        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int, count: Int, after: Int
        ) {
            textBefore = s?.toString()
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onInfoEditTextChanged(editText, textBefore)
        }

        override fun afterTextChanged(s: Editable) {
        }
    }
}