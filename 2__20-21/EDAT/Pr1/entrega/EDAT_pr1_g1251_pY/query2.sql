SELECT productline, 
       Avg(shippeddate - orderdate) 
FROM   (((productlines 
          natural JOIN products) 
         natural JOIN orderdetails) 
        natural JOIN orders) AS x 
GROUP  BY productline; 
