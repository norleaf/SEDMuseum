use sedmuseum;
select DISTINCT invoice.nr, discount, valuta, product_nr, invoicelines.amount, price_euro, price_kr, discount_eur, discount_kr from invoice, invoicelines, product where product.nr = product_nr ORDER BY nr ASC;

select * from invoicelines;

select * from p_group;

select * from invoicelines join product on invoicelines.product_nr = product.nr;

select * from invoice;

select * from employee;

select * from product;

select * from invoicelines join product on invoicelines.product_nr = product.nr join invoice on invoicelines.invoice_nr = invoice.nr where i_date = '2014-04-29' and valuta = 'DKK';

-- for hver invoiceline vil vi have productkategorien, så vi skal lave et join på productnr

truncate till;

SELECT
    *
FROM
    till
        JOIN
    invoice ON till.t_date = invoice.i_date
        JOIN
    invoicelines ON invoice.nr = invoicelines.invoice_nr
        JOIN
    product ON invoicelines.product_nr = product.nr
        JOIN
    p_group ON product.p_group = p_group.nr
        JOIN
    employee ON invoice.employee = employee.nr;