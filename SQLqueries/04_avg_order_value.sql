-- Task 3: Calculate average order value for the last three months
SELECT 
    ROUND(AVG(amount), 2) as average_order_value
FROM orders
WHERE order_date >= date('2024-01-01')
  AND order_date <= date('2024-03-31');

-- Expected Result: 8,750 