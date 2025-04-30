# Expense Tracker Application

A web-based application for tracking and analyzing monthly expenses. This application helps users manage their expenses by providing features for adding expenses, calculating totals, and analyzing spending patterns.

## Features

- Add new expenses with predefined categories
- View all expenses in a table format
- Delete individual expenses
- Calculate total monthly expenses
- Calculate average daily expenses
- Display top 3 largest expenses
- Modern and responsive user interface

## Technical Details

### File Structure
- `index.html` - Main HTML file containing the application structure
- `styles.css` - CSS styles for the application
- `script.js` - JavaScript code containing all business logic

### Key Components

#### HTML Structure
- Expense input form with category dropdown and amount input
- Expenses table showing all added expenses
- Analysis section displaying calculated results

#### CSS Features
- Responsive design
- Modern color scheme
- Clean and intuitive user interface
- Interactive elements with hover effects

#### JavaScript Functionality
- Expense management (add/remove)
- Calculations for total and average expenses
- Top 3 expenses analysis
- Real-time UI updates

### Business Logic

1. **Adding Expenses**
   - Users can select from predefined categories
   - Amount must be a positive number
   - Expenses are stored in an array

2. **Calculations**
   - Total Expenses: Sum of all expense amounts
   - Average Daily: Total divided by 30 (monthly average)
   - Top 3 Expenses: Sorted by amount in descending order

3. **Data Management**
   - Expenses are stored in memory
   - Each expense has a category and amount
   - Expenses can be removed individually

## Usage

1. Select a category from the dropdown menu
2. Enter the expense amount
3. Click "Add Expense" to add to the list
4. Use the "Calculate" button to see the analysis
5. Remove expenses using the delete button

## Categories

- Groceries
- Rent
- Transportation
- Entertainment
- Communication
- Gym
