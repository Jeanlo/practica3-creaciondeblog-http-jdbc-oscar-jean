<#import "/plantillas/base.ftl" as base>
<@base.pagina>
   <div class="wrapper loginMAE">
       <form class="form-signin" method="post" action="/loginDatosEntrados">
           <h2 class="text-center">Ingresa a la página MAE: </h2>
           <div style="clear: right">
               <label for="user"><strong>Nombre de usuario:</strong> </label>
               <br>
               <input id = "user" type="text" class="form-control" name="username" placeholder="Ingresa tu usuario" required="" autofocus="" />
           </div>
           <div style="clear: right">
               <br>
               <label for="password"><strong>Contraseña: </strong></label>
               <br>
               <input id = password" type="password" class="form-control" name="password" placeholder="Ingresa tu contraseña" required=""/>
           </div>
           <br>
           <button class="btn btn-lg btn-primary btn-block" type="submit" action="/loginDatosEntrados">Ingresa a MAE</button>
           <br>
       </form>
       <h4>No te has registrado? - Clickea el botón <strong>"Registrar"</strong></h4>
       <br>
       <a href="/registro"><button class="btn btn-lg btn-danger btn-block" type="submit">Registrar</button></a>
   </div>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
</@base.pagina>