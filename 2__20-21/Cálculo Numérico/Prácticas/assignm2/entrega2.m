N = 200                     % Modelo 3 - Pablo Cuesta Sierra.
x = linspace(-2, 2, N)' ;   % vector columna
y = cos(3/2 * x + sin(x));
A = [ones(size(x)), x.^2, x.^4];
P = (A' * A) \ A';          % lo mismo que (inv(A' * A) * A')
c = P * y;                  % solución a A*c=y --> c=(A+)*y 
er = norm(A*c - y, 2)       % norma 2

% Apartado b)

er = norm(A*c - y, 'inf'); % equivalente a max(abs(A*c-y))
k = 2;
while er >= 10^-3
% las líneas de este bucle se pueden condensar en 3, pero sería menos legible
    k = k + 1;
    A = [A, x.^(2*k)]; % añadimos la columna siguiente
    P = (A' * A) \ A'; 
    c = P * y;           % q = A*c
    er = max(abs(A * c - y));
end

% al salir del bucle, k es el buscado
disp(['El k buscado es: ', num2str(k)]);
plot(x, y - A*c, 'k');

%{
Apartado c)

La función f(x) = cos(2/3*x+sen(x)) es simétrica:
    f(-x) = cos(-2/3*x-sen(x)) = f(x)

En el primer caso, se busca una aproximación de esta 
función con la forma :
g(x_j) = c_1 + c_2*x_j + c_2*(x_j ^2) + c_3*(x_j ^4) + c_4*(x_j^4-x_j^7)

Esto quiere decir que para todo x_j, el coeficiente de c_4
es (x_j^4 - x_j^7). Para su x_l simétrico: -x_j, ese 
coeficiente es distinto: (x_j^4 + x_j^7). Con x_N = 2 el coeficiente se 
hace: -112, y x_0 = -x_N tiene: 144. Además, se tiene que 
g(x_j) debe ser igual a g(-x_j) porque f(x)=f(-x). Esto lleva a que el 
valor de c_4 sea muy cercano a 0 para que se cumpla que g(x_j)=g(-x_j)
en la solución al problema de mínimos cuadrados. 
Por esto, en este primer caso, el error es el mismo, porque la solución 
será la misma si c_4 = 0. (nos saldrán c_1, c_2, c_3 iguales que con 3 
columnas de A)

En cambio, en el segundo caso, el coeficiente de c_4 es (x_j^3-x_j^6); es
igual para x_j que para -x_j. Esto significa que en este caso, c_4 no tiene
por qué ser 0. Por lo que varía el error.
%}
