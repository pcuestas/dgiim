f = @(x) (25*(x.^3) + 25*(x.^2) + x)./(1 + 25*(x.^2)); 
t = linspace(-1, 1, 300); % modelo 3 - Pablo Cuesta Sierra

f_t = f(t); % para no recalcular
err1 = zeros(1, 5); err2 = err1; err3 = err1;

for n = 30:-5:10 % orden invertido, para que el 10 sea el último
    j = n/5 - 1; %índice en los vectores de error: err1, err2, err3
    x_equi = linspace(-1, 1, n+1); % nodos equiespaciados
    y = f(x_equi);
    % polinomio interpolador y norma infinito del error (a):
    pol_int = polyfit(x_equi, y, n);
    err1_v = f_t - polyval(pol_int, t);
    err1(j) = norm(err1_v, 'inf'); 
    % splines y norma infinito del error (b):
    sp = spline(x_equi, y);
    err2(j) = norm(f_t - ppval(sp, t), 'inf');
    % con nodos de Chebyshev y pol. interpolador (c):
    x_cheb = -cos((2*(0:n) + 1) * pi / (2*n + 2));
    pol_int_cheb = polyfit(x_cheb, f(x_cheb), n);
    err3_v = f_t - polyval(pol_int_cheb, t);
    err3(j) = norm(err3_v, 'inf');
end
% como el bucle termina con n = 10, los últimos valores son los que 
% tenemos que usar para el apartado (d).
disp(err1); disp(err2); disp(err3);
figure(1);
plot(x_equi, zeros(size(x_equi)), 'ko', t, err1_v);
figure(2);
plot(x_cheb, zeros(size(x_cheb)), 'ko', t, err3_v);


%{
  Apartado e:
    El hecho de que este error sea igual se debe a que el polinomio es el mismo.
    La cuestión se reduce a ver por qué el polinomio interpolador con los 
  nodos {x_1,...,x_7} pasa por {x_0}. Simétricamente, también ocurre que
  el polinomio interpolador con los {x_0,...,x_6} pasa por {x_7}.
    Si construimos el polinomio interpolador con el método de Newton en el orden:
  {x_7, x_6, ..., x_1}, obtendremos un polinomio p_6(x) de P_6 (grado <=6).
  Para calcular finalmente el polinomio interpolador con los 8 nodos, habría que 
  calcular: p_7(x) = p_6(x) + f[x_0, ..., x_7] * (x-x_7)*...*(x-x_1)
  Y la última diferencia dividida: f[x_0, ..., x_7] es del orden de 10*eps, por lo que 
  el polinomio p_7 es, en [-1,1], igual a p_6 (excepto un muy pequeño error, debido al
  cálculo exacto de los cosenos).
    
    No llego a entender, sin embargo, a qué se debe concretamente esta simetría en 
  las diferencias divididas. Ya que se cumple esto para cualquier n impar mayor que
  3, no solo para el 7: las dos penúltimas diferencias divididas,
             f[x_0,...,x_{n-1}] = f[x_1,...,x_n]
    (también se da una cierta simetría en las diferencias divididas anteriores
     a estas dos)
     
    Nota final: pienso que esta simetría puede tener algo que ver con que 
  g(x) = f(x) - x, es una función simétrica y los nodos de Chebyshev también:
  x_{j} = -x_{n-j}. No me da tiempo a contemplarlo.
%}

