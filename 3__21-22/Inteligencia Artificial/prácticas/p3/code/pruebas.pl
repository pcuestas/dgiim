write_log(S) :- 
    open('error_logs.txt', append, Out), 
    write(Out, S), 
    write(Out, '\n'), 
    close(Out).

is_empty(List):- not(member(_,List)).

/*print an error*/
err(S):- print(S), write_log(S).


slice([X|_], 1, 1, [X]):-!.
slice([_], 1, K, _):-
    K >= 1, err('ERROR 3.2 Indices.'),!,fail.
slice([X|L], 1, K, [X|S]):-
    K1 is K-1,
    slice(L,1, K1, S).
slice([_|L], I, K, S):-
    K1 is K-1,
    I1 is I-1,
    slice(L, I1, K1, S).


/*
producto_kronecker([[X]], [[Y|_]], _):-
    (X < 0; Y < 0),
    print('ERROR 7.1 Elemento menor que cero.'),throw(false).

producto_kronecker(_, [[]], [[[[]]]]):-!.

producto_kronecker([[X] ], [[Y|F1]], [[[[Z|F2]]]]):-
    X >= 0, Y >= 0, Z is X*Y,
    producto_kronecker([[X]],[F1],[[[F2]]]).

producto_kronecker([[X]],[F1|R1],[[[F2|R2]]]):-
    producto_kronecker([[X]], [F1], [[[F2]]]),
    producto_kronecker([[X]],  R1,   [[R2]]).

producto_kronecker([[X|F0]],B,[[M|R]]):-
    producto_kronecker([[X]], B,[[M]]),
    producto_kronecker([F0] , B, [R]).

producto_kronecker([F0|R1], B, [M|R2]):-
    producto_kronecker([F0], B, [M]),
    producto_kronecker(R1, B, R2).
*/

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
norma_euclidea([], 0):-!.
norma_euclidea([X0|X], N):-
    norma_euclidea(X, NX),
    N is sqrt(NX*NX + X0*X0).

distancia_euclidea(X1, X2, D) :- 
    maplist(sub, X1, X2, Y), /* Y = X1 - X2*/
    norma_euclidea(Y, D).

sub(X, Y, Z):- Z is X-Y.

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

calcular_distancias_matriz_vector([], _Q, []):-!.
calcular_distancias_matriz_vector([P|X_entrenamiento], Q, [D|Distancias]):-
    distancia_euclidea(P, Q, D),
    calcular_distancias_matriz_vector(X_entrenamiento, Q, Distancias).

calcular_distancias(_X_entrenamiento, [], []):-!.
calcular_distancias(X_entrenamiento, [P|X_test], [F|Matriz_resultados]) :- 
    calcular_distancias_matriz_vector(X_entrenamiento, P, F),
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
predecir_etiquetas(Y_entrenamiento, K, Matriz_resultados, Y_test):-
    findall( Tag,
        (
            member(Vec_distancias, Matriz_resultados),
            predecir_etiqueta(Y_entrenamiento, K, Vec_distancias, Tag)    
        ),
        Y_test
    ).

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
predecir_etiqueta(Y_entrenamiento, K, Vec_distancias, Etiqueta) :- 
    calcular_K_etiquetas_mas_relevantes(Y_entrenamiento, K, Vec_distancias, K_etiquetas),
    calcular_etiqueta_mas_relevante(K_etiquetas, Etiqueta).

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

calcular_etiqueta_max([], [0,_]):-!.
calcular_etiqueta_max([[N,E]|Contadores], [Max,Etiqueta]):-
    calcular_etiqueta_max(Contadores,[K,F]),
    (
        (K =< N, Max=N, Etiqueta=E,!);
        (Max=K, Etiqueta=F)
    ).

calcular_contadores([], []):-!.
calcular_contadores([E|L], Contadores):-
    calcular_contadores(L, ContadoresL),
    incrementar_contador(E, ContadoresL, Contadores).

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
k_vecinos_proximos(X_entrenamiento, Y_entrenamiento, K, X_test, Y_test) :- 
    calcular_distancias(X_entrenamiento, X_test, Matriz_resultados),
    predecir_etiquetas(Y_entrenamiento, K, Matriz_resultados, Y_test).

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
    read_matrix(Patterns_file, Matrix),
    read_matrix(Tags_file, [Tags]),
    length(Matrix, Size),
    Max_index is Size-1,
    findall(
        Acierto, 
        (
            between(0, Max_index, J),
            nth0(J, Matrix, X_test, X_entrenamiento),
            nth0(J, Tags, Y_test, Y_entrenamiento),
            cuenta_acierto(X_entrenamiento, [X_test], K, Y_entrenamiento, Y_test, Acierto)
        ),
        Aciertos  
    ),
    sum_list(Aciertos, Num_aciertos),
    Tasa_aciertos is Num_aciertos/Size.

cuenta_acierto(X_entrenamiento, X_test, K, Y_entrenamiento, Y_test, Acierto):-
    calcular_distancias(X_entrenamiento, X_test, Matriz_resultados),
    predecir_etiquetas(Y_entrenamiento, K, Matriz_resultados, [Y_predict]),
    (
        (Y_test=Y_predict, Acierto is 1.0,!);
        Acierto is 0.0
    ).
    
read_matrix(File, Matrix):-
    csv_read_file(File, Rows),
    rows_to_matrix(Rows, Matrix).
rows_to_matrix(Rows, Matrix):- 
    maplist(row_to_list, Rows, Matrix).
row_to_list(Row, List):- 
    Row =.. [row|List].


fractal(Depth) :-
    new(D, window('Fractal')),
    send(D, size, size(800, 600)),
    drawLine(D, 100, 450, 700, 450, Depth),
    send(D, open).

drawLine(D, P1, P2, Q1, Q2, 0):-
    new(Line, line(P1, P2, Q1, Q2, none)),
    send(D, display, Line).

drawLine(D, P1, P2, Q1, Q2, Depth):-
    Depth > 0,
    Next_depth is Depth-1,
    Dif1 is (Q1-P1)/3, Dif2 is (Q2-P2)/3, /*Dif vector*/
    Dif_ort1 is Dif2, Dif_ort2 is -Dif1, /*orthogonal to Dif*/
    R1 is P1+Dif1, R2 is P2+Dif2,
    T1 is P1+2*Dif1, T2 is P2+2*Dif2,
    S1 is R1+Dif1/2+sin(pi/3)*Dif_ort1, S2 is R2+Dif2/2+sin(pi/3)*Dif_ort2,
    drawLine(D, P1, P2, R1, R2, Next_depth),
    drawLine(D, R1, R2, S1, S2, Next_depth),
    drawLine(D, S1, S2, T1, T2, Next_depth),
    drawLine(D, T1, T2, Q1, Q2, Next_depth).

/*
fractal :-
    new(D, window('Fractal')),
    send(D, size, size(800, 600)),
    drawTree(D, 400, 500, -90, 8),
    send(D, open).

drawTree(_D, _X, _Y, _Angle, 0).

drawTree(D, X1, Y1, Angle, Depth) :-
    X2 is X1 + cos(Angle * pi / 180.0) * Depth * 10.0,
    Y2 is Y1 + sin(Angle * pi / 180.0) * Depth * 10.0,
    new(Line, line(X1, Y1, X2, Y2, none)),
    send(D, display, Line),
    A1 is Angle - 30,
    A2 is Angle + 30,
    From is Depth - 1,
    drawTree(D, X2, Y2, A1, From),
    drawTree(D, X2, Y2, A2, From).

*/