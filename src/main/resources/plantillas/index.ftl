<#import "/plantillas/base.ftl" as base>
<@base.pagina>
 <#list articulos as articulo>
     ${articulo.titulo}
 </#list>
</@base.pagina>