package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;

import com.aeat.valida.Validador;
import com.bean.Usuario;
import com.constantes.CONSTANTES;
import com.google.gson.Gson;

/**
 * Controller principal de la aplicación
 * Se llama desde la página inicial de la aplicación.
 * @author Sereno
 *
 */
public class Menu extends HttpServlet implements Serializable{

	/**
	 * Constante para serializar la clase
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * Parámetro de la clase, que servirá para mostrar los logs en la consola
     */
    private static final Logger log = Logger.getLogger(Menu.class.getName());
    
    
    /**
     * Método Post del servlet
     */
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    	try {
    		log.info ("Entra en el servlet Menu");
    		HttpSession sesion = req.getSession(false);
    		if (sesion!=null){
    			if ("OK".equals(sesion.getAttribute("compraRealizada"))){
    				CancelaPaypal cp= new CancelaPaypal();
    				cp.borrarReserva(sesion);
    			}
    		}
    		int valor=0;
    		if (req.getParameter("valorMenu")!=null){
    			valor=Integer.valueOf(req.getParameter("valorMenu"));
    		}
    		if (valor==1){
    			ReservaHabitaciones rh= new ReservaHabitaciones();
    			rh.doPost(req, resp);
    		}else if (valor==5){
    			Administrador admin= new Administrador();
    			admin.doPost(req, resp);
    		}else{
	            RequestDispatcher rd =null;
	            req.setAttribute("despliege", valor);
	
	            rd=req.getRequestDispatcher("jsp/PaginaBasica.jsp");
	            rd.forward(req,resp);
    		}

    		log.info("Sale del servlet Menu");
	    	
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
