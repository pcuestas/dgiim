write_log(S) :- 
    open('error_logs.txt', append, Out), 
    write(Out, S), 
    write(Out, '\n'), 
    close(Out).

/*print an error*/
err(S):- 
    /*print(S),*/
    write_log(S).

/***************
* EJERCICIO 2. sum_pot_prod/4
*
*	ENTRADA:
*		X: Vector de entrada de numeros de valor real.
*		Y: Vector de entrada de numeros de valor real.
*		Potencia: Numero de valor entero, potencia.
*	SALIDA:
*		Resultado: Numero de valor real resultado de la operacion sum_pot_prod. 
*
****************/
/*base case*/
sum_pot_prod([], [], _, 0):-!.

/*possible errors: Potencia<0, or vectors of different length*/
sum_pot_prod([], _, _, _):- err('ERROR 1.2 Longitud'),!,fail.

sum_pot_prod(_, [], _, _):- err('ERROR 1.2 Longitud'),!,fail.

sum_pot_prod(_, _, Potencia, _):- 
    Potencia < 0, 
    err('ERROR 1.1 Potencia'),!,fail.

sum_pot_prod([X|Lx], [Y|Ly], Potencia, Result) :-
    Product is X*Y,
    P is Product**Potencia,
    sum_pot_prod(Lx, Ly, Potencia, Result1),
    Result is (P + Result1). 
    
/***************
* EJERCICIO 3. segundo_penultimo/3
*
*       ENTRADA:
*               L: Lista de entrada de numeros de valor real.
*       SALIDA:
*               X : Numero de valor real. Segundo elemento.
*		Y : Numero de valor real. Penultimo elemento.
*
****************/
segundo_penultimo([], _, _):-
    err('ERROR 2.1 Longitud'),!,fail.

segundo_penultimo([_], _, _):-
    err('ERROR 2.1 Longitud'),!,fail.

segundo_penultimo(L, X, Y):- 
    append([_,X], _, L), 
    append(_, [Y,_], L).

/***************
 * EJERCICIO 4. sublista/5
*
*       ENTRADA:
*		L: Lista de entrada de cadenas de texto.
*		Menor: Numero de valor entero, indice inferior.
*		Mayor: Numero de valor entero, indice superior.
*		E: Elemento, cadena de texto.
*       SALIDA:
*		Sublista: Sublista de salida de cadenas de texto.
*
****************/
sublista(_, Menor, Mayor, _, _):-
    ((Menor > Mayor);(Menor =< 0)), 
    err('ERROR 3.2 Indices.'),!,fail.

sublista(L, Menor, Mayor, E, Sublista) :-
    slice(L, Menor, Mayor, Sublista),
    (   
        (member(E, Sublista),!);
        (err('ERROR 3.1 Elemento'),!,fail)
    ).

/*************
 * slice/4
 *      INPUT:
 *          L: input list (indexed starting by 1)
 *          I: first index of sublist
 *          K: last index of sublist
 *      OUTPUT:
 *          Sublist: L's sublist from index I to index K (both included) 
 *************/
slice([X|_], 1, 1, [X]):-!.
slice([_], 1, _, _):-
    err('ERROR 3.2 Indices.'),!,fail.
slice([X|L], 1, K, [X|S]):-
    K1 is K-1,
    slice(L,1, K1, S).
slice([_|L], I, K, S):-
    K1 is K-1,
    I1 is I-1,
    slice(L, I1, K1, S).

/***************
* EJERCICIO 5. espacio_lineal/4
*
*       ENTRADA:
*               Menor: Numero de valor entero, valor inferior del intervalo.
*               Mayor: Numero de valor entero, valor superior del intervalo.
*               Numero_elementos: Numero de valor entero, numero de valores de la rejilla.
*       SALIDA:
*               Rejilla: Vector de numeros de valor real resultante con la rejilla.
*
****************/
espacio_lineal(Menor, Mayor, Numero_elementos, _):-
    (   
        Menor >= Mayor; 
        Numero_elementos =< 1
    ), err('ERROR 4.1 Indices'),!,fail.

espacio_lineal(Menor, Mayor, Numero_elementos, Rejilla) :- 
    N is Numero_elementos-1,
    findall( X,
        (
            between(0, N, J),
            X is Menor + (Mayor-Menor)*J/N
        ),
        Rejilla
    ).
    

/***************
* EJERCICIO 6. normalizar/2
*
*       ENTRADA:
*		Distribucion_sin_normalizar: Vector de numeros reales de entrada. Distribucion sin normalizar.
*       SALIDA:
*		Distribucion: Vector de numeros reales de salida. Distribucion normalizada.
*
****************/
normalizar(Distribucion_sin_normalizar, _):-
    member(X, Distribucion_sin_normalizar),
    X < 0,
    err('ERROR 5.1 Negativos'),!,fail.

normalizar(Distribucion_sin_normalizar, Distribucion) :- 
    sum_list(Distribucion_sin_normalizar, Z),
    findall( P,
        (
            member(X, Distribucion_sin_normalizar),
            P is X/Z
        ),
        Distribucion
    ).

/***************
* EJERCICIO 7. divergencia_kl/3
*
*       ENTRADA:
*		D1: Vector de numeros de valor real. Distribucion.
*		D2: Vector de numeros de valor real. Distribucion.
*       SALIDA:
*		KL: Numero de valor real. Divergencia KL.
*
****************/
divergencia_kl(D1,D2,_):-
    (sum_list(D1, Sum); sum_list(D2, Sum)),
    not(Sum is 1.0), not(Sum is 1),
    err('ERROR 6.2 Divergencia KL definida solo para distribuciones.'),!,fail.

divergencia_kl(D1, D2, KL) :-     
    sum_divkl(D1,D2,KL).

/*base case*/
sum_divkl([], [], 0):-!.
/*if distributions have different support (length), the KL divergence is not defined*/
sum_divkl([], _, _):- err('ERROR 6.1 Divergencia KL no definida.'),!,fail.
sum_divkl(_, [], _):- err('ERROR 6.1 Divergencia KL no definida.'),!,fail.
sum_divkl([P|_], [Q|_], _) :-
    (P =< 0; Q =< 0),
    err('ERROR 6.1 Divergencia KL no definida.'),!,fail.
sum_divkl([P|D1], [Q|D2], KL) :- 
    R is P*log(P/Q),
    sum_divkl(D1, D2, K1),
    KL is R + K1.

/***************
* EJERCICIO 8. producto_kronecker/3
*
*       ENTRADA:
*		Matriz_A: Matriz de numeros de valor real.
*		Matriz_B: Matriz de numeros de valor real.
*       SALIDA:
*		Matriz_bloques: Matriz de bloques (matriz de matrices) de numeros reales.
*
****************/
producto_kronecker([], _, []):-!.
producto_kronecker([Fx|Mx], Matriz_B, [Rz|Matriz_bloques]):-
    pk_row_matrix(Fx, Matriz_B, Rz),
    producto_kronecker(Mx, Matriz_B, Matriz_bloques).

/*multiply a row by a matrix, returns a row of matrices*/
pk_row_matrix([], _, []):-!.
pk_row_matrix([X|Fx], My, [Mz|Rz]):-
    pk_elem_matrix(X, My, Mz),
    pk_row_matrix(Fx, My, Rz).

/*multiply an element by a matrix, returns a matrix*/
pk_elem_matrix(_,[],[]):-!.
pk_elem_matrix(X,[Fy|My],[Fz|Mz]):-
    pk_elem_row(X, Fy, Fz),
    pk_elem_matrix(X, My, Mz).

/*multiply an element by a row, returns a row*/
pk_elem_row(_, [], []):-!.
pk_elem_row(X, [Y|Fy], [Z|Fz]):-
    X >= 0, Y >= 0, !,
    Z is X*Y,
    pk_elem_row(X, Fy, Fz).
pk_elem_row(_, _, _):-
    err('ERROR 7.1 Elemento menor que cero.'),!,fail.


/***************
* EJERCICIO 9a. distancia_euclidea/3
*
*       ENTRADA:
*               X1: Vector de numeros de valor real. 
*               X2: Vector de numeros de valor real.
*       SALIDA:
*               D: Numero de valor real. Distancia euclidea.
*
****************/
distancia_euclidea([], [], 0):-!.
distancia_euclidea([X|Xs], [Y|Ys], Distance) :- 
    distancia_euclidea(Xs, Ys, Distance_s),
    Distance is sqrt((Y-X)**2 + Distance_s**2).

/***************
* EJERCICIO 9b. calcular_distancias/3
*
*       ENTRADA:
*               X_entrenamiento: Matriz de numeros de valor real donde cada fila es una instancia representada por un vector.
*               X_test: Matriz de numeros de valor real donde cada fila es una instancia representada por un vector. Instancias sin etiquetar.
*       SALIDA:
*               Matriz_resultados: Matriz de numeros de valor real donde cada fila es un vector con la distancia de un punto de test al conjunto de entrenamiento X_entrenamiento.
*
****************/

/*
* Calculates the distance from a point to all the points in a list
* dist_to_others(Point,List,Distances)
*/
dist_to_others(_, [], []):-!.
dist_to_others(P, [X|Xs], [D|Distances]):-
    distancia_euclidea(P, X, D),
    dist_to_others(P, Xs, Distances).

/*Iterate through all rows using the auxiliar procedure dist_to_others*/
calcular_distancias(_,[],[]):-!.
calcular_distancias(X_entrenamiento, [P|X_test], [Distances|Matriz_resultados]):-
    dist_to_others(P, X_entrenamiento, Distances),
    calcular_distancias(X_entrenamiento, X_test, Matriz_resultados).

/***************
* EJERCICIO 9c. predecir_etiquetas/4
*
*       ENTRADA:
*               Y_entrenamiento: Vector de valores alfanumericos de una distribucion categorica. Cada etiqueta corresponde a una instancia de X_entrenamiento.
*               K: Numero de valor entero.
*               Matriz_resultados: Matriz de numeros de valor real donde cada fila es un vector con la distancia de un punto de test al conjunto de entrenamiento X_entrenamiento.
*       SALIDA:
*               Y_test: Vector de valores alfanumericos de una distribucion categorica. Cada etiqueta corresponde a una instancia de X_test.
*
****************/
/*Base case*/
predecir_etiquetas(_, _, [],[]):-!.

/*Iterate over the rows of Matriz_resultados using predecir_etiqueta*/
predecir_etiquetas(Y_entrenamiento, K, [Vec_distancias|Matriz_resultados], [Tag|Y_test]):-
	predecir_etiquetas(Y_entrenamiento, K, Matriz_resultados, Y_test),
	predecir_etiqueta(Y_entrenamiento, K, Vec_distancias, Tag).

/***************
* EJERCICIO 9d. predecir_etiqueta/4
*
*       ENTRADA:
*               Y_entrenamiento: Vector de valores alfanumericos de una distribucion categorica. Cada etiqueta corresponde a una instancia de X_entrenamiento.
*               K: Numero de valor entero.
*               Vec_distancias: Vector de valores reales correspondiente a una fila de Matriz_resultados.
*       SALIDA:
*               Etiqueta: Elemento de valor alfanumerico.
*
****************/
predecir_etiqueta(Y_entrenamiento, K, Vec_distancias, Etiqueta):-
    calcular_K_etiquetas_mas_relevantes(Y_entrenamiento, K, Vec_distancias, Etiquetas),
    calcular_etiqueta_mas_relevante(Etiquetas, Etiqueta).

/***************
* EJERCICIO 9e. calcular_K_etiquetas_mas_relevantes/4
*
*       ENTRADA:
*               Y_entrenamiento: Vector de valores alfanumericos de una distribucion categorica. Cada etiqueta corresponde a una instancia de X_entrenamiento.
*               K: Numero de valor entero.
*               Vec_distancias: Vector de valores reales correspondiente a una fila de Matriz_resultados.
*       SALIDA:
*		K_etiquetas: Vector de valores alfanumericos de una distribucion categorica.
*
****************/
calcular_K_etiquetas_mas_relevantes(Y_entrenamiento, K, Vec_distancias, K_etiquetas):-
    pairs_keys_values(Pairs, Vec_distancias, Y_entrenamiento),
    keysort(Pairs, Sorted_pairs),
    slice(Sorted_pairs, 1, K, K_pairs),
    pairs_values(K_pairs, K_etiquetas).

/***************
* EJERCICIO 9f. calcular_etiqueta_mas_relevante/2
*
*       ENTRADA:
*               K_etiquetas: Vector de valores alfanumericos de una distribucion categorica.
*       SALIDA:
*               Etiqueta: Elemento de valor alfanumerico.
*
****************/
calcular_etiqueta_mas_relevante(K_etiquetas, Etiqueta) :-
    calcular_contadores(K_etiquetas, Contadores),
    calcular_etiqueta_max(Contadores, [_,Etiqueta]).

/***********
 * calcular_etiqueta_max/2
 *      INPUT: 
 *          Contadores: counters like the output from calcular_contadores/2
 *      OUTPUT:
 *          [MaxCounter, Tag]: The Tag with maximum counter from Contadores 
 **********/
calcular_etiqueta_max([], [0,_]):-!.
calcular_etiqueta_max([[N,E]|Contadores], [Max,Etiqueta]):-
    calcular_etiqueta_max(Contadores,[K,F]),
    (
        (K =< N, Max=N, Etiqueta=E,!);
        (Max=K, Etiqueta=F)
    ).

/*****************
 * calcular_contadores/2: outputs a list of counters given a list of tags
 *      INPUT: List of tags [a,b,a,c,d]
 *      OUTPUT: List of counters [[2,a],[2,b],[1,c]]
 ***************/
calcular_contadores([], []):-!.
calcular_contadores([E|L], Contadores):-
    calcular_contadores(L, ContadoresL),
    incrementar_contador(E, ContadoresL, Contadores).

/***************
 * incrementar_contador/2: increases by 1 the counter of a tag E (or initializes
 * the tag's counter if it is not in the list of counters)
 *       INPUT: List of counters: (eg.) [[2,'a'],[5,'b'],[1,'d']] and tag: (eg.) 'b'
 *       OUTPUT: The updated counters: [[2,'a'],[6,'b'],[1,'d']]
****************/
incrementar_contador(E, [], [[1,E]]):-!.
incrementar_contador(E, [[C1,E]|Contadores], [[C2,E]|Contadores]):-
    C2 is C1+1, !.
incrementar_contador(E, [[C,F]|Contadores1], [[C,F]|Contadores2]):-
    incrementar_contador(E, Contadores1, Contadores2).

/***************
* EJERCICIO 9g. k_vecinos_proximos/5
*
*       ENTRADA:
*		X_entrenamiento: Matriz de numeros de valor real donde cada fila es una instancia representada por un vector.
*		Y_entrenamiento: Vector de valores alfanumericos de una distribucion categorica. Cada etiqueta corresponde a una instancia de X_entrenamiento.
*		K: Numero de valor entero.
*		X_test: Matriz de numeros de valor real donde cada fila es una instancia representada por un vector. Instancias sin etiquetar.
*       SALIDA:
*		Y_test: Vector de valores alfanumericos de una distribucion categorica. Cada etiqueta corresponde a una instancia de X_test.
*
****************/
k_vecinos_proximos(X_entrenamiento,Y_entrenamiento,K,X_test,Y_test):-
    calcular_distancias(X_entrenamiento,X_test,Matriz_resultados),
    predecir_etiquetas(Y_entrenamiento,K,Matriz_resultados,Y_test).

/***************
* EJERCICIO 9h. clasifica_patrones/4
*
*       ENTRADA:
*		iris_patrones.csv: Fichero con los patrones a clasificar, disponible en Moodle.
*		iris_etiquetas.csv: Fichero con las etiquetas de los patrones a clasificar, disponible en Moodle.
*		K: Numero de valor entero.
*       SALIDA:
*		tasa_aciertos: Tasa de acierto promediada sobre las iteraciones leave-one-out
*
****************/
clasifica_patrones(Patterns_file, Tags_file, K, Tasa_aciertos):-
    /*read the files*/
    read_matrix(Patterns_file, Matrix),
    read_matrix(Tags_file, [Tags]),

    /*leave-one-out iterations:*/
    length(Matrix, Size),
    Max_index is Size-1,
    findall(
        Acierto, 
        (
            between(0, Max_index, J),
            nth0(J, Matrix, X_test, X_entrenamiento),
            nth0(J, Tags, Y_test, Y_entrenamiento),
            cuenta_acierto(X_entrenamiento, X_test, K, Y_entrenamiento, Y_test, Acierto)
        ),
        Aciertos  
    ),
    sum_list(Aciertos, Num_aciertos),
    Tasa_aciertos is Num_aciertos/Size.

/* 
    Calculates the prediction of X_test using X_entrenamiento with K=K:
        Acierto is 1.0 if the prediction is correct (Prediction==Y_test)
        Acierto is 0.0 otherwise
*/
cuenta_acierto(X_entrenamiento, X_test, K, Y_entrenamiento, Y_test, Acierto):-
    k_vecinos_proximos(X_entrenamiento, Y_entrenamiento, K, [X_test], [Y_predict]),
    (
        (Y_test=Y_predict, Acierto is 1.0,!);
        Acierto is 0.0
    ).

/* predicates for reading matrices from csv files */
read_matrix(File, Matrix):-
    csv_read_file(File, Rows),
    rows_to_matrix(Rows, Matrix).
rows_to_matrix(Rows, Matrix):- 
    maplist(row_to_list, Rows, Matrix).
row_to_list(Row, List):- 
    Row =.. [row|List].

/*************
 * EXERCISE 10: fractal
 *  INPUT: the depth of the fractal
 *  OUTPUT: displays the fractal described in the assignment's figure 
 * 
 *************/

fractal(Depth):-
    new(D, window('Fractal')),
    send(D, size, size(1500, 600)),
    drawLine(D, 50, 500, 1450, 500, Depth),
    send(D, open).

fractal:- fractal(4).

drawLine(D, P1, P2, Q1, Q2, 0):-
    new(Line, line(P1, P2, Q1, Q2, none)),
    send(D, display, Line).

drawLine(D, P1, P2, Q1, Q2, Depth):-
    Depth > 0,
    Next_depth is Depth-1,
    
    Dif1 is (Q1 - P1)/3, 
    Dif2 is (Q2 - P2)/3, 
    
    Dif_ort1 is Dif2, 
    Dif_ort2 is -Dif1,
    
    R1 is P1 + Dif1, 
    R2 is P2 + Dif2,

    T1 is R1 + Dif1, 
    T2 is R2 + Dif2,

    S1 is R1 + (Dif1 + sqrt(3)*Dif_ort1)/2, 
    S2 is R2 + (Dif2 + sqrt(3)*Dif_ort2)/2,
    
    /*draw the lines between the points: P, R, S, T, Q*/
    drawLine(D, P1, P2, R1, R2, Next_depth),
    drawLine(D, R1, R2, S1, S2, Next_depth),
    drawLine(D, S1, S2, T1, T2, Next_depth),
    drawLine(D, T1, T2, Q1, Q2, Next_depth).
