package com.bean;

import java.io.Serializable;
import java.util.List;

import com.bbdd.CRUDdatastore;
import com.constantes.CONSTANTES;
import com.google.apphosting.api.DatastorePb.GetResponse.Entity;


public class MenuImagenes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<MenuImagenes> listaImagenes;
	
	private String tipo;
	
	private String nombreMenu;

	private String fotoMenu;

	public String getNombreMenu() {
		return nombreMenu;
	}

	public void setNombreMenu(String nombreMenu) {
		this.nombreMenu = nombreMenu;
	}

	public String getFotoMenu() {
		if (this.fotoMenu.indexOf(CONSTANTES.DRIVEIMAGENES)==-1){
			return this.fotoMenu;
			
		}else{
			return this.fotoMenu.substring(this.fotoMenu.indexOf(CONSTANTES.DRIVEIMAGENES), this.fotoMenu.length());
			
		}
	}

	public void setFotoMenu(String fotoMenu) {
		this.fotoMenu = CONSTANTES.DRIVEIMAGENES+ fotoMenu;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void insertarImagenes() {
		CRUDdatastore<MenuImagenes> conn= new CRUDdatastore<MenuImagenes>();
		conn.insertarElement(this);
	}
	
	public String[] toArray() {
		String[] valorSalida = { this.nombreMenu, this.fotoMenu };
		return valorSalida;
	}

	public List<MenuImagenes> devolverTodo() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MenuImagenes> getListaImagenes() {
		return listaImagenes;
	}

	public void setListaImagenes(List<MenuImagenes> listaImagenes) {
		this.listaImagenes = listaImagenes;
	}

}

