SELECT o1.productcode, 
       o2.productcode, 
       Count(*) 
FROM   orderdetails AS o1, 
       orderdetails AS o2 
WHERE  o1.ordernumber = o2.ordernumber 
       AND o1.productcode < o2.productcode 
GROUP  BY o1.productcode, 
          o2.productcode; 
