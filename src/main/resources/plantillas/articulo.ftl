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
                <form class="col-11 py-5" method="post" action="/articulo/${articulo.id}/comentar">
                    <div class="panel px-2 py-3 bg-white">
                        <textarea name="comentario" class="form-control rounded-0"></textarea>
                    </div>
                    <button class="btn btn-outline-dark btn-block my-3" type="submit">
                        COMENTAR
                    </button>
                </form>
                <h5 class="col-1 pt-3">
                    <strong class="upbottom-letters">COMENTARIO</strong>
                </h5>

                COMENTARIOS ETIQUETAS
            </div>
        </div>
    </div>
</div>
</@base.pagina>