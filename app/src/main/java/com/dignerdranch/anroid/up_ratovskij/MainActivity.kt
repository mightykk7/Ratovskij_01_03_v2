package com.dignerdranch.anroid.up_ratovskij

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_activity)
    }
    fun Navigate(view: View){
        val intent = Intent(this, Second::class.java)
        startActivity(intent)
        finish()
    }
}