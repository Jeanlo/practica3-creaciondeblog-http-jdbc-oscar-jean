<#macro pagina logueado=false usuario="" fondo="fondo.png" permisos=false admin=false>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Manga Anime Empire</title>
    <link rel="stylesheet" href="/estilos/estilo.css">

    <!-- Font Awesome 5 -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">

    <!-- Bootstrap 4 CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">

    <!-- Bootstrap 4 JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
</head>
<body style="background: url('/imagenes/${fondo}') no-repeat fixed; background-size: cover">
<div class="container-fluid">
    <div class="row">
        <div class="col">
            <div class="row">
                <div class="col-12 p-0">
                    <nav class="navbar navbar-expand-lg navbar-light bg-white p-0 boxed-shadow">
                        <a class="navbar-brand" href="/"><img src="/imagenes/logo.png" width="180"></a>
                        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            <#if logueado>
                                <ul class="navbar-nav mr-auto">
                                    <#if permisos>
                                        <li class="nav-item">
                                            <a class="btn btn-link text-dark" href="/articulo/crear">
                                                <i class="fas fa-newspaper"></i> Crear artículo
                                            </a>
                                        </li>
                                    </#if>
                                    <#if admin>
                                        <li class="nav-item">
                                            <a class="btn btn-link text-dark" href="/registrar">
                                                <i class="fas fa-user-plus"></i> Registrar usuario
                                            </a>
                                        </li>
                                    </#if>
                                </ul>
                                <img class="rounded-circle boxed-shadow" src="/imagenes/perfil.jpg" alt="foto de usuario" width="64px">
                                <a class="mx-2 nombre-usuario px-1"><strong>${usuario}</strong></a>
                                <a class="btn btn-dark mr-2" href="/salir">
                                    <i class="fas fa-sign-out-alt"></i> Salir
                                </a>
                            </#if>
                        </div>
                    </nav>
                </div>
            </div>
            <#nested>
        </div>
    </div>
</div>
</body>
</html>
</#macro>