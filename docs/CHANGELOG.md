# Cambios

## Model

- *Interfaz Figure pasa a ser una clase abstracta*
    + Esto es porque Circle, Square, Rectangle y Ellipse mantienen una relacion de "son una" Figure. Ademas hay un estado interno que se comparte entre las clases que no puede ser simulado por una interfaz

    + Los Points en esta interfaz pasan a ser MovablePoints pues posibilitan el traslado de las mismas.

### Ser Movable es una funcionalidad extra
Esto quiere decir que es una interfaz, la cual sera implementada por todas las entidades que vayan a moverse por el canvas

Dentro de esta interfaz vamos a tener movimientos entre los principales puntos cardinales y los secundarios van a ser una combinacion de ambos por lo tanto metodos default dentro de la interfaz

- *Point -> MoveblePoint*
    + Atributos de point visibilidad publica -> privada + Implementacion de getters

### Ser Resizeable es una funcionalidad extra
Se lleva a cabo la creación de la interfaz Resizeable que permitirá a las entidades tener la funcionalidad de cambiar sus propiedades en caso de ser deseado.


### Canvas
- *ArrayList -> HashSet*
    Mayor eficiencia para acceso

## Front

- *PaintPane*
    + Accede a variables del back lo cual es una mala practica, se modifica para que el acceso sea a través de getters
    + Modifican a variables del back lo cual es una mala practica, se modifica para que se haga a través de setters
    + Codigo imperativo: Chequea instancia de figura por figura para moverlas y todas las figuras son movibles y tienen metodos de movimiento
    + Codigo imperativo: El relleno de figuras es imperativo, un cuadrado es un rectangulo y un circulo es una elipse, por lo que hacer todos los casos no es correcto. Unificamos en dos casos: Oval y Rect
    + FigureBelongs: Codigo imperativo, creacion de funciones isContained para saber si un punto esta contenido en una figura, comportamiento requerido por la clase figure


### Posibles extras

- Agregar el moveDiag?  --> implementado por la interfaz Movable como método default. (check)


# Problemas

## Representacion de posicion en el canvas

- Uso de double vs int
    No estamos seguros si usar double en el back para una representacion mas extensible de las clases o usar un integer para una representacion mas fiel a la de la esperada por la aplicacion.
    Decidimos dejarlo en double y en caso de querer reducir los recursos usados, esta la posibilidad de pasar a usar int

