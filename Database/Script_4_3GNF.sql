USE sedmuseum;
DROP TABLE IF EXISTS city;
CREATE TABLE city AS
(SELECT DISTINCT zipcode, city FROM employee); 
ALTER TABLE city ADD PRIMARY KEY(zipcode); ALTER TABLE employee
DROP COLUMN city, ADD FOREIGN KEY(zipcode) REFERENCES city(zipcode);