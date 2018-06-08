<#import "/plantillas/base.ftl" as base>
<@base.pagina logueado=estaLogueado usuario=nombreUsuario fondo="404-fondo.png" permisos=tienePermisos>
<div class="col-12 no-encontrado py-5 px-1">
    <h5 class="upbottom-letters text-white text-shadow float-right">
        NO SE ENCONTRO
        <br>
        EL RECURSO QUE QUERIAS
        <br>
        VE A <a href="/" class="text-white"><strong>INICIO</strong></a>
    </h5>
    <h5 class="upbottom-letters text-white text-shadow float-left">
        ESTADO 404
    </h5>
</div>
</@base.pagina>