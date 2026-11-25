package com.svishs.proportion_crosswise

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat




import com.svishs.proportion_crosswise.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {
    //    private fun
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // панель с заголовком и меню приложения
        setSupportActionBar(binding.toolbar)
//        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val inpNum2: EditText = binding.num2
        val inpNum3: EditText = binding.num3
        val inpNum4: EditText = binding.num4
        val inpNum1: EditText = binding.num1
//        val clBut1:Button = findViewById(R.id.clBut1)
        // var num1: Float = inpNum1.text.toString().toFloat()

//        val calc:Button = binding.calc


        binding.calc.setOnClickListener {
            if (inpNum1.text.toString().isNotBlank()&& inpNum2.text.toString().isNotBlank()){
                val n1:Float = inpNum1.text.toString().toFloatOrNull()?: 0f
                val n2:Float = inpNum2.text.toString().toFloatOrNull()?: 0f

                // если первые 2 числа почти ноль, то выходим
                if (n1==0f || n2 ==0f){
                    Toast.makeText(this, R.string.toast_zero_field, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (inpNum3.text.toString().isNotBlank()|| inpNum4.text.toString().isNotBlank()){
                    // если непустое 3ее поле, значит изменяем четвёртое
                    if(inpNum3.text.toString().isNotBlank()) {
                        // установка для 4го поля
                        val n3:Float = inpNum3.text.toString().toFloatOrNull()?: 0f
                        val res:Float  = (n3*n2)/n1
                        inpNum4.setText(String.format(Locale.US, "%f", res))
                    }
                    // если непустое 4ое поле, то меняем третье
                    else {
                        // установка для 3го поля

                        val n4:Float = inpNum4.text.toString().trim().toFloatOrNull()?: 0f
                        inpNum3.setText(String.format(Locale.US, "%f", (n1*n4) / n2))
                    }
                }
                else                {
                    Toast.makeText(this, R.string.toast_missing_value, Toast.LENGTH_SHORT).show()

                }
            }
            else {
                Toast.makeText(this, R.string.toast_empty_base_fields, Toast.LENGTH_SHORT).show()
            }
        }

        binding.clBut1.setOnClickListener(clearListener)
        binding.clBut3.setOnClickListener(clearListener)
        binding.clBut4.setOnClickListener(clearListener)
        binding.clAllBut.setOnClickListener(clearListener)
    }

    // Общий слушатель: определяем кнопку по её id и очищаем нужные поля
    private val clearListener = View.OnClickListener { view ->
        clearFields(view.id)
    }
    private fun clearFields(buttonId: Int) {
        val fields = when(buttonId) {

            R.id.clBut1 -> listOf(binding.num1, binding.num2)
            R.id.clBut3 -> listOf(binding.num3)
            R.id.clBut4 -> listOf(binding.num4)
            R.id.clAllBut -> listOf(binding.num1, binding.num2, binding.num3, binding.num4)
            else -> emptyList()
        }
        fields.forEach { it.text.clear() }
    }

    // Меню приложения: переход к разделам «Сравнение», «Помощь», «О программе»
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_compare -> {
                startActivity(Intent(this, ComparisonActivity::class.java))
                true
            }
            R.id.action_help -> {
                startActivity(Intent(this, HelpActivity::class.java))
                true
            }
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
