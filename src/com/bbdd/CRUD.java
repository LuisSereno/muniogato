package com.bbdd;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.constantes.CONSTANTES;
import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.cloud.sql.jdbc.ResultSetMetaData;

/**
 * Esta clase posee los metodos de lectura, actualizacin, borrado, inserción,conexión y desconexión de la base de datos
 * @author Sereno
 *
 */
public class CRUD implements Serializable{

	/**
	 * Constante para serializar la clase
	 */
    private static final long serialVersionUID = 1L;
	
    /**
     * Parámetro de la clase, que tendrá los datos de la conexión
     */
    Connection con = null;
    
    /**
     * Parámetro de la clase, que servirá para mostrar los logs en la consola
     */
    private static final Logger log = Logger.getLogger(CRUD.class.getName());
	

    /**
     * Constructor por defecto
     */
	public CRUD() {
	}
	
	/**
	 * Función que conecta con la base de datos.
	 * @throws SQLException
	 */
	public void conectar() throws SQLException{
	    try {
	        DriverManager.registerDriver(new AppEngineDriver());
	        con = DriverManager.getConnection(CONSTANTES.CONSTANTECONEXION);
	        log.info(CONSTANTES.CONSTANTECONEXION);
	    }catch (SQLException e) {
	    	log.info("ERROR AL CONECTAR CON LA BASE DE DATOS" + e.getMessage());
	        con.close();
	        e.printStackTrace();
        }
	}
	
	/**
	 * Función que cierra la conexión con la base de datos.
	 * @throws SQLException
	 */
	public void cerrarConexion() throws SQLException{
	    try {
	    	con.close();
	    }catch (SQLException e) {
	    	log.info("ERROR AL CERRAR LA BASE DE DATOS" + e.getMessage());
	        con.close();
	        e.printStackTrace();
        }
	}
	
	/**
	 * Función que lee los datos de la base de datos.
	 * @param cadena Es la query que queremos ejecutar
	 * @param valores Son los valores que añadiremos a la query que ejecutaremos
	 * @return Devuelve una lista con los valores que nos devuelve la base de datos.
	 * @throws SQLException
	 */
	public List <String> leer(String cadena, String [] valores) throws SQLException{
		log.info("Entra en la función leer(String cadena, String [] valores): " + cadena + " "+ valores);
		this.conectar();
		List <String> listaDatos = new ArrayList <String> ();
		try{

		PreparedStatement stmt = con.prepareStatement(cadena);
		if(valores != null){
			for(int i=0; i< valores.length;i++){
				stmt.setString(i+1,valores[i]);
			}
		}
        ResultSet rs = stmt.executeQuery();
        ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
        int numeroColumnas= rsmd.getColumnCount();
        String fila;
        while (rs.next()){
        	
        	//Si la columna es solo 1, no hace falta que dividamos las columnas
        	if (numeroColumnas >1){
        		fila ="";
        		fila=rs.getString(1);
	        	for (int i=2;i<=numeroColumnas;i++){
	        			fila=fila + "::" + rs.getString(i);
	        	}
	        	listaDatos.add(fila);
        	}else{
        		listaDatos.add(rs.getString(1));
        	}
        }
        log.info("Los valores leidos son: " + listaDatos.toString());
		}catch(Exception e){
			log.info("CRUD: Ha ocurrido un error" );
			log.info("CRUD: el error es " + e.getMessage().toString());
			con.close();
			e.printStackTrace();
		}finally{
			this.cerrarConexion();
		}
		 log.info("Sale de la función leer(String cadena, String [] valores)");
        return listaDatos;
        
	}
	
	/**
	 * Función que actualiza los valores de la base de datos.
	 * @param cadena
	 */
	public void actualizar (String cadena){
		log.info("Entra en la función actualizar (String cadena)");
		
		log.info("Sale de la función actualizar (String cadena)");
	}
	
	/**
	 * Función que inserta un valor en la base de datos.
	 * @param cadena
	 * @throws Exception 
	 */
	public int insertar(String cadena, String [] valores) throws Exception{
		log.info("Entra en la funci�n insertar(String cadena) " + cadena + " "+ valores);
		ResultSet keyGenerada;
		int key=-1;
		this.conectar();
		try{
	
			PreparedStatement stmt = con.prepareStatement(cadena);
			if(valores != null){
				for(int i=0; i< valores.length;i++){
					stmt.setString(i+1,valores[i]);
				}
			}
			int resultado=stmt.executeUpdate();
			
	        if (resultado==0){
	        	log.warning("ERROR: No se ha podido insertar en la base de datos");
	        	throw new Exception("No se ha podido insertar en la base de datos");
	        }else if (resultado==1){
	        	keyGenerada=stmt.getGeneratedKeys();
	        	while (keyGenerada.next()){
	        		key=keyGenerada.getInt(1);
	        		log.info("La key es: " + key);
	        	}
	        	log.info("Se ha insertado correctamente la reserva");
	        }
		}finally{
			this.cerrarConexion();
		}
		log.info("Sale de la función insertar(String cadena)");
		return key;
	}
	
	/**
	 * Función que borra valores en la base de datos.
	 * @param cadena
	 * @throws SQLException 
	 */
	public boolean borrar(String cadena, String [] valores) throws SQLException{
		log.info("Entra en la función borrar(String cadena) " + cadena + " "+ valores);
		boolean borradoCorrecto=true;
		try{
			this.conectar();
			PreparedStatement stmt = con.prepareStatement(cadena);
			if(valores != null){
				for(int i=0; i< valores.length;i++){
					stmt.setString(i+1,valores[i]);
				}
			}
	        int rs = stmt.executeUpdate();
	        if (rs==0){
	        	borradoCorrecto=true;
	        }
			
		}catch(Exception e){
			borradoCorrecto=false;
			log.info("CRUD: Ha ocurrido un error" );
			log.info("CRUD: el error es " + e.getMessage().toString());
			con.close();
			e.printStackTrace();
		}finally{
			this.cerrarConexion();
			log.info("Sale de la función borrar(String cadena)");
		}
		return borradoCorrecto;
	}

}
