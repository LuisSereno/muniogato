<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.bean.Reserva"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%

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

<script type="text/javascript">
	precioTotalCarrito=<%=precioCarritoCompra%>;
	
	//Vamos a rellenar el array de canales y colores
	<%
	for (int i=0;i<listaReser.size();i++ ){
		 Reserva reser=listaReser.get(i);
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
</script>



<table id="tablaDetalleCompra" border=1>

	<tbody>
	<tr id="Cabecera">
		<td>Id. Ref.</td>
		<td>Nombre</td>
		<td>Descripción</td>
		<td>Ocupación</td>
		<td>Precio Día</td>
		<td class="imagenTabla">Imagen</td>
		<td>Fecha Inicio</td>
		<td>Fecha Fin</td>
		<td>Precio Total</td>
	</tr>
	</tbody>
</table>

<br>
<br>

<div id="botonFormularioPaypal">
	<% if (request.getParameter("usuario")!=null && "admin".equals(request.getParameter("usuario"))){  %>
		<input value="Pago Paypal" type="button" class="button orange" onclick="location.href='/paymentwithpaypal'" class="button">
	<% }else{ %>
		<input value="Pago Paypal" type="button" class="button orange" onclick="location.href='/compraCorrecta'" class="button">
	<% } %>
</div>

<div id="avisoTipoCompra" style="margin-top: 15%">
	<label>Va a realizar la compra a través de la plataforma segura Paypal, es muy sencillo, no se preocupe.</label><br>
	<label>Por favor, cuando termine de realizar la compra, vuelva a nuestra página, para que le enviemos su factura por correo de manera automática.</label><br>
	<label>Recordamos que también puede reservar por teléfono o contactando con nosotros, las dos opciones las puede ver en la pestaña <u>Contactos</u>.</label><br>
</div>