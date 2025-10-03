# 📱 Mobile Finance Manager (Assignment 1 - SOFE 4640U)

A simple Android app that helps users calculate loan EMIs, manage income and expenses, and track monthly savings or deficit.

---

## 🧭 Overview

This project was developed as part of **SOFE 4640U – Mobile Application Development (Fall 2025)**.  
The app integrates **layouts**, **views**, and **intents** to provide a functional personal finance management tool.

**Key Objectives**
- Practice Android development using Kotlin and XML.
- Apply object-oriented design through custom classes and data encapsulation.
- Implement modular UI navigation using explicit intents.

---

## ⚙️ Features

### 💰 EMI Calculator
- Inputs: Loan name, amount, annual interest rate, and tenure (in years).
- Calculates monthly EMI using the formula  
  `EMI = [P * R * (1 + R)^N] / [(1 + R)^N - 1]`
- Handles zero-interest loans gracefully.
- Displays total principal, total interest, and monthly breakdown.

### 💵 Income Manager
- Add or clear monthly income entries.
- Automatically updates and displays total income.
- Provides real-time feedback via Toasts.

### 🧾 Expense Manager
- Add both **one-time** and **recurring** expenses.
- Calculates total one-time and monthly expenses with a clear summary.
- “GRAND TOTAL” section shows combined spending.
- Includes Clear functionality and user input validation.

### 📊 Monthly Balance Summary
- Combines EMI, expenses, and income data.
- Displays **Monthly Surplus** or **Deficit** in a clean formatted view.

---

## 🧩 App Structure

| Component | Description |
|------------|--------------|
| `MainActivity` | Navigation hub linking to Income, Expense, and EMI sections via explicit intents. |
| `IncomeActivity` | Handles user input for income and displays cumulative total. |
| `ExpenseActivity` | Manages expense entries, separates one-time and recurring costs, and computes totals. |
| `EmiActivity` | Performs EMI calculations and displays full loan and savings overview. |
| `IncomeManager`, `ExpenseManager`, `LoanManager` | Singleton classes managing data and calculations for each category. |
| `Income`, `Expense`, `Loan` | Data classes defining structured data for each entity. |

---

## 🧠 Technical Highlights

- **Kotlin + XML UI Design**
- **ConstraintLayout & LinearLayout** used for clean UI alignment.
- **Explicit Intents** for navigation between activities.
- **Validation Handling:** prevents crashes using `toDoubleOrNull()` checks.
- **Encapsulation:** data models isolate logic from UI for clarity.
- **Dynamic Updates:** all totals refresh automatically upon user interaction.

---

### 👨‍💻 Author
**Kunal Pandya**  
Software Engineering Student @ Ontario Tech University  
📧 [GitHub Profile](https://github.com/kunalpanda)
