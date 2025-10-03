package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

data class Loan( //Make a custom data type and class to hold all loan info
    val name: String,
    val principal: Double,
    val interestRate: Double,
    val tenureYears: Double
) {
    // Calculate monthly payment (EMI)
    fun getMonthlyPayment(): Double {
        val monthlyRate = interestRate / 100 / 12
        val numberOfPayments = tenureYears * 12

        return if (monthlyRate == 0.0) {
            principal / numberOfPayments
        } else {
            val emi = principal * monthlyRate * Math.pow(1 + monthlyRate, numberOfPayments) /
                    (Math.pow(1 + monthlyRate, numberOfPayments) - 1)
            emi
        }
    }

    // Calculate total interest paid
    fun getTotalInterest(): Double {
        return (getMonthlyPayment() * tenureYears * 12) - principal
    }

    // Calculate monthly principal payment (average)
    fun getMonthlyPrincipal(): Double {
        return principal / (tenureYears * 12)
    }

    // Calculate monthly interest payment (average)
    fun getMonthlyInterest(): Double {
        return getTotalInterest() / (tenureYears * 12)
    }
}

object LoanManager { //Create object to manage list of loans
    val loans = mutableListOf<Loan>()

    //Number of getters to isolate functionality and improve organization and readability of code
    fun getTotalMonthlyPayment(): Double {
        return loans.sumOf { it.getMonthlyPayment() }
    }

    fun getTotalPrincipal(): Double {
        return loans.sumOf { it.principal }
    }

    fun getTotalInterest(): Double {
        return loans.sumOf { it.getTotalInterest() }
    }

    fun getTotalMonthlyPrincipal(): Double {
        return loans.sumOf { it.getMonthlyPrincipal() }
    }

    fun getTotalMonthlyInterest(): Double {
        return loans.sumOf { it.getMonthlyInterest() }
    }

    // Clear function for the list if the user makes a mistake
    fun clearLoans() {
        loans.clear()
    }
}

class EmiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_emi)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Variables to handle of of the user inputs and text outputs
        val nameInput = findViewById<EditText>(R.id.loanNameInput)
        val amountInput = findViewById<EditText>(R.id.loanAmountInput)
        val interestInput = findViewById<EditText>(R.id.interestRateInput)
        val tenureInput = findViewById<EditText>(R.id.tenureInput)
        val loanListText = findViewById<TextView>(R.id.expenseListText)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val clearButton = findViewById<Button>(R.id.clearButton)
        val backButton = findViewById<Button>(R.id.backButton)

        // Set click listener to navigate back to the home page
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Set click listener to clear list
        clearButton.setOnClickListener {
            LoanManager.clearLoans()
            Toast.makeText(this, "All loans cleared", Toast.LENGTH_SHORT).show()
            updateLoanDisplay(loanListText)
        }

        // update display when activity opens (in case user returns to the page)
        updateLoanDisplay(loanListText)

        // Submit button - add loan to list, includes a number of user input verifications
        submitButton.setOnClickListener {
            val name = nameInput.text.toString()
            val amount = amountInput.text.toString().toDoubleOrNull()
            val interest = interestInput.text.toString().toDoubleOrNull()
            val tenure = tenureInput.text.toString().toDoubleOrNull()

            // Validate inputs
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter loan name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (amount == null || amount <= 0) {
                Toast.makeText(this, "Please enter valid loan amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (interest == null || interest < 0) {
                Toast.makeText(this, "Please enter valid interest rate", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (tenure == null || tenure <= 0) {
                Toast.makeText(this, "Please enter valid tenure", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Add loan
            LoanManager.loans.add(Loan(name, amount, interest, tenure))
            Toast.makeText(this, "Loan added", Toast.LENGTH_SHORT).show()

            // Clear inputs
            nameInput.text.clear()
            amountInput.text.clear()
            interestInput.text.clear()
            tenureInput.text.clear()

            // Update display
            updateLoanDisplay(loanListText)
        }
    }

    private fun updateLoanDisplay(textView: TextView) { //Format paragraph to provide user with useful loan info
        if (LoanManager.loans.isEmpty()) {
            textView.text = "No loans added yet"
        } else {
            val textBuilder = StringBuilder()

            LoanManager.loans.forEach { loan ->
                textBuilder.append("${loan.name}\n")
                textBuilder.append("─────────────────\n")
                textBuilder.append("Principal: $${String.format("%.2f", loan.principal)}\n")
                textBuilder.append("Total Interest: $${String.format("%.2f", loan.getTotalInterest())}\n")
                textBuilder.append("Monthly Payment: $${String.format("%.2f", loan.getMonthlyPayment())}\n")
                textBuilder.append("Tenure: ${String.format("%.1f", loan.tenureYears)} years\n")
                textBuilder.append("\n")
            }

            textView.text = textBuilder.toString()
        }
    }

}