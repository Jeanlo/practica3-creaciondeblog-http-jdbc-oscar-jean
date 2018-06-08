<#import "/plantillas/base.ftl" as base>
<@base.pagina logueado=estaLogueado usuario=nombreUsuario permisos=tienePermisos>
<div class="col-12 p-2">
    <div class="row">
        <#list articulos as articulo>
            <div class="col-6 mx-auto p-0">
                <div class="card mx-2 mb-5">
                    <div class="card-body">
                        <h5 class="card-title">${articulo.titulo}</h5>
                        <p class="card-text text-muted m-0 cuerpo-corto">${articulo.cuerpoCorto}</p>
                        <a href="/articulo/${articulo.id}" class="text-danger float-right"><strong>Leer más...</strong></a>
                    </div>
                    <div class="card-footer p-2">
                        <strong class="text-danger m-0">
                            <i class="fas fa-calendar-alt"></i> ${articulo.fecha}
                        </strong>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
</@base.pagina>