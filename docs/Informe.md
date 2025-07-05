# Informe del Trabajo Práctico Especial
**Materia:** 72.33 Programación Orientada a Objetos  
**Fecha:** Julio 2025  
**Grupo 16**  
### Integrantes
- **García Vautrin Raggio, Juan Ignacio** - 63319
- **Naso Rodríguez, Gerónimo** - 64177
- **Park, Victoria** - 64498
- **Choe, Ivonne Samanta** - 64880

---

## 1. Introducción

El presente informe describe el desarrollo e implementación de una aplicación de dibujo de figuras geométricas en Java utilizando JavaFX como interfaz gráfica. El objetivo principal del trabajo fue aplicar los conceptos fundamentales del paradigma orientado a objetos para extender y mejorar una base de código provista por la cátedra.  
La aplicación permite al usuario dibujar, mover, borrar y modificar distintas figuras aplicando efectos visuales, operaciones geométricas y distintos estilos de formato. A su vez, se buscó mejorar la estructura del proyecto corrigiendo malas prácticas, modularizando el código y asegurando una separación clara entre la lógica del backend y la vista del frontend.

---

## 2. Cambios realizados sobre la implementación original

A continuación se describen los cambios efectuados sobre el código provisto:

- **Refactorización de clases:**
  
  **Backend:**
  - Reemplazo de la interfaz `Figure` por una clase abstracta.
  - Reemplazo de las clases `Square` y `Circle` ahora extienden de `Rectangle` y `Ellipse` respectivamente para poder reutilizar su comportamiento.
  - Adición de la interfaz `Movable` la cual permite a las figuras que la implementen la posibilidad de desplazarse.
  - Adición de la clase `MovablePoint` la cual extiende a `Point` agregando la funcionalidad de la interfaz `Movable`.
  - Adición de la interface `Resizable` la cual permite que las figuras que las implementen puedan cambiar sus dimensiones.

- **Corrección de malas prácticas:**

  **Backend**
  - Reemplazo de magic values por constantes.
  - Remoción los atributos public y protected, reemplazándolos por private.
  
  **Frontend**
  - Acceso y modificacion de variables del backend directamente, se reemplazaron por metodos getters y setters.
  - En el metodo`redrawCanvas()` y para el movimiento de figuras verificaba cada tipo de figura por separado. Con la introducción de herencia, se simplificó la lógica al verificar únicamente si la figura es una instancia de `Rectangle` o `Ellipse`.
  - El metodo `figureBelongs()` se removio pues es comportamiento de la clase `Point`.

---

## 3. Funcionalidades implementadas

Se agregaron las siguientes funcionalidades al proyecto base. Se implementaron de la siguiente manera:

- **Efectos visuales (Oscurecer, Aclarar, Espejos):**
    - Implementados como un `Enum Effects`.
    - Se aplican en `CustomizeFigure#format()` al momento del dibujo.

- **Operaciones geométricas (Dividir, Multiplicar, Transferir):**
    - Definidas mediante un `enum Operations` con polimorfismo.
    - El método `execute(...)` devuelve nuevas figuras o modifica la existente.

- **Formato (Relleno, Borde, Copiar/Pegar formato):**
    - Uso de un objeto `Format` interno en `CustomizeFigure`.
    - Implementación de un portapapeles simple en `PaintPane` para la funcionalidad copy format.

---

## 4. Problemas encontrados durante el desarrollo

Durante el desarrollo surgieron los siguientes desafíos:

- Para respetar adecuadamente el paradigma invertimos una gran cantidad de tiempo intentando reutilizar comportamiento entre clases, lo cual en algunas situaciones era complejo.
- Al momento de desarrollar la clase `Point` nos surgió el interrogante sobre si utilizar int o doubles para la representacion de los mismos, optamos por utilizar doubles, para que pueda ser reutilizado por otros frontends que requieran mayor precision y no tan limitante.

---

## 5. Conclusión 

A lo largo del proyecto, pudimos aplicar los contenidos estudiados en la materia y adquirimos experiencia práctica en el desarrollo de interfaces gráficas, tanto mediante JavaFX como en el diseño de interfaces visuales de manera general.
