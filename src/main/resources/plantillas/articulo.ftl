<#import "/plantillas/base.ftl" as base>
<@base.pagina logueado=estaLogueado usuario=nombreUsuario>
<div class="col-12 p-2">
    <div class="row">
        <div class="card col-10 mx-auto p-0">
            <div class="card-body">
                <h5 class="card-title">
                    ${articulo.titulo}
                    <strong class="text-danger m-0 float-right">
                        <i class="fas fa-calendar-alt"></i> ${articulo.fecha}
                    </strong>
                </h5>
                <p class="card-text text-muted m-0">${articulo.cuerpo}</p>
            </div>
            <div class="card-footer p-2">
                COMENTARIOS ETIQUETAS
            </div>
        </div>
    </div>
</div>
</@base.pagina>