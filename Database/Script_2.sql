use sedmuseum;

set foreign_key_checks = 0;

drop table if exists invoicelines;
create table invoicelines
(invoice_nr	int,
product_nr	int(3),
amount	int,
valuta	int,
primary key(invoice_nr, product_nr),
foreign key(invoice_nr) references invoice(nr),
foreign key(product_nr) references product(nr)
)engine = InnoDB;

alter table product
add foreign key(p_group) references p_group(nr),
engine = innodb;

alter table invoice
add foreign key(employee) references employee(nr),
add foreign key(i_date) references till(t_date),
engine = innodb;

drop table if exists telefon;
create table telefon
(employee	int(3)	not null,
tlf			char(8)	not null,
primary key(employee, tlf),
foreign key(employee) references employee(nr)
)engine = innodb;

alter table employee
drop column tlf_home,
drop column tlf_cell,
drop column tlf_work;