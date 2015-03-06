<%@page import="com.bean.Habitaciones"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
<%
log("Entramos en el JSP HABITACIONES");
HashMap mapaInformacion = (HashMap) request.getAttribute("habitaciones");
log("LOG!!!");
ArrayList <Habitaciones> listaHabitaciones = (ArrayList) mapaInformacion.get("habitaciones");
log ("TERMINAMOS DE CARGAR LOS ARRAYS");
%>


<h2>ESTAS SON NUESTAS HABITACIONES: </h2>
<ul id="listaHabitaciones">

	<%
	String margen="0";
	Habitaciones habita= new Habitaciones();
	for (int i=0; i<listaHabitaciones.size();i++){
		habita=listaHabitaciones.get(i);
	%>
	<li style="margin-top:<%=margen%>%">
		<div style="clear:both;">		
			<div  style="width: 100%;">
				<h3><%=habita.getNombre()%></h3>
			
			</div>
			<div style="width: 100%; height: 100%; ">
				<div style="float: left; margin-left: 10%; width: 50%;">
					<img style="width: 50%;" src='<%=habita.getFotos().get(0)%>'>
				</div>
				
				<div style="float: right; width: 20%; text-align:left; margin-right: 20%;">
					<label>Precio por noche: <%=habita.getPrecio()%></label><br>
					<label>Ocupación Máxima: <%=habita.getOcupacionMax()%></label><br>
					<br>
					<br>
					<br>
					<button	value="descubrela" class="button orange" name="descubrela" type="reset" onclick="detalleHabitacion(<%=habita.getReferencia()%>);">Descúbrela</button>
				</div>
			</div>		
		</div>
		</li>
	<%
	margen="20";
	}
	%>

</ul>


