# Cambios

## Model

- *Interfaz Figure pasa a ser una clase abstracta*
    + Esto es porque Circle, Square, Rectangle y Ellipse mantienen una relacion de "son una" Figure. Ademas hay un estado interno que se comparte entre las clases que no puede ser simulado por una interfaz

    + Los Points en esta interfaz pasan a ser 

### Ser Movable es una funcionalidad extra
Esto quiere decir que es una interfaz, la cual sera implementada por todas las entidades que vayan a moverse por el canvas

Dentro de esta interfaz vamos a tener movimientos entre los principales puntos cardinales y los secundarios van a ser una combinacion de ambos por lo tanto metodos default dentro de la interfaz

- *Point -> MoveblePoint*
    + Atributos de point visibilidad publica -> privada + Implementacion de getters

- 

## Front

- *PaintPane*
    + Accede a variables del back lo cual es una mala practica, se modifica para que el acceso sea atravez de getters
    + Modifican a variables del back lo cual es una mala practica, se modifica para que se haga atravez de setters


### Posibles extras

- Agregar el moveDiag?


