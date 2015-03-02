/**
 * 
 */
package com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Logger;

import com.bbdd.CRUD;
import com.constantes.CONSTANTES;
import com.interfaces.ConectaRecursos;

/**
 * @author Sereno
 *
 */
public class Habitaciones extends Recursos implements ConectaRecursos<Habitaciones>, Serializable {

	
    /**
     * Parámetro de la clase, que servirá para mostrar los logs en la consola
     */
    private static final Logger log = Logger.getLogger(Habitaciones.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = -8471744289668854738L;
	
	/**
	 * 
	 */
	private int km;

	/**
	 * 
	 */
	private int dificultad;
	
	/**
	 * 
	 */
	private String lugar;
	
	/**
	 * 
	 */
	private int numeroHabitaciones;
	
	/**
	 * 
	 */
	private int numeroHabitacionesLibres;
	
	/**
	 * 
	 */
	private List<String> caracteristicas;
	

	/**
	 * 
	 */
	public Habitaciones() {
	}

	/**
	 * @param nombre
	 * @param descripcion
	 * @param fotos
	 * @param precio
	 * @param ocupacionMax
	 * @param numDisponible
	 */
	public Habitaciones(int referencia,String nombre,String tipo, String descripcion, List<String> fotos,
			float precio, int ocupacionMax, int numDisponible, List <String[]> fecha, int dificultad, int km, String lugar,int numeroHabitaciones, int numeroHabitacionesLibres) {
		super(referencia,nombre, tipo, descripcion, fotos, precio, ocupacionMax, numDisponible, fecha);
		
		this.dificultad=dificultad;
		this.km= km;
		this.lugar =lugar;
		this.numeroHabitaciones=numeroHabitaciones;
		this.numeroHabitacionesLibres= numeroHabitacionesLibres;
	}

	/**
	 * @return the km
	 */
	public int getKm() {
		return km;
	}

	/**
	 * @param km the km to set
	 */
	public void setKm(int km) {
		this.km = km;
	}

	/**
	 * @return the dificultad
	 */
	public int getDificultad() {
		return dificultad;
	}

	/**
	 * @param dificultad the dificultad to set
	 */
	public void setDificultad(int dificultad) {
		this.dificultad = dificultad;
	}

	/**
	 * @return the lugar
	 */
	public String getLugar() {
		return lugar;
	}

	/**
	 * @param lugar the lugar to set
	 */
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	
	/**
	 * @return the numeroHabitaciones
	 */
	public int getNumeroHabitaciones() {
		return numeroHabitaciones;
	}

	/**
	 * @param numeroHabitaciones the numeroHabitaciones to set
	 */
	public void setNumeroHabitaciones(int numeroHabitaciones) {
		this.numeroHabitaciones = numeroHabitaciones;
	}

	/**
	 * @return the numeroHabitacionesLibres
	 */
	public int getNumeroHabitacionesLibres() {
		return numeroHabitacionesLibres;
	}

	/**
	 * @param numeroHabitacionesLibres the numeroHabitacionesLibres to set
	 */
	public void setNumeroHabitacionesLibres(int numeroHabitacionesLibres) {
		this.numeroHabitacionesLibres = numeroHabitacionesLibres;
	}
	
	public List<String> getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(List<String> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	@Override
	public List<Habitaciones> devolverTodo() {
		List <String> listaCadena= new ArrayList<String>();

		List <Habitaciones> listaHabitaciones= new ArrayList<Habitaciones>();
	
		try{
			CRUD bbdd;
			bbdd= new CRUD();
			String [] cadena = null;
	
			listaCadena=bbdd.leer(CONSTANTES.DEVOLVERTODO, cadena);
			
			for (int x=0;x<listaCadena.size();x++){
				listaHabitaciones.add(this.traducirElemento(listaCadena.get(x)));
			}
		}catch(Exception e){
			log.warning("SE HA PRODUCIDO EL ERROR: " + e);
			e.printStackTrace();
		} 
		return listaHabitaciones;
	}

	@Override
	public List<Habitaciones> devolverTodoPrecio(int precioMin, int precioMax) {
		return null;
	}

	@Override
	public List<Habitaciones> devolverTodoTipo(int tipo) {
		return null;
	}

	@Override
	public List<Habitaciones> devolverTodoOcupacionMax(int ocupacionMax,
			int ocupacionMin) {
		return null;
	}

	@Override
	public Habitaciones devolverElemento() {
		//Esto tendremos que ponerlo como null para controlar el error más arriba tal y como hago con el usuario
		Habitaciones habitat= new Habitaciones();
		try{
			CRUD bbdd;
			bbdd= new CRUD();
			String [] cadena = {String.valueOf(this.getReferencia())};
			List <String> listaCadena= new ArrayList<String>();
			
			listaCadena=bbdd.leer(CONSTANTES.DEVOLVERFILA, cadena);
			if (listaCadena.size()!=0){
				habitat=this.traducirElemento(listaCadena.get(0));
			}
		}catch(Exception e){
			log.warning("SE HA PRODUCIDO EL ERROR: " + e);
			e.printStackTrace();
		} 
		return habitat;
	}

	@Override
	public Habitaciones traducirElemento(String cadenaEntera) {
		
		Habitaciones habita= new Habitaciones();
		
		try{
			String[] cadena = cadenaEntera.split("::");
			int referencia= Integer.parseInt(cadena[0]);
			
			habita.setReferencia(referencia);
			habita.setNombre(cadena[1]);
			habita.setOcupacionMax(Integer.parseInt(cadena[2]));
			habita.setDescripcion(cadena[3]);
			habita.setPrecio(Float.parseFloat(cadena[4]));
			habita.setNumeroHabitaciones(Integer.parseInt(cadena[5]));
			String [] cadenaFotos=cadena[6].split("<<-->>");
			List <String> listaFotos= new ArrayList<String>();
			for (int i=0;i<cadenaFotos.length;i++){
				
				listaFotos.add(cadenaFotos[i]);
			}
			habita.setFotos(listaFotos);
			
			String [] cadenaCaracteristicas=cadena[7].split("<<-->>");
			List <String> listaCaracteristicas= new ArrayList<String>();
			for (int i=0;i<cadenaCaracteristicas.length;i++){
				
				listaCaracteristicas.add(cadenaCaracteristicas[i]);
			}
			habita.setCaracteristicas(listaCaracteristicas);
			
		}catch(Exception e){
			log.warning("SE HA PRODUCIDO EL ERROR: " + e);
			e.printStackTrace();
		}
		return habita;
		
	}

	@Override
	public int insertarElemento() {
		return -1;
		
	}

	@Override
	public List<Habitaciones> devolverTodoFecha(Date fechaOrigen,
			Date fechaDestino) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<String[]> devolverFechas() {
		//Esto tendremos que ponerlo como null para controlar el error más arriba tal y como hago con el usuario
		List <String[]> listaFechas= new ArrayList<String[]>();
		try{
			
			CRUD bbdd;
			bbdd= new CRUD();
			String [] cadena = {String.valueOf(this.getReferencia())};
			List <String> listaCadena= new ArrayList<String>();
			listaCadena=bbdd.leer(CONSTANTES.DEVOLVERFECHASHABITACION, cadena);
			
			String [][] rangoFechasOcupadas= new String [this.getNumeroHabitaciones()][2];
			
			for (int i=0;i<listaCadena.size();i++){
				log.info("LAS FECHAS SON" + listaCadena.get(i));
				String[] cadenaPartida = listaCadena.get(i).split("::");
				int fechaFinal=Integer.parseInt(cadenaPartida[1].replace("-", ""));
				int fechaInicial=Integer.parseInt(cadenaPartida[0].replace("-", ""));
				//Empieza la magia
				int numSuperar= 0;
				rangoFechasOcupadas[numSuperar][0]=cadenaPartida[0];
				rangoFechasOcupadas[numSuperar][1]=cadenaPartida[1];
				numSuperar ++;
				int fechaInicialBucle=0;
				int fechaFinalBucle=0;
				if (this.getNumeroHabitaciones()==1){
					listaFechas.add(rangoFechasOcupadas[0]);
				}else{
					for (int x=i+1;x<listaCadena.size();x++){
						log.info("DA VUELTAS EN EL BUCLE: " + listaCadena.get(x));
						fechaInicialBucle=Integer.parseInt(listaCadena.get(x).split("::")[0].replace("-", ""));
						fechaFinalBucle=Integer.parseInt(listaCadena.get(x).split("::")[1].replace("-", ""));
						
						if (fechaInicialBucle<=fechaInicial && fechaFinalBucle>=fechaInicial){
							rangoFechasOcupadas[numSuperar][0]=listaCadena.get(x).split("::")[0];
							rangoFechasOcupadas[numSuperar][1]=listaCadena.get(x).split("::")[1];
							listaFechas.add(fechaComun(rangoFechasOcupadas));
							rangoFechasOcupadas[numSuperar]= new String[2];
						}else if(fechaInicialBucle<=fechaFinal && fechaFinalBucle>=fechaInicial){
							rangoFechasOcupadas[numSuperar][0]=listaCadena.get(x).split("::")[0];
							rangoFechasOcupadas[numSuperar][1]=listaCadena.get(x).split("::")[1];
							listaFechas.add(fechaComun(rangoFechasOcupadas));
							rangoFechasOcupadas[numSuperar]= new String[2];
						}
					}
				}
				log.info("La lista de fechas es_: " + rangoFechasOcupadas);	
				 rangoFechasOcupadas= new String [this.getNumeroHabitaciones()][2];
			}
		}catch(Exception e){
			log.warning("SE HA PRODUCIDO EL ERROR: " + e);
			e.printStackTrace();
		} 
		log.info("La lista de fechas es_: " + listaFechas);
		

		for (int i=0;i<listaFechas.size();i++){
			for (int x=i+1;x<listaFechas.size();x++){
				if (listaFechas.get(i)[0].equals(listaFechas.get(x)[0])&&listaFechas.get(i)[1].equals(listaFechas.get(x)[1])){
					listaFechas.remove(x);
				}
			}
		}
		
		return listaFechas;
	}

	
	private String[] fechaComun(String[][] rangoFechasOcupadas) {
		
		String[] cadenaSalida= new String[2];
		String fechaInicioMayorSTR=rangoFechasOcupadas[0][0];
		String fechaFinalMenorSTR=rangoFechasOcupadas[0][1];
		int fechaFinalMenor=Integer.parseInt(rangoFechasOcupadas[0][1].replace("-", ""));
		int fechaInicioMayor=Integer.parseInt(rangoFechasOcupadas[0][0].replace("-", ""));
		for (int i=1;i<rangoFechasOcupadas.length;i++){
			if (Integer.parseInt(rangoFechasOcupadas[i][0].replace("-", ""))>fechaInicioMayor){
				fechaInicioMayorSTR=rangoFechasOcupadas[i][0];
			}
			if(Integer.parseInt(rangoFechasOcupadas[i][1].replace("-", ""))<fechaFinalMenor){
				fechaFinalMenorSTR=rangoFechasOcupadas[i][1];
			}
		}
		cadenaSalida[0]=fechaInicioMayorSTR;
		cadenaSalida[1]=fechaFinalMenorSTR;

		return cadenaSalida;
	}

	
	public String[] devolverFechasOnline(String fechaIni,String fechaFin) {
		//Esto tendremos que ponerlo como null para controlar el error más arriba tal y como hago con el usuario
		List <String[]> listaFechas= new ArrayList<String[]>();
		String [][] rangoFechasOcupadas= new String [this.getNumeroHabitaciones()][2];
		try{
			
			CRUD bbdd;
			bbdd= new CRUD();
			String [] cadena = {String.valueOf(this.getReferencia())};
			List <String> listaCadena= new ArrayList<String>();
			listaCadena=bbdd.leer(CONSTANTES.DEVOLVERFECHASHABITACION, cadena);
			
			
			log.info("Fecha inicial devolver fechas online: " + fechaIni);
			log.info("Fecha final devolver fechas online: " + fechaFin);

			int fechaFinal=Integer.parseInt(fechaFin.replace("/", ""));
			int fechaInicial=Integer.parseInt(fechaIni.replace("/", ""));
			
			log.info("Fecha inicial devolver fechas online: " + fechaInicial);
			log.info("Fecha final devolver fechas online: " + fechaFinal);
			//Empieza la magia
			int numSuperar= 0;
			rangoFechasOcupadas[numSuperar][0]=fechaIni;
			rangoFechasOcupadas[numSuperar][1]=fechaFin;
			numSuperar ++;
			int fechaInicialBucle=0;
			int fechaFinalBucle=0;
			for (int x=0;x<listaCadena.size()&&numSuperar<this.getNumeroHabitaciones();x++){
				log.info("DA VUELTAS EN EL BUCLE: " + listaCadena.get(x));
				fechaInicialBucle=Integer.parseInt(listaCadena.get(x).split("::")[0].replace("-", ""));
				fechaFinalBucle=Integer.parseInt(listaCadena.get(x).split("::")[1].replace("-", ""));
				
				if (fechaInicialBucle<fechaInicial && fechaFinalBucle>fechaInicial){
					rangoFechasOcupadas[numSuperar][0]=listaCadena.get(x).split("::")[0];
					rangoFechasOcupadas[numSuperar][1]=listaCadena.get(x).split("::")[1];
					numSuperar++;
				}else if(fechaInicialBucle<fechaFinal && fechaFinalBucle>fechaInicial){
					rangoFechasOcupadas[numSuperar][0]=listaCadena.get(x).split("::")[0];
					rangoFechasOcupadas[numSuperar][1]=listaCadena.get(x).split("::")[1];
					numSuperar++;
				}
			}
			log.info("La lista de fechas es_: " + rangoFechasOcupadas);		
			
		}catch(Exception e){
			log.warning("SE HA PRODUCIDO EL ERROR: " + e);
			e.printStackTrace();
		} 
		log.info("La lista de fechas es_: " + listaFechas);
		return fechaComunOnline(rangoFechasOcupadas);
	}
	

private String[] fechaComunOnline(String[][] rangoFechasOcupadas) {
	
	String[] cadenaSalida= new String[2];
	String fechaInicioMayorSTR=rangoFechasOcupadas[0][0];
	String fechaFinalMenorSTR=rangoFechasOcupadas[0][1];
	int fechaFinalMenor=Integer.parseInt(rangoFechasOcupadas[0][1].replace("/", ""));
	int fechaInicioMayor=Integer.parseInt(rangoFechasOcupadas[0][0].replace("/", ""));
	for (int i=1;i<rangoFechasOcupadas.length;i++){
		
		if (Integer.parseInt(rangoFechasOcupadas[i][0].replace("-", ""))>fechaInicioMayor){
			fechaInicioMayorSTR=rangoFechasOcupadas[i][0];
		}
		if(Integer.parseInt(rangoFechasOcupadas[i][1].replace("-", ""))<fechaFinalMenor){
			fechaFinalMenorSTR=rangoFechasOcupadas[i][1];
		}

	}
	cadenaSalida[0]=fechaInicioMayorSTR.replace("/", "-");
	cadenaSalida[1]=fechaFinalMenorSTR.replace("/", "-");

	return cadenaSalida;
}




public boolean comprobarRangoFechas(String fechaIni,String fechaFin, List<Reserva> listaReservas) {
	
	boolean permitido=true;
	List<String[]> listaFechasOcupadas=this.devolverFechas();
	String[] fechas= new String [2];
	for (int i=0;i<listaReservas.size();i++){
		if (listaReservas.get(i).getHb().getReferencia()==this.getReferencia()){
			fechas[0]=listaReservas.get(i).getFechaInicio();
			fechas[1]=listaReservas.get(i).getFechaFin();
			listaFechasOcupadas.add(fechas);
			fechas= new String [2];
		}
	}

	int fechaInicio=Integer.parseInt(fechaIni.replace("/", ""));
	int fechaFinal=Integer.parseInt(fechaFin.replace("/", ""));

	int contador=0;
	
	int fechaFinalBucle;
	int fechaInicioBucle;
	for (int i=0;i<listaFechasOcupadas.size() && contador<this.getNumeroHabitaciones();i++){
		fechaInicioBucle=Integer.parseInt(listaFechasOcupadas.get(i)[0].replace("-", "").replace("/", ""));
		fechaFinalBucle=Integer.parseInt(listaFechasOcupadas.get(i)[1].replace("-", "").replace("/", ""));
		
		
		if (fechaInicio>=fechaInicioBucle && fechaFinal<=fechaFinalBucle){
			contador++;
		}
		if (fechaInicio>=fechaInicioBucle && fechaFinalBucle>fechaInicio && fechaFinal>=fechaFinalBucle){
			contador++;
			
		}
		if (fechaInicio<=fechaInicioBucle && fechaFinal<fechaFinalBucle && fechaFinal>=fechaInicioBucle){
			contador++;
			
		}
		if (fechaInicio<=fechaInicioBucle && fechaFinal>=fechaFinalBucle){
			contador++;
			
		}
		
		
	}
	
	if (contador==this.getNumeroHabitaciones()){
		permitido=false;
	}
	
	
	return permitido;
	
}

}

