package com.techand.shopus.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.techand.shopus.R
import com.techand.shopus.redux.AppStore
import com.techand.shopus.redux.StateChangeListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Splash : AppCompatActivity() {
    var listener: StateChangeListener? = null
    lateinit var store :AppStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val intent = Intent(this, MainActivity::class.java)
        GlobalScope.launch {
            delay(3000L)
            startActivity(intent)
            finish()
        }
    }
}