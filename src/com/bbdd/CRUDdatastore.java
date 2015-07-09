package com.bbdd;
import java.util.List;

import com.bean.MenuImagenes;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


public class CRUDdatastore <K>{
	
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public void insertarElement(K valorEntidad){
		Entity employee=null;
		List<MenuImagenes> listaDatos= ((MenuImagenes) valorEntidad).getListaImagenes();
		for (MenuImagenes menuImagenes : listaDatos) {
			employee = new Entity("MenuImagenes",menuImagenes.getNombreMenu());
			employee.setProperty("tipo", menuImagenes.getTipo());
			employee.setProperty("nombre", menuImagenes.getNombreMenu());
			employee.setProperty("foto", menuImagenes.getFotoMenu());
			datastore.put(employee);	
		}
	}
	

	public Entity obtenerElement(String tipo){
		Entity employee = null;
		try {
			Key k = KeyFactory.createKey("MenuImagenes", tipo);
			employee = datastore.get(k);
			
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		return employee;
	}
}
