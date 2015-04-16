SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;

use sedmuseum; 
TRUNCATE `invoice`;
INSERT INTO `invoice` (`nr`, `i_date`, `employee`, `valuta`, `discount`) VALUES
	(1, '2014-05-22', 1, 'DKK', 0),
	(2, '2014-05-22', 1, 'EUR', 0),
	(3, '2014-05-22', 1, 'DKK', 1),
	(4, '2014-05-22', 1, 'EUR', 1);


TRUNCATE `invoicelines`;
INSERT INTO `invoicelines` (`invoice_nr`, `product_nr`, `amount`) VALUES
	(1, 1, 5),
	(1, 2112, 5),
	(1, 2180, 1),
	(1, 2181, 3),
	(2, 1, 5),
	(2, 2112, 5),
	(2, 2180, 1),
	(2, 2181, 3),
	(3, 1, 5),
	(3, 2112, 5),
	(3, 2180, 1),
	(3, 2181, 3),
	(4, 1, 5),
	(4, 2112, 5),
	(4, 2180, 1),
	(4, 2181, 3);


TRUNCATE `till`;
INSERT INTO `till` (`t_date`, `start_balance_kr`, `start_balance_euro`, `closed`) VALUES
	('2014-05-22', 1000, 100, 0);
SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS);

-- Angiv scripts der viser salget for en bestemt dato i kr.
SELECT 
invoice.nr, invoice.i_date, invoice.valuta, invoice.discount, invoice.employee,
ROUND(SUM(invoicelines.amount * 
IF(invoice.discount,
				IF(invoice.valuta like 'DKK', product.discount_kr, product.discount_eur),
				IF(invoice.valuta like 'DKK', product.price_kr, product.price_euro)
				)),2) AS Total
FROM invoice
LEFT JOIN invoicelines ON invoicelines.invoice_nr = invoice.nr
LEFT JOIN product ON invoicelines.product_nr = product.nr
WHERE invoice.valuta = 'DKK' AND invoice.i_date = '2014-05-22'
GROUP BY invoice.nr;

-- Angiv scripts der viser salget for en bestemt dato i euro.
SELECT 
invoice.nr, invoice.i_date, invoice.valuta, invoice.discount, invoice.employee,
ROUND(SUM(invoicelines.amount * 
IF(invoice.discount,
				IF(invoice.valuta like 'DKK', product.discount_kr, product.discount_eur),
				IF(invoice.valuta like 'DKK', product.price_kr, product.price_euro)
				)),2) AS Total
FROM invoice
LEFT JOIN invoicelines ON invoicelines.invoice_nr = invoice.nr
LEFT JOIN product ON invoicelines.product_nr = product.nr
WHERE invoice.valuta = 'EUR' AND invoice.i_date = '2014-05-22'
GROUP BY invoice.nr;

-- Angiv scripts der viser salget for en bestemt ansat i en angiven periode.
SELECT 
invoice.nr, invoice.i_date, invoice.valuta, invoice.discount, invoice.employee,
ROUND(SUM(invoicelines.amount * 
IF(invoice.discount,
				IF(invoice.valuta like 'DKK', product.discount_kr, product.discount_eur),
				IF(invoice.valuta like 'DKK', product.price_kr, product.price_euro)
				)),2) AS Total
FROM invoice
LEFT JOIN invoicelines ON invoicelines.invoice_nr = invoice.nr
LEFT JOIN product ON invoicelines.product_nr = product.nr
WHERE invoice.employee = 1 AND week(i_date,1) = 21 
GROUP BY invoice.nr;

-- i need a reload