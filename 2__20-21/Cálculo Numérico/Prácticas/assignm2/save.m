%part a

N = 200;
x = linspace(-2, 2, N)' ; % vector columna
y = cos(3/2 * x + sin(x));
A = [ones(size(x)), x.^2, x.^4];
P = (A' * A) \ A'; 
c = P*y; % solución a A*c=y --> c = (A+)*y 
er = norm(A*c - y);
plot(x, y, 'r',x, (A*c),'k')

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%part b

N = 200;
x = linspace(-2, 2, N)' ;   % vector columna
y = cos(3/2 * x + sin(x));
A = [ones(size(x)), x.^2, x.^4];
P = (A' * A) \ A';          % lo mismo que (inv(A' * A) * A')
c = P*y;                    % solución a A*c=y --> c = (A+)*y 
er = norm(A*c - y, 2)       % norma 2

% apartado b)
er = norm(A*c - y, 'inf'); % equivalente a max(abs(A*c-y))
k = 2;
while er >= 10^-3
    k = k + 1
    A = [A, x.^(2*k)]; % añadimos la columna siguiente
    P = (A' * A) \ A'; 
    c = P*y;          % q = A*c
    er = max(abs(A*c - y))
end
% al salir del bucle, k es el buscado
plot(x, y-A*c, 'k')
