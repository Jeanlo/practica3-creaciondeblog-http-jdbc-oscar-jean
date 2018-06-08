<#import "/plantillas/base.ftl" as base>
<@base.pagina logueado=estaLogueado usuario=nombreUsuario permisos=tienePermisos admin=esAdmin>
<div class="col-lg-8 col-md-10 col-sm-12 mx-auto">
    <div class="row">
        <div class="col-12 mt-2 bg-light px-4 rounded-0 login">
            <div class="row">
                <form class="col-11 py-5" method="post" action="/articulo/eliminar/${articulo.id}">
                    <div class="panel px-2 py-3 bg-white">
                        ¿Está seguro de eliminar el artículo <strong>${articulo.titulo}</strong>?
                    </div>
                    <button class="btn btn-outline-dark btn-block my-3" type="submit">
                        ELIMINAR ARTÍCULO
                    </button>
                </form>
                <h5 class="col-1 pt-3">
                    <strong class="upbottom-letters">ARTÍCULO 記事</strong>
                </h5>
            </div>
        </div>
    </div>
</div>
</@base.pagina>