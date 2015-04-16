drop database if exists sedmuseum; 
create database sedmuseum;

use sedmuseum;

set foreign_key_checks = 0; 

drop table if exists product; 
create table product
(nr	int(4) primary key,
p_name	varchar(100),
p_group	int(3),
supplyer	varchar(30),
purchase_price	int,
price_kr	double, 
price_euro double,
discount_kr	double, 
discount_eur double, 
amount int
);

drop table if exists p_group; 
create table p_group
(nr	int(3)	auto_increment primary key,
category	varchar(30)
);

drop table if exists employee;
create table employee
(nr	int(3)	auto_increment primary key,
fname	varchar(20),
lname	varchar(20),
tlf_home	char(8),
tlf_cell	char(8),
tlf_work	char(8),
address		varchar(50),
zipcode		char(4), 
city		varchar(20),
username	char(4),
passwrd		char(4)
);

drop table if exists invoice;
create table invoice
(nr	int	auto_increment primary key,
i_date	date,
employee	int(3),
valuta	char(3),
discount boolean
);

drop table if exists till;
create table till
(t_date	date	primary key, 
start_balance_kr	int,
start_balance_euro	int
);


drop table if exists invoicelines;
create table invoicelines
(invoice_nr	int,
product_nr	int(3),
amount	int,
primary key(invoice_nr, product_nr),
foreign key(invoice_nr) references invoice(nr) ON DELETE CASCADE ON UPDATE CASCADE,
foreign key(product_nr) references product(nr) ON DELETE CASCADE ON UPDATE CASCADE
)engine = InnoDB;

alter table product
add foreign key(p_group) references p_group(nr) ON DELETE CASCADE ON UPDATE CASCADE,
engine = innodb;

alter table invoice
add foreign key(employee) references employee(nr) ON DELETE CASCADE ON UPDATE CASCADE,
add foreign key(i_date) references till(t_date) ON DELETE CASCADE ON UPDATE CASCADE,
engine = innodb;

drop table if exists telephone;
create table telephone
(employee	int(3)	not null,
tlf			char(8)	not null,
primary key(employee, tlf),
foreign key(employee) references employee(nr) ON DELETE CASCADE ON UPDATE CASCADE
)engine = innodb;

alter table employee
drop column tlf_home,
drop column tlf_cell,
drop column tlf_work;



truncate employee;
insert into employee values
(0, 'Laila', 'Kristensen', 'Elverhoejsvej 001', '4700', 'Næstved', 'lakr', '1234'), 
(0, 'Peter', 'Hansen', 'Bygade 5', '4700', 'Næstved', 'peha', '1234'),
(0, 'Jens', 'Jensen', 'Jernbanegade 3', '4690', 'Haslev', 'jeje', '1234'),
(0, 'Anne', 'Pedersen', 'Allégade 24', '4700', 'Næstved', 'anpe', '1234');

truncate telephone;
insert into telephone values
(1, '11223344'),
(1, '22334455'), 
(2, '33445566'),
(2, '44556677'),
(3, '55667788'),
(3, '66778899'),
(4, '12234556'),
(4, '23455678');

truncate p_group;
insert into p_group(category)
values
('Billetter'),
('Hæfter'),
('Legetøj'),
('Café'), 
('Diverse'), 
('Smykker'), 
('Mobiler'), 
('Postkort'),
('Museums Glas'),
('Kâhler Keramik'),
('Holmegaard'),
('Katalog'), 
('Plakat'),
('Mjød'), 
('Øl'), 
('Weibel'), 
('Klippeark'),
('Bøger');

truncate product;
INSERT INTO product VALUES	(1210, 'Liv og Levn 1 (kopi)', 2, NULL, NULL, 40.00, 6.00, 36.00, 5.00,100),
(1211, 'Liv og Levn 2 (kopi)',2, NULL, NULL, 40.00,6.00,36.00, 5.00,100),
(4320, 'Aktuel Plakat', 13, NULL, NULL, 10.00,1.50,9.00,1.00,100),
(1379, 'Tidstavle', 13, NULL, NULL, 75.00, 10.00, 67.50, 9.00,100),
(4205, 'A. Schröder, farvet', 8, NULL, NULL, 5.00, 0.60,4.50,0.50,100), 
(4210, 'A. Schröder, sort/hvid',8, NULL, NULL, 3.00,0.40,3.00, 0.40,100), 
(4250, 'Kuverter til postkort', 8, NULL, NULL, 1.00, 0.1,1,0.1,100),
(2180, 'Stor Stempelkaffe', 4, NULL, NULL, 30.00, 4.00, 30.00, 4.00, 0),
(2181, 'Juice, kildevand', 4, NULL, NULL, 6.00, 0.80, 6.00, 0.80, 0),
(2183, 'Varm Kakao', 4, NULL, NULL, 10.00, 1.5, 10.00, 1.5, 0),
(5001, 'Vedh. Ormesmykke',8, NULL, NULL,65.00,9.00,59.00,8.00,3),
(5002, 'Vedh. Troldkors',8, NULL, NULL,65.00,9.00,59.00,8.00,5),
(5003, 'Vedh. Ulvekorset/Islands Kors',8, NULL, NULL,195.00,26.00,176.00,24.00,100),
(5004, 'Vedh. Kors, Middelalder',8, NULL, NULL,225.00,30.00,202.50,27.00,100),
(5005, 'Vedh. Nøgle til Himlen',8, NULL, NULL,310.00,41.50,280.00,37.50,100),
(1006, 'Aktuel Arkæologi', 18, NULL, NULL, 56.00, 8.00, 50.50, 7.00, 100),
(1036, 'Brug Havnen', 18, NULL, NULL, 25.00, 4.00, 22.50, 3.00, 100),
(1038, 'C.F. Hansen I Danmark og Tyskland', 18, NULL, NULL, 229.00, 31.00, 206.00, 28.00, 100),
(1047, 'Danmark og Renæssancen', 18, NULL, NULL, 249.00, 34.00, 224.00, 30.00, 100),
(1050, 'Danmarks kongelige familier i 1000 år', 18, NULL, NULL, 198.00, 27.00, 178.00, 24.00, 100),
(1057, 'Det glaserede teater', 18, NULL, NULL, 129.00, 17.50, 116.00, 16.00, 100),
(2201, 'Kuffertsæt', 3, NULL, NULL, 195.00, 26.50, 175.00, 23.50, 100),
(2202, 'Blyanter 4 stk. i æske', 3, NULL, NULL, 8.00, 1.50, 7.00, 1.00, 100),
(2203, 'Hånddukke egern/drager/isbjørn', 3, NULL, NULL, 70.00, 9.50, 63.00, 8.50, 100),
(2204, 'Vw Beetle', 3, NULL, NULL, 39.00, 5.50, 35.00, 5, 100),
(2205, 'Spyd', 3, NULL, NULL, 49.00, 7.00, 44.00, 6.00, 100),
(2208, 'Sokkevenner pr stk', 3, NULL, NULL, 29.00, 4.00, 26.00, 3.50, 100),
(2262, 'Saltkar med ske/horn', 5, NULL, NULL, 115.00, 16.00, 103.50, 14.00, 100), 
(2298, 'Sæbe - mandel', 5, NULL, NULL, 20.00, 3.00, 18.00, 2.50, 100), 
(3062, 'Hellig Tre Konger Lys', 5, NULL, NULL, 40.00, 5.50, 36.00, 5.00, 100),
(2185, 'Nipsenål', 5, NULL, NULL, 15.00, 2.00, 13.50, 1.80, 100), 
(2186, 'Nipsenåle', 5, NULL, NULL, 15.00,2.00,13.50,1.80,100), 
(2111, 'Dannebro Ko', 5, NULL, NULL, 40.00, 5.50, 36.00, 2.50, 100), 
(2112, 'Dannebro Blyant', 5, NULL, NULL, 8.00, 1.50, 7.50, 1.00, 100), 
(2110, 'Drikkehorn', 5, NULL, NULL, 120.00, 16.00, 108.00, 15.00, 100), 
(2360, 'Kähler USB-stik', 5, NULL, NULL, 399.00, 54.00, 359.00, 48.00, 100),
(4015, 'Dorte Dukker m.fl.', 17, NULL, NULL, 20.00, 3.00, 18.00, 2.50, 100), 
(4150, 'Glansbilleder', 17, NULL, NULL, 10.00, 1.50, 9.00, 1.00, 100), 
(4061, 'Store og lille Marie dukker', 17, NULL, NULL, 10.00, 1.50,9.00,1.00, 100), 
(4062, 'Store og lille Marie tøj', 17, NULL, NULL, 19.00, 3.00, 17.00, 2.50,100), 
(2258, 'Chokolade i metalæske', 16, NULL, NULL, 42.00, 6.00, 37.50, 5.00, 100),
(0001, 'Voksne', 1, NULL, NULL, 40.00, 6.00, 0.00, 0.00, 0),
(0002, 'Børn under 18 år', 1, NULL, NULL, 0.00, 0.00, 0.00, 0.00, 0),
(0003, 'Voksengrupper', 1, NULL, NULL, 35.00, 5.00, 0.00, 0.00, 0),
(0004, 'Skoler og institutioner', 1, NULL, NULL, 0.00, 0.00, 0.00, 0.00, 0),
(0005, 'Museumsforeninger', 1, NULL, NULL, 0.00, 0.00, 0.00, 0.00, 0),
(0006, 'Aftenåben', 1, NULL, NULL, 120.00, 16.50, 120.00, 16.50, 0),
(0007, 'Omvisning/Byvandring, alm. åbningstid', 1, NULL, NULL, 45.00, 6.50, 45.00, 6.50, 0),
(0008, 'Omvisning/byvandring, aften/weekend', 1, NULL, NULL, 65.00, 9.00, 65.00, 9.00, 0),
(0009, 'Omvisning for skoler', 1, NULL, NULL, 600.00, 81.00, 600.00, 81.00, NULL),
(0010, 'Foredrag', 1, NULL, NULL, 2000.00, 268.00, 2000.00, 268.00, 0),
(0011, 'Af museet planlagte foredrag', 1, NULL, NULL, 75.00, 10.50, 75.00, 10.50, 0); 


drop table if exists city;
create table city
as
(select distinct zipcode, city from employee);

alter table city
add primary key(zipcode);

alter table employee
drop column city,
add foreign key(zipcode) references city(zipcode);

TRUNCATE `till`;
INSERT INTO `till` (`t_date`, `start_balance_kr`, `start_balance_euro`, `closed`) VALUES
	('2014-05-05', 500, 500, 1),
	('2014-05-06', 500, 500, 1),
	('2014-05-07', 500, 500, 1),
	('2014-05-08', 500, 500, 1),
	('2014-05-09', 500, 500, 1),
	('2014-05-10', 500, 500, 1),
	('2014-05-11', 500, 500, 1),
	('2014-05-12', 500, 500, 1),
	('2014-05-13', 500, 500, 1),
	('2014-05-14', 500, 500, 1),
	('2014-05-15', 500, 500, 1),
	('2014-05-16', 500, 500, 1),
	('2014-05-17', 500, 500, 1),
	('2014-05-18', 500, 500, 1),
	('2014-05-19', 500, 500, 1),
	('2014-05-20', 500, 500, 1),
	('2014-05-21', 500, 500, 1),
	('2014-05-22', 500, 500, 1),
	('2014-05-23', 500, 500, 1),
	('2014-05-24', 500, 500, 1),
	('2014-05-25', 500, 500, 1);
TRUNCATE `invoice`;
INSERT INTO `invoice` (`i_date`, `employee`, `valuta`, `discount`) VALUES
	('2014-05-05', 1, 'DKK', TRUE),
	('2014-05-05', 1, 'DKK', FALSE),
	('2014-05-06', 1, 'EUR', TRUE),
	('2014-05-06', 1, 'DKK', FALSE),
	('2014-05-07', 1, 'DKK', FALSE),
	('2014-05-07', 1, 'DKK', FALSE),
	('2014-05-08', 1, 'DKK', FALSE),
	('2014-05-08', 1, 'DKK', FALSE),
	('2014-05-09', 1, 'EUR', FALSE),
	('2014-05-09', 1, 'DKK', FALSE),
	('2014-05-10', 1, 'EUR', FALSE),
	('2014-05-10', 1, 'DKK', TRUE),
	('2014-05-11', 1, 'DKK', TRUE),
	('2014-05-11', 1, 'DKK', FALSE),
	('2014-05-12', 1, 'DKK', FALSE),
	('2014-05-12', 1, 'EUR', TRUE),
	('2014-05-13', 1, 'EUR', TRUE),
	('2014-05-13', 1, 'EUR', TRUE),
	('2014-05-14', 1, 'DKK', TRUE),
	('2014-05-14', 1, 'DKK', TRUE),
	('2014-05-15', 1, 'EUR', TRUE),
	('2014-05-15', 1, 'EUR', FALSE),
	('2014-05-16', 1, 'DKK', TRUE),
	('2014-05-16', 1, 'EUR', FALSE),
	('2014-05-17', 1, 'DKK', TRUE),
	('2014-05-17', 1, 'EUR', FALSE),
	('2014-05-18', 1, 'EUR', FALSE),
	('2014-05-18', 1, 'DKK', FALSE),
	('2014-05-19', 1, 'DKK', TRUE),
	('2014-05-19', 1, 'DKK', FALSE),
	('2014-05-20', 1, 'DKK', TRUE),
	('2014-05-20', 1, 'DKK', TRUE),
	('2014-05-21', 1, 'DKK', FALSE),
	('2014-05-21', 1, 'EUR', TRUE),
	('2014-05-22', 1, 'EUR', TRUE),
	('2014-05-22', 1, 'EUR', TRUE),
	('2014-05-23', 1, 'DKK', TRUE),
	('2014-05-23', 1, 'EUR', FALSE),
	('2014-05-24', 1, 'EUR', FALSE),
	('2014-05-24', 1, 'DKK', TRUE),
	('2014-05-25', 1, 'EUR', FALSE),
	('2014-05-25', 1, 'DKK', TRUE);
TRUNCATE `invoicelines`;
INSERT INTO `invoicelines` (`invoice_nr`, `product_nr`, `amount`) VALUES
	(1, 1, 8),
	(1, 2, 5),
	(1, 3, 25),
	(1, 4, 0),
	(2, 1, 7),
	(2, 2, 4),
	(2, 3, 20),
	(2, 4, 21),
	(3, 1, 0),
	(3, 2, 7),
	(3, 3, 28),
	(3, 4, 0),
	(4, 1, 1),
	(4, 2, 1),
	(4, 3, 28),
	(4, 4, 18),
	(5, 1, 0),
	(5, 2, 3),
	(5, 3, 18),
	(5, 4, 15),
	(6, 1, 4),
	(6, 2, 0),
	(6, 3, 11),
	(6, 4, 0),
	(7, 1, 4),
	(7, 2, 6),
	(7, 3, 0),
	(7, 4, 0),
	(8, 1, 3),
	(8, 2, 4),
	(8, 3, 19),
	(8, 4, 29),
	(9, 1, 0),
	(9, 2, 0),
	(9, 3, 30),
	(9, 4, 0),
	(10, 1, 0),
	(10, 2, 7),
	(10, 3, 16),
	(10, 4, 12),
	(11, 1, 9),
	(11, 2, 8),
	(11, 3, 24),
	(11, 4, 26),
	(12, 1, 0),
	(12, 2, 2),
	(12, 3, 28),
	(12, 4, 21),
	(13, 1, 2),
	(13, 2, 4),
	(13, 3, 13),
	(13, 4, 24),
	(14, 1, 6),
	(14, 2, 4),
	(14, 3, 0),
	(14, 4, 0),
	(15, 1, 8),
	(15, 2, 3),
	(15, 3, 0),
	(15, 4, 21),
	(16, 1, 7),
	(16, 2, 1),
	(16, 3, 0),
	(16, 4, 0),
	(17, 1, 8),
	(17, 2, 8),
	(17, 3, 24),
	(17, 4, 0),
	(18, 1, 0),
	(18, 2, 3),
	(18, 3, 17),
	(18, 4, 0),
	(19, 1, 1),
	(19, 2, 6),
	(19, 3, 18),
	(19, 4, 27),
	(20, 1, 6),
	(20, 2, 7),
	(20, 3, 20),
	(20, 4, 24),
	(21, 1, 0),
	(21, 2, 0),
	(21, 3, 16),
	(21, 4, 25),
	(22, 1, 9),
	(22, 2, 9),
	(22, 3, 0),
	(22, 4, 12),
	(23, 1, 4),
	(23, 2, 5),
	(23, 3, 13),
	(23, 4, 17),
	(24, 1, 0),
	(24, 2, 1),
	(24, 3, 14),
	(24, 4, 11),
	(25, 1, 3),
	(25, 2, 8),
	(25, 3, 30),
	(25, 4, 0),
	(26, 1, 0),
	(26, 2, 2),
	(26, 3, 20),
	(26, 4, 0),
	(27, 1, 7),
	(27, 2, 3),
	(27, 3, 0),
	(27, 4, 24),
	(28, 1, 6),
	(28, 2, 0),
	(28, 3, 0),
	(28, 4, 0),
	(29, 1, 5),
	(29, 2, 3),
	(29, 3, 0),
	(29, 4, 0),
	(30, 1, 2),
	(30, 2, 7),
	(30, 3, 17),
	(30, 4, 12),
	(31, 1, 0),
	(31, 2, 0),
	(31, 3, 16),
	(31, 4, 13),
	(32, 1, 2),
	(32, 2, 9),
	(32, 3, 29),
	(32, 4, 0),
	(33, 1, 6),
	(33, 2, 0),
	(33, 3, 19),
	(33, 4, 0),
	(34, 1, 2),
	(34, 2, 7),
	(34, 3, 11),
	(34, 4, 24),
	(35, 1, 0),
	(35, 2, 4),
	(35, 3, 24),
	(35, 4, 23),
	(36, 1, 4),
	(36, 2, 0),
	(36, 3, 26),
	(36, 4, 0),
	(37, 1, 3),
	(37, 2, 9),
	(37, 3, 28),
	(37, 4, 19),
	(38, 1, 1),
	(38, 2, 0),
	(38, 3, 0),
	(38, 4, 21),
	(39, 1, 7),
	(39, 2, 2),
	(39, 3, 14),
	(39, 4, 23),
	(40, 1, 0),
	(40, 2, 1),
	(40, 3, 14),
	(40, 4, 11),
	(41, 1, 9),
	(41, 2, 9),
	(41, 3, 23),
	(41, 4, 24),
	(42, 1, 0),
	(42, 2, 5),
	(42, 3, 16),
	(42, 4, 24); 

set foreign_key_checks = 1;