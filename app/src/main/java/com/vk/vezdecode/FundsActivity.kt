package com.vk.vezdecode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class FundsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.funds_activity)
    }

    fun onFundCreateButtonClicked(view: View) {
        val intent = Intent(this, FundTypeChooseActivity::class.java)
        startActivity(intent)
    }
}