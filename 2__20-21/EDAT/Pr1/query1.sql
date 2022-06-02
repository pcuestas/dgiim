SELECT customernumber, 
       customername, 
       Sum(amount) AS total 
FROM   ((((products 
           natural JOIN orderdetails) 
          natural JOIN orders) 
         natural JOIN customers) 
        natural JOIN payments) AS x 
WHERE  productname = '1940 Ford Pickup Truck' 
GROUP  BY customernumber, 
          customername 
ORDER  BY total DESC;
