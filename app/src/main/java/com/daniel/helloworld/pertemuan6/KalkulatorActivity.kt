package com.daniel.helloworld.pertemuan6

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

class KalkulatorActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var tvOp: TextView
    private lateinit var etFirst: EditText
    private lateinit var etSecond: EditText
    private lateinit var btnCalculate: Button
    private lateinit var btnAdd: Button
    private lateinit var btnSub: Button
    private lateinit var btnMpy: Button
    private lateinit var btnDiv: Button

    private var result: Double = 0.0
    private var op: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kalkulator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
    }

    private fun addOp(value: String) {
        tvOp.text = value
        tvOp.alpha = 1.0f
        op = value
    }

    private fun calculateResult() {
        val a = etFirst.text.trim().toString()
        val b = etSecond.text.trim().toString()

        if (a.isEmpty() || b.isEmpty() || op.isEmpty()) {
            Toast.makeText(this, "Isi form yang diperlukan", Toast.LENGTH_SHORT).show()
            return
        }

        when (op) {
            "+" -> result = a.toDouble() + b.toDouble()
            "-" -> result = a.toDouble() - b.toDouble()
            "X" -> result = a.toDouble() * b.toDouble()
            ":" -> result = a.toDouble() / b.toDouble()
        }

        tvResult.text = result.toString()
        tvResult.alpha = 1.0f
    }

    private fun initView() {
        tvResult = findViewById(R.id.tv_result)
        tvOp = findViewById(R.id.tv_op)
        etFirst = findViewById(R.id.et_first)
        etSecond = findViewById(R.id.et_second)
        btnCalculate = findViewById(R.id.btn_calculate)
        btnAdd = findViewById(R.id.btn_add)
        btnSub = findViewById(R.id.btn_sub)
        btnMpy = findViewById(R.id.btn_mpy)
        btnDiv = findViewById(R.id.btn_div)

        btnAdd.setOnClickListener { addOp("+") }
        btnSub.setOnClickListener { addOp("-") }
        btnMpy.setOnClickListener { addOp("X") }
        btnDiv.setOnClickListener { addOp(":") }
        btnCalculate.setOnClickListener { calculateResult() }
    }
}