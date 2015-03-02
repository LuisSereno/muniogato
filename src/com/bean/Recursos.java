/**
 * 
 */
package com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Sereno
 *
 */
public class Recursos implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6883486184843102190L;
	
	/**
	 * 
	 */
	private int referencia;
	/**
	 * 
	 */
	private String nombre;

	/**
	 * 
	 */
	private String descripcion;
	
	/**
	 * 
	 */
	private String tipo;
	
	/**
	 * 
	 */
	private List <String> fotos;
	
	/**
	 * 
	 */
	private float precio;
	
	/**
	 * 
	 */
	private int ocupacionMax;
	
	/**
	 * 
	 */
	private int numDisponible;

	
	/**
	 * 
	 */
	private List <String[]> fechas= new ArrayList<String[]>();
	
	
	/**
	 * 
	 */
	public Recursos() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	public Recursos(int referencia, String nombre, String tipo, String descripcion,List <String> fotos,float precio,int ocupacionMax,int numDisponible, List <String[]> fecha) {

		this.setReferencia(referencia);
		this.descripcion=descripcion;
		this.fotos=fotos;
		this.nombre=nombre;
		this.numDisponible=numDisponible;
		this.ocupacionMax=ocupacionMax;
		this.precio=precio;
		this.tipo=tipo;
		this.fechas=fecha;
	
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the fotos
	 */
	public List<String> getFotos() {
		return fotos;
	}

	/**
	 * @param fotos the fotos to set
	 */
	public void setFotos(List<String> fotos) {
		this.fotos = fotos;
	}

	/**
	 * @return the precio
	 */
	public float getPrecio() {
		return precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(float precio) {
		this.precio = precio;
	}

	/**
	 * @return the ocupacionMax
	 */
	public int getOcupacionMax() {
		return ocupacionMax;
	}

	/**
	 * @param ocupacionMax the ocupacionMax to set
	 */
	public void setOcupacionMax(int ocupacionMax) {
		this.ocupacionMax = ocupacionMax;
	}

	/**
	 * @return the numDisponible
	 */
	public int getNumDisponible() {
		return numDisponible;
	}

	/**
	 * @param numDisponible the numDisponible to set
	 */
	public void setNumDisponible(int numDisponible) {
		this.numDisponible = numDisponible;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the referencia
	 */
	public int getReferencia() {
		return referencia;
	}

	/**
	 * @param referencia the referencia to set
	 */
	public void setReferencia(int referencia) {
		this.referencia = referencia;
	}

	/**
	 * @return the fechas
	 */
	public List <String[]> getFechas() {
		return fechas;
	}

	/**
	 * @param fechas the fechas to set
	 */
	public void setFechas(List <String[]> fechas) {
		this.fechas = fechas;
	}

}
