package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;

import com.aeat.valida.Validador;
import com.bean.Correos;
import com.bean.Reserva;
import com.bean.Usuario;
import com.constantes.CONSTANTES;
import com.google.gson.Gson;

/**
 * Controller principal de la aplicaci�n
 * Se llama desde la p�gina inicial de la aplicaci�n.
 * @author Sereno
 *
 */
public class Administrador extends HttpServlet implements Serializable{

	/**
	 * Constante para serializar la clase
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * Par�metro de la clase, que servir� para mostrar los logs en la consola
     */
    private static final Logger log = Logger.getLogger(Administrador.class.getName());
    
    
    /**
     * M�todo Post del servlet
     */
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    	try {
    		log.info ("Entra en el servlet Administrador");
			Reserva reser= new Reserva();
			Usuario usu= new Usuario();
			log.info("La acción es: " + req.getParameter("accion"));
    		if (req.getParameter("accion")==null || "".equals(req.getParameter("accion"))){
	    		HashMap<String, List> datosAdministrador= new HashMap<String, List>();
	
				datosAdministrador.put("reservas", reser.devolverTodo());
				datosAdministrador.put("facturas", reser.devolverTodasFacturas());
				datosAdministrador.put("usuarios", usu.devolverTodo());
	            RequestDispatcher rd =null;
	            req.setAttribute("datosAdministrador", datosAdministrador);
	
	            rd=req.getRequestDispatcher("jsp/Administrador.jsp");
	            rd.forward(req,resp);
    		}else if ("Enviar".equals(req.getParameter("accion"))){
    			reser.setNumFactura(Integer.parseInt(req.getParameter("referencia")));
    			
    			boolean resultado=reser.enviarFactura();
    			Gson gson= new Gson();
    			String salida=gson.toJson (resultado);
    			resp.setContentType("application/json");
    			PrintWriter out = resp.getWriter();
    			out.println(salida);
    			out.flush();
    			out.close();
    		}else if("Borrar".equals(req.getParameter("accion"))){
    			reser.setNumFactura(Integer.parseInt(req.getParameter("referencia")));
    			boolean resultado=reser.borrarFacturaCorreoReserva();
    			Gson gson= new Gson();
    			String salida=gson.toJson (resultado);
    			resp.setContentType("application/json");
    			PrintWriter out = resp.getWriter();
    			out.println(salida);
    			out.flush();
    			out.close();
    			
    		}else if("Pagada".equals(req.getParameter("accion"))){
    			reser.setNumFactura(Integer.parseInt(req.getParameter("referencia")));
    			boolean resultado=false;
    			if ("Y".equals(req.getParameter("pago"))){
    				if(reser.facturaPagada("N")!=-1){
    					resultado=true;
    				}
    			}else{
    				if(reser.facturaPagada("Y")!=-1){
    					resultado=true;
    				}
    			}
    			
    			Gson gson= new Gson();
    			String salida=gson.toJson (resultado);
    			resp.setContentType("application/json");
    			PrintWriter out = resp.getWriter();
    			out.println(salida);
    			out.flush();
    			out.close();
    			
    		}else if("UsuariosReserva".equals(req.getParameter("accion"))){
    			log.info("Entra en reservar");
	    		HashMap<String, List> datosAdministrador= new HashMap<String, List>();
	    		
				datosAdministrador.put("usuarios", usu.devolverTodo());
    			Gson gson= new Gson();
    			String salida=gson.toJson (datosAdministrador);
    			resp.setContentType("application/json");
    			PrintWriter out = resp.getWriter();
    			out.println(salida);
    			out.flush();
    			out.close();
    			
    		}

    		log.info("Sale del servlet Administrador");
	    	
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
