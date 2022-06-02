a = rand(1);            % PABLO CUESTA SIERRA - Modelo 3
b = rand(1);
c = rand(1);

% ax^2+bx+c=0 sii  x=(-b +-sqrt(b^2-4ac))/(2a)

d = b^2 - 4*a*c;
if d < 0
    disp("No hay solución real")
elseif d==0
    fprintf("Hay solución única y es %.12f\n", (-b/(2*a)))
else
    x1 = (-b + sqrt(d)) / (2*a);
    x2 = (-b - sqrt(d)) / (2*a);    
    fprintf("Hay dos soluciones reales y son %.12f y %.12f\n",x1,x2)
end
