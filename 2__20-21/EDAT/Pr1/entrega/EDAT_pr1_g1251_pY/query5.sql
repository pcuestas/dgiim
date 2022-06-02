SELECT country, 
       Count(officecode) AS noffices 
FROM   offices 
WHERE  officecode NOT IN (SELECT DISTINCT officecode 
                          FROM   (((offices 
                                    natural JOIN employees AS e) 
                                   JOIN customers 
                                     ON salesrepemployeenumber = 
                                        e.employeenumber) 
                                  natural JOIN orders) 
                          WHERE  orderdate > '2002-12-31' 
                                 AND orderdate < '2004-01-01') 
GROUP  BY country 
ORDER  BY noffices DESC; 
