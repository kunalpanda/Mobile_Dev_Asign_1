package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

        val loanButton = findViewById<Button>(R.id.loanButton)
        val incomeButton = findViewById<Button>(R.id.incomeButton)
        val expenseButton = findViewById<Button>(R.id.expenseButton)

        // Set click listener to navigate to EMI page
        loanButton.setOnClickListener {
            val intent = Intent(this, EmiActivity::class.java)
            startActivity(intent)
        }

        // Set click listener to navigate to EMI page
        incomeButton.setOnClickListener {
            intent = Intent(this, IncomeActivity::class.java)
            startActivity(intent)
        }
        
        // Set click listener to navigate to EMI page
        expenseButton.setOnClickListener {
            intent = Intent(this, ExpenseActivity::class.java)
            startActivity(intent)
        }
    }
}