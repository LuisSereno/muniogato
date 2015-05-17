<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>


<img  class="bannerLateralCarrito" src="imagenes/general/carrito.png" alt="">
<br>
<br>

<table id="tablaCarrito" border="3" class ="bannerLateralCarritoTabla">

	<tbody>
	<tr id="Cabecera">
		<td>PRODUCTO</td>
		<td class="tablaCarritoDias">DÍAS</td>
		<td>PRECIO</td>
	</tr>
	</tbody>
</table>

<br>
<br>

<%
log("EL USUARIO ES: " + request.getParameter("usuario"));
if (request.getParameter("usuario")!=null && "admin".equals(request.getParameter("usuario"))){
%>
	<button value="Pagar"  class="button orange" name="pagoTotalCarrito" id="pagoTotalCarrito" onclick="modalUsuarios();">Pagar</button>
<%
}else{
%>
	<button value="Pagar" class="button orange" name="pagoTotalCarrito" id="pagoTotalCarrito" onclick="ventanaPago();">Pagar</button>

<%
}
%>