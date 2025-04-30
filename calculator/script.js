/**
 * Expense Tracker Application
 * This application allows users to track their expenses, calculate totals,
 * and analyze their spending patterns.
 */

// Store expenses in an array
let expenses = [];

// DOM Elements
const expenseForm = document.getElementById('expenseForm');
const expensesTableBody = document.getElementById('expensesTableBody');
const totalExpensesElement = document.getElementById('totalExpenses');
const averageDailyElement = document.getElementById('averageDaily');
const topExpensesElement = document.getElementById('topExpenses');
const expenseAnalysis = document.querySelector('.expense-analysis');

/**
 * Add a new expense to the list
 * @param {string} category - The expense category
 * @param {number} amount - The expense amount
 */
function addExpense(category, amount) {
    expenses.push({ category, amount: parseFloat(amount) });
    updateExpensesTable();
    updateAnalysisResults();
}

/**
 * Remove an expense from the list
 * @param {number} index - The index of the expense to remove
 */
function removeExpense(index) {
    expenses.splice(index, 1);
    updateExpensesTable();
    updateAnalysisResults();
}

/**
 * Update the expenses table in the UI
 */
function updateExpensesTable() {
    expensesTableBody.innerHTML = '';
    expenses.forEach((expense, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${expense.category}</td>
            <td>$${expense.amount.toLocaleString()}</td>
            <td>
                <button class="delete-btn" onclick="removeExpense(${index})">Delete</button>
            </td>
        `;
        expensesTableBody.appendChild(row);
    });
}

/**
 * Calculate total expenses
 * @returns {number} The total amount of all expenses
 */
function calculateTotalExpenses() {
    return expenses.reduce((total, expense) => total + expense.amount, 0);
}

/**
 * Calculate average daily expense
 * @returns {number} The average daily expense
 */
function calculateAverageDaily() {
    const total = calculateTotalExpenses();
    return total / 30; // Assuming 30 days in a month
}

/**
 * Get top 3 largest expenses
 * @returns {Array} Array of top 3 expenses
 */
function getTopExpenses() {
    return [...expenses]
        .sort((a, b) => b.amount - a.amount)
        .slice(0, 3);
}

/**
 * Update the analysis results in the UI
 */
function updateAnalysisResults() {
    if (expenses.length === 0) {
        expenseAnalysis.classList.add('hidden');
        return;
    }

    const total = calculateTotalExpenses();
    const average = calculateAverageDaily();
    const topExpenses = getTopExpenses();

    totalExpensesElement.textContent = `$${total.toLocaleString()}`;
    averageDailyElement.textContent = `$${average.toLocaleString()}`;

    topExpensesElement.innerHTML = '';
    topExpenses.forEach(expense => {
        const li = document.createElement('li');
        li.textContent = `${expense.category}: $${expense.amount.toLocaleString()}`;
        topExpensesElement.appendChild(li);
    });

    expenseAnalysis.classList.remove('hidden');
}

// Event Listeners
expenseForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const category = document.getElementById('category').value;
    const amount = document.getElementById('amount').value;
    
    if (category && amount) {
        addExpense(category, amount);
        expenseForm.reset();
    }
});

// Initialize the application
updateExpensesTable();
updateAnalysisResults(); 