drop database if exists sedmuseum; 
create database sedmuseum;

use sedmuseum;

drop table if exists product; 
create table product
(nr	int(3),
p_name	varchar(30),
p_group	varchar(30),
supplyer	varchar(30),
purchase_price	int,
price_kr	double, 
price_euro double,
discount	double, 
amount int
);

drop table if exists p_group; 
create table p_group
(nr	int(3),
category	varchar(30)
);

drop table if exists employee;
create table employee
(nr	int(3),
fname	varchar(20),
lname	varchar(20),
tlf_home	char(8),
tlf_cell	char(8),
tlf_work	char(8),
address		varchar(50),
zipcode		char(4), 
city		varchar(20)
);

drop table if exists invoice;
create table invoice
(nr	int,
i_date	date,
employee	int(3)
);

drop table if exists till;
create table till
(t_date	date, 
start_balance_kr	int,
start_balance_euro	int
);


