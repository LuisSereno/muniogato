<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<br>
<br>
<div class="paginaFormulario">
	<div id="parteArribaContacto">
		<div id="googleMap">
			<div class="mapaGrande">
				<iframe marginheight="0" marginwidth="0"
					src="https://maps.google.es/?ie=UTF8&amp;t=m&amp;layer=c&amp;cbll=38.706273,-5.322355&amp;panoid=YC9265GQqToR3hG7TwKPkQ&amp;cbp=13,265.48,,0,-2.36&amp;source=embed&amp;ll=38.705377,-5.321835&amp;spn=0.001649,0.003755&amp;z=18&amp;output=svembed"
					frameborder="0" height="300" scrolling="no" width="500"></iframe>
				<br>
			</div>
			<div class="mapaMediano">
				<iframe marginheight="0" marginwidth="0"
					src="https://maps.google.es/?ie=UTF8&amp;t=m&amp;layer=c&amp;cbll=38.706273,-5.322355&amp;panoid=YC9265GQqToR3hG7TwKPkQ&amp;cbp=13,265.48,,0,-2.36&amp;source=embed&amp;ll=38.705377,-5.321835&amp;spn=0.001649,0.003755&amp;z=18&amp;output=svembed"
					frameborder="0" height="170" scrolling="no" width="300"></iframe>
				<br>
			</div>
			<small><a
				href="https://maps.google.es/?ie=UTF8&amp;t=m&amp;layer=c&amp;cbll=38.706273,-5.322355&amp;panoid=YC9265GQqToR3hG7TwKPkQ&amp;cbp=13,265.48,,0,-2.36&amp;source=embed&amp;ll=38.705377,-5.321835&amp;spn=0.001649,0.003755&amp;z=18"
				style="color: rgb(0, 0, 255); text-align: left;">Ver mapa</a></small>
		</div>
		<div id="datosHotel"
			style="display: inline-block; width: 40%; margin-left: 5%; font-size: 120%">
			<label><b><u>Dirección</u>: Carretera Ex-104 km: 104
					Cabeza del Buey Badajoz(Extremadura)</b></label> <br> <br> <label><b><u>CP</u>:06600</b></label>
			<br> <br> <label><b><u>Teléfono</u>: 649067253</b></label>
			<br> <br> <label><b><u>Email</u>:
					hrgranmaestre@gmail.com</b></label>
		</div>
	</div>
	<br> <br>
	<div id="parteAbajoContacto"
		style="clear: both; width: 90%; height: 20%;">
		<form style="margin-left: 5%;" method="post"
			action="/formularioContacto" name="FormularioContacto"
			onsubmit="return comprobarFormularioEnvioCorreo();">
			<fieldset>
				<legend style="caption-side: left;">Contacta con nosotros</legend>
				<div id="camposRellenarFormulario">
					<div style="text-align: left;">
						<div>
							<div class="camposFormulario">
								<label>Email:</label> <input onfocus="" name="email" id="email"
									value="Por favor, introduce tu email">
							</div>
						</div>
						<br> <br>
						<div>
							<div class="camposFormulario">
								<label>Nombre:</label> <input
									value="Por favor, introduzca su nombre" onfocus=""
									name="nombre" id="nombre">
							</div>
						</div>
						<br> <br>
						<div>
							<div class="camposFormulario">
								<label>Asunto:</label> <input onfocus="" name="asunto"
									id="asunto" value="Por favor, introduzca el asunto del mensaje">
							</div>
						</div>
						<br> <br>
						<div>
							<div class="camposFormulario">
								<label>Mensaje:</label>
								<textarea cols="100" rows="10" id="mensaje" name="mensaje"></textarea>
								<br>
							</div>
						</div>
						<br> <br>
					</div>
				</div>
				<div class="botonesFormulario">
					<button value="VALOR" class="button orange" name="enviarContacto">ENVIAR</button>
					<button value="VACIAR" class="button orange" name="vaciarContacto"
						type="reset">VACIAR</button>
					<br>
				</div>
			</fieldset>
		</form>
	</div>
</div>
