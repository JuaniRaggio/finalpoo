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
    - Detallar....

- **Corrección de malas prácticas:**
    - Reemplazo de valores mágicos por constantes.

- **PONER MAS...No se me ocurreeee**

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
    - Implementación de un portapapeles simple en `PaintPane`. (si no???--> CHECK)

---

## 4. Problemas encontrados durante el desarrollo

Durante el desarrollo surgieron los siguientes desafíos:

- ** COMPLETAR **

---

## 5. Conclusión 



---
