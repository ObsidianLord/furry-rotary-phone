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
import com.vk.vezdecode.model.FundType
import com.vk.vezdecode.vo.FundView
import kotlinx.android.synthetic.main.fund_info_edit_activity.*
<<<<<<< Updated upstream
import java.lang.IllegalStateException
=======
import java.io.BufferedInputStream
import java.io.InputStream

>>>>>>> Stashed changes

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
        }

        when (editText.id) {
            R.id.nameEditText -> fundView.name = text
            R.id.priceEditText -> {
                if (text.matches(Regex("\\d+"))) {
                    fundView.price = text.toInt()
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
        if (!fundView.name.isNullOrEmpty() && fundView.price != null) {
            val price = fundView.price ?: -1
            buttonNext.isEnabled = price > 0
        } else {
            buttonNext.isEnabled = false
        }
    }

    fun onButtonNextClick(view: View) {

        val activityClass = when(fundView.type) {
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