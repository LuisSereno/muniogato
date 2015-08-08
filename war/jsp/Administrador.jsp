<%@page import="com.bean.Reserva"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.bean.Usuario"%>
<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<% 

HashMap mapaInformacion = (HashMap) request.getAttribute("datosAdministrador");

ArrayList <Reserva> listaReservas = new ArrayList <Reserva>();
listaReservas=(ArrayList)mapaInformacion.get("reservas");
log("LA LISTA DE RESERVAS ES: " + listaReservas.size());
ArrayList <String[]> listaFacturas = new ArrayList <String[]>();
listaFacturas=(ArrayList)mapaInformacion.get("facturas");
log("LA LISTA DE FACTURAS ES: " + listaFacturas.size());
ArrayList <Usuario> listaUsuarios = new ArrayList <Usuario>();
listaUsuarios=(ArrayList)mapaInformacion.get("usuarios");
log("LA LISTA DE USUARIOS ES: " + listaUsuarios.size());
ArrayList <String[]> listaImagenesMenu = new ArrayList <String[]>();
listaImagenesMenu=(ArrayList)mapaInformacion.get("imagenUsuarios");
log("LA LISTA DE IMAGENES DE MENU ES: " + listaImagenesMenu.size());
%>



	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"> 
	<link rel="stylesheet" type="text/css" media="all" href="estilos/jqGalScroll.css" />
	<link rel="stylesheet" type="text/css" media="all" href="estilos/pro_dropdown_2.css" />

<div id="divAdminReservas" style="margin-top: 5%;overflow:auto; height: 300px; ">
	<table id="adminReservas" border="5" style="">
		<tbody>
		<tr id="adminReservasCabecera"  style="background-color: #B9FFAA">
			<td>REF</td>
			<td>N. HABITACION</td>
			<td>N. USUARIO</td>
			<td>FECHA-INICIO</td>
			<td>FECHA-FIN</td>
			<td>PRECIO</td>
			<td>FACTURA</td>
		</tr>
		
		<% 
			for (int i=0;i<listaReservas.size();i++){
		%>
		<tr id="<%=listaReservas.get(i).getReferencia()%>">
			<td><%=listaReservas.get(i).getHb().getReferencia()%></td>
			<td><%=listaReservas.get(i).getHb().getNombre()%></td>
			<td><%=listaReservas.get(i).getUsu().getNombre()%></td>
			<td><%=listaReservas.get(i).getFechaInicio()%></td>
			<td><%=listaReservas.get(i).getFechaFin()%></td>
			<td><%=listaReservas.get(i).getPrecioTotal()%></td>
			<td><%=listaReservas.get(i).getNumFactura()%></td>
			<td><%=listaReservas.get(i).getNumFactura()%></td>
		</tr>
		<%
			}
		%>
		</tbody>
	</table>
</div>

<div id="divAdminFacturas" style="overflow:auto; height: 300px;">
	<table id="adminFacturas" border="5" >
		<tbody>
		<tr id="adminFacturasCabecera" style="background-color: #B9FFAA">
			<td>REF</td>
			<td>NOMBRE</td>
			<td>PAGADA</td>
			<td>ENVIADA</td>
			<td>CANTIDAD</td>
		</tr>
		
		<% 
			for (int i=0;i<listaFacturas.size();i++){
		%>
		<%
			if ("N".equals(listaFacturas.get(i)[2])){
		%>
				<tr id="<%=listaFacturas.get(i)[0]%>" style="background-color: #FF4D35">
		<%
			}else{
		%>
				<tr id="<%=listaFacturas.get(i)[0]%>" style="background-color:#7D75FF">
		<%} %>
			<td><%=listaFacturas.get(i)[0]%></td>
			<td><%=listaFacturas.get(i)[1]%></td>
			<td><button onclick="cambiarPago('<%=listaFacturas.get(i)[0]%>','<%=listaFacturas.get(i)[2]%>')" style="background:transparent;"><%=listaFacturas.get(i)[2]%></button></td>
			<td><%=listaFacturas.get(i)[3]%></td>
			<td><%=listaFacturas.get(i)[4]%></td>
			<td style="height: 10%;width: 20%;" onclick="borrarReservaFactura('<%=listaFacturas.get(i)[0]%>');"><button><img style="width: 40px;" alt="" src="imagenes/general/ico_aspa.png"></button></td>
			
			<%
			if ("N".equals(listaFacturas.get(i)[2])){
			%>
			<td style="height: 10%;width: 20%;" onclick="enviarFactura('<%=listaFacturas.get(i)[0]%>');"><button><img style="width: 40px;" alt="" src="imagenes/general/factura-email.gif"></button></td>
			<%
			}
			%>
		</tr>
		<%
			}
		%>
		</tbody>
	</table>
</div>

<div id="divAdminUsuarios" style="margin-top: 5% ;overflow:auto; height: 300px;">
	<table id="adminUsuarios" border="5" >
		<tbody>
		<tr id="adminUsuariosCabecera"  style="background-color: #B9FFAA">
			<td>REF</td>
			<td>NOMBRE</td>
			<td>PRIMER APE</td>
			<td>SEGUNDO APE</td>
			<td>DNI</td>
			<td>CALLE</td>
			<td>NUM</td>
			<td>CIUDAD</td>
			<td>PROVINCIA</td>
			<td>PAIS</td>
			<td>TELÉFONO</td>
			<td>EMAIL</td>
			<td>FECHA ALTA</td>
		</tr>
		
		<% 
			for (int i=0;i<listaUsuarios.size();i++){
		%>
		<tr id="<%=listaUsuarios.get(i).getReferencia()%>">
			<td><%=listaUsuarios.get(i).getReferencia()%></td>
			<td><%=listaUsuarios.get(i).getNombre()%></td>
			<td><%=listaUsuarios.get(i).getApellido1()%></td>
			<td><%=listaUsuarios.get(i).getApellido2()%></td>
			<td><%=listaUsuarios.get(i).getDni()%></td>
			<td><%=listaUsuarios.get(i).getCalle()%></td>
			<td><%=listaUsuarios.get(i).getNumero()%></td>
			<td><%=listaUsuarios.get(i).getCiudad()%></td>
			<td><%=listaUsuarios.get(i).getProvincia()%></td>
			<td><%=listaUsuarios.get(i).getPais()%></td>
			<td><%=listaUsuarios.get(i).getTelefono()%></td>
			<td><%=listaUsuarios.get(i).getCorreoElec()%></td>
			<td><%=listaUsuarios.get(i).getFecha()%></td>
		</tr>
		<%
			}
		%>
		
		</tbody>
	</table>
</div>

<div id="divMenu" style="margin-top: 5% ;overflow:auto; height: 300px;">
	<table id="fotosMenus" border="5" >
		<tbody>
		<tr id="cabeceraMenu"  style="background-color: #B9FFAA">
			<td>Nombre</td>
			<td>Url</td>
			<td>Tipo</td>
		</tr>
		<% 
		for (int i=0;i<listaImagenesMenu.size();i++){
		%>
			<tr>
			<td><input type="text" name="nombreMenu" value="<%=listaImagenesMenu.get(i)[0]%>" disabled></td>
			<td><input type="text" name="fotoMenu" value="<%=listaImagenesMenu.get(i)[1]%>" disabled></td>
			<td>
				<select name="tipo" disabled>
				<%if ("menu".equals(listaImagenesMenu.get(i)[2])) { 
				System.out.println("Mira aqui capullo");%>
					<option value="menu" selected>Menu</option>
				<%}else{
				System.out.println("Mira aqui capullo2"); %>
					<option value="menu">Menu</option>
				<%} %>
				<% if ("actividades".equals(listaImagenesMenu.get(i)[2])) { 
				System.out.println("Mira aqui capullo3");%>
					<option value="actividades" selected>Actividades</option>
				<%}else{
				System.out.println("Mira aqui capullo4"); %>
					<option value="actividades">Actividades</option>
				<%} %>
				
				</select>
			</td>
			<td><button onclick="botonBorrarImagenMenu(this);" ><img style="width: 10px;" alt="" src="imagenes/general/ico_aspa.png"></button></td>
			</tr>
		<%
		}
		%>
		<tr>
		<td><input type="text" name="nombreMenu"></td>
		<td><input type="text" name="fotoMenu"></td>
		<td>
			<select name="tipo">
				<option value="menu">Menu</option>
				<option value="actividades">Actividades</option>
			</select>
		</td>	
		<td><button onclick="botonBorrarImagenMenu(this);"><img style="width: 10px;" alt="" src="imagenes/general/ico_aspa.png"></button></td>
		</tr>
		</tbody>
	</table>
	
	<button id="addFilaMenu">Añadir Fila</button>
	<button id="saveDatos">Guardar Datos</button>
</div>


	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<script src="http://malsup.github.com/jquery.form.js"></script> 
	<script type="text/javascript" src="js/Metodos.js"></script>
	<script type="text/javascript" src="js/stuHover.js"></script>
	
	
<script>

	$("#addFilaMenu").click(function() {
		
		var n = $('tr:last td', $("#fotosMenus")).length;
		var tds = '<tr>';
		tds += '<td><input type="text" name="nombreMenu"></td>';
		tds += '<td><input type="text" name="fotoMenu"></td>';
		tds += '<td><select><option value="menu">Menu</option><option value="actividades">Actividades</option></select></td>';
		tds += '<td><button onclick="botonBorrarImagenMenu(this);"><img style="width: 10px;" alt="" src="imagenes/general/ico_aspa.png"></button></td>';
		tds += '</tr>';
		$("#fotosMenus").append(tds);
		
	});
	
	
	$("#saveDatos").click(function() {
		guardarDatosTablaImagenesAdministrador();
	});
	
	
</script>