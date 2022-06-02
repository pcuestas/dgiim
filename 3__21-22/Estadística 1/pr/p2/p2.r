GammaNewton = function(muestra,alpha0){
  # Sucesion que converge al e.m.v. del parametro alpha de la gamma
  m = mean(muestra)
  mlog = mean(log(muestra))
  alpha1 = alpha0 *(1+(digamma(alpha0)+log(m/alpha0)-mlog)/(1-alpha0*trigamma(alpha0)))
}

emvalphaGamma = function(muestra,iter=FALSE){
  # Calcula el e.m.v. del parametro alpha de una Gamma a partir de una muestra.
  # Si iter=TRUE, imprime en pantalla el error en cada iteracion.
  m = mean(muestra)
  v = var(muestra)
  # Inicio de la iteracion = estimador de alpha por el metodo de momentos:
  alpha0 = m^2/v 
  error = 1
  i = 0
  while(error>1E-4){
    i = i+1
    alpha1 = GammaNewton(muestra,alpha0)
    error = abs(alpha1-alpha0)
    alpha0 = alpha1
    if (iter==TRUE){writeLines(paste("Iteracion ",i,"   Error =",error))}
  }
  alpha1
}


n = 50
alpha = 5
beta = 2
X = rgamma(n,shape=alpha,scale=beta)
m = mean(X)
emvalpha = emvalphaGamma(X,iter=TRUE)
emvbeta = m/emvalpha
writeLines(paste("n =",n))
writeLines(paste("alpha =",alpha,"  e.m.v.(alpha) =",round(emvalpha,digits=4)))
writeLines(paste("beta =",beta,"   e.m.v.(beta) =",round(emvbeta,digits=4)))


# ejercicio 1
Datos = read.table("http://verso.mat.uam.es/~amparo.baillo/MatEstI/WilksTableA2.txt")
X = Datos$V2 # vector con las precipitaciones medias
mx = mean(X)
emvalpha = emvalphaGamma(X)
emvbeta = mx/emvalpha
t = seq(0,7,0.01)
hist(X,freq=F,breaks=seq(0,7,0.5))
d = dgamma(t, shape=emvalpha, scale=emvbeta)
lines(t, d, type='l')


# Bangladesh
Datos = read.table('http://verso.mat.uam.es/~amparo.baillo/MatEstI/Bangladesh.txt', header=T)
X = Datos$Rainfall
mx = mean(X)
emvalpha = emvalphaGamma(X)
emvbeta = mx/emvalpha
t = seq(0,20,0.01)
hist(X,freq=F,breaks=seq(0,20,0.8))
d = dgamma(t, shape=emvalpha, scale=emvbeta)
lines(t, d, type='l')

# ejercicio 2
# experimento Monte Carlo 
n = 50
a = 5 #alpha
b = 2 #beta
nMC = 1000 #num muestras Monte Carlo
emvalphavector = rep(0, nMC)
emvbetavector = rep(0, nMC)
for (i in 1:nMC){
  X = rgamma(n, shape=a, scale=b)
  m = mean(X)
  emvalphavector[i] = emvalphaGamma(X)
  emvbetavector[i] = m/emvalphavector[i]
}
writeLines(paste('Tamaño muestral n = ', n))
writeLines(paste('Número de muestras Monte Carlo nMC = ', nMC))
writeLines(paste('alpha =',alpha,'   e.m.v.(alpha)  ->  Media =',round(mean(emvalphavector),digits=4),
                 '   Varianza =',round(var(emvalphavector),digits=4)))
writeLines(paste('beta =',beta,'    e.m.v.(beta) ->  Media =',round(mean(emvbetavector),digits=4),
                 '   Varianza =',round(var(emvbetavector),digits=4)))

