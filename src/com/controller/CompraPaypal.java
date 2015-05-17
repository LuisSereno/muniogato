package com.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.bean.Correos;
import com.bean.Reserva;
import com.bean.Usuario;
import com.constantes.CONSTANTES;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;
import com.paypal.util.GenerateAccessToken;
import com.paypal.util.ResultPrinter;

/**
 * Controller principal de la aplicaci�n
 * Se llama desde la p�gina inicial de la aplicaci�n.
 * @author Sereno
 *
 */
public class CompraPaypal extends HttpServlet implements Serializable{

	/**
	 * Constante para serializar la clase
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * Par�metro de la clase, que servir� para mostrar los logs en la consola
     */
    private static final Logger log = Logger.getLogger(CompraPaypal.class.getName());
    
    
	public void init(ServletConfig servletConfig) throws ServletException {
		log.info("Entra en el ini de compraPayPal");
		// ##Load Configuration
		// Load SDK configuration for
		// the resource. This intialization code can be
		// done as Init Servlet.
		InputStream is = PaypPalNuevo.class
				.getResourceAsStream("/com/autentia/tutorial/conf/sdk_config.properties");

		try {
			PayPalResource.initConfig(is);
		} catch (PayPalRESTException e) {
			log.warning(e.getMessage());
		}

	}
    /**
     * M�todo Post del servlet
     */
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

	   log.info ("Entra en dopost CompraPayPal");
    	try {
    		
    		HttpSession sesion = req.getSession(false);
    		
    		if (sesion.getAttribute("usuario") != null && sesion.getAttribute("usuario") != "admin"){
         		Payment createdPayment = null;
        		APIContext apiContext = null;
        		String accessToken = null;
        		try {
        			accessToken = GenerateAccessToken.getAccessToken();

        			// ### Api Context
        			// Pass in a `ApiContext` object to authenticate
        			// the call and to send a unique request id
        			// (that ensures idempotency). The SDK generates
        			// a request id if you do not pass one explicitly.
        			apiContext = new APIContext(accessToken);
        			// Use this variant if you want to pass in a request id
        			// that is meaningful in your application, ideally
        			// a order id.

        			// String requestId = Long.toString(System.nanoTime());
        			// apiContext = new APIContext(accessToken, requestId);

        		} catch (PayPalRESTException e) {
        			log.warning("ERRORpaypalrest: " + e.getMessage());
        			e.printStackTrace();
        		}
        		
        		if (req.getParameter("PayerID") != null) {
        			Payment payment = new Payment();
        			if (req.getParameter("guid") != null) {
        				payment.setId(PaypPalNuevo.map.get(req.getParameter("guid")));
        			}

        			PaymentExecution paymentExecution = new PaymentExecution();
        			paymentExecution.setPayerId(req.getParameter("PayerID"));
        			try {
        				createdPayment = payment.execute(apiContext, paymentExecution);
        				log.info("Datos de la compra");
        				log.info(createdPayment.getCreateTime());
        				log.info(createdPayment.getId());
        				log.info(createdPayment.getIntent());
        				log.info(createdPayment.getUpdateTime());
        				log.info(createdPayment.getPayer().toJSON());
        				log.info(createdPayment.toJSON());
        				log.info(createdPayment.toString());
        			} catch (PayPalRESTException e) {
        				log.warning(e.getLocalizedMessage() + e.getMessage());
        				log.warning(e.getStackTrace().toString());
        				e.printStackTrace();
        				log.warning("Error: ultimo request " + Payment.getLastRequest());
        			}
        		}    			
    		}
 
    		
    		log.info ("Entra en el servlet Comprar");
    		List <Reserva> listaReserva = new ArrayList<Reserva>();
    		listaReserva=(List<Reserva>) sesion.getAttribute("listaReservas");
    		List <String> mensajesAgradecimiento= new ArrayList<String>();
    		int numeroFactura=(int) sesion.getAttribute("numeroFactura");
    		Correos [] correos = (Correos[]) sesion.getAttribute("correosFactura");
    		
    		//Recogemos el mail del usuario administrador
    		Usuario usuario= new Usuario();
    		usuario.setNickname("admin");
    		usuario.setContrasena("reserva");
    		usuario=usuario.devolverElemento();
    		
    		Reserva reser= new Reserva();
    		Correos correo= new Correos();
    		correo=correos[0];
			//Nos enviamos el correo primero a nosotros
    		
    		reser.insertarElemento(numeroFactura,-1);
    		
			if (correo.enviarCorreo(usuario,true)){
				correo= new Correos();
				correo=correos[1];
    			if (!correo.enviarCorreo(usuario,true)){
        			mensajesAgradecimiento.add("Mi señor, tenemos un problema");
        			mensajesAgradecimiento.add("Acaba de ocurrir un error y no ha sido posible conseguir que su consulta nos llege.");
        			mensajesAgradecimiento.add("Por favor, vuelva a realizar al consulta en unos minutos. Disculpe las molestias.");

    			}else{
    				reser.actualizarEnvioFactura(numeroFactura);
    				mensajesAgradecimiento.add("¡¡¡Gracias por reservar con nosotros!!!");
    				mensajesAgradecimiento.add("Ahora recibirá un email en el que se le confirmar su reserva.");
    				mensajesAgradecimiento.add("Esperemos que pase una plácida estancia con nosotros");
    			}
    			
			}
			else{
    			mensajesAgradecimiento.add("Mi señor, tenemos un problema");
    			mensajesAgradecimiento.add("Acaba de ocurrir un error y no ha sido posible conseguir que su consulta nos llege.");
    			mensajesAgradecimiento.add("Por favor, vuelva a realizar al consulta en unos minutos. Disculpe las molestias.");

			}

 		
			//Vaciamos todos los datos del usuario
			listaReserva.clear();
			sesion.setAttribute("listaReservas",null);
			sesion.setAttribute("precioCarrito",null);
			sesion.setAttribute("numeroFactura",null);
			sesion.setAttribute("correosFactura",null);
			sesion.setAttribute("compraRealizada","KO");
			
	        RequestDispatcher rd =null;
	        req.setAttribute("despliege", "4");
	        req.setAttribute("listaMensajesAgradecimiento", mensajesAgradecimiento);
	        rd=req.getRequestDispatcher("jsp/PaginaBasica.jsp");
	        rd.forward(req,resp);

			
	    	log.info ("Sale del servlet Compra");
	    	
		}catch (Exception e) {
			log.info(e.getMessage() + e.getLocalizedMessage());
			log.info ("No se ha podido ir a la página web correcta porque ha ocurrido un error");
	        log.info("Redirigiendo...");
	        e.printStackTrace();
	        resp.sendRedirect("jsp/error.jsp");
    	
		}
        
    }


   /**
    * M�todo Get del servlet
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {  
    	doPost(request, response);  
	} 
    
    
}
