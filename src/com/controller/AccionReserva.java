package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.*;

import com.bean.Correos;
import com.bean.Habitaciones;
import com.bean.Reserva;
import com.bean.Usuario;
import com.constantes.CONSTANTES;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Controller principal de la aplicaci�n Se llama desde la p�gina inicial de la
 * aplicaci�n.
 * 
 * @author Sereno
 *
 */
public class AccionReserva extends HttpServlet implements Serializable {

	/**
	 * Constante para serializar la clase
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Par�metro de la clase, que servir� para mostrar los logs en la consola
	 */
	private static final Logger log = Logger.getLogger(AccionReserva.class
			.getName());

	public static HashMap<String, List<String[]>> refFechasReser = new HashMap<String, List<String[]>>();

	/**
	 * M�todo Post del servlet
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Gson gson = new Gson();
		String salida = "";
		List<String> cadenaErrores = new ArrayList<String>();
		try {

			HttpSession sesion = req.getSession(false);
			Session sesionLocal = new Session();

			log.info("Comprobamos si se ha iniciado sesión");
			if (sesion != null) {
				log.info("Comprobamos si se hay usuario");
				if (sesion.getAttribute("usuario") != null) {

					Habitaciones hb = new Habitaciones();
					Usuario usu = new Usuario();
					Reserva reser = new Reserva();
					
					log.info("Es el administrador o no?");
					log.info(sesion.getAttribute("usuario").toString());
					if (CONSTANTES.ADMIN.equals(sesion.getAttribute("usuario"))){
						log.info("ES EL ADMINISTRADOR IMITANDO AL USUARIO: ");
						log.warning(req.getParameter("usuarioRepresentado").toString());
						usu.setNickname(req.getParameter("usuarioRepresentado").toString());
					}else{
						usu.setNickname(sesion.getAttribute("usuario").toString());						
					}
					// Comprobamos que existan tanto el usuario como la
					// habitacion
					usu.setContrasena("reserva");
					usu = usu.devolverElemento();
					log.warning("El usario sigue siendo: " + usu);

					log.info("Devolvemos el usuario correspondiente");
					if (usu != null && req.getParameter("diaFin") != null
							&& req.getParameter("diaInicio") != null) {
						reser.setFechaFin(req.getParameter("diaFin"));
						reser.setFechaInicio(req.getParameter("diaInicio"));
						if (calcularNumeroDias(reser.getFechaInicio(),
								reser.getFechaFin()) > 0) {
							log.info("Se cumplen los requisitos para reservar la habitación");
							if ("Anadir".equals(req.getParameter("reserva")
									.toString())) {
								log.info("Añadimos habitacion al carrito");
								hb.setReferencia(Integer.parseInt(req
										.getParameter("codigoHabitacion")));
								hb = hb.devolverElemento();
								hb.setNumeroHabitaciones(1);
								log.info("Devuelve la habitación");
								if (hb != null) {
									salida = anadirListaReserva(req, gson,
											cadenaErrores, sesion, sesionLocal,
											hb, usu, reser);

									// Devolvemos el precio de la habitacion
									// para a�adirlo al carrito
								}
							} else {

								// Si es igual a null habr� que decirle mediante
								// un mensaje que se tiene que registrar
								cadenaErrores
										.add("Por favor, reg&iacute;strate antes de reservar");
								salida = gson.toJson(cadenaErrores);
							}
						} else {
							// Devolvemos el error correspondiente por no
							// existir el usuario
							cadenaErrores
									.add("Por favor, reg&iacute;strate antes de reservar");
							salida = gson.toJson(cadenaErrores);
						}

					} else if ("Borrar".equals(req.getParameter("reserva")
							.toString())) {

						log.info("Comprobamos si es un borrado");
						salida = borrarListaReserva(req, gson, cadenaErrores,
								sesion, sesionLocal, reser);

					} else {
						throw new Exception("Faltan datos en la reserva");
					}
				} else {

					// Si es igual a null habr� que decirle mediante un mensaje
					// que se tiene que registrar
					cadenaErrores
							.add("Por favor, reg&iacute;strate antes de reservar");
					salida = gson.toJson(cadenaErrores);

				}

				log.info("Escribimos la salida en el objeto JSON: " + salida);
				resp.setContentType("application/json");
				PrintWriter out = resp.getWriter();

				out.println(salida);
				out.flush();
				out.close();

				log.info("Sale del servlet PaginaPrincipal");
			}
		} catch (Exception e) {

			cadenaErrores
					.add("Lo sentimos, ha ocurrido un error. Vuelva a intentarlo en unos minutos");
			salida = gson.toJson(cadenaErrores);

			log.info("Escribimos la salida en el objeto JSON");
			resp.setContentType("application/json");
			PrintWriter out = resp.getWriter();

			out.println(salida);
			out.flush();
			out.close();

		}

	}

	/**
	 * @param req
	 * @param gson
	 * @param cadenaErrores
	 * @param sesion
	 * @param sesionLocal
	 * @param reser
	 * @return
	 */
	private String borrarListaReserva(HttpServletRequest req, Gson gson,
			List<String> cadenaErrores, HttpSession sesion,
			Session sesionLocal, Reserva reser) {
		String salida;
		log.info("Comprobamos es un borrado");
		// TENDREMOS AHORA QUE BORRAR LA FACTURA Y LOS CORREOS!!!
		int reservaBorrar = Integer.parseInt(req.getParameter("idReserva"));
		int contador = 0;
		Habitaciones hb = new Habitaciones();
		boolean error = false;
		List<Reserva> listaReservas = (List<Reserva>) sesion
				.getAttribute("listaReservas");
		List<Reserva> listaReservasAuxiliar = new ArrayList<Reserva>();
		float precioTotal = 0;
		while (contador < listaReservas.size()) {
			if (listaReservas.get(contador).getReferencia() == reservaBorrar) {
				hb = listaReservas.get(contador).getHb();
				if (!listaReservas.get(contador).borrarReserva()) {
					error = true;
				}
			} else {
				precioTotal = precioTotal
						+ listaReservas.get(contador).getPrecioTotal();
				listaReservasAuxiliar.add(listaReservas.get(contador));
			}
			contador++;
		}
		// Cuando pagemos, vaciamos los arrays y los precios
		if (error) {

			cadenaErrores
					.add("No se ha podido realizar el borrado, por favor, vuelva a intentarlo");
			salida = gson.toJson(cadenaErrores);
		} else {

			sesion.setAttribute("listaReservas", listaReservasAuxiliar);
			sesion.setAttribute("precioCarrito", precioTotal);

			// List <String[]>
			// listaFechasOcupadas=comprobarReservaHabitaciones(listaReservasAuxiliar,listaReservasAuxiliar.get(0).getHb());
			// sesion.setAttribute("listaFechasOcupadas",listaFechasOcupadas);

			extraerFechasOcupadas(hb, listaReservasAuxiliar);
			sesion.setAttribute("hashFechasOcupadas", refFechasReser);
			if ("OK".equals(sesion.getAttribute("compraRealizada"))) {
				reser.setNumFactura((int) sesion.getAttribute("numeroFactura"));
				reser.borrarFactura();
				Correos[] corr = (Correos[]) sesion
						.getAttribute("correosFactura");
				corr[0].borrarCorreo();
				corr[1].borrarCorreo();

				sesion.setAttribute("numeroFactura", null);
				sesion.setAttribute("correosFactura", null);
				sesion.setAttribute("compraRealizada", "KO");
			}
			sesionLocal.copiarSesion(sesion);
			salida = gson.toJson(sesionLocal);
		}
		return salida;
	}

	/**
	 * @param req
	 * @param gson
	 * @param cadenaErrores
	 * @param sesion
	 * @param sesionLocal
	 * @param hb
	 * @param usu
	 * @param reser
	 * @return
	 * @throws ParseException
	 */
	private String anadirListaReserva(HttpServletRequest req, Gson gson,
			List<String> cadenaErrores, HttpSession sesion,
			Session sesionLocal, Habitaciones hb, Usuario usu, Reserva reser)
			throws ParseException {
		
		log.warning("Que es el usuario: " + usu);
		String salida;
		// Realizamos la reserva de manera correspondiente con los datos de
		// abajo

		reser.setFechaFin(req.getParameter("diaFin"));
		reser.setFechaInicio(req.getParameter("diaInicio"));

		// Calculamos el precio total
		reser.setPrecioTotal(this.calcularPrecioTotal(reser.getFechaInicio(),
				reser.getFechaFin(), hb.getPrecio()));
		reser.setHb(hb);
		log.warning("EL usuario es: " + usu.getCorreoElec());
		reser.setUsu(usu);
		log.info("Hemos calculado el precio total y a�adido a la reserva la habitacion y el usuario");
		// Si es la primera reserva la a�adimos a la sesion, sino cogemos la
		// lista de reservas, a�adimos uno mas y luego volvemos a guardar la
		// lista de reservas
		// Esto en JS va a ser una movida considerable.
		if (sesion.getAttribute("listaReservas") != null
				&& ((List) sesion.getAttribute("listaReservas")).size() != 0) {
			log.info("La lista de reservas trae datos");
			List<Reserva> listaReservas = (List<Reserva>) sesion
					.getAttribute("listaReservas");
			// Comparamos que esta habitacion cumpla con las condiciones (que
			// haya habitaciones para reservar y en caso de no haberlas, que no
			// sea la misma fecha que la que tenemos reservada)

			Reserva reserva;
			log.info("Antes de calcular la ultima referencia");
			int ultimaReferencia = listaReservas.get(listaReservas.size() - 1)
					.getReferencia();
			float precioTotalReserva = 0;
			if (hb.comprobarRangoFechas(reser.getFechaInicio(),
					reser.getFechaFin(), listaReservas)) {
				for (int i = 0; i < reser.getHb().getNumeroHabitaciones(); i++) {
					log.info("Dentro del for de numero de habitaciones");
					ultimaReferencia = ultimaReferencia + 1;
					reserva = new Reserva(reser.getHb(), reser.getUsu(), 0,
							reser.getFechaInicio(), reser.getFechaFin(),
							reser.getPrecioTotal(), reser.getNumFactura());
					reserva.setReferencia(ultimaReferencia);
					listaReservas.add(reserva);
					precioTotalReserva = precioTotalReserva
							+ reserva.getPrecioTotal();
				}

				// yo tengo ahora todas mis reservas, ahora lo que hago es
				// separarlas por habitacion y para cada habitacion, guardo en
				// mi hash las fechas seleccionadas

				extraerFechasOcupadas(hb, listaReservas);

				log.info("Añadimos lista de reservas al atributo sesion");
				sesion.setAttribute("listaReservas", listaReservas);
				sesion.setAttribute("precioCarrito",
						((Float) sesion.getAttribute("precioCarrito"))
								+ precioTotalReserva);
				sesion.setAttribute("hashFechasOcupadas", refFechasReser);
				sesionLocal.copiarSesion(sesion);
				salida = gson.toJson(sesionLocal);

			} else {
				cadenaErrores
						.add("Por favor, ese rango de fechas no está permitido");
				salida = gson.toJson(cadenaErrores);
			}
		} else {
			log.info("La lista de reservas está vacía");
			List<Reserva> listaReservas = new ArrayList<Reserva>();
			reser.setReferencia(1);
			listaReservas.add(reser);
			float precioTotalReserva = reser.getPrecioTotal();
			log.info("Después de añadir el precio total");
			if (hb.comprobarRangoFechas(reser.getFechaInicio(),
					reser.getFechaFin(), listaReservas)) {
				log.info("NO COMPRUEBA EL RANGO DE FECHAS BIEN");
				List<String[]> listaFechasOcupadas = comprobarReservaHabitaciones(
						listaReservas, hb);
				refFechasReser.put(String.valueOf(hb.getReferencia()),
						listaFechasOcupadas);
				log.info("Añadimos las reservas al atributo sesion");
				sesion.setAttribute("listaReservas", listaReservas);
				sesion.setAttribute("precioCarrito", precioTotalReserva);
				sesion.setAttribute("hashFechasOcupadas", refFechasReser);
				sesionLocal.copiarSesion(sesion);
				salida = gson.toJson(sesionLocal);
			} else {
				log.info("COMPRUEBA EL RANGO DE FECHAS BIEN");
				cadenaErrores
						.add("Por favor, ese rango de fechas no está permitido");
				salida = gson.toJson(cadenaErrores);
			}
		}
		log.info("salida es: "  +  salida);
		return salida;
	}

	/**
	 * @param hb
	 * @param listaReservas
	 * @param listaReservasAuxiliar
	 */
	private void extraerFechasOcupadas(Habitaciones hb,
			List<Reserva> listaReservas) {
		List<Reserva> listaReservasAuxiliar = new ArrayList<Reserva>();
		List<String[]> listaFechasOcupadas;
		for (int x = 0; x < listaReservas.size(); x++) {
			if (hb.getReferencia() == listaReservas.get(x).getHb()
					.getReferencia()) {
				listaReservasAuxiliar.add(listaReservas.get(x));
			}
		}
		listaFechasOcupadas = comprobarReservaHabitaciones(
				listaReservasAuxiliar, hb);
		refFechasReser.put(String.valueOf(hb.getReferencia()),
				listaFechasOcupadas);
		listaFechasOcupadas = new ArrayList<String[]>();
	}

	/**
	 * M�todo Get del servlet
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		doPost(request, response);
	}

	private float calcularPrecioTotal(String fechaInicio, String fechaFin,
			float precioHabitacion) {

		float precioTotal = 0;

		precioTotal = calcularNumeroDias(fechaInicio, fechaFin) * precioHabitacion;

		return precioTotal;
	}

	public static int calcularNumeroDias(String fechaInicio, String fechaFin) {

		String fechaInicioOrdernada = fechaInicio.replace("/", "");
		String fechaFinOrdenada = fechaFin.replace("/", "");

		int numDias = Integer.parseInt(fechaFinOrdenada)
				- Integer.parseInt(fechaInicioOrdernada);
		if (numDias == 0) {
			numDias = 1;
		}
		return numDias;
	}

	// ESTA PARTE ES IMPORTANTE, PERO NO SE COMO PENSARLA AHORA MISMO, HAY QUE
	// DARLE UNA VUELTA
	private List<String[]> comprobarReservaHabitaciones(
			List<Reserva> listaReservas, Habitaciones hb) {
		List<Reserva> listaReservasTipo = new ArrayList<Reserva>();
		for (int x = 0; x < listaReservas.size(); x++)
			listaReservasTipo.add(listaReservas.get(x));

		String fechaInicio = "";
		String fechaFin = "";
		List<String[]> listaReservasOnline = new ArrayList<String[]>();
		for (int i = 0; i < listaReservasTipo.size(); i++) {

			fechaInicio = listaReservasTipo.get(i).getFechaInicio();
			fechaFin = listaReservasTipo.get(i).getFechaFin();

			listaReservasOnline.add(hb.devolverFechasOnline(fechaInicio,
					fechaFin));

		}

		return listaReservasOnline;
	}

}
