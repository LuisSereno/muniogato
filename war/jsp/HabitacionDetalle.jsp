<%@ page language="java" contentType="text/html; charset=iso-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<meta http-equiv="content-type" content="text/html; charset=UTF-8">  

		<script type="text/javascript">
		    $(document).ready(function(){
		        $("#demoOne").jqGalScroll({height:500,width:500});
		    });
		</script>

		
<body  onunload="salirPagina();" style="color: rgb(0, 0, 0); background-color: rgb(255, 102, 102);" alink="#000099" link="#000099" vlink="#990099">

	<script language="JavaScript">
		if(window.screen.availWidth >= 1280){
			window.parent.document.body.style.zoom="100%"
		}else if(window.screen.availWidth >= 1152){
			window.parent.document.body.style.zoom="90%"
		}else if(window.screen.availWidth >= 1024){
			window.parent.document.body.style.zoom="80%"
		}else if(window.screen.availWidth >= 800){
			window.parent.document.body.style.zoom="70%";
		}else if(window.screen.availWidth >= 640){
			window.parent.document.body.style.zoom="60%"
		}else{
			window.parent.document.body.style.zoom="50%"
		}
	</script> 
		
	<br>
	<br>
	<div style="text-align: center;">
	<span style="font-size:3em;"><strong><%=request.getParameter("nombreHb")%></strong></span>

		<input type="hidden" id="idRefHabitacion" name="idRefHabitacion" value='<%=request.getParameter("referencia")%>'/>
	  
		  <div id="contenedorParalelo" style="width: 100%;">
		  
			  	<div id="contenedorImagen" style="display: inline-block; margin-right: 10%" >
			  		<ul id="demoOne" >
			  			<%
			  			log("VA A MOSTRAR LAS FOTOS DE LAS HABITACIONES");
						String cadenaRequest=request.getParameter("foto");
			  			log("LAS HABITACIONES SON" + cadenaRequest);
			  			log("LAS HABITACIONES SON atribute" + request.getAttribute("foto"));
			  			String [] cadenaHabitaciones=cadenaRequest.split(",");
						log("LOS USUARIOS SON" + cadenaHabitaciones[0]);
						
			  			for (int i=0;i<cadenaHabitaciones.length;i++){
			  			%>
			  			<li><img class="curvadoImagenes" src="<%=cadenaHabitaciones[i]%>"/></li>
			  			<%
			  			}
			  			%>
					</ul>
				</div>
				<div id="contenedorTexto" style="text-align: left; display:inline-block;">
					<div style="margin-left: 3%;">
						Nº de personas max: <%=request.getParameter("ocupacionmax")%>
						<br>
						<br>
						Precio: <%=request.getParameter("precio")%>
						<br>
						<br>
				 			Fecha Inicio:&nbsp;&nbsp; 
						
 						<input type="text" id="datepickerInicio" style="width : 14%; heigth : 5%" />
			 			
						
						
						<script type="text/javascript">
						
							$ ( "#datepickerInicio" ).datepicker({
							    maxDate: null,
							    minDate: '+0d',
							    dateFormat: 'dd/mm/yy',
							     beforeShowDay: DisableDays,
							     showButtonPanel: false,
							     onClose: function (selectedDate) {
									$("#datepickerFin").datepicker("option", "minDate", selectedDate);
								}
							});
								
						</script>
						
								Fecha fin:&nbsp;&nbsp;
						
 						<input type="text" id="datepickerFin" style="width : 14%; heigth : 5%;"/>
						 
						<script type="text/javascript">
						
							$ ( "#datepickerFin" ).datepicker({
							    maxDate: null,
							    minDate: '+0d',
							    dateFormat: 'dd/mm/yy',
							    beforeShowDay: DisableDays ,
							    showButtonPanel: false,
							    onClose: function (selectedDate) {
									$("#datepickerInicio").datepicker("option", "maxDate", selectedDate);
								}
							});
							
							//http://www.desarrolloweb.com/articulos/jquery-ui-datepicker-avanzado.html
								
						</script>
						
						<br>
						<br>
						<br>
						Servicios de los que dispone la habitación:<br>
					</div>
					<div style="margin-left: 6%;">
						<ul>
			  			<%
			  			
			  			log("CARACTERISTICAS DE LA HABITACION");
						String cadenaRequestCarac=request.getParameter("caracteristicas");
			  			log("LAS CARACTERISTICAS SON: " + cadenaRequestCarac);
			  			String [] cadenaCaracteristicas=cadenaRequestCarac.split(",");
						log("CARACTERISTICAS" + cadenaCaracteristicas[0]);
						
			  			for (int i=0;i<cadenaCaracteristicas.length;i++){
			  			%>
			  			<li><%=cadenaCaracteristicas[i]%></li>
			  			<%
			  			}
			  			%>
			  			
			  			
						</ul>
					</div>
				</div>
			</div>
			<div id="contenedorInferior" style="clear:both;width: 100%;height: 20%;">
				<div style="text-align: left; margin-left: 5%;"> <%=request.getParameter("descripcion")%></div>
				<div style="float: right; margin-top: 3%;">
					<div style="display:inline-block;">
						<button	value="ATRAS" class="button orange" name="ATRAS" type="reset" onclick=" window.parent.document.body.style.zoom='100%'; atras();">Atrás</button>
					</div>
					<div style="display:inline-block;">
	 					<button	value="RESERVA" class="button orange" name="RESERVA" id="RESERVA" type="submit" onclick=" window.parent.document.body.style.zoom='100%'; reservaHabitacion('<%=request.getParameter("idRef")%>')">Reserva</button>
					</div>
					<br>
				</div> 
			</div> 
		<br>
	</div>
</body>


