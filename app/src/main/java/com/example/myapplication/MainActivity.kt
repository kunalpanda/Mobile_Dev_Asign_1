package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // All variables to handle different kinds of user input and text output

        val loanButton = findViewById<Button>(R.id.loanButton)
        val incomeButton = findViewById<Button>(R.id.incomeButton)
        val expenseButton = findViewById<Button>(R.id.expenseButton)
        val dashboardContent = findViewById<TextView>(R.id.dashboardContent)

        // Update content upon opening the activity
        updateDashboard(dashboardContent)

        // Set click listener to navigate to EMI page
        loanButton.setOnClickListener {
            val intent = Intent(this, EmiActivity::class.java)
            startActivity(intent)
        }

        // Set click listener to navigate to Income page
        incomeButton.setOnClickListener {
            intent = Intent(this, IncomeActivity::class.java)
            startActivity(intent)
        }
        
        // Set click listener to navigate to Expense page
        expenseButton.setOnClickListener {
            intent = Intent(this, ExpenseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateDashboard(textView: TextView) {
        // Calculate total monthly income
        val totalMonthlyIncome = IncomeManager.getTotalIncome() //get income from the income class

        // Calculate total monthly expenditure, retrieve info from the expenses class
        val oneTimeExpensesMonthly = ExpenseManager.getTotalOneTime() / 12.0
        val recurringExpenses = ExpenseManager.getTotalRecurring()
        val loanPayments = LoanManager.getTotalMonthlyPayment()
        val totalMonthlyExpenditure = oneTimeExpensesMonthly + recurringExpenses + loanPayments

        // Calculate net monthly (income - expenditure)
        val netMonthly = totalMonthlyIncome - totalMonthlyExpenditure

        // Build display text
        val textBuilder = StringBuilder()

        textBuilder.append("MONTHLY SUMMARY\n")
        textBuilder.append("═════════════════\n\n")

        textBuilder.append("Total Monthly Income:\n")
        textBuilder.append("$${String.format("%.2f", totalMonthlyIncome)}\n\n")

        textBuilder.append("Total Monthly Expenditure:\n")
        textBuilder.append("$${String.format("%.2f", totalMonthlyExpenditure)}\n\n")

        textBuilder.append("BREAKDOWN\n")
        textBuilder.append("─────────────────\n")
        textBuilder.append("One-time Expenses (÷12): $${String.format("%.2f", oneTimeExpensesMonthly)}\n")
        textBuilder.append("Recurring Expenses: $${String.format("%.2f", recurringExpenses)}\n")
        textBuilder.append("Loan Payments: $${String.format("%.2f", loanPayments)}\n\n")

        textBuilder.append("═════════════════\n")
        textBuilder.append("Net Monthly: ")

        if (netMonthly >= 0) {
            textBuilder.append("$${String.format("%.2f", netMonthly)}\n")
            textBuilder.append("(Surplus)")
        } else {
            textBuilder.append("-$${String.format("%.2f", Math.abs(netMonthly))}\n")
            textBuilder.append("(Deficit)")
        }

        //Output text info dashboard in paragraph format
        textView.text = textBuilder.toString()
    }

}