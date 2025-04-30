# Store API Test

A web-based automated test suite for validating data from the Fake Store API. This application helps identify data anomalies and defects in the API response.

## Features

- Automated testing of the Fake Store API products endpoint
- Real-time validation of product data
- Detection of data anomalies and defects
- Visual presentation of test results
- Response time measurement
- Detailed error reporting

## Test Criteria

The test suite validates the following aspects of each product:

1. **Server Response**
   - Verifies HTTP status code (expected 200)

2. **Product Data Validation**
   - Title: Must not be empty
   - Price: Must not be negative
   - Rating: Must not exceed 5

## Technical Details

### File Structure
- `index.html` - Main HTML file containing the test interface
- `styles.css` - CSS styles for the application
- `script.js` - JavaScript code containing test logic

### Key Components

#### HTML Structure
- Test controls with run button
- Server response information
- Validation summary
- Defective products list

#### CSS Features
- Responsive design
- Modern color scheme
- Loading animations
- Clean and intuitive interface

#### JavaScript Functionality
- API request handling
- Data validation
- Real-time UI updates
- Error handling

## Usage

1. Open `index.html` in a web browser
2. Click the "Run Tests" button to start the validation
3. View the test results in the interface:
   - Server response status and time
   - Total number of products
   - Number of products with issues
   - List of defective products with specific issues

## API Endpoint

The test suite uses the Fake Store API products endpoint:
```
https://fakestoreapi.com/products
```

## Error Handling

The application handles various error scenarios:
- Network errors
- Invalid API responses
- Data validation failures
- Missing or malformed data 