<%@page import="java.util.HashMap"%>
<%@page import="com.bean.Usuario"%>
<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <script>
$(function() {
$( "#dialog-message" ).dialog({
	modal: true,
	buttons: {
	Ok: function() {
			$( this ).dialog( "close" );
		}
	}
	}); 
});

$('#adminUsuarios').on('click', 'tr', function(e) {
	
    $("#adminUsuarios tr").each(function( index,element ) {
	   if (index>0){
			$(element).css('background-color', '');
	   }
	});
	if ($(this).attr("id")!="adminUsuariosCabecera"){
    	$(this).css('background-color', 'blue');
    	//idHabitacion;
    	
    }
	
});


</script>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">  

<div id="dialog-message" title="<%=request.getParameter("titulo")%>">
	<%
		String cadenaError="";
		log("VALORES ERROR: " + request.getParameter("valoresError"));
		log("VALORES TITULO: " + request.getParameter("titulo"));
		if (request.getParameter("valoresError")!=null){
			cadenaError=request.getParameter("valoresError");
			String [] cadenaErrores=cadenaError.split(",");
			log(" EL ERROR ES!!!! " + cadenaError);
		%>
			<div id="listaErroresModal">
			 	<ul>
		
		
						<li> <%=cadenaError %></li>
			
			 	
				</ul> 
			</div>
	<%		
		}
	%>
		<%
		log("VALOR DE TODOSUSUARIOS: "  + request.getParameter("todosUsuarios"));
		log("VALOR DE TODOSUSUARIOS: "  + request.getAttribute("todosUsuarios"));
	if (request.getParameter("todosUsuarios")!=null){	
		%>
	<div id="adminUsuarios">
	<input type="hidden" id="referenciaOculta" value='<%=request.getParameter("referenciaReserva")%>'>
		<table  border="5">
			<tbody>
			<tr id="adminUsuariosCabecera"  style="background-color: #B9FFAA">
				<td>REF</td>
				<td>NOMBRE</td>
				<td>PRIMER APE</td>
				<td>SEGUNDO APE</td>
				<td>DNI</td>
				<td>EMAIL</td>
			</tr>
			
				<% 
					String cadenaRequest=request.getParameter("todosUsuarios");
					String [] cadenaUsuarios=cadenaRequest.split(",");
					log("LOS USUARIOS SON" + cadenaUsuarios[0]);
					for (int i=0;i<cadenaUsuarios.length;i++){
				%>
						<tr onclick="reserAdminforUser('<%= cadenaUsuarios[i].split("::")[0]%>');">
						<td><%= cadenaUsuarios[i].split("::")[0]%> </td>
						<td><%= cadenaUsuarios[i].split("::")[1]%> </td>
						<td><%= cadenaUsuarios[i].split("::")[2]%> </td>
						<td><%= cadenaUsuarios[i].split("::")[3]%> </td>
						<td><%= cadenaUsuarios[i].split("::")[4]%> </td>
						<td><%= cadenaUsuarios[i].split("::")[13]%> </td>
						</tr>
		
				<%
					}
				%>
			</tbody>
			
		</table>
	</div>
	<% 
			}
	%>
</div>
 
