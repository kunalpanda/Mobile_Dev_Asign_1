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

data class Income(val amount: Double) // Create income class to hold income account (not needed but good for consistency and future proofing the app)

object IncomeManager { // Income manage to handle various income tasks, mainly adding and resting sources of income
    var totalMonthlyIncome = 0.00;

    // Getter to isolate functionality
    fun getTotalIncome(): Double {
        return totalMonthlyIncome
    }

    //Incrementor to isolate functionality
    fun addIncome(amount: Double){
        totalMonthlyIncome+=amount
    }

    // Clear function incase the user makes a mistake or wants to reset the app
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

        // Variables to handle various user inputs and output texts
        val backButton = findViewById<Button>(R.id.backButton)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val clearButton = findViewById<Button>(R.id.clearButton)
        val incomeInput = findViewById<EditText>(R.id.incomeInput)
        val incomeText = findViewById<TextView>(R.id.incomeText)

        // Update display upon opening the activity in case the user return to the page
        updateIncomeDisplay(incomeText)

        // Set click listener to navigate back to the home page
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Set click listener to reset income
        clearButton.setOnClickListener {
            IncomeManager.resetMonthlyIncome()
            incomeInput.text.clear()
            updateIncomeDisplay(incomeText)
            Toast.makeText(this, "Income cleared", Toast.LENGTH_SHORT).show()
        }

        // Submit button - add income, includes user input verification
        submitButton.setOnClickListener {
            val incomeAmount = incomeInput.text.toString().toDoubleOrNull()

            // Ensure income is valid, non negative integer
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

    private fun updateIncomeDisplay(textView: TextView) { // Format text to display income info to the user (not needed given the simplicity of the info but good for consistency)
        val totalIncome = IncomeManager.getTotalIncome()
        textView.text = "Monthly Income: $${String.format("%.2f", IncomeManager.getTotalIncome())}"
    }
}