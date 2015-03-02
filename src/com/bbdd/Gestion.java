//package com.src.bbdd;
//
//import java.io.Serializable;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Logger;
//import com.src.constantes.CONSTANTES;
//
///**
// * Esta clase gestiona la conexión con la base de datos.
// * @author Sereno
// *
// */
//public class Gestion implements Serializable{
//
//	
//	/**
//	 * Constante para serializar la clase
//	 */
//    private static final long serialVersionUID = 1L;
//    
//    /**
//     * Parámetro de la clase, que servirá para mostrar los logs en la consola
//     */
//    private static final Logger log = Logger.getLogger(Gestion.class.getName());
//    
//    /**
//     * Constructor por defecto de la clase
//     */
//	public Gestion() {
//
//	}
//
//	/**
//	 * Función que recoge los datos de todos los países	
//	 * @return Devuelve una lista con los datos que se nos devuelven de la base de datos.
//	 * @throws SQLException
//	 */
//	public List <String> recogerDatosPaises() throws SQLException{
//		log.info ("Hemos entrado en recogerDatosPaises");
//        CRUD bbdd= new CRUD();
//        String [] parametros = null;
//        List <String> paises = new ArrayList <String>(); 
//        
//        log.info ("Hemos conectado con paises");
//        paises = bbdd.leer(CONSTANTES.LECTURAPAISES,parametros);
//        log.info ("Hemos leido paises");
//		
//		return paises;
//	}
//	
//	/**
//	 * Función que recoge los datos de todos los colores	
//	 * @return Devuelve una lista con los datos que se nos devuelven de la base de datos.
//	 * @throws SQLException
//	 */
//	public List <String> recogerDatosColores () throws SQLException{
//		log.info ("Hemos entrado en recogerDatosColores");
//        CRUD bbdd= new CRUD();
//        String [] parametros = null;
//        List <String> colores = new ArrayList <String> ();
//
//        log.info ("Hemos conectado con colores");
//        colores = bbdd.leer(CONSTANTES.LECTURACOLORES,parametros);
//        log.info ("Hemos leido colores");
//
//        return colores;
//	}
//	
//	
//	/**
//	 * Función que recoge los datos de todos los colores	
//	 * @return Devuelve una lista con los datos que se nos devuelven de la base de datos.
//	 * @throws SQLException
//	 */
//	public List <String> recogerDatosColoresMarkers () throws SQLException{
//		log.info ("Hemos entrado en recogerDatosColores");
//        CRUD bbdd= new CRUD();
//        String [] parametros = null;
//        List <String> coloresMarkers = new ArrayList <String> ();
//
//        log.info ("Hemos conectado con colores");
//        coloresMarkers = bbdd.leer(CONSTANTES.LECTURACOLORESMARKERS,parametros);
//        log.info ("Hemos leido colores");
//
//        return coloresMarkers;
//	}
//	
//	
//	/**
//	 * Función que recoge los datos de todas las tiendas	
//	 * @return Devuelve una lista con los datos que se nos devuelven de la base de datos.
//	 * @throws SQLException
//	 */
//	public List <String> recogerDatosTiendas () throws SQLException{
//		log.info ("Hemos entrado en recogerDatosTiendas");
//		String [] parametros = null;
//        CRUD bbdd= new CRUD();
//        
//        List <String> tiendas = new ArrayList <String> ();
//        log.info ("Hemos conectado con tiendas");
//        tiendas = bbdd.leer(CONSTANTES.LECTURATIENDAS,parametros);
//        log.info ("Hemos leido tiendas");
//		
//		return tiendas;
//	}
//	
//	/**
//	 * Función que recoge los nombres de todos los países
//	 * @return Devuelve una lista con los datos que se nos devuelven de la base de datos.
//	 * @throws SQLException
//	 */
//	public List <String> nombrePaises () throws SQLException{
//		log.info ("Hemos entrado en nombrePais");
//		String [] parametros = null;
//        CRUD bbdd= new CRUD();
//        
//        List <String> paises = new ArrayList <String> ();
//        log.info ("Hemos conectado con los nombres de los paises");
//        paises = bbdd.leer(CONSTANTES.NOMBREPAISES,parametros);
//        log.info ("Hemos leido los nombres de los paises");
//		
//		return paises;
//	}
//	
//	/**
//	 * Función que recoge las abreviaturas de los países	
//	 * @return Devuelve una lista con los datos que se nos devuelven de la base de datos.
//	 * @throws SQLException
//	 */
//	public List <String> abreviaturaPaises () throws SQLException{
//		log.info ("Hemos entrado en abreviaturasPaises");
//		String [] parametros = null;
//        CRUD bbdd= new CRUD();
//        
//        List <String> tiendas = new ArrayList <String> ();
//        log.info ("Hemos conectado con las abreviaturas de los paises");
//        tiendas = bbdd.leer(CONSTANTES.ABREVIATURAPAISES,parametros);
//        log.info ("Hemos leido las abreviaturas de los paises");
//		
//		return tiendas;
//	}
//	
//	
//	/**
//	 * funcion que devuelve el numero de tiendas de un país
//	 * @param abPais este par�metro contendra la abreviatura del país.
//	 * @return Devuelve una lista con los datos que se nos devuelven de la base de datos.
//	 * @throws SQLException
//	 */
//	public List <String> numTiendasPais (String abPais, String [] canal) throws SQLException{
//		log.info ("Hemos entrado en numTiendasPais");
//		int longitud = canal.length + 1;
//		String [] parametros = new String[longitud];
//		parametros[0]=abPais;
//	    for (int i=1;i<longitud;i++){
//	    	parametros[i]=canal[i-1].trim();
//	    }
//        CRUD bbdd= new CRUD();
//        List <String> tiendas = new ArrayList <String> ();
//        String constante = "";
//        if (canal.length == 1){
//        	constante=CONSTANTES.NUMEROTIENDAS1;
//        }else if (canal.length == 2){
//        	constante=CONSTANTES.NUMEROTIENDAS2;
//        }else if (canal.length == 3){
//        	constante=CONSTANTES.NUMEROTIENDAS3;
//        }else if (canal.length == 4){
//        	constante=CONSTANTES.NUMEROTIENDAS4;
//        }else if (canal.length == 5){
//        	constante=CONSTANTES.NUMEROTIENDAS5;
//        }else if (canal.length == 6){
//        	constante=CONSTANTES.NUMEROTIENDAS6;
//        }      
//		tiendas = bbdd.leer(constante,parametros);
//        log.info ("Hemos leido el numero de tiendas del pais");
//		return tiendas;
//		
//	}
//	
//	/**
//	 * funcion que devuelve el zoom de un pais según donde esté la tienda
//	 * @param abPais este parámetro contendra la abreviatura del país.
//	 * @return Devuelve una lista con los datos que se nos devuelven de la base de datos.
//	 * @throws SQLException
//	 */
//	public List <String> zoomTienda (String idTienda) throws SQLException{
//		log.info ("Hemos entrado en zoomTienda");
//		String [] parametros = new String[1];
//	    parametros[0]=idTienda;
//        CRUD bbdd= new CRUD();
//        
//        List <String> tiendas = new ArrayList <String> ();
//        log.info ("Hemos conectado con el zoom de la tienda");
//        tiendas = bbdd.leer(CONSTANTES.ZOOMPAIS,parametros);
//        log.info ("Hemos leido el zoom de la tienda");
//		
//		return tiendas;
//	}
//	
//	/**
//	 * funcion que devuelve la lista de canales
//	 * @param 
//	 * @return Devuelve una lista con los datos que se nos devuelven de la base de datos.
//	 * @throws SQLException
//	 */
//	public List <String> listaCanales () throws SQLException{
//		log.info ("Hemos entrado en listaCanales");
//		String [] parametros = null;
//        CRUD bbdd= new CRUD();
//        
//        List <String> canales = new ArrayList <String> ();
//        log.info ("Hemos conectado con el zoom de la tienda");
//        canales = bbdd.leer(CONSTANTES.LISTACANALES,parametros);
//        log.info ("Hemos leido el zoom de la tienda");
//		
//		return canales;
//		
//	}
//	
//	/**
//	 * funcion que devuelve la lista de colores de los marcadores
//	 * @param 
//	 * @return Devuelve una lista con los datos que se nos devuelven de la base de datos.
//	 * @throws SQLException
//	 */
//	public List <String> listaColorMarcador () throws SQLException{
//		log.info ("Hemos entrado en listaColorMarcador");
//		String [] parametros = null;
//        CRUD bbdd= new CRUD();
//        
//        List <String> colorMarc = new ArrayList <String> ();
//        log.info ("Hemos conectado con el color del canal");
//        colorMarc = bbdd.leer(CONSTANTES.LISTACOLORMENU,parametros);
//        log.info ("Hemos leido el color del canal");
//
//		return colorMarc;
//		
//	}
//	
//	/**
//	 * funcion que devuelve el numero de tiendas maximas por canal
//	 * @param 
//	 * @return Devuelve una lista con los datos que se nos devuelven de la base de datos.
//	 * @throws SQLException
//	 */
//	public List <String> numTiendasMaximas (String [] canal) throws SQLException{
//		log.info ("Hemos entrado en numTiendasMaximas");
//        CRUD bbdd= new CRUD();
//        int longitud = canal.length;
//		String [] parametros = new String[longitud];
//		for (int i=0;i<longitud;i++){
//	    	parametros[i]=canal[i].trim();
//	    	log.info(parametros[i] + " ");
//	    }
//        List <String> numTiendasMaximas = new ArrayList <String> ();
//        String constante ="";
//        switch (parametros.length) {
//			case 1:
//				 constante = CONSTANTES.NUMEROMAXTIENDAS1;
//				break;
//			case 2:
//				 constante = CONSTANTES.NUMEROMAXTIENDAS2;
//				break;
//			case 3:
//				 constante = CONSTANTES.NUMEROMAXTIENDAS3;
//				break;
//			case 4:
//				 constante = CONSTANTES.NUMEROMAXTIENDAS4;
//				break;
//			case 5:
//				 constante = CONSTANTES.NUMEROMAXTIENDAS5;
//				break;
//			case 6:
//				 constante = CONSTANTES.NUMEROMAXTIENDAS6;
//				break;
//			default:
//				break;
//		}
//        log.info(constante + " ");
//        numTiendasMaximas = bbdd.leer(constante,parametros);
//        log.info ("Hemos leido el color del canal");
//
//		return numTiendasMaximas;
//	}
//	
//	/**
//	 * funcion que devuelve las constantes que tiene la aplicacion
//	 * @return Devuelve una lista con los datos que se nos devuelven de la base de datos.
//	 * @throws SQLException
//	 */
//	public List <String> constantes() throws SQLException{
//		log.info ("Hemos entrado en constantes");
//        CRUD bbdd= new CRUD();
//        List <String> constantes = new ArrayList<String>();
//		String [] parametros = null;
//        
//        constantes = bbdd.leer(CONSTANTES.CONSTANTES,parametros);
//		
//        log.info ("Hemos leido las constantes");	
//        
//        return constantes;
//	}
//
//}
