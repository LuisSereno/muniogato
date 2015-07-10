package com.bbdd;
import java.util.ArrayList;
import java.util.List;

import com.bean.MenuImagenes;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;


public class CRUDdatastore <K>{
	
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public void insertarElement(K valorEntidad){
		Entity elemento=null;
		List<MenuImagenes> listaDatos= ((MenuImagenes) valorEntidad).getListaImagenes();
		for (MenuImagenes menuImagenes : listaDatos) {
			elemento = new Entity("MenuImagenes",menuImagenes.getNombreMenu());
			elemento.setProperty("tipo", menuImagenes.getTipo());
			elemento.setProperty("nombre", menuImagenes.getNombreMenu());
			elemento.setProperty("foto", menuImagenes.getFotoMenu());
			datastore.put(elemento);	
		}
	}
	

	public List<MenuImagenes> obtenerElement(String propiedad, String valor){
		List<MenuImagenes> listaDatosSalida=new ArrayList<MenuImagenes>();
		Query q = new Query("MenuImagenes").setFilter(FilterOperator.EQUAL.of(propiedad,valor));
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			MenuImagenes mu= new MenuImagenes();
			mu.setTipo((String) result.getProperty("tipo"));
			mu.setNombreMenu((String) result.getProperty("nombre"));
			mu.setFotoMenu((String) result.getProperty("foto"));
			listaDatosSalida.add(mu);
		}
		return listaDatosSalida;
	}
	
	public K obtenerElementNombre(String nombre) {
		K employee = null;
		Key k = KeyFactory.createKey("MenuImagenes", nombre);
		try {
			employee = (K) datastore.get(k);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		return employee;
	}
}
