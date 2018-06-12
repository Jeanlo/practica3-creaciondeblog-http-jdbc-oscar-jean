# Práctica 3 - HTTP y JDBC - Creación de blog

![PUCMMM-logo](https://i.imgur.com/9eEIci9.png)

Tercera práctica realizada para la asignatura **Programación Web (ISC-415)** perteneciente a la carrera **Ingeniería de Sistemas y Computación** de la **Pontificia Universidad Católica Madre y Maestra (PUCMM)** en el ciclo **Mayo-Agosto 2018**.

## Realizado por

**JEAN LOUIS TEJEDA GARCÍA** -  MAT. 2013-1459

**OSCAR DIONISIO NÚÑEZ SIRI** -  MAT. 2014-0056

## Objetivo general

Crear una aplicación web utilizando SparkJava que utilice persistencia en la base de datos gracias a JDBC y permita ejecutar las tareas especificadas en la sección [Tareas requeridas](#tareas-requeridas).

## Vídeo de demostración del proyecto

https://youtu.be/L-UvkVRljwc

## Tecnologías requeridas

- Java SE
- Gradle
- SparkJava
- FreeMarker
- JDBC
- Bootstrap 4

## Otras tecnologías utilizadas

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

- Modelar clases.
- Autentificar usuarios.
- Permitir operaciones sobre artículos:
  - Crear (si es autor o administrador).
  - Eliminar (si es el autor o administrador).
  - Ver (si está autentificado).
  - Listar (si está autentificado).
  - Modificar (si es el autor o es administrador).
- Agregar etiquetas a los artículos.
- Agregar comentarios a los artículos.
- Registrar usuarios (si es administrador).
- Generar usuario administrador, por defecto.
- Validar sesión de usuario.
- Mantener la sesión durante 1 semana, cifrada.
