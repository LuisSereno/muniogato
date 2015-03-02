<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

<ul id="listaErrores">

</ul>
<div style="text-align: left; margin-left: 10%;">
 <form style="height: 60%;"  id="registroUsuario">
	<fieldset class="cuadroFormularioRegistro"><legend style="caption-side: left;">Regístrate</legend><br>
		<div style="text-align: justify;">
		<div style="text-align: left;">
		<label>Todos los campos son obligatorios, disculpe las molestias que esto pueda ocasionarle.</label>
		<br>
		<br>
		<br>
		<div id="camposIzquierda" style="display: inline-block; width: 40%;">
			<div>
				<div style="display: inline-block; width: 30%;">
					<label>Nombre:</label>
				</div>
				<div style="display: inline-block; width: 30%;">
					<input id="nombre" name="nombre" value="Escriba aquí su nombre">
				</div>
			</div>
			<br>
			<br>
			<div>
				<div style="display: inline-block; width: 30%;">
					<label>Primer Apellido:</label> 
				</div>
				<div style="display: inline-block; width: 30%;">
					<input id="primerApellido" name="primerApellido" value="Escriba aquí su primer apellido">
				</div>
			</div>	
			<br>
			<br>
			<div>
				<div style="display: inline-block; width: 30%;">
					<label>DNI:</label>
				</div>
				<div style="display: inline-block; width: 30%;">
					<input id ="dni" name="dni" value="Escriba aquí su DNI">
				</div>
			</div>
			<br>
			<br>
			<div>
				<div style="display: inline-block; width: 30%;">
					<label>Calle:</label>
				</div>
				<div style="display: inline-block; width: 30%;">
					<input id ="calle" name="calle" value="Escriba aquí su calle">
				</div>
			</div>
			<br>
			<br>
			<div>
				<div style="display: inline-block; width: 30%;">
					<label>Ciudad:</label>
				</div>
				<div style="display: inline-block; width: 30%;">
					<input id ="ciudad" name="ciudad" value="Escriba aquí la ciudad de su domicilio">
				</div>
			</div>
			<br>
			<br>
			<div>
				<div style="display: inline-block; width: 30%;">
					<label>Usuario</label>
				</div>
				<div style="display: inline-block; width: 30%;">
					<input id ="usuario" name="usuario" value="Escriba aquí su usuario">
				</div>
			</div>
			<br>
			<br>
			<div>
				<div style="display: inline-block; width: 30%;">
					<label>Email:</label>
				</div>
				<div style="display: inline-block; width: 30%;">
					<input id ="email" name="email" value="Escriba aquí su email">
				</div>
			</div>

		</div>
		<div id="camposDerecha" style="display: inline-block; width: 50%;">
			<div>
				<div style="display: inline-block; width: 30%;">
					<label>Teléfono:</label>
				</div>
				<div style="display: inline-block; width: 30%;">
					<input id ="telefono" name="telefono" value="Escriba aquí su teléfono">
				</div>
				
			</div>
			<br>
			<br>
			<div>
				<div style="display: inline-block; width: 30%;">
					<label>Segundo Apellido:</label>
				</div>
				<div style="display: inline-block; width: 30%;">
					<input id ="segundoApellido" name="segundoApellido" value="Escriba aquí su segundo apellido">
				</div>
			</div>
			<br>
			<br>
			<div>
				<div style="display: inline-block; width: 30%;">
					<label>País:</label>
				</div>
				<div style="display: inline-block; width: 30%;">
					<input id ="pais" name="pais" value="Escriba aquí el país de su domicilio">
				</div>
			</div>
			<br>
			<br>
			<div>
				<div style="display: inline-block; width: 30%;">
					<label>Número:</label>
				</div>
				<div style="display: inline-block; width: 30%;">
					<input id ="numero" name="numero" value="Escriba aquí el número de su casa">
				</div>
			</div>
			<br>
			<br>
			<div>
				<div style="display: inline-block; width: 30%;">
					<label>Provincia:</label>
				</div>
				<div style="display: inline-block; width: 30%;">
					<input id ="provincia"	name="provincia" value="Escriba aquí la provincia de su domicilio">
				</div>
			</div>
			<br>
			<br>
			<div>
				<div style="display: inline-block; width: 30%;">
					<label>Contraseña:</label>
				</div>
				<div style="display: inline-block; width: 30%;">
					<input id ="contrasena" name="contrasena" value="Escriba aquí su contraseña" type="password">
				</div>
			</div>
			<br>
			<br>
			<div style="margin-left: 40%">
				<input value="Registrarse" type="button" class="button orange" onclick="introducirUsuario();" class="button">
				<input type="reset" class="button orange" value="Limpiar">
			</div>
		</div>
	</div>
	</div>
	</fieldset>
 </form> 
</div>


