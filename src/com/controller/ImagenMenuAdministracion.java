/**
 * 
 */
package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.MenuImagenes;
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
			List<MenuImagenes> datoAux = new ArrayList<MenuImagenes>();
			if (req.getParameter("imagenesAdministrador") != null) {
				out = resp.getWriter();
				resp.setContentType("application/json");				
				gson = new Gson();
				if ("guardar".equals(req.getParameter("accion"))){
					MenuImagenes[] datos = gson.fromJson(
							req.getParameter("imagenesAdministrador"),
							MenuImagenes[].class);

					for (MenuImagenes dato : datos) {
						if (!dato.getFotoMenu().equals("") && !dato.getNombreMenu().equals("")) {
							datoAux.add(dato);
						}
					}
					if (!datoAux.isEmpty()){
						MenuImagenes muin= new MenuImagenes();
						muin.setListaImagenes(datoAux);
						muin.insertarImagenes();
					}
				}else if ("borrar".equals(req.getParameter("accion"))){
					MenuImagenes mui= new MenuImagenes();
					mui.setNombreMenu(req.getParameter("imagenesAdministrador"));
					mui.borrarImagenes();
				}

			} else {
				int despliege=8;
				List <String[]> listaValoresImagenes= new ArrayList <String[]> ();				
				MenuImagenes meIm= new MenuImagenes();
				meIm.devolverTodo("menu");
				for (MenuImagenes dao:meIm.getListaImagenes()){
					listaValoresImagenes.add(dao.toArray(false));
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

