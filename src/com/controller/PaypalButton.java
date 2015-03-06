package com.controller;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;
 




import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 




import com.bean.Correos;
import com.bean.Reserva;
import com.bean.Usuario;
import com.constantes.CONSTANTES;
import com.google.gson.Gson;
 
/**
 * Servlet implementation class PaypalButton
 */
public class PaypalButton extends HttpServlet implements Serializable{
    private static final long serialVersionUID = 1L;
        
    
    /**
     * Par�metro de la clase, que servir� para mostrar los logs en la consola
     */
    private static final Logger log = Logger.getLogger(PaypalButton.class.getName());
    
    /**
     * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	try{
    		
    		//Primero cogemos y vamos a la base de datos a ver si esta disponbible, luego a paypal y luego correo con factura
			HttpSession sesion = request.getSession(false);
			float total=  (float) sesion.getAttribute("precioCarrito");
			List <String> cadenaErrores = new ArrayList<String>();
			Gson gson = new Gson();
			String salida="";
			String tipoPay="";
			if (request.getParameter("tipoPaypal")!=null){
				tipoPay=request.getParameter("tipoPaypal");
			}
			if ("DETALLE".equals(tipoPay)){
				log.warning("LLEGA AL DETALLE");
			}else{
				if (total>0){
		    		boolean envioMail=true;
		    		int reservaError=0;
		    		List <Reserva> listaReserva = new ArrayList<Reserva>();
		    		List <Integer> listaBorrar = new ArrayList <Integer> ();
		    		String habitacionErronea="";
		    		//Insertamos en la base de datos
		    		listaReserva=(List<Reserva>) sesion.getAttribute("listaReservas");
		    		//Para insertar el elemento, tendremos que recuperar el n�mero de la factura que tenemos que aplicar. Introducirlo en la sesion, para almacenar el correo en el.
		    		if (listaReserva.size()>0){
		    			salida = reserva(sesion, total, cadenaErrores, gson, salida, envioMail, reservaError, listaReserva,	listaBorrar, habitacionErronea);
		    		}else{
		    			cadenaErrores.add("Acaba de ocurrir un error, por favor, vuelva a realizar el alta en unos minutos.");
		    		}
				}else{
					//Mostrariamos error porque esa habitacion no se puede reservar ese dia
					cadenaErrores.add("Lo sentimos, se ha producido un error al generar el detalle del pago");
					salida=gson.toJson (cadenaErrores);
				}
			}
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.println(salida);
			out.flush();
			out.close();
    	}catch(Exception e){
			log.info(e.getMessage());
			log.info ("No se ha podido ir a la p�gina web correcta porque ha ocurrido un error");
	        log.info("Redirigiendo...");
	        e.printStackTrace();
	        response.sendRedirect("jsp/error.jsp");
    	}
         
    }


	/**
	 * @param sesion
	 * @param total
	 * @param cadenaErrores
	 * @param gson
	 * @param salida
	 * @param envioMail
	 * @param reservaError
	 * @param listaReserva
	 * @param listaBorrar
	 * @param habitacionErronea
	 * @return
	 */
	private String reserva(HttpSession sesion, float total,	List<String> cadenaErrores, Gson gson, String salida,boolean envioMail,
			int reservaError, List<Reserva> listaReserva,List<Integer> listaBorrar, String habitacionErronea) {
		
		Reserva reser= new Reserva();
		List <Integer> numerosReservas= new ArrayList <Integer>();
		for (int i=0;i<listaReserva.size()&&envioMail;i++){
			reser= listaReserva.get(i);
			int numRe=reser.insertarElemento();
			if(numRe==-1){
				habitacionErronea=reser.getHb().getNombre();
				envioMail=false;
				reservaError=i;
			}
			numerosReservas.add(numRe);
			reser.setReferencia(numRe);
		}
		if (!envioMail){
			for (int i=0;i<reservaError;i++){
				reser= listaReserva.get(i);
				listaBorrar.add(reser.devolverElemento().getReferencia());
			}
			for (int i=0;i<listaBorrar.size();i++){
				reser.borrarReserva(listaBorrar.get(i));
			}
			cadenaErrores.add("La habitacion "+ habitacionErronea + " es posible que la habitacion haya sido ocupada mientras usted decidia");
		}else{
			salida = botonFacturaCorreo(sesion, total, cadenaErrores, gson,	listaReserva, reser,numerosReservas);
		}
		return salida;
	}


	/**
	 * @param sesion
	 * @param total
	 * @param cadenaErrores
	 * @param gson
	 * @param listaReserva
	 * @param reser
	 * @return
	 */
	private String botonFacturaCorreo(HttpSession sesion, float total,	List<String> cadenaErrores, Gson gson, 
			List<Reserva> listaReserva,Reserva reser,List <Integer> numerosReservas) {
		
		String salida = null;
		try {
			//CREAMOS LA FACTURA QUE LUEGO, SINO, BORRAREMOS
			//Recogemos el mail del usuario administrador
			Usuario usuario= new Usuario();
			usuario.setNickname("admin");
			usuario.setContrasena("reserva");
			usuario=usuario.devolverElemento();
			Correos corrAdmin=new Correos();
			int referenciaCorreoAdmin=-1;
			if (usuario!=null){
				corrAdmin= new Correos(usuario.getCorreoElec(),"" , CONSTANTES.ASUNTORESERVA, "RESERVA", listaReserva.get(0).getUsu().getNombre());
				corrAdmin.setEstado("No enviado");
				referenciaCorreoAdmin=corrAdmin.insertarElemento();
				
			}
			Correos corrUsu= new Correos(listaReserva.get(0).getUsu().getCorreoElec(), "", CONSTANTES.ASUNTORESERVA, "RESERVA", listaReserva.get(0).getUsu().getNombre());
			corrUsu.setEstado("No enviado");
			int referenciaCorreoUsu=corrUsu.insertarElemento();
			
			//Necesitamos coger el numero de la factura. Para ello creamos los correos vacios, asignamos las facturas a esos correos, y le a�adimos luego los textos.
			String cadenaUsurario=this.cadenaFactura(referenciaCorreoUsu,sesion);
			String cadenaAdministrador="FACTURA A NOMBRE DE: " +listaReserva.get(0).getUsu().getNombre() + "\n" + cadenaUsurario;
			corrAdmin.setMensaje(cadenaAdministrador);
			corrUsu.setMensaje(cadenaUsurario);
			corrAdmin.setReferencia(referenciaCorreoAdmin);
			corrUsu.setReferencia(referenciaCorreoUsu);
			corrAdmin.actualizarElemento();
			corrUsu.actualizarElemento();
			
			Correos [] correos = new Correos [2];
			correos[0]=corrAdmin;
			correos[1]=corrUsu;
			
			int numeroFactura=-1;
			numeroFactura=reser.insertarElemento(numeroFactura, referenciaCorreoUsu);
			
			//actualizamos las reservas
			
			for (int i=0;i<numerosReservas.size();i++){
				reser=new Reserva();
				reser.setReferencia(numerosReservas.get(i));
				reser.setNumFactura(numeroFactura);
				reser.actualizarReserva();
			}
			
			sesion.setAttribute("numeroFactura", numeroFactura);
			sesion.setAttribute("correosFactura", correos);
			sesion.setAttribute("compraRealizada", "OK");
		} catch (Exception e) {
			//Mostrariamos error porque esa habitacion no se puede reservar ese dia
			cadenaErrores.add("Lo sentimos, se ha producido un error al generar el detalle del pago");
			salida=gson.toJson (cadenaErrores);
		}
		return salida;
	}
    
    
   private String cadenaFactura(int numFactura,HttpSession sesion){
	 	
   	GregorianCalendar calendario = new GregorianCalendar();
   	int m = calendario.get(GregorianCalendar.MONTH) + 1;
   	 int d = calendario.get(GregorianCalendar.DATE);
   	 String mm = Integer.toString(m);
   	 String dd = Integer.toString(d);
   	 String fechaActual= "" +calendario.get(GregorianCalendar.YEAR)+"/" + (m < 10 ? "0" + mm : mm) +"/" +   (d < 10 ? "0" + dd : dd);
   	 
   	 
   	Float cantidadIVA= (((Float) sesion.getAttribute("precioCarrito") * Float.valueOf(CONSTANTES.IMPUESTOS))/100)+(Float) sesion.getAttribute("precioCarrito");
   	String cantidadTotal=String.valueOf(cantidadIVA);
   	String numeroFactura="";
   	if (numFactura<10){
   		numeroFactura="000" + numFactura;
   	}else if (numFactura<100){
   		numeroFactura="00" + numFactura;
   	}else if (numFactura<1000){
   		numeroFactura="0" + numFactura;
   	}else{
   		numeroFactura= String.valueOf(numFactura);
   	}
   	List <Reserva> lista= (List<Reserva>) sesion.getAttribute("listaReservas");
   	String primeraParteFactura="";
   	String segundaParteFactura="";
   	String terceraParteFactura="";
   	String factura="";

   	primeraParteFactura="<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>"+
				"<html>"+
				"<head>"+
				"	<meta content='text/html; charset=ISO-8859-1'"+
				"	http-equiv='content-type'>"+
				"	<title>Factura</title>"+
				"</head>"+
				"<body>"+			"	<div style='text-align: center;'><big><big><big>RESERVA Nº "+ numeroFactura+"</big></big></big>"+
				"	<br>"+
				"	<br>"+
				"	<div id='divFacturaArribaIzquierda' style='text-align: left; float: left; width: 50%;'>"+
				"		<br>"+
				"		<br>"+
				"		<br>"+
				"		<br>"+
				"		<br>"+
				"		<br>"+
				"		<br>"+
				"		<br>"+
				"		<br>"+
				"		<br>"+
				"		Hotel Rural Gran Maestre<br>"+
				"		NIF:XXXXXXXXXX<br>"+
				"		Cabeza del Buey (Badajoz)<br>"+
				"		CP:06600<br>"+
				"		Carrt. EX-104<br>"+
				"		Extremadura<br>"+
				"		<br>"+
				"		<br>"+
				"	</div>"+
				"	<div id='divFacturaArribaDerecha' style='text-align: right; float: right; width: 50%;'>"+
				"		<img style='width: 300px; height: 100px;' src=\'cid:logotipo\'	alt='' align='top'>"+
				"		<br>"+
				"		<br>"+
				"		<br>"+
				"		FECHA:"+fechaActual +
				"		<br>"+
				"		<br>"+
				"	</div>"+
				"	<table style='text-align: left; width: 100%;' border='5' cellpadding='2' cellspacing='2'>"+
				"		<tbody>"+
				"			<tr>"+				"				<td	style='vertical-align: top; width: 20%; background-color: rgb(0, 204, 204);'>Nombre Habitación<br></td>"+
				"				<td	style='vertical-align: top; width: 30%; background-color: rgb(0, 204, 204);'>Fecha Inicio Reserva<br></td>"+
				"				<td	style='vertical-align: top; width: 30%; background-color: rgb(0, 204, 204);'>Fecha Fin Reserva<br></td>"+
				"				<td	style='vertical-align: top; width: 20%; background-color: rgb(0, 204, 204);'>Precio<br></td>"+
				"			</tr>";
    	
    			
    		for (int i=0;i<lista.size();i++){
      			segundaParteFactura= segundaParteFactura+ "<tr>"+
      					"				<td style='vertical-align: top; width: 20%;'>"+lista.get(i).getHb().getNombre()+"<br></td>"+
      					"				<td style='vertical-align: top; width: 30%;'>"+lista.get(i).getFechaInicio()+"<br></td>"+
      					"				<td style='vertical-align: top; width: 30%;'>"+lista.get(i).getFechaFin()+"<br></td>"+
      					"				<td style='vertical-align: top; width: 20%;'>"+lista.get(i).getPrecioTotal()+"<br></td>"+
      					"			</tr>";
    		}
    			
    			
			terceraParteFactura=
			"			<tr align='right'>"+
			"				<td colspan='1' rowspan='1' style='vertical-align: top;'><br></td>"+
			"				<td colspan='1' style='vertical-align: top;'><br></td>"+
			"				<td colspan='1' style='vertical-align: top;'><br></td>"+
			"				<td	style='vertical-align: top; background-color: rgb(0, 204, 204);'>TOTAL + IVA: 21%: "+ cantidadTotal+"</td>"+
			"			</tr>"+
			"		</tbody>"+
			"	</table>"+
			"	</div>"+
			"</body>"+
			"</html>";
	
			factura=primeraParteFactura+segundaParteFactura+terceraParteFactura;
	return factura;
   }

 
}