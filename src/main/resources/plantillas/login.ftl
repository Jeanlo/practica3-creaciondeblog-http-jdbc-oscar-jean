<#import "/plantillas/base.ftl" as base>
<@base.pagina>
<div class="col-lg-8 col-md-10 col-sm-12 mx-auto">
    <div class="row">
        <div class="col-lg-6 col-md-6 col-sm-10 mt-2 bg-light px-4 rounded-0 login">
            <div class="row">
                <form class="col-11 py-5" method="post" action="/login">
                    <div class="panel px-2 py-3 bg-white">
                        <label for="user"><strong>Nombre de usuario</strong> </label>
                        <input type="text" class="form-control rounded-0" name="username" placeholder="usuario"
                               required=""
                               autofocus=""/>
                        <br>
                        <label for="password"><strong>Contraseña</strong></label>
                        <input type="password" class="form-control rounded-0" name="password" placeholder="contraseña"
                               required=""/>
                    </div>
                    <button class="btn btn-outline-dark btn-block my-3" type="submit">
                        ACCEDER
                    </button>
                </form>
                <h5 class="col-1 pt-3">
                    <strong class="upbottom-letters">LOGIN ログイン</strong>
                </h5>
            </div>
        </div>
        <div class="col-lg-6 col-md-6 col-sm-10 mt-2 bg-light px-4 rounded-0 login">
            <div class="row">
                <form class="col-11 py-5" method="post" action="/registrar">
                    <div class="panel px-2 py-3 bg-white">
                        <label for="user"><strong>Nombre de usuario</strong> </label>
                        <input type="text" class="form-control rounded-0" name="username" placeholder="usuario"
                               required=""
                               autofocus=""/>
                        <br>
                        <label for="password"><strong>Contraseña</strong></label>
                        <input type="password" class="form-control rounded-0" name="password" placeholder="contraseña"
                               required=""/>
                    </div>
                    <button class="btn btn-outline-dark btn-block my-3" type="submit">
                        REGISTRARSE
                    </button>
                </form>
                <h5 class="col-1 pt-3">
                    <strong class="upbottom-letters">REGISTRO レジスター</strong>
                </h5>
            </div>
        </div>
    </div>
</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
</@base.pagina>