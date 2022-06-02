% PABLO CUESTA SIERRA - Modelo 3

function [A] = p13(A)
    %P13 Recibe una matriz A, de tamaño 2n
    %   y devuelve esa matriz con las filas 
    %   1 y 2 intercambiadas, las filas 3 y 4 
    %   intercambiadas, y así hasta la 2n − 1 y la 2n.
    
    % nota: asumimos que la matriz de entrada 
    % es cuadrada y tiene un tamaño par
    n = size(A,1)/2;
    k=1:n;
    A([2*k-1,2*k],:) = A([2*k,2*k-1],:);
    
end

