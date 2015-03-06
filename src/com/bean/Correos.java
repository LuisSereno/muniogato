package com.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.bbdd.CRUD;
import com.constantes.CONSTANTES;
import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailServiceFactory;
import com.interfaces.ConectaRecursos;

public class Correos  implements ConectaRecursos<Correos>, Serializable {

    /**
     * Par�metro de la clase, que servir� para mostrar los logs en la consola
     */
    private static final Logger log = Logger.getLogger(Correos.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 8388719614067216670L;
	
	/**
	 * 
	 */
	private String email;
	
	
	/**
	 * 
	 */
	private String mensaje;
	
	/**
	 * 
	 */
	private String asunto;
	
	/**
	 * 
	 */
	private String estado;
	
	/**
	 * 
	 */
	private String tipoCorreo;
	
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
	public Correos(String email,String mensaje,String asunto,String tipoCorreo,String nombre) {
		this.asunto=asunto;
		this.email=email;
		this.mensaje=mensaje;
		this.tipoCorreo=tipoCorreo;
		this.setNombre(nombre);
	}

	/**
	 * 
	 */
	public Correos() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}



	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}



	/**
	 * @return the asunto
	 */
	public String getAsunto() {
		return asunto;
	}



	/**
	 * @param asunto the asunto to set
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}



	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}



	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	
	/**
	 * @return the tipoCorreo
	 */
	public String getTipoCorreo() {
		return tipoCorreo;
	}



	/**
	 * @param tipoCorreo the tipoCorreo to set
	 */
	public void setTipoCorreo(String tipoCorreo) {
		this.tipoCorreo = tipoCorreo;
	}



	/**
	 * @return the emailEnvio
	 */
	public String getEmail() {
		return email;
	}



	/**
	 * @param emailEnvio the emailEnvio to set
	 */
	public void setEmailEnvio(String email) {
		this.email = email;
	}


	public boolean enviarCorreo (Usuario administrador,boolean Factura){
		
		boolean enviado=true;
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
		session.setDebug(true);
		
		MimeMessage message = new MimeMessage(session);
		
		// Quien envia el correo
		try {
			message.setFrom(new InternetAddress(administrador.getCorreoElec()));
	
			// A quien va dirigido
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.getEmail()));
			
			message.setSubject(this.getAsunto());
			if (!Factura){
				message.setText(this.getMensaje());
			}else{
				// Create your new message part
				BodyPart messageBodyPart = new MimeBodyPart();
				String htmlText = this.getMensaje();
				messageBodyPart.setContent(htmlText, "text/html");
				// Create a related multi-part to combine the parts
				MimeMultipart multipart = new MimeMultipart("related");
				multipart.addBodyPart(messageBodyPart);
				
				// Create part for the image
				messageBodyPart = new MimeBodyPart();

				messageBodyPart.setFileName("FacturaHRGM.jpg");
				// Fetch the image and associate to part
				DataSource fds = new FileDataSource("imagenes/general/logohotel-2.png");
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader("Content-ID","<logotipo>");

				
				// Add part to multi-part
				multipart.addBodyPart(messageBodyPart);

				// Associate multi-part with message
				message.setContent(multipart);
			}
			log.info("ANTES DEL TRANSPORT");
			Transport.send(message);
			log.info("DESPUES DEL TRANSPORT");
			
			this.setEstado("Enviado");
			this.actualizarElemento();
			
		} catch (MessagingException e) {
			log.warning("ERROR: " + e);
			this.setEstado("No enviado");
			this.insertarElemento();
			e.printStackTrace();
			enviado=false;
		} catch (Exception e) {
			enviado=false;
			log.warning(e.getLocalizedMessage() + e.getMessage());
			log.warning("Error al enviar el correo");
			e.printStackTrace();
			
		}
		return enviado;
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

	@Override
	public List<Correos> devolverTodo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Correos> devolverTodoFecha(Date fechaOrigen, Date fechaDestino) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Correos> devolverTodoPrecio(int precioMin, int precioMax) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Correos> devolverTodoTipo(int tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Correos> devolverTodoOcupacionMax(int ocupacionMax,
			int ocupacionMin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Correos devolverElemento() {
		//Esto tendremos que ponerlo como null para controlar el error m�s arriba tal y como hago con el usuario
		Correos corr= new Correos();
		try{
			CRUD bbdd;
			bbdd= new CRUD();
			String [] cadena = {this.getEmail(),this.getAsunto(),this.getEstado(),this.getNombre()};
			List <String> listaCadena= new ArrayList<String>();
			
			listaCadena=bbdd.leer(CONSTANTES.SELECCIONACORREO, cadena);
			if (listaCadena.size()!=0){
				corr=this.traducirElemento(listaCadena.get(0));
			}
		}catch(Exception e){
			e.printStackTrace();
		} 
		return corr;
	}

	@Override
	public Correos traducirElemento(String cadenaEntera) {
		Correos corr= new Correos();
		
		try{
			String[] cadena = cadenaEntera.split("::");
			int referencia= Integer.parseInt(cadena[0]);
			String [] cadenaParametros = new String[1];
			cadenaParametros[0]=String.valueOf(referencia);
			
			corr.setReferencia(referencia);
			corr.setEmailEnvio(cadena[1]);
			corr.setAsunto(cadena[2]);
			corr.setMensaje(cadena[3]);
			corr.setEstado(cadena[4]);
			corr.setNombre(cadena[5]);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return corr;
	}

	@Override
	public int insertarElemento() {
		int referenciaInsercion=-1;
		try {
			CRUD bbdd= new CRUD();
		
			String [] valores = {this.email,this.asunto,this.mensaje,this.estado,this.nombre};
			referenciaInsercion=bbdd.insertar(CONSTANTES.INSERTACORREO, valores);
		} catch (SQLException e) {
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return referenciaInsercion;
	}
	
	public int actualizarElemento() {
		int referenciaInsercion=-1;
		try {
			CRUD bbdd= new CRUD();
		
			String [] valores = {this.email,this.asunto,this.mensaje,this.estado,this.nombre,String.valueOf(this.getReferencia())};
			referenciaInsercion=bbdd.insertar(CONSTANTES.ACTUALIZACORREO, valores);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return referenciaInsercion;
	}

	
	public int borrarCorreo() {
		int referenciaInsercion=-1;
		try {
			CRUD bbdd= new CRUD();
		
			String [] valores = {String.valueOf(referencia)};
			referenciaInsercion=bbdd.insertar(CONSTANTES.BORRARCORREO, valores);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return referenciaInsercion;
	}
	
	
	public Correos devolverElementoReferencia() {
		//Esto tendremos que ponerlo como null para controlar el error m�s arriba tal y como hago con el usuario
		Correos corr= new Correos();
		try{
			CRUD bbdd;
			bbdd= new CRUD();
			String [] cadena = {String.valueOf(this.getReferencia())};
			List <String> listaCadena= new ArrayList<String>();
			
			listaCadena=bbdd.leer(CONSTANTES.ENVIARCORREOADMINISTRADOR, cadena);
			if (listaCadena.size()!=0){
				corr=this.traducirElemento(listaCadena.get(0));
			}
		}catch(Exception e){
			e.printStackTrace();
		} 
		return corr;
	}
	
}


class GMailAuthenticator extends Authenticator {
    String user;
    String pw;
    public GMailAuthenticator (String username, String password)
    {
       super();
       this.user = username;
       this.pw = password;
    }
   public PasswordAuthentication getPasswordAuthentication()
   {
      return new PasswordAuthentication(user, pw);
   }
}
