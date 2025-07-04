# Espejar

Copiar la figura por debajo

## Dividir

+ Primero Magnificar

    - Dividir a lo ancho -> Espejar verticalmente (vMirror que seria poner el espejo verticalmente)

    - Dividir a lo alto -> Espejar normalmente (hMirror que seria poner el espejo horizontalmente)

# Filtros

- Crear una clase que encapsule las figuras y filtros (posibles enums)

# Para el CanvasState:

IMPORTANTE: No extender el comportamiento lógico de Figure, sino agregar presentación

IDEA: Hacer que extienda a ArrayList

## _Opcion A_:

```java

public class CanvasState<T extends Figure> extends ArrayList<T> {}

```

- NO
- Porque *CustomizableFigure* deberia extender a *Figure* y en tal caso no podes agregar una *Ellipse* porque *Ellipse* extiende a *Figure* pero *CustomizableFigure* no tiene ninguna relacion de herencia

En el front:

```java

private CanvasState<CustomizeFigure> cs;

// ...

cs.add(...);
// De que?
// No se puede

```

## _Opcion B_:

```java

public class CanvasState<T> extends ArrayList<T> {}

public class CustomizableFigure {
    // ...
    private Figure figure;
    // ...
}

```

En el front:

```java

private CanvasState<CustomizableFigure> cs;

cd.add(new CustomizableFigure(new Figure(event.getX(), event.getY())));

```

## _Opcion C_:


```java

public class CanvasState

```

# Para los filtros

- Como agrupamos todo?

- Estaria bien juntarlos los dos enums (Mirrors y Filters)?

