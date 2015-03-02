/**
 * 
 */
package com.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.interfaces.ConectaRecursos;

/**
 * @author Sereno
 *
 */
public class Actividades extends Recursos  implements ConectaRecursos<Actividades> , Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2654438998227986077L;
	
	

	/**
	 * 
	 */
	public Actividades() {
	}

	/**
	 * @param nombre
	 * @param descripcion
	 * @param fotos
	 * @param precio
	 * @param ocupacionMax
	 * @param numDisponible
	 */
	public Actividades(int referencia,String nombre, String tipo, String descripcion, List<String> fotos,
			int precio, int ocupacionMax, int numDisponible, List <String[]> fecha) {
		super(referencia, nombre, tipo, descripcion, fotos, precio, ocupacionMax, numDisponible, fecha);
	}
	

	@Override
	public Actividades traducirElemento(String cadenaEntera) {
		return null;
	}

	@Override
	public List<Actividades> devolverTodo() {
		return null;
	}

	@Override
	public List<Actividades> devolverTodoPrecio(int precioMin, int precioMax) {
		return null;
	}

	@Override
	public List<Actividades> devolverTodoTipo(int tipo) {
		return null;
	}

	@Override
	public List<Actividades> devolverTodoOcupacionMax(int ocupacionMax,
			int ocupacionMin) {
		return null;
	}

	@Override
	public Actividades devolverElemento() {
		return null;
	}

	@Override
	public int insertarElemento() {
		return -1;
		
	}


	@Override
	public List<Actividades> devolverTodoFecha(Date fechaOrigen,
			Date fechaDestino) {
		// TODO Auto-generated method stub
		return null;
	}}
