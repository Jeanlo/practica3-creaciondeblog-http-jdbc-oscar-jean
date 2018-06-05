<#import "/plantillas/base.ftl" as base>
<@base.pagina>
 <#list articulos as articulo>
     <div class="card col-4">
         <div class="card-body">
             <h5 class="card-title">${articulo.titulo}</h5>
             ${articulo.cuerpo}
         </div>
         <div class="card-footer">
             <p class="fecha">${articulo.fecha}</p>
         </div>
     </div>
 </#list>
</@base.pagina>