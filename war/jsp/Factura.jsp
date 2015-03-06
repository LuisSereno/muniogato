<%@ page language='java' contentType='text/html; charset=ISO-8859-1'
    pageEncoding='ISO-8859-1'%>
<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">  
	<title>Factura</title>
	
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
	<div style='text-align: center;'><big><big><big>FACTURA Nº xxxxxxxxxxxx</big></big></big>
	<br>
	<br>
	<div id='divFacturaArribaIzquierda' style='text-align: left; float: left; width: 50%;'>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		Hotel Rural Gran Maestre<br>
		NIF:XXXXXXXXXX<br>
		Cabeza del Buey (Badajoz)<br>
		CP:06600<br>
		Carrt. EX-104<br>
		Extremadura<br>
		<br>
		<br>
	</div>
	<div id='divFacturaArribaDerecha' style='text-align: right; float: right; width: 50%;'>
		<img style='width: 270px; height: 208px;' src='imagenes/general/00008897.jpg'	alt='' align='top'>
		<br>
		<br>
		<br>
		FECHA: 2013/09/10
		<br>
		<br>
	</div>
	<table style='text-align: left; width: 100%;' border='5' cellpadding='2' cellspacing='2'>
		<tbody>
			<tr>
				<td	style='vertical-align: top; width: 20%; background-color: rgb(0, 204, 204);'>Nombre Habitación<br></td>
				<td	style='vertical-align: top; width: 30%; background-color: rgb(0, 204, 204);'>Fecha Inicio Reserva<br></td>
				<td	style='vertical-align: top; width: 30%; background-color: rgb(0, 204, 204);'>Fecha Fin Reserva<br></td>
				<td	style='vertical-align: top; width: 20%; background-color: rgb(0, 204, 204);'>Precio<br></td>
			</tr>
			<tr>
				<td style='vertical-align: top; width: 20%;'><br></td>
				<td style='vertical-align: top; width: 30%;'><br></td>
				<td style='vertical-align: top; width: 30%;'><br></td>
				<td style='vertical-align: top; width: 20%;'><br></td>
			</tr>
			<tr align='right'>
				<td colspan='1' rowspan='1' style='vertical-align: top;'><br></td>
				<td colspan='1' style='vertical-align: top;'><br></td>
				<td colspan='1' style='vertical-align: top;'><br></td>
				<td	style='vertical-align: top; background-color: rgb(0, 204, 204);'>TOTAL + IVA: 21%: XXXXX</td>
			</tr>
		</tbody>
	</table>
	</div>
</body>
</html>

