f = @(x) tan(x)-x;
a = pi*(n+1);
b = pi/2 + pi*(n+1) - .01;

disp([f(a), f(b)]);

c = (b+a)/2;
while abs(f(c)) > (10^-7)
    if f(a)*f(c)<0
        b = c;
    else
        a = c;
    end
    c = (b+a)/2;
end

disp([n, c, tan(c), f(c)])

