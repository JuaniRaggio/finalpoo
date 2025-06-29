# Diagrama UML del Proyecto Paint

Este archivo contiene el diagrama UML del proyecto Paint desarrollado en Java con JavaFX.

## Archivos

- `UML_Diagram.puml`: Archivo PlantUML que contiene el diagrama de clases del proyecto
- `README_UML.md`: Este archivo de documentación

## Cómo visualizar el diagrama UML

### Opción 1: PlantUML Online
1. Ve a [PlantUML Online Server](http://www.plantuml.com/plantuml/uml/)
2. Copia y pega el contenido del archivo `UML_Diagram.puml`
3. El diagrama se generará automáticamente

### Opción 2: Extensiones de IDE
- **VS Code**: Instala la extensión "PlantUML"
- **IntelliJ IDEA**: Instala el plugin "PlantUML integration"
- **Eclipse**: Instala el plugin "PlantUML"

### Opción 3: PlantUML Desktop
1. Descarga PlantUML desde [plantuml.com](https://plantuml.com/download)
2. Instala Java si no lo tienes
3. Ejecuta: `java -jar plantuml.jar UML_Diagram.puml`

## Estructura del Proyecto

### Backend
- **CanvasState**: Gestiona la colección de figuras
- **Figure**: Interfaz que define el contrato para todas las figuras geométricas

### Modelo (Backend)
- **Point**: Representa un punto en el plano cartesiano
- **Circle**: Implementa la interfaz Figure para círculos
- **Ellipse**: Implementa la interfaz Figure para elipses
- **Rectangle**: Implementa la interfaz Figure para rectángulos
- **Square**: Implementa la interfaz Figure para cuadrados

### Frontend
- **AppLauncher**: Punto de entrada de la aplicación JavaFX
- **MainFrame**: Contenedor principal que organiza los componentes
- **PaintPane**: Panel principal de dibujo con canvas y herramientas
- **StatusPane**: Barra de estado que muestra información
- **AppMenuBar**: Barra de menú con opciones de archivo y ayuda

## Relaciones Principales

1. **Herencia**: Todas las figuras (Circle, Ellipse, Rectangle, Square) implementan la interfaz Figure
2. **Composición**: CanvasState contiene una colección de objetos Figure
3. **Dependencia**: PaintPane crea y manipula instancias de las figuras
4. **Herencia JavaFX**: Las clases del frontend heredan de componentes JavaFX

## Patrones de Diseño

- **MVC (Model-View-Controller)**: Separación entre modelo (figuras), vista (frontend) y controlador (PaintPane)
- **Strategy Pattern**: Diferentes tipos de figuras implementan la misma interfaz
- **Observer Pattern**: StatusPane observa cambios en el estado del canvas

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal
- **JavaFX**: Framework para la interfaz gráfica
- **PlantUML**: Herramienta para generar diagramas UML 
