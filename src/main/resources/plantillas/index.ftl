<#import "/plantillas/base.ftl" as base>
<@base.pagina logueado=estaLogueado usuario=nombreUsuario permisos=tienePermisos admin=esAdmin>
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
                            <span class="text-warning">
                                <i class="fas fa-calendar-alt"></i> ${articulo.fecha}
                            </span>
                            <span class="text-success ml-5">
                                <i class="fas fa-comments"></i> ${articulo.listaComentarios?size}
                            </span>
                            <#if articulo.listaEtiquetas?size gt 0>
                                <span class="text-primary ml-5">
                                    <i class="fas fa-hashtag"></i>
                                    <#list articulo.listaEtiquetas as etiqueta>
                                        ${etiqueta.etiqueta}
                                    </#list>
                                </span>
                            </#if>
                        </strong>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
</@base.pagina>