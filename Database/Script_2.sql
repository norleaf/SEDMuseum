USE sedmuseum; SET foreign_key_checks = 0;
DROP TABLE IF EXISTS invoicelines;
CREATE TABLE invoicelines
(invoice_nr INT,
product_nr INT(3),
amount INT, 
PRIMARY KEY(invoice_nr, product_nr), 
FOREIGN KEY(invoice_nr) REFERENCES invoice(nr) ON DELETE CASCADE ON UPDATE CASCADE, 
FOREIGN KEY(product_nr) REFERENCES product(nr) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;
ALTER TABLE product ADD FOREIGN KEY(p_group) REFERENCES p_group(nr) ON
DELETE CASCADE ON UPDATE CASCADE, ENGINE = innodb; 
ALTER TABLE invoice 
ADD FOREIGN KEY(employee) REFERENCES employee(nr) ON DELETE CASCADE ON UPDATE CASCADE, 
ADD FOREIGN KEY(i_date) REFERENCES till(t_date) ON DELETE CASCADE ON UPDATE CASCADE,
ENGINE = innodb;
DROP TABLE IF EXISTS telephone;
CREATE TABLE telephone
(employee INT(3) NOT NULL,
tlf CHAR(8) NOT NULL, 
PRIMARY KEY(employee, tlf), 
FOREIGN KEY(employee) REFERENCES employee(nr) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = innodb; 
ALTER TABLE employee
DROP COLUMN tlf_home,
DROP COLUMN tlf_cell,
DROP COLUMN tlf_work;