package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;

import com.aeat.valida.Validador;
import com.bean.Correos;
import com.bean.Usuario;
import com.constantes.CONSTANTES;
import com.google.gson.Gson;

/**
 * Controller principal de la aplicación
 * Se llama desde la página inicial de la aplicación.
 * @author Sereno
 *
 */
public class FormularioContacto extends HttpServlet implements Serializable{

	/**
	 * Constante para serializar la clase
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * Parámetro de la clase, que servirá para mostrar los logs en la consola
     */
    private static final Logger log = Logger.getLogger(FormularioContacto.class.getName());
    
    
    /**
     * Método Post del servlet
     */
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    	try {
    		log.info ("Entra en el servlet Formulario");
    		log.info(req.getParameter("email"));
    		log.info(req.getParameter("asunto"));
    		log.info(req.getParameter("mensaje"));
    		
    		//Recogemos el mail del usuario administrador
    		Usuario usuario= new Usuario();
    		usuario.setNickname("admin");
    		usuario.setContrasena("reserva");
    		usuario=usuario.devolverElemento();
    		ArrayList<String> mensajesAgradecimiento= new  ArrayList <String>();
    		
    		if (usuario!=null){
    			//Nos enviamos el correo primero a nosotros
    			String mensajeUsuario= "El usuario "+ req.getParameter("nombre") + " tiene la siguiente pregunta: " + req.getParameter("mensaje");
    			Correos correoAdmin= new Correos(usuario.getCorreoElec(), mensajeUsuario, req.getParameter("asunto"), "INFO",req.getParameter("nombre"));
    			if (correoAdmin.enviarCorreo(usuario,false)){
    				mensajeUsuario= "Se ha recibido el mensaje con texto: " + req.getParameter("mensaje");
        			Correos correoUsu= new Correos(req.getParameter("email"), mensajeUsuario, req.getParameter("asunto"), "INFO",req.getParameter("nombre"));
        			if (correoUsu.enviarCorreo(usuario,false)){
            			mensajesAgradecimiento.add("¡¡¡Muchas Gracias!!!");
            			mensajesAgradecimiento.add("Le agradecemos que quiera ponerse en contacto con nosotros. Nos pondremos en contacto con usted con la mayor brevedad posible.");
            			mensajesAgradecimiento.add("En unos instantes llegará a su correo un mensaje avisándole de que hemos recibido correctamente su cuestón.");

        			}else{
            			mensajesAgradecimiento.add("Houston, tenemos un problema");
            			mensajesAgradecimiento.add("Acaba de ocurrir un error y no ha sido posible conseguir que su consulta nos llege.");
            			mensajesAgradecimiento.add("Por favor, vuelva a realizar al consulta en unos minutos. Disculpe las molestias.");

        			}
        			
    			}
    			else{
        			mensajesAgradecimiento.add("Houston, tenemos un problema");
        			mensajesAgradecimiento.add("Acaba de ocurrir un error y no ha sido posible conseguir que su consulta nos llege.");
        			mensajesAgradecimiento.add("Por favor, vuelva a realizar al consulta en unos minutos. Disculpe las molestias.");

    			}

    		}else{
    			
    			mensajesAgradecimiento.add("Houston, tenemos un problema");
    			mensajesAgradecimiento.add("Acaba de ocurrir un error y no ha sido posible conseguir que su consulta nos llege.");
    			mensajesAgradecimiento.add("Por favor, vuelva a realizar al consulta en unos minutos. Disculpe las molestias.");

    		}
    		
    		
			//Vamos a la página de inicio
            RequestDispatcher rd =null;
            req.setAttribute("despliege", "4");
            req.setAttribute("listaMensajesAgradecimiento", mensajesAgradecimiento);
            rd=req.getRequestDispatcher("jsp/PaginaBasica.jsp");
            rd.forward(req,resp);

    		log.info("Sale del servlet Formulario");
	    	
		}catch (Exception e) {
			log.info(e.getMessage());
			log.info ("No se ha podido ir a la página web correcta porque ha ocurrido un error");
	        log.info("Redirigiendo...");
	        e.printStackTrace();
	        resp.sendRedirect("jsp/error.jsp");
    	
		}
        
    }


   /**
    * Método Get del servlet
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {  
    	doPost(request, response);  
	} 



}
