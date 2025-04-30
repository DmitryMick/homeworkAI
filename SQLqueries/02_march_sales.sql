-- Task 1: Calculate total sales volume for March 2024
SELECT 
    SUM(amount) as total_sales_march
FROM orders
WHERE strftime('%Y-%m', order_date) = '2024-03';

-- Expected Result: 27,000 