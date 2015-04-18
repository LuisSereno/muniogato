<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<meta http-equiv="content-type" content="text/html; charset=UTF-8">  

<div id="imagenAnonima" style="display:block;">
	<img class="imagenLateralRegistrado" src="imagenes/general/usuarioNoRegistrado.png" alt="">
</div>
<div id="imagenRegistrado"  style="display:none;">
	<img class="imagenLateralRegistrado" src="imagenes/general/usuarioRegistrado.png" alt="">
</div>
<br>
<div id="spanTextNombre">
	<span style="font-family: Calibri;"></span>
</div>
<br>
<input class="informacionBannerLateral" name="nombreUsuario" onfocus="if (this.value=='Inserte su Usuario') this.value='';" id="nombreUsuario" value="Inserte su Usuario"><br>
<br>
<input class="informacionBannerLateral" name="contrasenaLateral" onfocus="if (this.value=='Inserte su Contraseña') this.value='';" id="contrasenaLateral" value="Inserte su Contraseña" type="password"><br>
<br>
<div id="botonIniciarSesion" style="display:block;">
	<button value="Iniciar" class="button orange" name="iniciarSesion"  onclick="iniciarSesion();">Iniciar</button><br>
</div>
<div id="botonDesconectar" style="display:none;">
	<button value="desconectar" class="button orange" name="desconectar" id="desconectar" onclick="salirSesion();">Salir</button><br>
</div>
<br>
<div  class="informacionBannerLateral" style="text-align: left;"><small>¿<span style="font-family: Calibri;">Todavía no se ha registrado</span>?
 <button style="background:transparent;"><a	name="Pulse Aquí­" onclick="registrarUsuario()" style="color: #0000B8">Pulse Aquí</a></button></small><br>
</div>