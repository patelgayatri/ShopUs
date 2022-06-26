package com.techand.shopus.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.currencyconvertor.Currency

import com.techand.shopus.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var result = Currency.convert(2000, 1.5)
        val intent = Intent(this, MainActivity::class.java)
        GlobalScope.launch {
            delay(3000L)
            startActivity(intent)
            finish()
        }
    }
}