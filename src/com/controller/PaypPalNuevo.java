// #Create Payment Using PayPal Sample
// This sample code demonstrates how you can process a 
// PayPal Account based Payment.
// API used: /v1/payments/payment
package com.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.Reserva;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;
import com.paypal.util.GenerateAccessToken;
import com.paypal.util.ResultPrinter;

/**
 * @author lvairamani
 * 
 */
public class PaypPalNuevo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(PaypPalNuevo.class
			.getName());
	static Map<String, String> map = new HashMap<String, String>();

	private String redirect = "";

	public void init(ServletConfig servletConfig) throws ServletException {
		// ##Load Configuration
		// Load SDK configuration for
		// the resource. This intialization code can be
		// done as Init Servlet.
		InputStream is = PaypPalNuevo.class
				.getResourceAsStream("/com/autentia/tutorial/conf/sdk_config.properties");

		try {
			PayPalResource.initConfig(is);
		} catch (PayPalRESTException e) {
			LOGGER.warning(e.getMessage());
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	// ##Create
	// Sample showing to create a Payment using PayPal
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		createPayment(req, resp);
		if (redirect.equals("")) {
			req.getRequestDispatcher("jsp/response.jsp").forward(req, resp);
		} else {
			resp.sendRedirect(redirect);
			redirect = "";
		}
	}

	public Payment createPayment(HttpServletRequest req,
			HttpServletResponse resp) {
		Payment createdPayment = null;
		// ###AccessToken
		// Retrieve the access token from
		// OAuthTokenCredential by passing in
		// ClientID and ClientSecret
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
			LOGGER.warning(e.getMessage());
			e.printStackTrace();
		}

		// ###Details
		// Let's you specify details of a payment amount.

		HttpSession sesion = req.getSession(false);
		float total=  (float) sesion.getAttribute("precioCarrito");
		Details details = new Details();
		details.setSubtotal(String.format("%.2f", total));

		// ###Amount
		// Let's you specify a payment amount.
		Amount amount = new Amount();
		amount.setCurrency("EUR");
		// Total must be equal to sum of shipping, tax and subtotal.
		amount.setTotal(String.format("%.2f", total));
		amount.setDetails(details);

		// ###Transaction
		// A transaction defines the contract of a
		// payment - what is the payment for and who
		// is fulfilling it. Transaction is created with
		// a `Payee` and `Amount` types
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription("Reserva en Hotel Rural Gran Maestre");

		// ### Items
		List<Reserva> listaReserva=(List<Reserva>) sesion.getAttribute("listaReservas");
		ItemList itemList = new ItemList();
		List<Item> items = new ArrayList<Item>();
		Item item = new Item();
		
		for (Reserva reser : listaReserva){
			item.setName(reser.getHb().getNombre() + " Fecha Entrada: " + reser.getFechaInicio() + " Fecha Salida: " + reser.getFechaFin())
			.setQuantity(String.valueOf(AccionReserva.calcularNumeroDias(reser.getFechaInicio(), reser.getFechaFin())))
			.setCurrency("EUR").setPrice(String.format("%.2f", reser.getHb().getPrecio()));
			items.add(item);	
			item = new Item();
		}
		itemList.setItems(items);

		transaction.setItemList(itemList);

		// The Payment creation API requires a list of
		// Transaction; add the created `Transaction`
		// to a List
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		// ###Payer
		// A resource representing a Payer that funds a payment
		// Payment Method
		// as 'paypal'
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		// ###Payment
		// A Payment Resource; create one using
		// the above types and intent as 'sale'
		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);

		// ###Redirect URLs
		RedirectUrls redirectUrls = new RedirectUrls();
		String guid = UUID.randomUUID().toString().replaceAll("-", "");
		redirectUrls.setCancelUrl(req.getScheme() + "://" + req.getServerName()
				+ ":" + req.getServerPort() + req.getContextPath()
				+ "/compraIncorrecta?guid=" + guid);
		redirectUrls.setReturnUrl(req.getScheme() + "://" + req.getServerName()
				+ ":" + req.getServerPort() + req.getContextPath()
				+ "/compraCorrecta?guid=" + guid);
		payment.setRedirectUrls(redirectUrls);

		// Create a payment by posting to the APIService
		// using a valid AccessToken
		// The return object contains the status;
		try {
			createdPayment = payment.create(apiContext);
			LOGGER.info("Created payment with id = " + createdPayment.getId()
					+ " and status = " + createdPayment.getState());
			// ###Payment Approval Url
			Iterator<Links> links = createdPayment.getLinks().iterator();
			while (links.hasNext()) {
				Links link = links.next();
				LOGGER.info(link.getHref());
				if (link.getRel().equalsIgnoreCase("approval_url")) {
					redirect = link.getHref();
				}
			}
			map.put(guid, createdPayment.getId());
		} catch (PayPalRESTException e) {
			ResultPrinter.addResult(req, resp, "Payment with PayPal",
					Payment.getLastRequest(), null, e.getMessage());
		}
		return createdPayment;
	}
}
