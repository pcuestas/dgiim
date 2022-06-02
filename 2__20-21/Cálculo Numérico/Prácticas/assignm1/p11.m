% PABLO CUESTA SIERRA - Modelo 3
x = linspace(0,5,200);
x1= 0:1:5;
f = @(z) cos(z.^(3/2)).*sin(z);
plot(x,f(x),'k',x1,f(x1),'ko');