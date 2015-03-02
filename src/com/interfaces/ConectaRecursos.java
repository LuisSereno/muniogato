package com.interfaces;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public interface ConectaRecursos <K>  extends Serializable{
	
	/**
	 * 
	 * @return
	 */
	public List<K> devolverTodo ();
	
	/**
	 * 
	 * @param fecha
	 * @return
	 */
	public List<K> devolverTodoFecha (Date fechaOrigen, Date fechaDestino);
	
	/**
	 * 
	 * @param precio
	 * @return
	 */
	public List<K> devolverTodoPrecio (int precioMin,int precioMax);
	
	/**
	 * 
	 * @param tipo
	 * @return
	 */
	public List<K> devolverTodoTipo (int tipo);
	
	/**
	 * 
	 * @param ocupacion
	 * @return
	 */
	public List<K> devolverTodoOcupacionMax (int ocupacionMax, int ocupacionMin);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public K devolverElemento (); 
	
	/**
	 * 
	 * @param cadenaEntera
	 * @return
	 */
	public K traducirElemento (String cadenaEntera); 
	
	/**
	 * 
	 * @param elemento
	 * @return 
	 */
	public int insertarElemento ();

}
