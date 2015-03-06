package com.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;

import com.bean.Correos;
import com.bean.Reserva;
import com.bean.Usuario;
import com.constantes.CONSTANTES;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.util.GenerateAccessToken;
import com.paypal.util.ResultPrinter;

/**
 * Controller principal de la aplicaci�n
 * Se llama desde la p�gina inicial de la aplicaci�n.
 * @author Sereno
 *
 */
public class CancelaPaypal extends HttpServlet implements Serializable{

	/**
	 * Constante para serializar la clase
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * Par�metro de la clase, que servir� para mostrar los logs en la consola
     */
    private static final Logger log = Logger.getLogger(CancelaPaypal.class.getName());
    
    
    /**
     * M�todo Post del servlet
     */
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    	try {
    			
    		log.info ("Entra en el servlet Cancelar");
    		
    		HttpSession sesion = req.getSession(false);
    		log.info("Antes de borrar la reserva");
    		borrarReserva(sesion);
    		log.info("Despu�s de borrar la reserva");
	        RequestDispatcher rd =null;
	        req.setAttribute("despliege", "0");
	        log.info("Despliegue 0");
	        rd=req.getRequestDispatcher("jsp/PaginaBasica.jsp");
	        log.info("Antes del forward");
	        rd.forward(req,resp);

	    	log.info ("Sale del servlet Cancelar");
	    	
		}catch (Exception e) {
			log.info(e.getMessage());
			log.info ("No se ha podido ir a la p�gina web correcta porque ha ocurrido un error");
	        log.info("Redirigiendo...");
	        e.printStackTrace();
	        resp.sendRedirect("jsp/error.jsp");
    	
		}
        
    }

   /*
    * M�todo Get del servlet
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {  
    	doPost(request, response);  
	}
    
	/**
	 * @param sesion
	 * @param listaReserva
	 */
	protected void borrarReserva(HttpSession sesion) {
		log.info("Entra en Borrar Reserva");
		List <Reserva> listaReserva = new ArrayList<Reserva>();
		listaReserva=(List<Reserva>) sesion.getAttribute("listaReservas");
		Reserva reser= new Reserva();
		List <Integer> listaBorrar = new ArrayList <Integer> ();
		log.info("Antes del primer for para coger las referencias");
		for (int i=0;i<listaReserva.size();i++){
			listaBorrar.add(listaReserva.get(i).devolverElemento().getReferencia());
		}
		log.info("Antes del segundo for para coger las referencias");
		for (int i=0;i<listaBorrar.size();i++){
			reser.borrarReserva(listaBorrar.get(i));
		}
		log.info("Comprobamos si la compra realizada ha sido OK");
		if ("OK".equals(sesion.getAttribute("compraRealizada"))){
			log.info("ES OK LA COMPRA REALIZADA");
			reser.setNumFactura((int)sesion.getAttribute("numeroFactura"));
			log.info("Borramos la factura");
			reser.borrarFactura();
			Correos [] corr=(Correos[]) sesion.getAttribute("correosFactura");
			log.info("Borramos el primer correo");
			corr[0].borrarCorreo();
			log.info("Borramos el segundo correo");
			corr[1].borrarCorreo();
			sesion.setAttribute("numeroFactura",null);
			sesion.setAttribute("correosFactura",null);
			log.info("Hemos borrado los correos y las facturas");
		}
		sesion.setAttribute("compraRealizada", "KO");
		
		log.info("Sale de Borrar Reserva");
	}


}
