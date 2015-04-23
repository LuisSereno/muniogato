var arrayHabitaciones = {};
var arrayCarritoCompra = {};
var precioTotalCarrito;
var boton;
var RangeDates = []; //"8/2/2013, 8/10/2013", "9/12/2013, 9/20/2013", "11/20/2013, 11/15/2013", "12/29/2013"
var RangeDatesIsDisable = true;

/**
 * Funcion que se usa para movernos por la pagina en el menu
 */
function pulsado(num)
{
	$("#valorMenu").val(num);
	document.forms["formMenu"].submit();
	
}

function cargaDatos(){
	$.post('/pantallaAdministrador',params, function(output){
		$("#pantallaAdministrador").load("jsp/Administrador.jsp", {reservas: output['reservas'],facturas: output['facturas'], usuarios:output['usuarios']}, function (){
		});	
	});
}

/**
 * Esta funcion va hacia el detalle de la habitaci�n
 * @param idRef
 */
function detalleHabitacion(idRef)
{
	document.body.style.cursor = "wait";
	muestraOculta("todasHabitaciones");
	 
	params={"codigoHabitacion": idRef,"tipoPantalla":'detalle'};
	$.post('/habitaciones',params, function(output){
		
		RangeDates= [];
		RangeDates.length = 0;
		
		var listaFechas=output['fechas'];
		for (i in listaFechas){
			RangeDates.push(listaFechas[i]);
		}
		
		var fotitos="";
		for (x in output['fotos']){
			fotitos=output['fotos'][x] +","+fotitos;
		}
		
		var carac="";
		for (x in output['caracteristicas']){
			carac=output['caracteristicas'][x] +","+carac;
		}
		
		$("#habitacionDetalle").load("jsp/HabitacionDetalle.jsp", {idRef:idRef,habitalibres: output['numeroHabitacionesLibres'],nombreHb: output['nombre'], precio:output['precio'],descripcion:output['descripcion'],ocupacionmax:output['ocupacionMax'],foto:fotitos,caracteristicas:carac,referencia:output['referencia']}, function (){
			muestra_oculta('habitacionDetalle');	
		});		
		document.body.style.cursor = "auto";
	});	

}

/**
 * funcion replaceAll
 */
function replaceAll(find, replace, str) {
	  return str.replace(new RegExp(find, 'g'), replace);
	}

/**
 * Esta funcion cambia las fechas dependiendo de la habitacion y lo que tengas en el carrito
 */
function reservasTiempoReal(output){
	
	if ($("#idRefHabitacion").val()!=null){
		var cadenasHash=output['_values']['hashFechasOcupadas'];
	
		var cadenas=cadenasHash[$("#idRefHabitacion").val()];
		for (i in cadenas){
			RangeDates.push(cadenas[i]);
		}
	}
	
}


/**
 * Esta funcion sirve para reservar una habitacion
 * @param idRef
 */
function reservaHabitacion(idRef)
{
	document.body.style.cursor = "wait";
	if ($('#datepickerInicio').val()=="" || $('#datepickerFin').val()==""){
		mostrarModal("Seleccione una fecha de inicio y de fin");
		document.body.style.cursor = "auto";
	}else{
		params={"reserva":"Anadir","codigoHabitacion": idRef,"diaInicio":$('#datepickerInicio').val(),"diaFin":$('#datepickerFin').val(),"numeroHabitaciones":$('#habitacionesReservar').val()};
		$.post('/accionReservar',params, function(output){
			
			$("#datepickerInicio").val("");
			$("#datepickerFin").val("");
			
			if (output['_values']!=null){
				if (output['_values'].error!=null || output['_values'].error!=""){
				
					//Actualizamos las fechas de los calendarios para empezar
					reservasTiempoReal(output);

					var datosTotales=[];
					var listaReser=output['_values'].listaReservas;
					var total=output['_values'].precioCarrito;
					var datos=[];
					for (dat in listaReser){
			
						var dias=parseInt(replaceAll("/", "", listaReser[dat].fechaFin))-parseInt(replaceAll("/", "", listaReser[dat].fechaInicio));
						datos= [listaReser[dat].referencia,listaReser[dat].hb.nombre,dias];
						datosTotales.push(datos);
						
					}
					$("#carritoCompra").load("jsp/CarritoCompra.jsp", {"datos":datos,"total":total,"usuario":$("#sesionUsuario").val()}, function (){
						for (datos in datosTotales){
							anadirFilaTabla("#tablaCarrito",datosTotales[datos]);
						}
						anadirFilaTotal("#tablaCarrito",total,3);
						
						if (document.getElementById){ 
							var el = document.getElementById("carritoCompra"); 
							el.style.display = 'block'; 
						}
					});	
				
				}
			}else{
				mostrarModal(output);
			}
			document.body.style.cursor = "auto";
		});	
	}

}

/**
 * 
 */
function anadirFilaTabla(nomTabla,datos){
	var n = $('tr:last td', $(nomTabla)).length;
	var tds = '<tr id="'+ datos[0]+'">';
	for(var i = 1; i <= n; i++){
		if (datos[i]!=null){
			if (datos[i].toString().indexOf("imagenes/")!=-1){
				tds += '<td  width="15%" height="15%"> <img width="100%" height="25%" alt="Imagen'+i+'" src="'+datos[i]+'"/></td>';
			}else{
				tds += '<td>'+datos[i]+'</td>';	
			}
		}
		
	}
	//borrarReserva(datos[0]);
	tds+='<td onclick="borrarReserva('+datos[0]+');"><img alt="aspa" src="imagenes/general/ico_aspa.png"/></td>';
	tds += '</tr>';
	$(nomTabla).append(tds);
}

/**
 * 
 */
function anadirFilaTotal(tabla,total,tamano){
	var tds ='<tr id="filaTotal">';
	tds += '<td COLSPAN="'+tamano+'" id="cantidadTotal"><div style="text-align:right;"><b>TOTAL:</b> '+total+' €</div></td>';
	tds += '</tr>';
	$(tabla).append(tds);
	
}

/**
 * Esta funcion sirve para cargar el fomulario para registrar un usuario
 */
function registrarUsuario(){
	if (document.getElementById){ 
		var el = document.getElementById("paginasMenu"); 
		el.style.display = 'none'; 
		var el2 = document.getElementById("PaginaDetallePago"); 
		el2.style.display ='none'; 
		var el3 = document.getElementById("registroUsuario"); 
		el3.style.display ='block'; 
	}
//	muestraOculta("paginasMenu");
	$("#registroUsuario").load("jsp/RegistroUsuario.jsp");	
}

/**
 * 
 * @param nombreDiv
 * @param nombreJSP
 */
function creaDiv (nombreDiv,nombreJSP){
	
	$("#cargarDatos").append("<div id='habitacionDetalle'/>");
}

/**
 * 
 * @param nombreBorrar
 */
function muestraOculta(nombreBorrar){
//	 $('#' +nombreBorrar + '').remove();
	 muestra_oculta(nombreBorrar);
}


/**
 * 
 * @param date
 * @returns {Array}
 */
function DisableDays(date) {
    var isd = RangeDatesIsDisable;
    var rd = RangeDates;
    var m = date.getMonth();
    var d = date.getDate();
    var y = date.getFullYear();
    
    for (i = 0; i < rd.length; i++) {
    	var fecha=rd[i].toString();
    	var ds = fecha.split(',');
        var di, df;
        var m1, d1, y1, m2, d2, y2;
        if (ds.length == 1) {
            di = ds[0].split('/');
            m1 = parseInt(di[0]);
            d1 = parseInt(di[1]);
            y1 = parseInt(di[2]);
            if (y1 == y && m1 == (m + 1) && d1 == d) {
            	return [!isd];
            }
        } else if (ds.length > 1) {
            di = ds[0].split('-');
            df = ds[1].split('-');
            m1 = parseInt(di[1]);
            d1 = parseInt(di[2]);
            y1 = parseInt(di[0]);
            m2 = parseInt(df[1]);
            d2 = parseInt(df[2]);
            y2 = parseInt(df[0]);
            if (y1 >= y || y <= y2) {
            	if ((m + 1) >= m1 && (m + 1) <= m2) {
                    if (m1 == m2) {
                        if (d >= d1 && d <= d2) return [!isd];
                    } else if (m1 == (m + 1)) {
                        if (d >= d1) return [!isd];
                    } else if (m2 == (m + 1)) {
                        if (d <= d2) return [!isd];
                    } else return [!isd];
                }
            }
        }
    }
    return [isd];
}



function comprobarCamposNumericos(campo, tipo, numeroPequeno, numeroGrande){
	
	var valoresDni = /(^([0-9]{8,8}[A-Z])|^)$/        //8 n�meros, un guion y una letra, o cadena vacia
	var valoresEmail = /(^(.+\@.+\..+)|^)$/  			//direccion de correo electronico o vacio
		
	if ($('#' +campo + '').val()==null || $('#' +campo + '').val()=="" ||  $('#' +campo + '').val().indexOf('Escriba') != -1){
		return "El campo " + campo + " no puede ser vac\u00edo";
	}
	//Para campos numericos
	if (tipo==1){
		var numero=parseInt($('#' +campo + '').val());
	        if (isNaN(numero)){ 
	        	return "El campo " + campo + " no es n�merico" ;
	        }
		if (!(numero>=numeroPequeno && numero<=numeroGrande)){
			return "El campo " + campo+ " no est\u00e1 en el rango" + numeroPequeno+" y " + numeroGrande;
		}
	//Para texto
	}else if (tipo==2){
		if (!($('#' +campo + '').val().length>=numeroPequeno && $('#' +campo + '').val().length<=numeroGrande)){
			return "El campo " + campo+ " tiene que tener una longitud entre " + numeroPequeno + " y " + numeroGrande;
		}
	//Para dni
	}else if (tipo==3){
		if (!valoresDni.test($('#' +campo + '').val())){
			return "El campo " + campo+ " no tiene un formato correcto (formato NNNNNNNNL)";
		}else{
			var value=$('#' +campo + '').val();
			var numero = value.substr(0,value.length-1);
			var let = value.substr(value.length-1,1);
			numero = numero % 23;
			var letra='TRWAGMYFPDXBNJZSQVHLCKET';
			letra=letra.substring(numero,numero+1);
			if (letra!=let){
				return "El campo " + campo+ " no tiene un formato correcto";
			}
			
			
		}
		
	//Para correo	
	}else if (tipo==4){
		if(!valoresEmail.test($('#' +campo + '').val())){
			return "El campo " + campo+ " no tiene un formato correcto";
		}
	}
	
	return "";
}


function comprobarFormulario (){

	var arrayErrores=[];
	arrayErrores.push(comprobarCamposNumericos("nombre", 2, 2, 80));
	arrayErrores.push(comprobarCamposNumericos("primerApellido", 2, 2, 80));
	arrayErrores.push(comprobarCamposNumericos("calle", 2, 3, 80));
	arrayErrores.push(comprobarCamposNumericos("email",4,0,0));
	arrayErrores.push(comprobarCamposNumericos("dni",3,0,0));
	arrayErrores.push(comprobarCamposNumericos("ciudad", 2, 2, 30));
	arrayErrores.push(comprobarCamposNumericos("usuario", 2, 2, 20));
	arrayErrores.push(comprobarCamposNumericos("telefono",1,100000000,999999999));
	arrayErrores.push(comprobarCamposNumericos("segundoApellido", 2, 2, 80));
	arrayErrores.push(comprobarCamposNumericos("pais", 2, 2, 20));
	arrayErrores.push(comprobarCamposNumericos("numero", 1, 0, 999));
	arrayErrores.push(comprobarCamposNumericos("provincia", 2, 2, 30));
	arrayErrores.push(comprobarCamposNumericos("contrasena", 2, 5, 20));
	
	var desactivarForm=true;
	//Borramos si hay una lista ya
	borrar("listaErrores");
	for (var i=0;i<arrayErrores.length;i++){
		if (arrayErrores[i]!=""){
			agregarLi(arrayErrores[i],"listaErrores");
			desactivarForm=false;
		}
		
	}
	
	if (desactivarForm){
		return true;
	}else{
		return false;
	}
}



function comprobarFormularioEnvioCorreo (){

	var arrayErrores=[];
	arrayErrores.push(comprobarCamposNumericos("nombre", 2, 2, 100));
	arrayErrores.push(comprobarCamposNumericos("mensaje", 2, 2, 200));
	arrayErrores.push(comprobarCamposNumericos("email",4,0,0));
	arrayErrores.push(comprobarCamposNumericos("asunto",2,2,50));
	
	var arrayErroresLimpio=[];
	var desactivarForm=true;
	//Borramos si hay una lista ya
	for (var i=0;i<arrayErrores.length;i++){
		if (arrayErrores[i]!=""){
			arrayErroresLimpio.push(arrayErrores[i]);
			desactivarForm=false;
		}
		
	}
	
	if (desactivarForm){
		return true;
	}else{
		mostrarModal(arrayErroresLimpio);
		return false;
	}
}



/**
 * Esta funci�n agrega las lineas de la tabla din�mica que hay en el borde inferior
 * @param maximo
 */
function agregarLi(texto,lista){
	try{
		var li;
		li=document.createElement('LI');
	    li.innerHTML= texto;
	    document.getElementById(lista).appendChild(li);
	}catch (e){
		alert ("AGREGAR LI: " + e);
	}
}


/**
 * Esta funcion borra la tabla del borde inferior de la pantalla.
 */
function borrar(lista){
	try{
		if (document.getElementById(lista).getElementsByTagName('li')!=null || document.getElementById(lista).getElementsByTagName('li').length !=0){
			lis=document.getElementById(lista).getElementsByTagName('li');
		    var tamano=lis.length;
		    for (var i=tamano-1;i>=0;i--){
	            lis[i].parentNode.removeChild(lis[i]);
	        }
		}
	}catch (e) {
		alert ("BORRAR: " + e);
	}
}

/**
 * Esta funcion borra la tabla del borde inferior de la pantalla.
 */
function borrarTabla(tabla){
    var table = document.getElementById(tabla);
    //or use : var table = document.all.tableid;
    for(var i = table.rows.length - 1; i > 0; i--)
    {
    	table.deleteRow(i);
    }
}



/**
 * 
 */
function introducirUsuario (){
	document.body.style.cursor = "wait";
	if (comprobarFormulario ()){
	
    	var datos={
				   nombre:$("#nombre").val(),
				   primerApellido:$("#primerApellido").val(),
				   segundoApellido:$("#segundoApellido").val(),
				   dni:$("#dni").val(),
				   calle:$("#calle").val(),
				   numero:$("#numero").val(),
				   ciudad:$("#ciudad").val(),
				   provincia:$("#provincia").val(),
				   pais:$("#pais").val(),
				   telefono:$("#telefono").val(),
				   usuario:$("#usuario").val(),
				   contrasena:$("#contrasena").val(),
				   email:$("#email").val(),
				   ingreso:"Alta"
			    };
		$.post('/registraUsuarios',datos, function(output){
			//Mostramos si ha habido un error
			cambiaPaginaSesion(output,datos["ingreso"]);
			document.body.style.cursor = "auto";
		});	
	}else{
		document.body.style.cursor = "auto";
	}
	
}

/**
 * 
 * @param output
 */
function cambiaPaginaSesion(output,ingreso){
	
	try{
		if (output['_values']!=null){
	//Si no hay error
			if (output['_values'].error==""){
				//Mostramos imagen buena
					muestra_oculta("imagenAnonima");
					muestra_oculta("imagenRegistrado");
				//Cambiamos de boton por el de salir de sesion
					muestra_oculta("botonIniciarSesion");
					muestra_oculta("botonDesconectar");
					document.getElementById('spanTextNombre').firstChild.nodeValue = "Bienvenid@ " + output['_values'].nombre;
					//Tenemos que cargar la pantalla b�sica esto es m�s dificil porque no sabemos desde la pagina de la que venimos, tendremos que tener una variable con los datos
					if (ingreso=="Alta"){
						pulsado("0");
					}
					
				}else{
					var errores=output['_values'];
					if (ingreso=="Alta"){
						borrar("listaErrores");
						var errores=output['_values'];
						for (i in errores){
							if (errores[i]!=""){
								agregarLi(errores[i],"listaErrores");
								desactivarForm=false;
							}
						}
					}else{
						mostrarModal(errores.error);
					}
					document.getElementById('spanTextNombre').firstChild.nodeValue = "";
				}
		}else{
			alert ("Ha ocurrido un error, disculpe las molestias: " + e);
			document.body.style.cursor = "auto";
		}
	}catch (e){
		alert ("Ha ocurrido un error, disculpe las molestias: " + e);
		document.body.style.cursor = "auto";
		pulsado("0");
	}
}

/**
 * 
 * @param output
 */
function cambiaPaginaSesionRequest(nombre){

	if (nombre!=""){
		//Mostramos imagen buena
			muestra_oculta("imagenAnonima");
			muestra_oculta("imagenRegistrado");
		//Cambiamos de boton por el de salir de sesion
			muestra_oculta("botonIniciarSesion");
			muestra_oculta("botonDesconectar");
			document.getElementById('spanTextNombre').firstChild.nodeValue = "Bienvenid@ " + nombre;
			//Tenemos que cargar la pantalla b�sica esto es m�s dificil porque no sabemos desde la pagina de la que venimos, tendremos que tener una variable con los datos
			
		}else{
			muestra_oculta("imagenAnonima");
			muestra_oculta("imagenRegistrado");
			muestra_oculta("botonIniciarSesion");
			muestra_oculta("botonDesconectar");
			document.getElementById('spanTextNombre').firstChild.nodeValue = "";
		}
}


/**
 * 
 */
function salirSesion(){
	var datos={};
	$.post('/registraUsuarios',datos, function(output){
		cambiaPaginaSesion(output);
	});
	document.getElementById('spanTextNombre').firstChild.nodeValue = "";
}

/**
 * 
 */
function iniciarSesion(){
	document.body.style.cursor = "wait";
	var datos={
			   usuario:$("#nombreUsuario").val(),
			   contrasena:$("#contrasenaLateral").val(),
			   ingreso:"Ingreso"
		    };
	$.post('/registraUsuarios',datos, function(output){
		cambiaPaginaSesion(output,datos["ingreso"]);
		document.body.style.cursor = "auto";
	});
}

/**
 * 
 * @param id
 */
function muestra_oculta(id){
	if (document.getElementById){ //se obtiene el id
		var el = document.getElementById(id); //se define la variable "el" igual a nuestro div
		el.style.display = (el.style.display == 'none') ? 'block' : 'none'; //damos un atributo display:none que oculta el div
	}
}


/**
 * 
 */
function atras(){
	muestra_oculta('todasHabitaciones'); 
	muestra_oculta('habitacionDetalle');
}

/**
 * Esta funcion mostrar� la ventana modal que nos servira para ense�ar los diferentes errores que podamos tener
 * @param errores
 */
function mostrarModal(errores){
	try{

		$("#paginaModal").load("jsp/ModalErrores.jsp", {titulo:"Error",valoresError:String(errores)});	
		
	}catch (e){
		alert ("MODAL ERROR: " + e);
	}
	
}


/**
 * Esta funcion borra las reservas seleccionadas
 * @param idReserva
 * @returns
 */
function borrarReserva(idReserva){
	
	document.body.style.cursor = "wait";
	
	RangeDates= [];
	RangeDates.length = 0;

	
	
	params={"reserva":"Borrar","idReserva": idReserva};
	$.post('/accionReservar',params, function(output){

		if (output['_values']!=null){
			var datosCarrito=[];
			var datosDetalle=[];
			var listaReser=output['_values'].listaReservas;
			var total=output['_values'].precioCarrito;
			var datos=[];
			for (dat in listaReser){
	
				var dias=parseInt(replaceAll("/", "", listaReser[dat].fechaFin))-parseInt(replaceAll("/", "", listaReser[dat].fechaInicio));
				datos= [listaReser[dat].referencia,listaReser[dat].hb.nombre,dias,listaReser[dat].precioTotal];
				datosCarrito.push(datos);
				
			}
			var contador=0;
			for (dat in listaReser){
				
				var dias=parseInt(replaceAll("/", "", listaReser[dat].fechaFin))-parseInt(replaceAll("/", "", listaReser[dat].fechaInicio));
				datos= [listaReser[dat].referencia,contador,listaReser[dat].hb.nombre,
				        listaReser[dat].hb.descripcion, listaReser[dat].hb.ocupacionMax,
				        listaReser[dat].hb.precio,listaReser[dat].hb.fotos[0],
				        listaReser[dat].fechaInicio,listaReser[dat].fechaFin,listaReser[dat].precioTotal];		
				
				datosDetalle.push(datos);
				contador=contador+1;
			}
			
			$("#carritoCompra").load("jsp/CarritoCompra.jsp", {"datos":datos,"total":total}, function (){
				for (datos in datosCarrito){
					anadirFilaTabla("#tablaCarrito",datosCarrito[datos]);
				}
				if (total!=0){
					anadirFilaTotal("#tablaCarrito",total,3);
				}else{
					
					if (document.getElementById){ 
						var el = document.getElementById("carritoCompra"); 
						el.style.display = 'none'; 
					}
				}
				var el = document.getElementById("PaginaDetallePago"); 
				if (el.style.display=='block'){
					parametros={"tipoPaypal":"DETALLE"};
					$.post('/paypalButton',parametros, function(output){
						boton=output;
						borrarTabla("tablaDetalleCompra");
						if (total!=null && total>0){
							for (datos in datosDetalle){
								anadirFilaTabla("#tablaDetalleCompra",datosDetalle[datos]);
							}
							anadirFilaTotal("#tablaDetalleCompra",total,9);
						}else{
							if (document.getElementById){ 
								var el = document.getElementById("paginasMenu"); 
								el.style.display = 'block'; 
								var el2 = document.getElementById("registroUsuario"); 
								el2.style.display ='none'; 
								var el3 = document.getElementById("PaginaDetallePago"); 
								el3.style.display ='none'; 
							}
							
							$("#desconectar").prop("disabled",false);
							$("#RESERVA").prop("disabled",false);
							$("#pagoTotalCarrito").prop("disabled",false);
							
							reservasTiempoReal(output);
							
						}
						document.body.style.cursor = "auto";
					});
				}else{
					
					var el2 = document.getElementById("habitacionDetalle"); 
					if (el2.style.display=='block'){

						reservasTiempoReal(output);
					}
				}
			});	
		
		}else{
			if (document.getElementById){ 
				var el = document.getElementById("carritoCompra"); 
				el.style.display = 'none'; 
			}
		}
		document.body.style.cursor = "auto";
	});	
}

	
	
function detalleVentanaPago(){
	
	$("#PaginaDetallePago").load("jsp/PaginaPagoDetalle.jsp",{arrayCarritoCompra:arrayCarritoCompra,precioTotalCarrito:precioTotalCarrito}, function (){
		
		if (document.getElementById){ 
			var el = document.getElementById("paginasMenu"); 
			el.style.display = 'none'; 
			var el2 = document.getElementById("registroUsuario"); 
			el2.style.display ='none'; 
			var el3 = document.getElementById("PaginaDetallePago"); 
			el3.style.display ='block'; 
		}
		var listaDatos=[];
		var booleano=false;
		var contador=0;
		for (datos in arrayCarritoCompra){
			booleano=true;
			//La incluimos dos veces, una para el nombre de la tabla, y otra para el valor de la columna
			listaDatos.push(arrayCarritoCompra[datos].referencia);	
			listaDatos.push(contador);
			listaDatos.push(arrayCarritoCompra[datos].producto);
			listaDatos.push( arrayCarritoCompra[datos].descripcion);
			listaDatos.push(arrayCarritoCompra[datos].ocupacionMax);
			listaDatos.push(arrayCarritoCompra[datos].precioDia);		
			listaDatos.push(arrayCarritoCompra[datos].imagen);
			listaDatos.push( arrayCarritoCompra[datos].fechaInicio);
			listaDatos.push(arrayCarritoCompra[datos].fechaFin);
			listaDatos.push(arrayCarritoCompra[datos].precio);
			anadirFilaTabla("#tablaDetalleCompra",listaDatos);
			listaDatos=[]; 
			contador=contador+1;
		}
		if (booleano){
			if (precioTotalCarrito!=null){
				anadirFilaTotal("#tablaDetalleCompra",precioTotalCarrito,9);
			}
		}else{
			
			$("#desconectar").prop("disabled",false);
			$("#RESERVA").prop("disabled",false);
			$("#pagoTotalCarrito").prop("disabled",false);
			
			if (document.getElementById){ 
				var el = document.getElementById("paginasMenu"); 
				el.style.display = 'block'; 
				var el2 = document.getElementById("registroUsuario"); 
				el2.style.display ='none'; 
				var el3 = document.getElementById("PaginaDetallePago"); 
				el3.style.display ='none'; 
			}
		}
	});	
	
	
}

/**
 * 
 */
function ventanaPago(){
	document.body.style.cursor = "wait";
	params={};
	$("#desconectar").prop("disabled",true);
	$("#RESERVA").prop("disabled",true);
	$("#pagoTotalCarrito").prop("disabled",true);
	
	$.post('/paypalButton',params, function(output){
		detalleVentanaPago();
		document.body.style.cursor = "auto";
	});
	
}

//Eliminar el contenido de un div
function limpiarDiv(id)
{
    var div;
    div = document.getElementById(id);
    while(div.hasChildNodes())
    {
        div.removeChild(div.lastChild);
    }
}


function salirPagina(){
	$("#compraRealizada").val("SalirPagina");
	$("#valorMenu").val("0");
	document.forms["formMenu"].submit();
}


function enviarFactura(referencia){
	
	params={"accion":"Enviar","referencia":referencia};
	
	$.post('/pantallaAdministrador',params, function(output){

		if (output==false){
			
			alert ("Ha ocurrido un error al enviar el mensaje, int&eacute;ntalo dentro de unos minutos");
		}else{
			 location.reload();
		}
			
	});
	
}


function borrarReservaFactura(referencia){
	
	
	params={"accion":"Borrar","referencia":referencia};
	
	$.post('/pantallaAdministrador',params, function(output){

		if (output==false){
			
			alert ("Ha ocurrido un error al borrar la reserva, int&eacute;ntalo dentro de unos minutos");
		}else{
			 location.reload();
		}
			
	});
	
}

function cambiarPago(referencia,pago){
	
	if (pago=="N"){
		alert ("Se va a aceptar el pago: " + referencia);
	}else{
		alert ("Se va a rechazar el pago: " + referencia);
	}
	
	params={"accion":"Pagada","referencia":referencia,"pago":pago};
	
	$.post('/pantallaAdministrador',params, function(output){

		if (output==false){
			alert ("Ha ocurrido un error al cambiar el pago de la reserva, int&eacute;ntalo dentro de unos minutos");
		}else{
			 location.reload();
		}
			
	});
	
}

function modalUsuarios(){
	
	params={"accion":"UsuariosReserva","arrayCarritoCompra":arrayCarritoCompra,"precioTotalCarrito":precioTotalCarrito};
	
	$.post('/pantallaAdministrador',params, function(output){
		
		var salida=[];
		var completar="";
		for (i in output){
			for (x in output[i]){
				for (z in output[i][x]){
					completar=completar+output[i][x][z]+"::";
				}
				completar=completar+",";
			}
		}
		$("#paginaModal").load("jsp/ModalErrores.jsp", {titulo:"Usuarios",todosUsuarios:completar},function (){
			
		});	
		
		
	});
}

function reserAdminforUser(user){
	
	alert ("EL USUARIO SELECCIONADO ES: " + user);
}
