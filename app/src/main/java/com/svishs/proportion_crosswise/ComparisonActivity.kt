package com.svishs.proportion_crosswise

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.svishs.proportion_crosswise.databinding.ActivityComparisonBinding
import java.util.Locale
import kotlin.math.abs

class ComparisonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComparisonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComparisonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // панель с заголовком и кнопкой «назад»
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val inpA: EditText = binding.numA
        val inpB: EditText = binding.numB
        val inpC: EditText = binding.numC
        val inpD: EditText = binding.numD
        val outResult: TextView = binding.compareResult

        binding.btnCompare.setOnClickListener {
            if (inpA.text.toString().isNotBlank() && inpB.text.toString().isNotBlank() &&
                inpC.text.toString().isNotBlank() && inpD.text.toString().isNotBlank()) {

                val a: Float = inpA.text.toString().toFloatOrNull() ?: 0f
                val b: Float = inpB.text.toString().toFloatOrNull() ?: 0f
                val c: Float = inpC.text.toString().toFloatOrNull() ?: 0f
                val d: Float = inpD.text.toString().toFloatOrNull() ?: 0f

                // знаменатели дробей (кол-во A и кол-во B) не могут быть нулевыми
                if (a == 0f || c == 0f) {
                    Toast.makeText(this, R.string.toast_denominator_zero, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // дроби для сравнения: b/a и d/c; крест-накрест: b/a и d/c  <=>  b·c и d·a
                val cross1: Float = b * c
                val cross2: Float = d * a

                // показываем, какая дробь меньше (меньше b/a — вариант A дешевле)
                val verdictRes = when {
                    abs(cross1 - cross2) < 0.000001f -> R.string.compare_result_equal
                    cross1 < cross2 -> R.string.compare_result_first
                    else -> R.string.compare_result_second
                }

                val f1: Float = b / a
                val f2: Float = d / c
                val details: String = getString(
                    R.string.compare_details_format,
                    String.format(Locale.US, "%f", f1),
                    String.format(Locale.US, "%f", f2)
                )

                outResult.text = getString(verdictRes) + "\n\n" + details
            }
            else {
                Toast.makeText(this, R.string.toast_fill_all_fields, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnClearCompare.setOnClickListener(clearListener)
    }

    // Общий слушатель: очистка всех полей и результата
    private val clearListener = View.OnClickListener {
        binding.numA.text.clear()
        binding.numB.text.clear()
        binding.numC.text.clear()
        binding.numD.text.clear()
        binding.compareResult.text = ""
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
