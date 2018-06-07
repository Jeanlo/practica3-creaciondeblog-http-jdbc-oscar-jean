# Práctica 3 - HTTP y JDBC - Creación de blog

![PUCMMM-logo](https://i.imgur.com/9eEIci9.png)

Tercera práctica realizada para la asignatura **Programación Web (ISC-415)** perteneciente a la carrera **Ingeniería de Sistemas y Computación** de la **Pontificia Universidad Católica Madre y Maestra (PUCMM)** en el ciclo **Mayo-Agosto 2018**.

**Realizado por:**

**JEAN LOUIS TEJEDA GARCÍA**   MAT. 2013-1459

**OSCAR DIONISIO NÚÑEZ SIRI**  MAT. 2014-0056

## Objetivo general

Crear una aplicación web utilizando SparkJava que utilice persistencia en la base de datos gracias a JDBC y permita ejecutar las tareas especificadas en la sección [Tareas requeridas](#tareas-requeridas).

## Tecnologías requeridas

- Java SE
- Gradle
- SparkJava
- FreeMarker
- JDBC

## Otras tecnologías utilizadas

- Bootstrap 4
- Font Awesome 5
- H2

## Modelo de datos
Para esta aplicación es requerido utilizar una colección estática de Estudiantes, para lo cual es necesaria la construcción de la clase Estudiante como parte del modelo de datos, y aquí se muestra su estructura:
```java
public class Etiqueta { 
  private long id;
  private String etiqueta;
}

public class Usuario { 
  private long id;
  private String username;
  private String password;
  private boolean adminstrator;
  private boolean autor;
}

public class Comentario { 
  private long id;
  private String comentario;
  private Usuario autor;
  private Articulo articulo;
}

public class Articulo { 
  private long id;
  private String titulo;
  private String cuerpo;
  private Usuario autor;
  private Date fecha;
  private ArrayList<Comentario> listaComentarios;
  private ArrayList<Etiqueta> listaEtiquetas;
}
```

## Tareas requeridas

