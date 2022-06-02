Dado = function(n = 50){
  # Genera n lanzamientos de un dado, 
  # devuelve la tabla de frecuencias
  
  #selecciona con reemplazam. n números en 1,...,6
  lanzamientos = sample(1:6, n, rep=T)
  #obtiene la tabla de frecuencias
  frecuencias = table(lanzamientos)
  #return(frecuencias) #línea innecesaria
  #si no se pone return(), se devuelve lo último calculado
}