package com.daniel.helloworld.mytest.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.daniel.helloworld.R
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class CalculatorActivity : AppCompatActivity() {

    private lateinit var btnDot: Button
    private lateinit var btnLeftCell: Button
    private lateinit var btnRightCell: Button
    private lateinit var btnAdd: Button

    private lateinit var btnOne: Button
    private lateinit var btnTwo: Button
    private lateinit var btnThree: Button
    private lateinit var btnSub: Button

    private lateinit var btnFour: Button
    private lateinit var btnFive: Button
    private lateinit var btnSix: Button
    private lateinit var btnMpy: Button

    private lateinit var btnSeven: Button
    private lateinit var btnEight: Button
    private lateinit var btnNine: Button
    private lateinit var btnDiv: Button

    private lateinit var btnClear: Button
    private lateinit var btnDel: Button
    private lateinit var btnZero: Button
    private lateinit var btnResult: Button


    private lateinit var tvDisplay: TextView

    private var display: String = "0.0"
    private var result: Double = 0.0
    private var isResult: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculator)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        tvDisplay = findViewById(R.id.tv_display)
        initDisplay()
        initButton()
    }

    private fun initDisplay() {
        if (display == "0") display = "0.0"
        tvDisplay.text = display
        if (display == "0.0") {
            tvDisplay.alpha = 0.5f
        } else {
            tvDisplay.alpha = 1.0f
        }
    }

    private fun setDisplay(value: String) {
        if (display == "0.0" || display == "0" || isResult) {
            display = ""
            isResult = false
        }
        display += value
        initDisplay()
    }

    private fun setResult() {
        try {
            val expression = ExpressionBuilder(display).build()
            result = expression.evaluate()
            display = result.toString()
            isResult = true
            initDisplay()
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun clearAll() {
        isResult = false
        result = 0.0
        display = "0.0"
        initDisplay()
    }

    private fun delete() {
        val length = display.length
        if (length > 0) {
            display = display.subSequence(0, length - 1).toString()
            if (display.isEmpty()) {
                display = "0.0"
            }
        }
        initDisplay()
    }

    private fun initButton() {
        btnDot = findViewById(R.id.btn_dot)
        btnLeftCell = findViewById(R.id.btn_left_cell)
        btnRightCell = findViewById(R.id.btn_right_cell)
        btnAdd = findViewById(R.id.btn_add)

        btnOne = findViewById(R.id.btn_one)
        btnTwo = findViewById(R.id.btn_two)
        btnThree = findViewById(R.id.btn_three)
        btnSub = findViewById(R.id.btn_sub)

        btnFour = findViewById(R.id.btn_four)
        btnFive = findViewById(R.id.btn_five)
        btnSix = findViewById(R.id.btn_six)
        btnMpy = findViewById(R.id.btn_mpy)

        btnSeven = findViewById(R.id.btn_seven)
        btnEight = findViewById(R.id.btn_eight)
        btnNine = findViewById(R.id.btn_nine)
        btnDiv = findViewById(R.id.btn_div)

        btnClear = findViewById(R.id.btn_clear)
        btnDel = findViewById(R.id.btn_del)
        btnZero = findViewById(R.id.btn_zero)
        btnResult = findViewById(R.id.btn_result)

        btnDot.setOnClickListener { setDisplay(".") }
        btnLeftCell.setOnClickListener { setDisplay("(") }
        btnRightCell.setOnClickListener { setDisplay(")") }
        btnAdd.setOnClickListener { setDisplay("+") }

        btnOne.setOnClickListener { setDisplay("1") }
        btnTwo.setOnClickListener { setDisplay("2") }
        btnThree.setOnClickListener { setDisplay("3") }
        btnSub.setOnClickListener { setDisplay("-") }

        btnFour.setOnClickListener { setDisplay("4") }
        btnFive.setOnClickListener { setDisplay("5") }
        btnSix.setOnClickListener { setDisplay("6") }
        btnMpy.setOnClickListener { setDisplay("*") }

        btnSeven.setOnClickListener { setDisplay("7") }
        btnEight.setOnClickListener { setDisplay("8") }
        btnNine.setOnClickListener { setDisplay("9") }
        btnDiv.setOnClickListener { setDisplay("/") }

        btnClear.setOnClickListener { clearAll() }
        btnDel.setOnClickListener { delete() }
        btnZero.setOnClickListener { setDisplay("0") }
        btnResult.setOnClickListener { setResult() }
    }


}