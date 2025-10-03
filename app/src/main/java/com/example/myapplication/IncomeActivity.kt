package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import java.time.format.TextStyle

data class Income(val amount: Double)

object IncomeManager {
    var totalMonthlyIncome = 0.00;

    fun getTotalIncome(): Double {
        return totalMonthlyIncome
    }
    fun addIncome(amount: Double){
        totalMonthlyIncome+=amount
    }

    fun resetMonthlyIncome(){
        totalMonthlyIncome = 0.0
    }
}

class IncomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_income)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton = findViewById<Button>(R.id.backButton)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val clearButton = findViewById<Button>(R.id.clearButton)
        val incomeInput = findViewById<EditText>(R.id.incomeInput)
        val incomeText = findViewById<TextView>(R.id.incomeText)

        updateIncomeDisplay(incomeText)

        // Set click listener to navigate to EMI page
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        clearButton.setOnClickListener {
            IncomeManager.resetMonthlyIncome()
            incomeInput.text.clear()
            updateIncomeDisplay(incomeText)
            Toast.makeText(this, "Income cleared", Toast.LENGTH_SHORT).show()
        }

        // Set click listener to show toast
        submitButton.setOnClickListener {
            val incomeAmount = incomeInput.text.toString().toDoubleOrNull()
            if (incomeAmount == null || incomeAmount <= 0){
                Toast.makeText(this, "Please input valid income value", Toast.LENGTH_SHORT).show()
            }
            else{
                IncomeManager.addIncome(incomeAmount)
                updateIncomeDisplay(incomeText)
                Toast.makeText(this, "Income added", Toast.LENGTH_SHORT).show()
                incomeInput.text.clear()
            }
        }
    }

    private fun updateIncomeDisplay(textView: TextView) {
        val totalIncome = IncomeManager.getTotalIncome()
        textView.text = "Monthly Income: $${String.format("%.2f", IncomeManager.getTotalIncome())}"
    }
}