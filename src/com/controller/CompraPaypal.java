package com.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;

import com.bean.Correos;
import com.bean.Reserva;
import com.bean.Usuario;
import com.constantes.CONSTANTES;

/**
 * Controller principal de la aplicaci�n
 * Se llama desde la p�gina inicial de la aplicaci�n.
 * @author Sereno
 *
 */
public class CompraPaypal extends HttpServlet implements Serializable{

	/**
	 * Constante para serializar la clase
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * Par�metro de la clase, que servir� para mostrar los logs en la consola
     */
    private static final Logger log = Logger.getLogger(CompraPaypal.class.getName());
    
    
    /**
     * M�todo Post del servlet
     */
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    	try {
    		log.info ("Entra en el servlet Comprar");
    		HttpSession sesion = req.getSession(false);
    		List <Reserva> listaReserva = new ArrayList<Reserva>();
    		listaReserva=(List<Reserva>) sesion.getAttribute("listaReservas");
    		List <String> mensajesAgradecimiento= new ArrayList<String>();
    		int numeroFactura=(int) sesion.getAttribute("numeroFactura");
    		Correos [] correos = (Correos[]) sesion.getAttribute("correosFactura");
    		
    		//Recogemos el mail del usuario administrador
    		Usuario usuario= new Usuario();
    		usuario.setNickname("admin");
    		usuario.setContrasena("reserva");
    		usuario=usuario.devolverElemento();
    		
    		Reserva reser= new Reserva();
    		Correos correo= new Correos();
    		correo=correos[0];
			//Nos enviamos el correo primero a nosotros
			if (correo.enviarCorreo(usuario,true)){
				correo= new Correos();
				correo=correos[1];
    			if (!correo.enviarCorreo(usuario,true)){
        			mensajesAgradecimiento.add("Houston, tenemos un problema");
        			mensajesAgradecimiento.add("Acaba de ocurrir un error y no ha sido posible conseguir que su consulta nos llege.");
        			mensajesAgradecimiento.add("Por favor, vuelva a realizar al consulta en unos minutos. Disculpe las molestias.");

    			}else{
    				reser.actualizarEnvioFactura(numeroFactura);
    				reser.insertarElemento(numeroFactura,-1);
    				mensajesAgradecimiento.add("¡¡¡Gracias por reservar con nosotros!!!");
    				mensajesAgradecimiento.add("Ahora recibirá un email en el que se le confirmar� su reserva.");
    				mensajesAgradecimiento.add("Esperemos que pase una pl�cida estancia con nosotros");
    			}
    			
			}
			else{
    			mensajesAgradecimiento.add("Houston, tenemos un problema");
    			mensajesAgradecimiento.add("Acaba de ocurrir un error y no ha sido posible conseguir que su consulta nos llege.");
    			mensajesAgradecimiento.add("Por favor, vuelva a realizar al consulta en unos minutos. Disculpe las molestias.");

			}

 		
			//Vaciamos todos los datos del usuario
			listaReserva.clear();
			sesion.setAttribute("listaReservas",null);
			sesion.setAttribute("precioCarrito",null);
			sesion.setAttribute("numeroFactura",null);
			sesion.setAttribute("correosFactura",null);
			sesion.setAttribute("compraRealizada","KO");
			
	        RequestDispatcher rd =null;
	        req.setAttribute("despliege", "4");
	        req.setAttribute("listaMensajesAgradecimiento", mensajesAgradecimiento);
	        rd=req.getRequestDispatcher("jsp/PaginaBasica.jsp");
	        rd.forward(req,resp);

			
	    	log.info ("Sale del servlet Compra");
	    	
		}catch (Exception e) {
			log.info(e.getMessage());
			log.info ("No se ha podido ir a la p�gina web correcta porque ha ocurrido un error");
	        log.info("Redirigiendo...");
	        e.printStackTrace();
	        resp.sendRedirect("jsp/error.jsp");
    	
		}
        
    }


   /**
    * M�todo Get del servlet
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {  
    	doPost(request, response);  
	} 
    
    
}
