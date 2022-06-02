SELECT officecode, 
       Sum(quantityordered) AS itemssold 
FROM   ((((offices 
           NATURAL JOIN employees AS e) 
          JOIN customers 
            ON salesrepemployeenumber = e.employeenumber) 
         NATURAL JOIN orders) 
        NATURAL JOIN orderdetails) 
GROUP  BY officecode 
ORDER  BY itemssold DESC 
LIMIT  1; 
