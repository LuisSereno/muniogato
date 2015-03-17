/**
 * 
 */
package com.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.bbdd.CRUD;
import com.constantes.CONSTANTES;
import com.interfaces.ConectaRecursos;

/**
 * @author Sereno
 *
 */
public class Reserva implements ConectaRecursos<Reserva>, Serializable{

	
    /**
     * Par�metro de la clase, que servir� para mostrar los logs en la consola
     */
    private static final Logger log = Logger.getLogger(Reserva.class.getName());
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 2862845664457118168L;

	/**
	 * 
	 */
	private Habitaciones hb;

	/**
	 * 
	 */
	private Usuario usu;
	
	/**
	 * 
	 */
	private float precioTotal;
	
	/**
	 * 
	 */
	private int referencia;

	/**
	 * 
	 */
	private String fechaInicio;
	
	/**
	 * 
	 */
	private String fechaFin;
	
	/**
	 * 
	 */
	private int numFactura;
	
	
	public Reserva(Habitaciones hb, Usuario usu, int referencia,
			String fechaInicio, String fechaFin, float precio,int numFactura) {
		super();
		this.hb = hb;
		this.usu = usu;
		this.referencia = referencia;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.precioTotal=precio;
		this.numFactura=numFactura;
	}


	/**
	 * 
	 */
	public Reserva() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the hb
	 */
	public Habitaciones getHb() {
		return hb;
	}


	/**
	 * @param hb the hb to set
	 */
	public void setHb(Habitaciones hb) {
		this.hb = hb;
	}


	/**
	 * @return the usu
	 */
	public Usuario getUsu() {
		return usu;
	}


	/**
	 * @param usu the usu to set
	 */
	public void setUsu(Usuario usu) {
		this.usu = usu;
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
	 * @return the fechaInicio
	 */
	public String getFechaInicio() {
		return fechaInicio;
	}


	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(String fechaInicio) {
		String [] cadena = new String [3];
		try{
			cadena=fechaInicio.split("/");
			this.fechaInicio = cadena[2] + "/" + cadena[1] + "/"+ cadena[0];
		}catch (ArrayIndexOutOfBoundsException e){
			cadena=fechaInicio.split("-");
			this.fechaInicio = cadena[2] + "/" + cadena[1] + "/"+ cadena[0];
		}
		
		
	}


	/**
	 * @return the fechaFin
	 */
	public String getFechaFin() {
		return fechaFin;
	}


	/**
	 * @param fechaFin the fechaFin to set
	 * @throws ParseException 
	 */
	public void setFechaFin(String fechaFin) throws ParseException {
		String [] cadena = new String [3];
		try{
			cadena=fechaFin.split("/");
			this.fechaFin = cadena[2] + "/" + cadena[1] + "/"+ cadena[0];
		}catch (ArrayIndexOutOfBoundsException e){
			cadena=fechaFin.split("-");
			this.fechaFin = cadena[2] + "/" + cadena[1] + "/"+ cadena[0];
		}
		
	}


	/**
	 * @return the precioTotal
	 */
	public float getPrecioTotal() {
		return precioTotal;
	}


	/**
	 * @param precioTotal the precioTotal to set
	 */
	public void setPrecioTotal(float precioTotal) {
		this.precioTotal = precioTotal;
	}

	
	/**
	 * @return the numFactura
	 */
	public int getNumFactura() {
		return numFactura;
	}


	/**
	 * @param numFactura the numFactura to set
	 */
	public void setNumFactura(int numFactura) {
		this.numFactura = numFactura;
	}


	/**
	 * 
	 */
	public boolean borrarReserva(int referencia){
		boolean correcto=true;
		try{
			CRUD bbdd;
			bbdd= new CRUD();
			String [] cadena = {String.valueOf(referencia)};
			bbdd.borrar(CONSTANTES.BORRARRESERVAREFERENCIA, cadena);
		}catch(Exception e){
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			correcto=false;
			e.printStackTrace();
		} 
		return correcto;
		
	}
	
	/**
	 * 
	 */
	public boolean borrarReserva(){
		boolean correcto=true;
		try{
			CRUD bbdd;
			bbdd= new CRUD();
			String [] cadena = {String.valueOf(this.getReferencia())};
			bbdd.borrar(CONSTANTES.BORRARRESERVA, cadena);
		}catch(Exception e){
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			correcto=false;
			e.printStackTrace();
		} 
		return correcto;
	}
	
	public int actualizarReserva() {
		
		int factura=-1;
		try {
			CRUD bbdd= new CRUD();
			String [] valores = {String.valueOf(this.getNumFactura()),String.valueOf(this.getReferencia())};
			factura=bbdd.insertar(CONSTANTES.ACTUALIZARRESERVA, valores);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return factura;
		
		
	}

	@Override
	public List<Reserva> devolverTodo() {
		
		//Esto tendremos que ponerlo como null para controlar el error m�s arriba tal y como hago con el usuario
		List<Reserva> listaReservas = new ArrayList<Reserva>();
		Reserva reser= new Reserva();
		try{
			CRUD bbdd;
			bbdd= new CRUD();
			String [] cadena = {};
			List <String> listaCadena= new ArrayList<String>();
			
			listaCadena=bbdd.leer(CONSTANTES.DEVOLVERRESERVASORDENADAS, cadena);
			
			for (int i=0;i<listaCadena.size();i++){
				reser=this.traducirElemento(listaCadena.get(i));
				listaReservas.add(reser);
				reser= new Reserva();
			}
		}catch(Exception e){
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			e.printStackTrace();
		} 
		return listaReservas;
		

	}

	@Override
	public List<Reserva> devolverTodoPrecio(int precioMin, int precioMax) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Reserva> devolverTodoTipo(int tipo) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Reserva> devolverTodoOcupacionMax(int ocupacionMax,
			int ocupacionMin) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Reserva devolverElemento() {

		//Esto tendremos que ponerlo como null para controlar el error m�s arriba tal y como hago con el usuario
		Reserva reser= new Reserva();
		try{
			CRUD bbdd;
			bbdd= new CRUD();
			String [] cadena = {String.valueOf(this.getHb().getReferencia()),String.valueOf(this.getUsu().getReferencia()),this.getFechaInicio(),this.getFechaFin(),String.valueOf(this.getPrecioTotal())};
			List <String> listaCadena= new ArrayList<String>();
			
			listaCadena=bbdd.leer(CONSTANTES.SELECCIONARESERVA, cadena);
			if (listaCadena.size()!=0){
				reser=this.traducirElemento(listaCadena.get(0));
			}
		}catch(Exception e){
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			e.printStackTrace();
		} 
		return reser;
		
		
	}


	@Override
	public Reserva traducirElemento(String cadenaEntera) {
		
		Reserva reser= new Reserva();
		Habitaciones hb= new Habitaciones();
		Usuario usu= new Usuario();
		try{
			CRUD bbdd;
			bbdd= new CRUD();
			String[] cadena = cadenaEntera.split("::");
			int referencia= Integer.parseInt(cadena[0]);
			reser.setReferencia(referencia);
			
			hb.setReferencia(Integer.valueOf(cadena[1]));
			hb=hb.devolverElemento();
			reser.setHb(hb);
			usu.setReferencia(Integer.valueOf(cadena[2]));
			usu=usu.devolverElementoReferencia();
			reser.setUsu(usu);
			
			reser.setFechaInicio(cadena[3]);
			reser.setFechaFin(cadena[4]);
			reser.setPrecioTotal(Integer.valueOf(cadena[5]));
			reser.setNumFactura(Integer.valueOf(cadena[6]));
			
			
		}catch(Exception e){
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			e.printStackTrace();
		}
		return reser;
		
	}


	@Override
	public int insertarElemento() {
		
		int referencia=-1;
		try {
			CRUD bbdd= new CRUD();
		
			String [] valores = {String.valueOf(this.hb.getReferencia()),String.valueOf(this.usu.getReferencia()),this.fechaInicio,this.fechaFin,String.valueOf(this.precioTotal)};
			referencia=bbdd.insertar(CONSTANTES.INSERTARESERVA, valores);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return referencia;
		
		
	}


	@Override
	public List<Reserva> devolverTodoFecha(Date fechaOrigen, Date fechaDestino) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int devolverNumUltFactura() {
		
		int numeroFactura=-1;
		try {
			CRUD bbdd= new CRUD();
			List <String> listaCadena= new ArrayList<String>();
			String [] valores = {};
			listaCadena=bbdd.leer(CONSTANTES.NUMEROMAXIMOFACTURA, valores);
			if (listaCadena!=null){
				if (listaCadena.size()!=0){
					numeroFactura=Integer.parseInt(listaCadena.get(0));
				}
			}else{
				numeroFactura=0;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numeroFactura;
		
	
	}

	
	public int insertarElemento(int numeroFactura,int numeroCorreo) {
		int factura=-1;
		try {
			CRUD bbdd= new CRUD();
			if (numeroFactura==-1){
				String [] valores = {String.valueOf(numeroCorreo),"N","N"};
				factura=bbdd.insertar(CONSTANTES.INSERTAFACTURA, valores);
			}else{
				String [] valores = {"Y",String.valueOf(numeroFactura)};
				factura=bbdd.insertar(CONSTANTES.ACTUALIZARFACTURAPAGADA, valores);
			}
		} catch (SQLException e) {
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			e.printStackTrace();
		} catch (Exception e) {
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return factura;
	}
	
	
	public int actualizarEnvioFactura(int numeroFactura) {
		
		int factura=-1;
		try {
			CRUD bbdd= new CRUD();
			String [] valores = {"Y",String.valueOf(numeroFactura)};
			factura=bbdd.insertar(CONSTANTES.ACTUALIZARFACTURAENVIADA, valores);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return factura;
		
		
	}
	
	public boolean borrarFactura(){
		boolean correcto=true;
		try{
			CRUD bbdd;
			bbdd= new CRUD();
			String [] cadena = {String.valueOf(this.numFactura)};
			//Tenemos que ver si la base de datos se encarga de borrar la columna factura de la tabla...lo dudo
			bbdd.borrar(CONSTANTES.BORRARFACTURA, cadena);
		}catch(Exception e){
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			correcto=false;
			e.printStackTrace();
		} 
		return correcto;
		
	}
	
	
	public List <String[]> devolverTodasFacturas(){
		
		List <String[]> cadenaFactura= new ArrayList<String[]>();
		String [] array= new String[5];
		try{
			CRUD bbdd;
			bbdd= new CRUD();
			String [] cadena = {};
			List <String> listaCadena= new ArrayList<String>();
			
			listaCadena=bbdd.leer(CONSTANTES.DEVOLVERTODASFACTURASADMINISTRADOR, cadena);
			
			for (int i=0;i<listaCadena.size();i++){
				array[0]=listaCadena.get(i).split("::")[0];
				array[1]=listaCadena.get(i).split("::")[1];
				array[2]=listaCadena.get(i).split("::")[2];
				array[3]=listaCadena.get(i).split("::")[3];
				array[4]=listaCadena.get(i).split("::")[4];
				cadenaFactura.add(array);
				array= new String[5];
			}
		}catch(Exception e){
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			e.printStackTrace();
		} 
		return cadenaFactura;
	}
	
	
	public boolean enviarFactura (){
		
		boolean envioCorrecto=true;
		try{
				
			CRUD bbdd;
			bbdd= new CRUD();
			String [] cadena = {String.valueOf(this.getNumFactura())};
			List <String> listaCadena= new ArrayList<String>();
				
				
			Correos corr= new Correos();
			Usuario usu= new Usuario();
			usu.setNickname("admin");
			usu.setContrasena("reserva");
			usu=usu.devolverElemento();
			
			listaCadena=bbdd.leer(CONSTANTES.DEVOLVERREFCORREO, cadena);
			
			corr.setReferencia(Integer.parseInt(listaCadena.get(0)));
			corr=corr.devolverElementoReferencia();
			corr.enviarCorreo(usu, true);
			
		}catch(Exception e){
			envioCorrecto=false;
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			e.printStackTrace();
		} 
		
		return envioCorrecto;
			
	}
	
	
	
	/**
	 * 
	 */
	public int borrarReservaFactura(){
		int referenciaInsercion=-1;
		try{
			CRUD bbdd;
			bbdd= new CRUD();
			String [] cadena = {String.valueOf(this.getNumFactura())};
			if (bbdd.borrar(CONSTANTES.BORRARRESERVAFACTURA, cadena)){
				referenciaInsercion=0;
			}
		}catch(Exception e){
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			e.printStackTrace();
			referenciaInsercion=-1;
		} 
		return referenciaInsercion;
	}
	

	public boolean borrarFacturaCorreoReserva (){
		
		boolean envioCorrecto=true;
		try{
			Reserva reser= new Reserva();
			Correos corr= new Correos();
			CRUD bbdd;
			bbdd= new CRUD();
			String [] cadena = {String.valueOf(this.getNumFactura())};
			List <String> listaCadena= new ArrayList<String>();
			listaCadena=bbdd.leer(CONSTANTES.DEVOLVERFACTURA, cadena);
			corr.setReferencia(Integer.parseInt(listaCadena.get(0).split("::")[1]));
			corr.borrarCorreo();
			this.borrarReservaFactura();
			envioCorrecto=this.borrarFactura();
			
		}catch(Exception e){
			envioCorrecto=false;
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			e.printStackTrace();
		} 
		
		return envioCorrecto;
			
	}
	
	public int facturaPagada(String pago) {
		int factura=-1;
		try {
			CRUD bbdd= new CRUD();
			String [] valores = {pago,String.valueOf(this.getNumFactura())};
			factura=bbdd.insertar(CONSTANTES.ACTUALIZARFACTURAPAGADA, valores);
		} catch (SQLException e) {
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			e.printStackTrace();
		} catch (Exception e) {
			log.warning("SE HA PRODUCIDO EL ERROR: " + e );
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return factura;
	}
	
}
