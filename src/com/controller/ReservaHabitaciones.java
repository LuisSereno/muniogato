package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;

import com.bean.Habitaciones;
import com.google.gson.Gson;

/**
 * Controller principal de la aplicación
 * Se llama desde la página inicial de la aplicación.
 * @author Sereno
 *
 */
public class ReservaHabitaciones extends HttpServlet implements Serializable{

	/**
	 * Constante para serializar la clase
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * Parámetro de la clase, que servirá para mostrar los logs en la consola
     */
    private static final Logger log = Logger.getLogger(ReservaHabitaciones.class.getName());
    
    
    /**
     * Método Post del servlet
     */
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    	try {
    		log.info ("Entra en el servlet ReservaHabitaciones");
    		Habitaciones hb = new Habitaciones ();
    		int despliege=0;

    		if (req.getParameter("tipoPantalla")==null){
    			log.info("Esta en tipo de pantalla");
	    		despliege=1;
		        HashMap<String, List<Habitaciones>> mapInformacion = new HashMap<String, List<Habitaciones>>();

		    	mapInformacion.put("habitaciones", hb.devolverTodo());
		    	
		    	req.setAttribute("habitaciones", mapInformacion);
		    	
	    		req.setAttribute("despliege", despliege);
		    	RequestDispatcher rd = req.getRequestDispatcher("jsp/PaginaBasica.jsp");
		    	rd.forward(req,resp); 
    		}else if("detalle".equals(req.getParameter("tipoPantalla"))){
    			
    			log.info("Detalle de la habitacion");
    			despliege=2;
    			hb.setReferencia(Integer.parseInt(req.getParameter("codigoHabitacion").toString()));
    			Habitaciones habiSalida= hb.devolverElemento();
    			
    			List <String[]> listaFechasReservadas=habiSalida.devolverFechas();
    			HashMap<String, List<String[]>> mapa = new HashMap<String, List<String[]>>();
    			mapa=AccionReserva.refFechasReser;
    			String identificador=String.valueOf(habiSalida.getReferencia());
    			if (mapa.get(identificador)!=null){
    				listaFechasReservadas.addAll(mapa.get(identificador));
    			}    			
    			log.info("EL dato de la habitacion es: " + habiSalida.getDescripcion() );
    			habiSalida.setFechas(listaFechasReservadas);
    			resp.setContentType("application/json");
    			PrintWriter out = resp.getWriter();
    			Gson gson = new Gson();
    			String salida=gson.toJson (habiSalida);
    			out.println(salida);
    			out.flush();
    			out.close();
    			
    		}

	    	log.info ("Sale del servlet PaginaPrincipal");
	    	
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
