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
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.cache.Cache;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.Habitaciones;
import com.constantes.CONSTANTES;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;
import com.google.gson.Gson;

/**
 * @author Sereno
 *
 */
public class ImagenMenuAdministracion extends HttpServlet implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1641763526206169244L;

	/**
	 * Par�metro de la clase, que servir� para mostrar los logs en la consola
	 */
	private static final Logger log = Logger
			.getLogger(ImagenMenuAdministracion.class.getName());

	public static Cache cache;

	/**
	 * M�todo Post del servlet
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		log.info("ENTRA EN IMAGENMENUADMINISTRACION POST");
		String salida = "ok";
		PrintWriter out = resp.getWriter();
		Gson gson = null;
		try {
			List<DataObject> datoAux = new ArrayList<DataObject>();
			if (req.getParameter("imagenesAdministrador") != null) {
				
				out = resp.getWriter();
				resp.setContentType("application/json");				
				CacheFactory cacheFactory = CacheManager.getInstance()
						.getCacheFactory();
				gson = new Gson();
				Map properties = new HashMap<>();
				properties.put(MemcacheService.SetPolicy.SET_ALWAYS, true);
		        properties.put(GCacheFactory.EXPIRATION_DELTA, TimeUnit.DAYS.toSeconds(20));
				cache = cacheFactory.createCache(properties);
				
				DataObject[] datos = gson.fromJson(
						req.getParameter("imagenesAdministrador"),
						DataObject[].class);

				for (DataObject dato : datos) {
					if (!dato.fotoMenu.equals("") && !dato.nombreMenu.equals("")) {
						dato.fotoMenu= CONSTANTES.DRIVEIMAGENES+ dato.fotoMenu;
						datoAux.add(dato);
					}
				}
				cache.put("imagenesMenu", datoAux);

			} else {
				int despliege=8;
				List <String[]> listaValoresImagenes= new ArrayList <String[]> ();				
				if (ImagenMenuAdministracion.cache!=null){
					List <DataObject> valor = (ArrayList<DataObject>)ImagenMenuAdministracion.cache.get("imagenesMenu");
					for (DataObject dao:valor){
						dao.fotoMenu=dao.fotoMenu.substring(dao.fotoMenu.indexOf(CONSTANTES.DRIVEIMAGENES), dao.fotoMenu.length());
						listaValoresImagenes.add(dao.toArray());
					}
				}
		    	req.setAttribute("imagenesMenu", listaValoresImagenes);
		    	req.setAttribute("despliege", despliege);
		    	RequestDispatcher rd = req.getRequestDispatcher("jsp/PaginaBasica.jsp");
		    	rd.forward(req,resp); 
			}

		} catch (Exception e) {
			salida = "ko";
			log.info(e.getMessage());
			log.info("No se ha podido ir a la p�gina web correcta porque ha ocurrido un error");
			log.info("Redirigiendo...");
			e.printStackTrace();
		} finally {
			if (req.getParameter("imagenesAdministrador") != null) {
				out.println(gson.toJson(salida));
				out.flush();
				out.close();
			}			
		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

	}

}

class DataObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String nombreMenu;

	public String fotoMenu;

	public String[] toArray() {
		String[] valorSalida = { this.nombreMenu, this.fotoMenu };
		return valorSalida;
	}

}
