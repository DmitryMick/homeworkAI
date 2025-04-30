/**
 * Fake Store API Test Suite
 * This script performs automated tests on the Fake Store API products endpoint
 * to validate data integrity and detect anomalies.
 */

// DOM Elements
const runTestsBtn = document.getElementById('runTests');
const testStatus = document.getElementById('testStatus');
const loadingSpinner = document.getElementById('loadingSpinner');
const responseStatus = document.getElementById('responseStatus');
const responseTime = document.getElementById('responseTime');
const totalProducts = document.getElementById('totalProducts');
const productsWithIssues = document.getElementById('productsWithIssues');
const defectiveProducts = document.getElementById('defectiveProducts');

// API URL
const API_URL = 'https://fakestoreapi.com/products';

/**
 * Validate a product's data
 * @param {Object} product - The product object to validate
 * @returns {Object} Object containing validation results and issues
 */
function validateProduct(product) {
    const issues = [];

    // Check title
    if (!product.title || product.title.trim() === '') {
        issues.push('Title is empty or missing');
    }

    // Check price
    if (typeof product.price !== 'number' || product.price < 0) {
        issues.push('Price is invalid or negative');
    }

    // Check rating
    if (!product.rating || typeof product.rating.rate !== 'number' || product.rating.rate > 5) {
        issues.push('Rating is invalid or exceeds 5');
    }

    return {
        isValid: issues.length === 0,
        issues
    };
}

/**
 * Update the UI with test results
 * @param {Object} results - The test results object
 */
function updateUI(results) {
    // Update server response
    responseStatus.textContent = results.status;
    responseStatus.style.color = results.status === 200 ? '#27ae60' : '#e74c3c';
    responseTime.textContent = `${results.responseTime}ms`;

    // Update validation summary
    totalProducts.textContent = results.totalProducts;
    productsWithIssues.textContent = results.defectiveProducts.length;

    // Update defective products list
    if (results.defectiveProducts.length === 0) {
        defectiveProducts.innerHTML = '<p class="no-data">No defective products found</p>';
    } else {
        defectiveProducts.innerHTML = results.defectiveProducts.map(product => `
            <div class="defective-item">
                <h3>${product.title || 'Untitled Product'}</h3>
                <ul class="issues">
                    ${product.issues.map(issue => `<li>${issue}</li>`).join('')}
                </ul>
            </div>
        `).join('');
    }
}

/**
 * Run the API tests
 */
async function runTests() {
    // Update UI for test start
    runTestsBtn.disabled = true;
    testStatus.textContent = 'Running tests...';
    loadingSpinner.classList.remove('hidden');
    defectiveProducts.innerHTML = '<p class="no-data">Testing in progress...</p>';

    const startTime = performance.now();

    try {
        // Fetch products from API
        const response = await fetch(API_URL);
        const products = await response.json();
        const endTime = performance.now();

        // Validate each product
        const validationResults = products.map(product => ({
            ...product,
            ...validateProduct(product)
        }));

        // Filter defective products
        const defectiveProducts = validationResults.filter(product => !product.isValid);

        // Update UI with results
        updateUI({
            status: response.status,
            responseTime: Math.round(endTime - startTime),
            totalProducts: products.length,
            defectiveProducts
        });

        testStatus.textContent = 'Tests completed';
    } catch (error) {
        console.error('Test failed:', error);
        testStatus.textContent = 'Test failed: ' + error.message;
        responseStatus.textContent = 'Error';
        responseStatus.style.color = '#e74c3c';
    } finally {
        // Reset UI state
        runTestsBtn.disabled = false;
        loadingSpinner.classList.add('hidden');
    }
}

// Event Listeners
runTestsBtn.addEventListener('click', runTests); 