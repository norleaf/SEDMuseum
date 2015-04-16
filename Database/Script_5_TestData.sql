SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;

DELETE FROM `invoice`;
INSERT INTO `invoice` (`nr`, `i_date`, `employee`, `valuta`, `discount`) VALUES
	(1, '2014-05-20', 1, 'DKK', 0);
	
DELETE FROM `invoicelines`;
INSERT INTO `invoicelines` (`invoice_nr`, `product_nr`, `amount`) VALUES
	(1, 1, 5),
	(1, 2112, 5),
	(1, 2180, 1),
	(1, 2181, 3);
	
DELETE FROM `till`;
INSERT INTO `till` (`t_date`, `start_balance_kr`, `start_balance_euro`, `closed`) VALUES
	('2014-05-20', 1000, 100, 0);
	
SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS);
