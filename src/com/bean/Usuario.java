/**
 * 
 */
package com.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import com.bbdd.CRUD;
import com.constantes.CONSTANTES;
import com.controller.AccionUsuarios;
import com.interfaces.ConectaRecursos;

/**
 * @author Sereno
 *
 */
public class Usuario implements ConectaRecursos<Usuario>, Serializable{

    /**
     * Parámetro de la clase, que servirá para mostrar los logs en la consola
     */
    private static final Logger log = Logger.getLogger(Usuario.class.getName());
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -3481653881633683438L;
	
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
	private String apellido1;
	
	/**
	 * 
	 */
	private String apellido2;
	
	/**
	 * 
	 */
	private String dni;
	
	/**
	 * 
	 */
	private String calle;
	
	/**
	 * 
	 */
	private int numero;
	
	/**
	 * 
	 */
	private String ciudad;
	
	/**
	 * 
	 */
	private String provincia;
	
	/**
	 * 
	 */
	private String pais;
	
	/**
	 * 
	 */
	private int telefono;
	
	/**
	 * 
	 */
	private String nickname;
	
	/**
	 * 
	 */
	private String contrasena;
	
	/**
	 * 
	 */
	private String correoElec;
	
	/**
	 * 
	 */
	private String fecha;
	
	
	/**
	 * 
	 */
	public Usuario() {
	}

	/**
	 * 
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param dni
	 * @param calle
	 * @param numero
	 * @param ciudad
	 * @param provincia
	 * @param pais
	 * @param telefono
	 */
	public Usuario(String nombre,String apellido1,String apellido2,String dni,String calle,int numero,String ciudad,String provincia,String pais,int telefono,String nickname, String contrasena, String correoElec) {
		this.apellido1=apellido1;
		this.apellido2=apellido2;
		this.calle=calle;
		this.ciudad=ciudad;
		this.dni=dni;
		this.nombre=nombre;
		this.numero=numero;
		this.pais=pais;
		this.provincia=provincia;
		this.telefono=telefono;
		this.nickname=nickname;
		this.contrasena=contrasena;
		this.correoElec=correoElec;
		
    	GregorianCalendar calendario = new GregorianCalendar();
    	int m = calendario.get(GregorianCalendar.MONTH) + 1;
		int d = calendario.get(GregorianCalendar.DATE);
		String mm = Integer.toString(m);
		String dd = Integer.toString(d);
		String fechaActual= "" +calendario.get(GregorianCalendar.YEAR)+"/" + (m < 10 ? "0" + mm : mm) +"/" +   (d < 10 ? "0" + dd : dd);
		log.info("La fecha actual es: " + fechaActual);
		this.fecha=fechaActual;
		
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
	 * @return the apellido1
	 */
	public String getApellido1() {
		return apellido1;
	}

	/**
	 * @param apellido1 the apellido1 to set
	 */
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	/**
	 * @return the apellido2
	 */
	public String getApellido2() {
		return apellido2;
	}

	/**
	 * @param apellido2 the apellido2 to set
	 */
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	/**
	 * @return the dni
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * @param dni the dni to set
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}

	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
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
	 * @return the numero
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * @return the provincia
	 */
	public String getProvincia() {
		return provincia;
	}

	/**
	 * @param provincia the provincia to set
	 */
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	/**
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * @return the telefono
	 */
	public int getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the contrasena
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * @param contrasena the contrasena to set
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * @return the correoElec
	 */
	public String getCorreoElec() {
		return correoElec;
	}

	/**
	 * @param correoElec the correoElec to set
	 */
	public void setCorreoElec(String correoElec) {
		this.correoElec = correoElec;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	@Override
	public List<Usuario> devolverTodo() {
		//Esto tendremos que ponerlo como null para controlar el error más arriba tal y como hago con el usuario
		List<Usuario> listaReservas = new ArrayList<Usuario>();
		Usuario usu= new Usuario();
		try{
			CRUD bbdd;
			bbdd= new CRUD();
			String [] cadena = {};
			List <String> listaCadena= new ArrayList<String>();
			
			listaCadena=bbdd.leer(CONSTANTES.DEVOLVERUSUARIOS, cadena);
			
			for (int i=0;i<listaCadena.size();i++){
				usu=this.traducirElemento(listaCadena.get(i));
				listaReservas.add(usu);
				usu= new Usuario();
			}
		}catch(Exception e){
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			e.printStackTrace();
		} 
		return listaReservas;
	}

	@Override
	public List<Usuario> devolverTodoPrecio(int precioMin, int precioMax) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> devolverTodoTipo(int tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> devolverTodoOcupacionMax(int ocupacionMax,
			int ocupacionMin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario devolverElemento() {
		Usuario usu=null;
		try {
			CRUD bbdd= new CRUD();
			List <String> listaCadena= new ArrayList<String>();	
			if (!"reserva".equals(this.contrasena)){
				String []  valores = {this.nickname,this.contrasena};
				listaCadena=bbdd.leer(CONSTANTES.DEVOLVERUSUARIO, valores);
			}else{
				String [] valores = {this.nickname};
				listaCadena=bbdd.leer(CONSTANTES.DEVOLVERUSUARIORESERVA, valores);
			}
			
			if (listaCadena.size()!=0){
				usu=this.traducirElemento(listaCadena.get(0));
			}
			
		} catch (SQLException e) {
			log.warning("ERROR: " + e);
			e.printStackTrace();
		}

		return usu;
	}

	@Override
	public Usuario traducirElemento(String cadenaEntera) {
	Usuario usu= new Usuario();
		
		try{
			String[] cadena = cadenaEntera.split("::");
			
			usu.setReferencia(Integer.parseInt(cadena[0]));
			usu.setNombre(cadena[1]);
			usu.setApellido1(cadena[2]);
			usu.setApellido2(cadena[3]);
			usu.setDni(cadena[4]);
			usu.setCalle(cadena[5]);
			usu.setNumero(Integer.parseInt(cadena[6]));
			usu.setCiudad(cadena[7]);
			usu.setProvincia(cadena[8]);
			usu.setPais(cadena[9]);
			usu.setTelefono(Integer.parseInt(cadena[10]));
			usu.setNickname(cadena[11]);
			usu.setContrasena(cadena[12]);
			usu.setCorreoElec(cadena[13]);
			usu.setFecha(cadena[14]);
			
			

		}catch(Exception e){
			log.warning("ERROR: " + e);
			e.printStackTrace();
		}
		return usu;
	}

	@Override
	public int insertarElemento() {
		int referencia=-1;
		try {
			CRUD bbdd= new CRUD();
			
			String [] valores = {this.nombre,this.apellido1,this.apellido2,this.dni,this.calle,String.valueOf(this.numero),this.ciudad,this.provincia,this.pais,String.valueOf(this.telefono),this.nickname,this.contrasena,this.correoElec,this.fecha};
			referencia=bbdd.insertar(CONSTANTES.INSERTARUSUARIO, valores);
		} catch (SQLException e) {
			log.warning("ERROR: " + e);
			e.printStackTrace();
		} catch (Exception e) {
			log.warning("ERROR: " + e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return referencia;
	}

	@Override
	public List<Usuario> devolverTodoFecha(Date fechaOrigen, Date fechaDestino) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public Usuario devolverElementoReferencia() {
		Usuario usu=null;
		try {
			CRUD bbdd= new CRUD();
			List <String> listaCadena= new ArrayList<String>();	
			String [] valores = {String.valueOf(this.referencia)};
			listaCadena=bbdd.leer(CONSTANTES.DEVOLVERUSUARIORREFERENCIA, valores);
			
			if (listaCadena.size()!=0){
				usu=this.traducirElemento(listaCadena.get(0));
			}
			
		} catch (SQLException e) {
			log.warning("ERROR: " + e);
			e.printStackTrace();
		}

		return usu;
	}

	

}
