<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
log("Entramos en el JSP Agradecimiento");
ArrayList <String> listaMensajesAgradecimiento = (ArrayList) request.getAttribute("listaMensajesAgradecimiento");

%>

<html>
	<head>
	
		<title><%=listaMensajesAgradecimiento.get(0)%></title>
		<meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />
		<script>
		  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
		
		  ga('create', 'UA-44978279-1', 'hotelruralgranmaestre.es');
		  ga('send', 'pageview');
		
		</script>
		
	</head>
	<body  onunload="salirPagina();">
		<h1 style="text-align: center;">
			<span style="color:#fef4e9;"><%=listaMensajesAgradecimiento.get(0)%></span></h1>
		<div>&nbsp;</div>
		<div style="margin-left: 40px;"><%=listaMensajesAgradecimiento.get(1)%></div>
		<div style="margin-left: 40px;">&nbsp;</div>
		<div style="margin-left: 40px;"><%=listaMensajesAgradecimiento.get(2)%></div>
		<div style="margin-left: 40px;">&nbsp;</div>
	</body>
</html>