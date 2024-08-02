package com.daniel.helloworld.mytest.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.daniel.helloworld.R
import java.lang.Exception

class Calculator2Activity : AppCompatActivity() {

    private lateinit var btnAdd: Button
    private lateinit var btnSub: Button
    private lateinit var btnMpy: Button
    private lateinit var btnDiv: Button
    private lateinit var btnResult: Button
    private lateinit var tvOp: TextView
    private lateinit var tvResult: TextView
    private lateinit var etFirst: EditText
    private lateinit var etSecond: EditText

    private var op: String = ""
    private var result: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculator2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
    }

    private fun setOperator(value: String) {
        tvOp.alpha = 1.0f
        tvOp.text = value
        op = value
    }

    private fun setResult() {
        try {
            val first = etFirst.text.trim().toString().toDouble()
            val second = etSecond.text.trim().toString().toDouble()

            when (op) {
                "+" -> result = first + second
                "-" -> result = first - second
                "X" -> result = first * second
                ":" -> result = first / second
            }

            tvResult.alpha = 1.0f
            tvResult.text = result.toString()

        } catch (e: Exception) {
            tvResult.alpha = 0.5f
            tvResult.text = "0.0"
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }

    }

    private fun initView() {
        tvResult = findViewById(R.id.tv_result)
        tvOp = findViewById(R.id.tv_op)
        etFirst = findViewById(R.id.et_first_number)
        etSecond = findViewById(R.id.et_second_number)

        btnAdd = findViewById(R.id.btn_add)
        btnSub = findViewById(R.id.btn_sub)
        btnMpy = findViewById(R.id.btn_mpy)
        btnDiv = findViewById(R.id.btn_div)
        btnResult = findViewById(R.id.btn_result)

        btnAdd.setOnClickListener { setOperator("+") }
        btnSub.setOnClickListener { setOperator("-") }
        btnMpy.setOnClickListener { setOperator("X") }
        btnDiv.setOnClickListener { setOperator(":") }
        btnResult.setOnClickListener { setResult() }
    }
}