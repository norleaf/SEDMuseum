SELECT 
invoice.nr, invoice.i_date, invoice.valuta, invoice.discount, invoice.employee,
SUM(invoicelines.amount * 
IF(invoice.discount,
				IF(invoice.valuta like 'DKK', product.discount_kr, product.discount_eur),
				IF(invoice.valuta like 'DKK', product.price_kr, product.price_euro)
				)) AS Total
FROM invoice
LEFT JOIN invoicelines ON invoicelines.invoice_nr = invoice.nr
LEFT JOIN product ON invoicelines.product_nr = product.nr GROUP BY invoice.nr;