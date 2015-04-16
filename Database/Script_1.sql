DROP DATABASE IF EXISTS sedmuseum;
CREATE DATABASE sedmuseum; USE sedmuseum;
DROP TABLE IF EXISTS product;
CREATE TABLE product
(nr INT(4) PRIMARY KEY,
p_name VARCHAR(100),
p_group INT(3),
supplyer VARCHAR(30),
purchase_price INT,
price_kr DOUBLE, 
price_euro DOUBLE,
discount_kr DOUBLE, 
discount_eur DOUBLE, 
amount INT
);
DROP TABLE IF EXISTS p_group;
CREATE TABLE p_group
(nr INT(3) AUTO_INCREMENT PRIMARY KEY,
category VARCHAR(30)
);
DROP TABLE IF EXISTS employee;
CREATE TABLE employee
(nr INT(3) AUTO_INCREMENT PRIMARY KEY,
fname VARCHAR(20),
lname VARCHAR(20),
tlf_home CHAR(8),
tlf_cell CHAR(8),
tlf_work CHAR(8),
address VARCHAR(50),
zipcode CHAR(4), 
city VARCHAR(20),
username CHAR(4),
passwrd CHAR(4)
);
DROP TABLE IF EXISTS invoice;
CREATE TABLE invoice
(nr INT AUTO_INCREMENT PRIMARY KEY,
i_date DATE,
employee INT(3),
valuta CHAR(3),
discount BOOL
);
DROP TABLE IF EXISTS till;
CREATE TABLE till
(t_date DATE PRIMARY KEY, 
start_balance_kr INT,
start_balance_euro INT,
closed BOOL
);