f = @(x) (25*(x.^3) + 25*(x.^2) + x)./(1 + 25*(x.^2)); 
t = linspace(-1, 1, 300); % modelo 3 - Pablo Cuesta Sierra

plot(t, f(t), 'r');hold on;

n = 7;
x_ = -cos((2*(0:n) + 1) * pi / (2*n + 2));
p = polyfit(x_, f(x_), n)
err7 = norm(f(t)-polyval(p, t), 'inf');
disp(err7);

plot(x_, zeros(size(x_)), 'ko', t, f(t)-polyval(p, t), 'b');hold on;

x = -cos((2*(0:n-1) + 1) * pi / (2*n + 2));
p = polyfit(x, f(x), n-1)
err6 = norm(f(t)-polyval(p, t), 'inf');
disp(err6);
plot(x, zeros(size(x)), 'bo', t, f(t)-polyval(p, t), 'g');hold off;


k = ((2*(0:n) + 1) / (2*n + 2))
x0=-cos(pi*k(1))
x7=-cos(pi*k(n+1))
f(x0),f(x7)
x_
f(x_)
pp = @(y) prod(y-x_)
prod([1,2,3])


N=n
M=NaN(N+1,N+2);
% Rellenamos la primera columna
M(:,1)=x_;
% Rellenamos la segunda columna
M(:,2)=f(x_);
for i=2:N+1
    for j=i:N+1
        M(j,i+1)=(M(j,i) - M(j-1,i)) / (M(j,1)   - M(j-i+1,1)  );
    end
end

M
