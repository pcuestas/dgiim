
\pagenumbering{gobble}


# Assignment 1 report

**Pablo Cuesta Sierra and Álvaro Zamanillo Sáez. Group 1251.**

### Section 2. Primary keys and Foreign keys of each table of the database:



offices(**officecode**, city, phone, addressline1, addressline2, state, country, postalcode, territory)

employees(**employeenumber**, lastname, firstname, extension, email, officecode$\rightarrow$offices.officecode, reportsto$\rightarrow$employeenumber, jobtitle)

customers(**customernumber**, customername, contactlastname, contactfirstname, phone, addressline1, addressline2, city, state, postalcode, country, salesrepemployeenumber$\rightarrow$employees.employeenumber, creditlimit)

payments(**customernumber**$\rightarrow$customers.customernumber, **checknumber**, paymentdate, amount)

orders(**ordernumber**, orderdate, requireddate, shippeddate, status, comments, customernumber$\rightarrow$customers.customernumber)

orderdetails(**ordernumber**$\rightarrow$orders.ordernumber, **productcode**$\rightarrow$products.productcode, quantityordered, priceeach, orderlinenumber)

products(**productcode**, productname, productline$\rightarrow$productlines.productline, productscale, productvendor, productdescription, quantityinstock, buyprice, msrp)

productlines(**productline**, textdescription, htmldescription, image)


> Note: as requested, primary keys are in bold and foreign keys are shown as: FK$\rightarrow$column they reference

In the following page is the requested database relational schema.

