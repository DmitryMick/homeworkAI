-- Create geo table
CREATE TABLE geo (
    id BIGSERIAL PRIMARY KEY,
    lat VARCHAR(255) NOT NULL,
    lng VARCHAR(255) NOT NULL
);

-- Create company table
CREATE TABLE company (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    catch_phrase VARCHAR(500) NOT NULL,
    bs VARCHAR(500) NOT NULL
);

-- Create address table
CREATE TABLE address (
    id BIGSERIAL PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    suite VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    zipcode VARCHAR(255) NOT NULL,
    geo_id BIGINT REFERENCES geo(id)
);

-- Create users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    address_id BIGINT REFERENCES address(id),
    phone VARCHAR(255) NOT NULL,
    website VARCHAR(255),
    company_id BIGINT REFERENCES company(id)
);

-- Create indexes for better performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_address_city ON address(city); 