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

data class Expense( //Create custom data type and class for holding expense info
    val name: String,
    val amount: Double,
    val monthly: Boolean)

object ExpenseManager { //Create manager object to handle list of expense and their various types
    val expenses = mutableListOf<Expense>()

    // Number of getters to isolate functionality and improve readability of code
    fun getTotalExpense(): Double {
        return expenses.sumOf { it.amount }
    }

    fun getOneTimeExpenses(): List<Expense> {
        return expenses.filter { !it.monthly }
    }

    fun getRecurringExpenses(): List<Expense> {
        return expenses.filter { it.monthly }
    }

    fun getTotalOneTime(): Double {
        return getOneTimeExpenses().sumOf { it.amount }
    }

    fun getTotalRecurring(): Double {
        return getRecurringExpenses().sumOf { it.amount }
    }

    // Clear function in case user makes a mistake or wants to reset
    fun clearExpenses(){
        expenses.clear()
    }
}

class ExpenseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_expense)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Variables to handle user inputs and text outputs
        val nameInput = findViewById<EditText>(R.id.expenseNameInput)
        val oneTimeInput = findViewById<EditText>(R.id.oneTimeExpenseInput)
        val recurringInput = findViewById<EditText>(R.id.reoccuringExpenseInput)
        val expenseListText = findViewById<TextView>(R.id.expenseListText)
        val clearButton = findViewById<Button>(R.id.clearButton)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val backButton = findViewById<Button>(R.id.backButton)

        // Update display when activity is opened in case user returns
        updateExpenseDisplay(expenseListText)

        // Set click listener to navigate back to the home page
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Set click listener to clear expenses
        clearButton.setOnClickListener {
            ExpenseManager.clearExpenses()
            updateExpenseDisplay(expenseListText)
            nameInput.text.clear()
            oneTimeInput.text.clear()
            recurringInput.text.clear()
            Toast.makeText(this, "Expenses cleared", Toast.LENGTH_SHORT).show()
        }

        // Submit button - add expense, includes a number of user input verification
        submitButton.setOnClickListener {
            val name = nameInput.text.toString()
            val oneTimeAmount = oneTimeInput.text.toString().toDoubleOrNull()
            val recurringAmount = recurringInput.text.toString().toDoubleOrNull()

            // Various input validation functions
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter expense name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if ((oneTimeAmount == null || oneTimeAmount <= 0) &&
                (recurringAmount == null || recurringAmount <= 0)) {
                Toast.makeText(this, "Please enter at least one valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Add one-time expense if entered
            if (oneTimeAmount != null && oneTimeAmount > 0) {
                ExpenseManager.expenses.add(Expense(name, oneTimeAmount, false))
            }

            // Add recurring expense if entered
            if (recurringAmount != null && recurringAmount > 0) {
                ExpenseManager.expenses.add(Expense("$name (Monthly)", recurringAmount, true))
            }

            //Let the user know their extry has been add to the system
            Toast.makeText(this, "Expense(s) added", Toast.LENGTH_SHORT).show()

            // Clear inputs
            nameInput.text.clear()
            oneTimeInput.text.clear()
            recurringInput.text.clear()

            // Update the display
            updateExpenseDisplay(expenseListText)
        }
    }


    private fun updateExpenseDisplay(textView: TextView) { // Format paragraph to provide user with useful and categorized expense info
        if (ExpenseManager.expenses.isEmpty()) {
            textView.text = "No expenses added yet"
        } else {
            val oneTimeExpenses = ExpenseManager.getOneTimeExpenses()
            val recurringExpenses = ExpenseManager.getRecurringExpenses()

            val textBuilder = StringBuilder()

            // One-time expenses
            if (oneTimeExpenses.isNotEmpty()) {
                textBuilder.append("ONE-TIME EXPENSES\n")
                textBuilder.append("─────────────────\n")
                oneTimeExpenses.forEach { expense ->
                    textBuilder.append("${expense.name}: $${String.format("%.2f", expense.amount)}\n")
                }
                textBuilder.append("\nTotal: $${String.format("%.2f", ExpenseManager.getTotalOneTime())}\n\n\n")
            }

            // Recurring expenses
            if (recurringExpenses.isNotEmpty()) {
                textBuilder.append("RECURRING EXPENSES\n")
                textBuilder.append("─────────────────\n")
                recurringExpenses.forEach { expense ->
                    textBuilder.append("${expense.name}: $${String.format("%.2f", expense.amount)}\n")
                }
                textBuilder.append("\nTotal: $${String.format("%.2f", ExpenseManager.getTotalRecurring())}\n\n\n")
            }

            textBuilder.append("═════════════════\n")
            textBuilder.append("GRAND TOTAL: $${String.format("%.2f", ExpenseManager.getTotalExpense())}")

            textView.text = textBuilder.toString()
        }
    }
}