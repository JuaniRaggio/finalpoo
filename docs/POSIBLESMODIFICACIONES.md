# Equals
    - No esta funcionando correctamente el equals de sobre un Set de Figures, no esta usando
    el de la figura particular

# Copy
    - Tenemos un copy en Point que no esta en una interfaz comun entre Point y Figures

# CheckValidAxe
    - Hacemos metodo que valide axe?
    - Podriamos hacer un metodo que valide positividad y reciba un mensaje de error y lo usamos pasa los casos que necesitemos
    
# Front
*TODO:* Queremos que se dibuje en caso de que el endpoint este a la izquierda del startpoint?

*TODO:* Metodo para el diferencial?

*TODO:* Pensar una solucion para el fillRect, fillOval, etc

*TODOs:*

    x Botones de Filtros
    x Poder seleccionar y modificar color/borde/filtro
    x Copiado de formato
    x Pegado de formato
    x Aplicar Filtros
    x Barra de operaciones

## Funcionamiento!!!!!!:
*IMPORTANTE VER*
- Cuando pones el mouse encima de una figura que esta encimada en otra hace una concatenacion de Strings de todas las figuras que contienen al mouse

## Estilo

- Correccion de magic numbers

## Optimizacion de creacion de CheckBoxes y Botones

Crear una clase que reciba el tipo de Accion que se puede hacer y te retorne el boton que necesitas con el texto y todo ya creado (aprovechar los toString de los enums)


# Para agregar al informe

- List -> TreeSet ??? NO

- Sobre CustomizeFigureBuilder

// This class is exclusively to generate CustomizeFigures with
// custom parameters. The class CustomizeFigure would need to have 
// Too many Constructors so we decide to do this

