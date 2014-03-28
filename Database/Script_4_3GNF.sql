use sedmuseum;

drop table if exists city;
create table city
as
(select distinct zipcode, city from employee);

alter table city
add primary key(zipcode);

alter table employee
drop column city,
add foreign key(zipcode) references city(zipcode);