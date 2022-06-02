notas = read.table('notas.txt', sep = ' ', dec = ',', header = T, as.is = F)
names(notas)
mean(notas$nota09)
colMeans(notas[,c(2,3)])
apply(notas[,c(2,3)],2, mean) # 2 porque se aplica por columnas, 1 sería filas
              ## Apply(matriz, dimensión, función)
apply(notas[,c(2,3)], 2, sd)
summary(notas)
hist(notas[,2])
hist(notas$nota09)
boxplot(notas$nota09, notas$nota10, names = c("2009", "20010"))
boxplot(notas$nota10 ~ notas$tipo)

plot(notas$nota09, notas$nota10, xlab = "Nota 2009", ylab = 'Nota 2010')

cov(notas$nota09, notas$nota10)
cor(notas$nota09, notas$nota10)

y = notas$nota10
x = notas$nota09
recta = lm(y~x) #linear model (lm)--y en función de x(y~x)
plot (x,y,xlab='Notas 2009',ylab='Notas 2010')
abline(recta,lwd=2,col='red')

Dado = function(n = 50){
  # Genera n lanzamientos de un dado, 
  # devuelve la tabla de frecuencias
  
  #selecciona con reemplazam. n números en 1,...,6
  lanzamientos = sample(1:6, n, rep=T)
  #obtiene la tabla de frecuencias
  frecuencias = table(lanzamientos)
  return(frecuencias) #línea innecesaria
  #si no se pone return(), se devuelve lo último calculado
}

Dado()

x <- seq(0, 2*pi, 0.01)
y <- sin(x)

pdf('graf2.pdf', width=5, height=3)# para empezar a pintar en el pdf
par(mar=c(0,0,0,0))#márgenes
plot(x,y, t='l', col='blue', lwd=2, bty='l') 
  #t tipo de línea
  #lwd line width
lines(x, cos(x), col='red', lty=2)
  #lty line type
abline(v=pi, lwd=3)
points(4,0,pch=2)
  #pch tipo de punto
legend('bottomleft', c('seno','coseno'),lty=c(1,2),lwd=c(2,1),col=c('blue','red'))
title('Funciones seno y coseno')
dev.off() # para dejar de pintar en el pdf
