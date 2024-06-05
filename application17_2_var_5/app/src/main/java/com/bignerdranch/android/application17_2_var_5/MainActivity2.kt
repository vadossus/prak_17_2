package com.bignerdranch.android.application17_2_var_5

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity2 : AppCompatActivity() {
    lateinit var spinner: Spinner
    lateinit var inputsLayout: LinearLayout
    private lateinit var calculateButton: Button
    private lateinit var resultText: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var image_view: ImageView

    companion object {
        private const val LAST_SELECTED_FIGURE = "last_selected_figure"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        spinner = findViewById(R.id.spinner)
        inputsLayout = findViewById(R.id.input_layout)
        calculateButton = findViewById(R.id.button)
        resultText = findViewById(R.id.resulttext)
        image_view = findViewById(R.id.image)
        sharedPreferences = getPreferences(MODE_PRIVATE)
        val lastSelectedPosition = sharedPreferences.getInt(LAST_SELECTED_FIGURE, 0)
        ArrayAdapter.createFromResource(
            this,
            R.array.figure,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.setSelection(lastSelectedPosition)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: android.view.View,
                position: Int,
                id: Long
            ) {
                updateInputs(position)
                with(sharedPreferences.edit())
                {
                    putInt(LAST_SELECTED_FIGURE, position)
                    apply()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>)
            {
            }
        }
        calculateButton.setOnClickListener {
            calculatePerimeter()
        }
    }

    @SuppressLint("StringFormatInvalid", "StringFormatMatches")
    private fun calculatePerimeter() {
        val position = spinner.selectedItemPosition
        var perimeter = 0.0
        try {
            when (position) {
                0 -> {
                    val side1 = inputsLayout.getChildAt(0) as EditText
                    val side2 = inputsLayout.getChildAt(1) as EditText
                    val side3 = inputsLayout.getChildAt(2) as EditText

                    if (proverka_triygol(side1.text.toString().toDouble(), side2.text.toString().toDouble(), side3.text.toString().toDouble())) {
                        perimeter = side1.text.toString().toDouble() + side2.text.toString().toDouble() + side3.text.toString().toDouble()
                    } else {
                        resultText.text = "Треугольник с такими сторонами - не существует"
                        return
                    }
                }

                1 -> {
                    val width = inputsLayout.getChildAt(0) as EditText
                    val height = inputsLayout.getChildAt(1) as EditText
                    perimeter =
                        2 * (width.text.toString().toDouble() + height.text.toString().toDouble())
                }

                2 -> {
                    val radius = inputsLayout.getChildAt(0) as EditText
                    perimeter = 2 * Math.PI * radius.text.toString().toDouble()
                }
            }
            resultText.text = getString(R.string.perimeter_result, perimeter)
        } catch (e: NumberFormatException) {
            resultText.text = getString(R.string.invalid_input)
        }
    }

    private fun updateInputs(position: Int) {
        inputsLayout.removeAllViews()
        when (position) {
            0 -> {
                addInputField(getString(R.string.A))
                addInputField(getString(R.string.B))
                addInputField(getString(R.string.C))
                image_view.setImageResource(R.drawable.triangle)
            }

            1 -> {
                addInputField(getString(R.string.width))
                addInputField(getString(R.string.height))
                image_view.setImageResource(R.drawable.im)
            }

            2 -> {
                addInputField(getString(R.string.radius))
                image_view.setImageResource(R.drawable.circle)
            }
        }
    }

    private fun addInputField(hint: String) {
        val editText = EditText(this)
        editText.hint = hint
        editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        inputsLayout.addView(editText)
    }

    private fun proverka_triygol(side1: Double, side2: Double, side3: Double): Boolean {
        return side1 + side2 > side3 && side1 + side3 > side2 && side2 + side3 > side1
    }
}
