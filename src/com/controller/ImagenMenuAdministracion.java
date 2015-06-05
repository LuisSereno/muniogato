/**
 * 
 */
package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.cache.Cache;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.gson.Gson;

/**
 * @author Sereno
 *
 */
public class ImagenMenuAdministracion extends HttpServlet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1641763526206169244L;
	
    /**
     * Par�metro de la clase, que servir� para mostrar los logs en la consola
     */
    private static final Logger log = Logger.getLogger(ImagenMenuAdministracion.class.getName());
    
    public static Cache cache;
    
    /**
     * M�todo Post del servlet
     */
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

	   log.info("ENTRA EN IMAGENMENUADMINISTRACION POST");
	   
	   String salida="ok";
	   resp.setContentType("application/json");
	   PrintWriter out = resp.getWriter();
	   Gson gson = new Gson();
	   
	   try {
		   
		   DataObject[] datos=gson.fromJson(req.getParameter("imagenesAdministrador"), DataObject[].class );
		   
	       CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
           Map properties = new HashMap<>();
           properties.put(MemcacheService.SetPolicy.SET_ALWAYS, true);
           cache = cacheFactory.createCache(properties);
           
           List <DataObject>datoAux= new ArrayList<DataObject>();
           for (DataObject dato: datos){
        	   if (!dato.fotoMenu.equals("") && !dato.nombreMenu.equals("")){
        		   datoAux.add(dato);
        	   }
           }
		   
           cache.put("imagenesMenu", datoAux);
		}catch (Exception e) {
			salida="ko";
			log.info(e.getMessage());
			log.info ("No se ha podido ir a la p�gina web correcta porque ha ocurrido un error");
	        log.info("Redirigiendo...");
	        e.printStackTrace();
		} finally{
			out.println(gson.toJson (salida));
			out.flush();
			out.close();
		} 
   }
   
   
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
	   
   }

}

class DataObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String nombreMenu;
	
	public String fotoMenu;
	 
	public String [] toArray() {
		String [] valorSalida={this.nombreMenu,this.fotoMenu};
		return valorSalida;
	}

}
