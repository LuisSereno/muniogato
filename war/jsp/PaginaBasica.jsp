<%@page import="java.util.HashMap"%>
<%@page import="com.bean.Reserva"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>


<%
HashMap mapaInformacion = (HashMap) request.getAttribute("habitaciones");
int pantallaDesplegar=0;
if (request.getAttribute("despliege") != null){
	pantallaDesplegar= Integer.parseInt(request.getAttribute("despliege").toString());
}

//cogemos los datos de la sesion
HttpSession actualSession = request.getSession(false); 
ArrayList <Reserva> listaReser= new ArrayList<Reserva>();
if (actualSession.getAttribute("listaReservas")!=null){
	listaReser= (ArrayList<Reserva>)actualSession.getAttribute("listaReservas");
}
Float precioCarritoCompra=null;
if (actualSession.getAttribute("precioCarrito")!=null){
	precioCarritoCompra=(Float)actualSession.getAttribute("precioCarrito");
}
%>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"> 
	<link rel="stylesheet" type="text/css" media="all" href="estilos/jqGalScroll.css" />
	<link rel="stylesheet" type="text/css" media="all" href="estilos/pro_dropdown_2.css" />
	<link rel="stylesheet" type="text/css" href="estilos/component.css" />
	
	<title>Hotel Rural Gran Maestre</title>
	
	
	<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
	
</head>
<body onload="cargaDatosInicial();">

	<input type="hidden" value="" id="idUsuRepresentado">
	
	<div id="fotoNavidad" title="Ven y disfruta" style="display:none">
		<img alt="postalNavidad" src="imagenes/general/MENUSANVALENTIN1.jpg" style="width: 99%; height: 99%;">
	</div> 

	<div id="divGeneralBody" class="formatoGeneralPantalla">
	
		<input type="hidden" id="sesionNombre"  name="sesionNombre" value="<%=actualSession.getAttribute("nombre")%>"/>
		<input type="hidden" id="sesionUsuario"  name="sesionUsuario" value="<%=actualSession.getAttribute("usuario")%>"/>
		<input type="hidden" id="sesionContrasena"  name="sesionContrasena" value="<%=actualSession.getAttribute("contrasena")%>"/>
		<input type="hidden" id="compraRealizada"  name="compraRealizada" value="<%=actualSession.getAttribute("compraRealizada")%>"/>
		<form action="/menu" id="formMenu" method="post">
			<input type="hidden" id="valorMenu"  name="valorMenu"/>
		</form>
		<h1 class="imagenBannerArriba"><img alt="" src="imagenes/general/logohotel-2.png"></h1>
			<div id="dl-menu" class="dl-menuwrapper">
				<button class="dl-trigger">Open Menu</button>
				<ul class="dl-menu">
					<li>
						<a onclick="pulsado(0);">Inicio</a>
					</li>
					<li>
						<a onclick="pulsado(2);">Historia</a>
					</li>
					<li>
						<a href="#">Reservas</a>
						<ul class="dl-submenu">
							<li><a onclick="pulsado(1);">Habitaciones</a></li>
						</ul>
					</li>
					<li>
						<a onclick="pulsado(7);">Actividades</a>
					</li>
					<li>
						<a onclick="pulsado(8);">Menús</a>
					</li>
					<li>
						<a onclick="pulsado(3);">Contacto</a>
					</li>
					<% 
						if (actualSession.getAttribute("usuario")!=null && "admin".equals(actualSession.getAttribute("usuario"))){
					%>
					<li>
						<a onclick="pulsado(5);">Administrador</a>
					</li>
					<%
							}
					%>
					<li>
						<a onclick="pulsado(6);">Imágenes</a>
					</li>
				</ul>
			</div><!-- /dl-menuwrapper -->
		
		<ul id="nav">
			<li class="top"><a onclick="pulsado(0);" class="top_link"><span>Inicio</span></a></li>
			<li class="top"><a onclick="pulsado(2);" id="historia" class="top_link"><span>Historia</span></a></li>
			<li class="top"><a id="reservas" class="top_link"><span class="down">Reservas</span></a>
				<ul class="sub">
					<li><a onclick="pulsado(1);" id="habitaciones">Habitaciones</a></li>
				</ul>
			</li>
			<li class="top"><a onclick="pulsado(7);" id="actividades" class="top_link"><span>Actividades</span></a></li>
			<li class="top"><a onclick="pulsado(8);" id="menuEspecial" class="top_link"><span>Menús</span></a></li>
			<li class="top"><a onclick="pulsado(3);" id="contacto" class="top_link"><span>Contacto</span></a></li>
			<% 
				if (actualSession.getAttribute("usuario")!=null && "admin".equals(actualSession.getAttribute("usuario"))){
			%>
				<li class="top"><a onclick="pulsado(5);" id="administrador" class="top_link"><span>Administrador</span></a></li>
			<%
				}
			%>
			<li class="top"><a onclick="pulsado(6);" id="imagenes" class="top_link"><span>Imágenes</span></a></li>
		</ul>
		
		<table style="text-align: left; width: 100%; height: 100%;"  cellpadding="2" cellspacing="2">
			  <tbody>
					<tr>
					  <td style="vertical-align: top;">
					  <table style="text-align: left; width: 100%; height: 100%;" cellpadding="2" cellspacing="2">
						<tbody>
							<tr>
								<td class="columnaIniciaSesion">
									<div id="iniciarSesion" class="bannerLateralInicio">
										<jsp:include page="IniciarSesion.jsp"></jsp:include>
									</div>
									<br>
								</td>
								<td id="cargarDatos" style="vertical-align: top; width: 80%; height: 100%;">

 								<div id="paginasMenu">
 								
									<%
										switch(pantallaDesplegar) {
										 case 0: 
											 %>
										     <div id="paginaInicio">
												<jsp:include page="Inicio.jsp"></jsp:include>
											</div>
										     <%
										     break;
										 case 1: 
										     %>
										     <div id="todasHabitaciones">
												<jsp:include page="TablaHabitaciones.jsp"></jsp:include>
											</div>
		 									<div id="habitacionDetalle"  style="display:none;">
											</div> 
										     <%
										     break;
										 case 2: 
											 %>
										     <div id="paginaHistoria">
												<jsp:include page="Historia.jsp"></jsp:include>
											</div>
										     <%
										     break;
										 case 3: 
											 %>
										     <div id="formularioContact">
												<jsp:include page="FormularioContacto.jsp"></jsp:include>
											</div>
										     <%
										     break;
										 case 4: 
											 %>
										     <div id="pantallaAgradecimiento">
												<jsp:include page="Agradecimiento.jsp"></jsp:include>
											</div>
										     <%
											 break;
										 case 5:
											 %>
											<div id="pantallaAdministrador">
												<jsp:include page="Administrador.jsp"></jsp:include>
											</div>		 
											 <% 
											 break;
										 case 6:
											 %>
											<div id="pantallaImagenes">
												<jsp:include page="Imagenes.jsp"></jsp:include>
											</div>		 
											 <% 
											 break;
										 case 7:
											 %>
											<div id="pantallaActividades">
												<jsp:include page="Actividades.jsp"></jsp:include>
											</div>		 
											 <% 
											 break;
										 case 8:
											 %>
											<div id="pantallaMenus">
												<jsp:include page="Menu.jsp"></jsp:include>
											</div>		 
											 <% 
											 break;
										 default: 
										     break;
										 }
									%>
								</div>

								<div id="paginaModal" style="display:none;">
								
								</div>

								<div id="registroUsuario">
									
								</div>	
								<div id="PaginaDetallePago">
									
								</div>
								
								<br>
								</td>
								<td class="lateralCarritoCompra">
									<div id="carritoCompra" style="display:none">
										<jsp:include page="CarritoCompra.jsp"></jsp:include>
									</div>
								<br>
									<div>
										<a href="http://www.booking.com/hotel/es/rural-gran-maestre.es.html"><img class="imagenBooking" src="imagenes/general/reservaBooking.png"/></a>
									</div>
									<div class="imagenTripAdvisor">
										<div   id="TA_virtualsticker862" class="TA_virtualsticker"><ul id="aQBboO3VQQ" class="TA_links BsZ7PDA"><li id="BoQe8a" class="XRCqrVY3EJn"><a target="_blank" href="http://www.tripadvisor.es/"><img src="http://www.tripadvisor.es/img/cdsi/img2/branding/tripadvisor_sticker_logo_88x55-18961-2.png" alt="TripAdvisor"/></a></li></ul></div><script src="http://www.jscache.com/wejs?wtype=virtualsticker&amp;uniq=862&amp;lang=es&amp;locationId=6209058&amp;display_version=2"></script>
									</div>
								</td>
							</tr>
						</tbody>
					  </table>
					  </td>
					</tr>
					
			  </tbody>
		</table>
		<div id="informacionAbajo">
			<div>
				<a href="https://www.facebook.com/pages/Hotel-rural-Gran-maestre/1787843874687916?fref=ts"><img class="imagenFace" src="imagenes/general/facebook.png"/></a>
			</div>
		</div>
			
	</div>

	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<script src="http://malsup.github.com/jquery.form.js"></script> 
	<script type="text/javascript" src="js/jqgalscroll.js"></script>
	<script type="text/javascript" src="js/stuHover.js"></script>
	<script src="js/modernizr.custom.js"></script>
	<script src="js/jquery.dlmenu.js"></script>
	<script type="text/javascript" src="js/Metodos.js"></script>

	<script type="text/javascript">
	
	precioTotalCarrito=<%=precioCarritoCompra%>;
	
	 //Vamos a rellenar el array de canales y colores
	 <%
	 for (int i=0;i<listaReser.size();i++ ){
		 Reserva reser=listaReser.get(i);
		 log("DA VUELTAS EN EL PUTO BUCLE");
    	String fechaInicioOrdernada=reser.getFechaInicio().replace("/", "");
    	log ("LA FECHA DE INICIO ORDENADA ES: " + fechaInicioOrdernada);
    	String fechaFinOrdenada=reser.getFechaFin().replace("/", "");
    	log ("LA FECHA DE FIN ORDENADA ES: " + fechaFinOrdenada);
    	int dias= Integer.parseInt(fechaFinOrdenada) - Integer.parseInt(fechaInicioOrdernada);
	 %>	
	 arrayCarritoCompra.<%="Referencia" + String.valueOf(i)%> = {   //ESTO NO VALE, HABRÍA QUE PONER LAS COMILLAS O ALGO!
			 referencia: '<%=reser.getReferencia()%>',
	 		 producto: '<%=reser.getHb().getNombre()%>',
	 		 dias:'<%=dias%>', 
	 		 precio:'<%=reser.getPrecioTotal()%>',
			 descripcion: '<%=reser.getHb().getDescripcion()%>',
	 		 ocupacionMax: '<%=reser.getHb().getOcupacionMax()%>',
	 		 fechaInicio:'<%=reser.getFechaInicio()%>', 
	 		 fechaFin:'<%=reser.getFechaFin()%>',
			 precioDia: '<%=reser.getHb().getPrecio()%>',
			 imagen: '<%=reser.getHb().getFotos().get(0)%>'
	 };
	 <%
	 }
	 %>
	 
		/** 
		 * Carga los datos iniciales
		 */
		function cargaDatosInicial(){
			
			//Esto lo tengo que quitar, solo para campaña de navidad
 
			if ($("#sesionNombre").val()!="null"&& $("#sesionNombre").val()!=""
					&& $("#sesionContrasena").val()!="null" &&  $("#sesionContrasena").val()!="" 
					&& $("#sesionUsuario").val()!="null" && $("#sesionUsuario").val()!=""){
				
	 			$("#nombreUsuario").val($("#sesionUsuario").val());
				$("#contrasenaLateral").val($("#sesionContrasena").val());
				cambiaPaginaSesionRequest($("#sesionNombre").val());
			}else{
				var el = document.getElementById("imagenAnonima"); //se define la variable "el" igual a nuestro div
				if (el.style.display=="none"){
					cambiaPaginaSesionRequest("");
				}
			}
			var listaDatos=[];
			
 			for (datos in arrayCarritoCompra){
  				listaDatos.push(arrayCarritoCompra[datos].referencia);
 				listaDatos.push( arrayCarritoCompra[datos].producto);
 				listaDatos.push(arrayCarritoCompra[datos].dias);
 				listaDatos.push(arrayCarritoCompra[datos].precio);
				anadirFilaTabla("#tablaCarrito",listaDatos);
				listaDatos=[]; 
			}
 			<%if (precioCarritoCompra!=null && precioCarritoCompra!=0){%>
				if (document.getElementById){ 
					var el = document.getElementById("carritoCompra"); 
					el.style.display = 'block'; 
				}
				anadirFilaTotal("#tablaCarrito",precioTotalCarrito,3); 
			<%
 			}else{
 			%>
				if (document.getElementById){ 
					var el = document.getElementById("carritoCompra"); 
					el.style.display = 'none'; 
				}
 			<%
 			} 			
 			%>
		}
		
		
		window.onbeforeunload = function exitAlert()
		{
			salirPagina();
		}

		$(function() {
			$("#dl-menu").dlmenu({
				animationClasses : { classin : 'dl-animate-in-3', classout : 'dl-animate-out-3' }
			});
		});
		
		$(function() {
		    $( document ).tooltip({
		      position: {
		        my: "center bottom-20",
		        at: "center top",
		        using: function( position, feedback ) {
		          $( this ).css( position );
		          $( "<div>" )
		            .addClass( "arrow" )
		            .addClass( feedback.vertical )
		            .addClass( feedback.horizontal )
		            .appendTo( this );
		        }
		      }
		    });
		  });
  
	</script>
		
</body>

</html>

