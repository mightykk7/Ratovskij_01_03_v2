package com.dignerdranch.anroid.up_ratovskij

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import android.widget.Toast

class Second : AppCompatActivity() {
    private lateinit var btnCount: Button
    private lateinit var shapes: Spinner
    private lateinit var v: EditText
    private lateinit var pic: ImageView
    private lateinit var rect: ImageView
    private lateinit var rectDop: ImageView
    private lateinit var b: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        btnCount = findViewById(R.id.btnCount)
        shapes = findViewById(R.id.shape)
        v = findViewById(R.id.editTextValue)
        b = findViewById(R.id.inputB)
        pic = findViewById(R.id.formula)
        rect = findViewById(R.id.rect4)
        rectDop = findViewById(R.id.rectR)

        val adapter = ArrayAdapter.createFromResource(this, R.array.items, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        shapes.adapter = adapter
        shapes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selShape = parent.getItemAtPosition(position).toString()
                ViewPic(selShape)
                val density = resources.displayMetrics.density

                val paramsRect = rect.layoutParams
                val paramsEditTextV = v.layoutParams
                if (selShape == "Круг") {
                    paramsRect.width = (300 * density).toInt()
                    rectDop.visibility = View.INVISIBLE
                    b.visibility = View.INVISIBLE
                    paramsEditTextV.width = (280 * density).toInt()
                    v.hint = getString(R.string.data)
                } else {
                    paramsRect.width = (140 * density).toInt()
                    rectDop.visibility = View.VISIBLE
                    b.visibility = View.VISIBLE
                    paramsEditTextV.width = (120 * density).toInt()
                    v.hint = getString(R.string.inputA)
                }
                rect.layoutParams = paramsRect
                view.requestLayout()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                pic.setImageDrawable(null)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Показ картинки с формулой
    private fun ViewPic(shape: String) {
        val shapeID = when (shape) {
            "Круг" -> R.drawable.v1
            "Треугольник" -> R.drawable.triangle
            else -> null
        }
        if (shapeID != null)
            pic.setImageResource(shapeID)
        else
            pic.setImageDrawable(null)
    }

    // Проверка корректности значений
    private fun ValidValue(): Boolean {
        val value1 = v.text.toString()
        val value2 = b.text.toString()

        if (value1 == "." || value2 == ".") {
            showToast("Неверный ввод")
            return false
        }
        if (value1.isEmpty() || (b.isVisible && value2.isEmpty())) {
            showToast("Введите значение")
            return false
        }

        // Проверка существования треугольника (неравенство треугольника)
        // Для равнобедренного треугольника со сторонами a, a, b: должно выполняться: 2a > b
        if (shapes.selectedItem.toString() == "Треугольник") {
            val a = value1.toDoubleOrNull() ?: return false
            val bVal = value2.toDoubleOrNull() ?: return false
            if (2 * a <= bVal) {
                showToast("Такого треугольника не существует")
                return false
            }
        }

        return true
    }

    // Удобный метод для показа Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Расчёт
    fun Count(view: View) {
        if (!ValidValue()) return

        val selShape = shapes.selectedItem.toString()
        var result: Double = 0.0
        val a = v.text.toString().toDouble()

        when (selShape) {
            "Круг" -> {
                result = a / (2 * Math.PI)  // возможно, это радиус? Уточните: a — это длина окружности?
            }
            "Треугольник" -> {
                val bVal = b.text.toString().toDouble()
                result = 2 * a + bVal  // периметр равнобедренного треугольника: 2a + b
            }
            else -> {
                showToast("Выберите фигуру")
                return
            }
        }

        val intent = Intent(this, Fierd_Activivity::class.java)
        intent.putExtra("selShape", selShape)
        intent.putExtra("result", result)
        startActivity(intent)
        finish()
    }
}