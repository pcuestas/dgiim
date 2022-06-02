SELECT e1.employeenumber, 
       e1.lastname 
FROM   ((employees AS e1 
         JOIN employees AS e2 
           ON e1.reportsto = e2.employeenumber) 
        JOIN employees AS e3 
          ON e2.reportsto = e3.employeenumber) 
WHERE  e3.reportsto IS NULL ;
