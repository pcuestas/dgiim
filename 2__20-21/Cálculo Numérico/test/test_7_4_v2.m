%n = 0;
f = @(x) x - 1/(tan(x)) + x /(tan(x)).^2;

a = pi/2 + pi*(n+1) - .001;
b = f(a);
k = 0;
while abs((a-b)/a) > (10^-6)
    a = b;
	b = f(a);
	k = k+1;
end

disp([n, b, tan(b), f(b), k])

