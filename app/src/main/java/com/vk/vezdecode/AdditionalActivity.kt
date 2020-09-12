package com.vk.vezdecode

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.funds_activity.*
import java.util.*


class FundsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.additional_activity)
    }

    fun onFundCreateButtonClicked(view: View) {
        val intent = Intent(this, FundTypeChooseActivity::class.java)
        startActivity(intent)
    }

    fun onDateButtonClicked(view: View) {
        val c = Calendar.getInstance()
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]


        val datePickerDialog = DatePickerDialog(
            this,
            OnDateSetListener { view_, year, monthOfYear, dayOfMonth -> setDateAndEnableButton(year, monthOfYear, dayOfMonth)},
            mYear,
            mMonth,
            mDay
        )
        datePickerDialog.show()
    }

    private fun setDateAndEnableButton(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val datStr = if (dayOfMonth > 9) dayOfMonth.toString() else "0${dayOfMonth}"
        val monthStr = if (monthOfYear > 8) (monthOfYear + 1).toString() else "0${monthOfYear + 1}"
        dateTextView.text = "${datStr}.${monthStr}.${year}"
        buttonNext.isEnabled = true
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radioEndBySum ->
                    if (checked) {
                        calendarDateLayout.visibility = View.INVISIBLE
                        buttonNext.isEnabled = true
                    }
                R.id.radioEndByDate ->
                    if (checked) {
                        calendarDateLayout.visibility = View.VISIBLE
                        buttonNext.isEnabled = dateTextView.text.isNotEmpty()
                    }
            }
        }
    }
}
