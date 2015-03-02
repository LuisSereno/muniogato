package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.*;

import com.aeat.valida.Validador;
import com.bean.Correos;
import com.bean.Usuario;
import com.constantes.CONSTANTES;
import com.google.appengine.api.users.User;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

/**
 * Controller principal de la aplicación
 * Se llama desde la página inicial de la aplicación.
 * @author Sereno
 *
 */
public class AccionUsuarios extends HttpServlet implements Serializable{

	/**
	 * Constante para serializar la clase
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * Parámetro de la clase, que servirá para mostrar los logs en la consola
     */
    private static final Logger log = Logger.getLogger(AccionUsuarios.class.getName());
    
    
    /**
     * Método Post del servlet
     */
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    	try {
    		log.info ("Entra en el servlet AccionUsuarios");
    		log.info ("ESTOS SON MIS COJONES TOREROS!!!");
    		HttpSession sesion = req.getSession(true);
    		Session sesionLocal= new Session();
    		
    		if (req.getParameter("ingreso")!=null && req.getParameter("ingreso")!=""&& "Alta".equals(req.getParameter("ingreso"))){
    			log.info ("Va a insertarse un nuevo usuario en la aplicación");
    			Usuario usu= new Usuario (req.getParameter("nombre"), req.getParameter("primerApellido"), req.getParameter("segundoApellido"), 
        				req.getParameter("dni"), req.getParameter("calle"), Integer.parseInt(req.getParameter("numero")), req.getParameter("ciudad"), req.getParameter("provincia"), req.getParameter("pais"), 
        				Integer.parseInt(req.getParameter("telefono")), req.getParameter("usuario"), req.getParameter("contrasena"), req.getParameter("email"));
	    		String campoErroneo=this.comprobarCampos(usu);
	    		boolean comprobacion="".equals(campoErroneo.trim());
	    		log.info ("El campo erróneo es: " + campoErroneo);
	    		log.info("La comprobacion nos dice que: " + comprobacion);
	    		if (comprobacion==true){
	    			log.info("Si es true debemos de estar aquí");
	    			if (usu.insertarElemento()==-1){
	    				log.info ("El usuario ya existe");
	    				sesion.setAttribute("nombre", "");
			    		sesion.setAttribute("usuario", "");
			    		sesion.setAttribute("error", "El usuario ya existe");
	    			}else{
	    				log.info ("Se ha dado de alta correctamente el usuario");
	    				String mensaje="Te acabas de registrar en la web HOTEL RURAL GRAN MAESTRE, tu usuario es: " + req.getParameter("usuario") +" y tu contraseña es: " + req.getParameter("contrasena");
	    				String asunto="Registro Hotel Rural Gran Maestre";
	    				Correos corr= new Correos(req.getParameter("email"), mensaje, asunto, "INFO", req.getParameter("nombre"));
	    	    		Usuario usuario= new Usuario();
	    	    		usuario.setNickname("admin");
	    	    		usuario.setContrasena("reserva");
	    	    		usuario=usuario.devolverElemento();
	    	    		corr.setEstado("No enviado");
	    	    		int referencia = corr.insertarElemento();
	    	    		corr.setReferencia(referencia);
	    				corr.enviarCorreo(usuario, false);
	    				log.info ("Acabamos de enviar el correo al usuario");
	    				sesion.setAttribute("nombre", usu.getNombre());
	    				sesion.setAttribute("usuario", usu.getNickname());
	    				sesion.setAttribute("contrasena", usu.getContrasena());
	    	    		sesion.setAttribute("error", "");
	    			}
	    		}else{
	    			log.info ("Hay un campo erroneo y no se realiza el alta");
		    		sesion.setAttribute("nombre", "");
		    		sesion.setAttribute("error", "El campo "+campoErroneo+" es erróneo");
	    		}
	    		

    		}else if ("Ingreso".equals(req.getParameter("ingreso"))){
    			log.info ("Va a realizarse un ingreso");
    			Usuario usu= new Usuario();
    			usu.setNickname(req.getParameter("usuario"));
    			usu.setContrasena(req.getParameter("contrasena"));
    			Usuario usuCompleto = new Usuario();
    			usuCompleto = usu.devolverElemento();
    			if (usuCompleto!=null){
    				log.info ("Se ingresa correctamente");
    				sesion.setAttribute("nombre", usuCompleto.getNombre());
    				sesion.setAttribute("usuario", usu.getNickname());
    				sesion.setAttribute("contrasena", usu.getContrasena());
    				sesion.setAttribute("error", "");
    			}else{
    				log.info ("Error al ingresar en la aplicación");
    				sesion.setAttribute("nombre", "");
    				sesion.setAttribute("usuario", "");
    				sesion.setAttribute("contrasena", "");
    				sesion.setAttribute("error", "El usuario no existe, por favor inténtelo de nuevo.");
    			}
    			
    		}else{
    			log.info ("Salimos de la sesión");
    			sesion.invalidate();
    		}
    		
    		sesionLocal.copiarSesion(sesion);
 			resp.setContentType("application/json");
			PrintWriter out = resp.getWriter();
			Gson gson = new Gson();
			log.info("ANTES DE SALIDA");
			String salida=gson.toJson(sesionLocal); //,HttpSession.class
			log.info("SALIDA TIENE LOS SIGUIENTES VALORES: " + salida);
			out.println(salida);
			out.flush();
			out.close();		
    		
	    	log.info ("Sale del servlet AccionUsuarios");
	    	
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

    
    private String comprobarCampos (Usuario usu){
    	 String campoErroneo="";
    	 boolean todoBien=true;
    	if (todoBien){
	    	if (usu.getApellido1().indexOf("Escriba")!= -1 &&!(usu.getApellido1().length()>CONSTANTES.TAMANOCADENAMIN && usu.getApellido1().length()<=CONSTANTES.TAMANOCADENAMAX)){
	    		todoBien= false;
	    		campoErroneo="Primer Apellido";
	    	}
    	}
    	if (todoBien){
	    	if (usu.getApellido2().indexOf("Escriba")!= -1 &&!(usu.getApellido2().length()>CONSTANTES.TAMANOCADENAMIN && usu.getApellido2().length()<=CONSTANTES.TAMANOCADENAMAX)){
	    		todoBien= false;
	    		campoErroneo="Segundo Apellido";
	    	}
    	}
    	if (todoBien){
	    	if (usu.getNombre().indexOf("Escriba")!= -1 &&!(usu.getNombre().length()>CONSTANTES.TAMANOCADENAMIN && usu.getNombre().length()<=CONSTANTES.TAMANOCADENAMAX)){
	    		todoBien= false;
	    		campoErroneo="Nombre";
	    	}
    	}
    	if (todoBien){
	    	if (usu.getProvincia().indexOf("Escriba")!= -1 &&!(usu.getProvincia().length()>CONSTANTES.TAMANOCADENAMIN && usu.getProvincia().length()<=CONSTANTES.TAMANOCADENAMAX)){
	    		todoBien= false;
	    		campoErroneo="Provincia";
	    	}
    	}
    	if (todoBien){
	    	if (usu.getCalle().indexOf("Escriba")!= -1 &&!(usu.getCalle().length()>CONSTANTES.TAMANOCALLEMIN && usu.getCalle().length()<=CONSTANTES.TAMANOCADENAMAX)){
	    		todoBien= false;
	    		campoErroneo="Calle";
	    	}
    	}
    	if (todoBien){
	    	if (usu.getCiudad().indexOf("Escriba")!= -1 &&!(usu.getCiudad().length()>CONSTANTES.TAMANOCADENAMIN && usu.getCiudad().length()<=CONSTANTES.TAMANODIRECCIONMAX)){
	    		todoBien= false;
	    		campoErroneo="Ciudad";
	    	}
    	}
    	if (todoBien){
	    	if (usu.getNickname().indexOf("Escriba")!= -1 &&!(usu.getNickname().length()>CONSTANTES.TAMANOCADENAMIN && usu.getNickname().length()<=CONSTANTES.TAMANOUSUARIOMAX)){
	    		todoBien= false;
	    		campoErroneo="Usuario";
	    	}
    	}
    	if (todoBien){
	    	if (usu.getPais().indexOf("Escriba")!= -1 &&!(usu.getPais().length()>CONSTANTES.TAMANOCADENAMIN && usu.getPais().length()<=CONSTANTES.TAMANOUSUARIOMAX)){
	    		todoBien= false;
	    		campoErroneo="País";
	    	}
    	}
    	if (todoBien){
	    	if (usu.getContrasena().indexOf("Escriba")!= -1 &&!(usu.getContrasena().length()>CONSTANTES.TAMANOCONTRASENAMINIMA && usu.getContrasena().length()<=CONSTANTES.TAMANOUSUARIOMAX)){
	    		todoBien= false;
	    		campoErroneo="Contraseña";
	    	}
    	}
    	if (todoBien){
	    	if (!(usu.getTelefono()>CONSTANTES.TAMANOTELEFONOMIN && usu.getTelefono()<=CONSTANTES.TAMANOTELEFONOMAX)){
	    		todoBien= false;
	    		campoErroneo="Teléfono";
	    	}
    	}
    	if (todoBien){
	    	if (!(usu.getNumero()>CONSTANTES.TAMANONUMEROMIN && usu.getNumero()<=CONSTANTES.TAMANONUMEROMAX)){
	    		todoBien= false;
	    		campoErroneo="Número";
	    	}
    	}
    	if (todoBien){
    		
    		Validador val= new Validador();
    		if (val.checkNif(usu.getDni().toUpperCase())!=1){
    			todoBien=false;
    			campoErroneo="DNI";
    		}
    	}
    	if (todoBien){
    			
    		if (!this.isEmail(usu.getCorreoElec())){
    			todoBien=false;
    			campoErroneo="Correo Electrónico";
    		}
    	}
    	return campoErroneo;
    }

    //metodo para validar correo electronio
    private boolean isEmail(String correo) {
        Pattern pat = null;
        Matcher mat = null;        
        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        mat = pat.matcher(correo);
        if (mat.find()) {
            System.out.println("[" + mat.group() + "]");
            return true;
        }else{
            return false;
        }        
    }

}

class Session implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6211757404374883335L;
	
	
	@SerializedName("name")
    protected String _name;
    @SerializedName("_values")
    protected Map<String,Object> _values = new HashMap<String,Object>();
    
    protected void copiarSesion (HttpSession sesion){
    	
    	Enumeration<String> enumerado=  sesion.getAttributeNames();
    	
    	while (enumerado.hasMoreElements()){
    		String nombre=enumerado.nextElement();
    		_values.put(nombre, sesion.getAttribute(nombre));
    	}
    	
    }
    
    
 }
