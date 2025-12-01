package com.dignerdranch.anroid.up_ratovskij

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Fierd_Activivity : AppCompatActivity() {
    private lateinit var res: TextView
    private lateinit var shape: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fierd_activivity)

        res = findViewById(R.id.res)
        shape = findViewById(R.id.shapeName)

        val selShape = intent.getStringExtra("selShape")
        val result = intent.getDoubleExtra("result", 0.0)

        shape.text = selShape
        res.text = "%.2f".format(result)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun OnFirstActivity(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}