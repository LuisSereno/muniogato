package com.bean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import com.bbdd.CRUDdatastore;
import com.constantes.CONSTANTES;
import com.controller.Administrador;
import com.google.apphosting.api.DatastorePb.GetResponse.Entity;


public class MenuImagenes implements Serializable {
    /**
     * Par�metro de la clase, que servir� para mostrar los logs en la consola
     */
    private static final Logger log = Logger.getLogger(MenuImagenes.class.getName());
    
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
		String aux1;
		if (this.fotoMenu.indexOf(CONSTANTES.DRIVEIMAGENES)==-1){
			aux1=this.fotoMenu;
		}else{
			aux1=this.fotoMenu.substring(this.fotoMenu.indexOf(CONSTANTES.DRIVEIMAGENES) + CONSTANTES.DRIVEIMAGENES.length(), this.fotoMenu.length());
		}
		return aux1;
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
	
	public void borrarImagenes() {
		CRUDdatastore<MenuImagenes> conn= new CRUDdatastore<MenuImagenes>();
		conn.borrarElement(this.getNombreMenu());
	}
	
	public String[] toArray(boolean  administracion) {
		if (administracion){
			String[] valorSalida = { this.getNombreMenu(), this.getFotoMenu(),this.getTipo() };
			return valorSalida;
		}else{
			String[] valorSalida = { this.getNombreMenu(), this.fotoMenu,this.getTipo() };
			return valorSalida;
		}

	}

	public void devolverTodo() {
		CRUDdatastore<MenuImagenes> conn= new CRUDdatastore<MenuImagenes>();
		this.setListaImagenes(conn.obtenerElement("",""));
	}
	
	public void devolverTodo(String tipo) {
		CRUDdatastore<MenuImagenes> conn= new CRUDdatastore<MenuImagenes>();
		this.setListaImagenes(conn.obtenerElement("tipo",tipo));
	}


	public List<MenuImagenes> getListaImagenes() {
		return listaImagenes;
	}

	public void setListaImagenes(List<MenuImagenes> listaImagenes) {
		this.listaImagenes = listaImagenes;
	}

}

