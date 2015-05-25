package com.constantes;

import java.io.Serializable;

/**
 * Fichero de constantes de la aplicación.
 * @author Sereno
 *
 */
public class CONSTANTES implements Serializable{
	
	/**
	 * Constante para serializar la clase
	 */
    private static final long serialVersionUID = 1L;

	/**
	 * Constante para clientID para PayPal
	 */
    public static final String CLIENT_ID="AXiseB4MXML_EezwE-grLSrqEuVXDMilcIJqJqIEP_9hY3TOVYWgi0A23p6V2c4RdEesalfcL4oo3mzs";

	/**
	 * Constante para clientSecret para PayPal
	 */
    public static final String CLIENT_SECRET="ECWY9AmfqDmXOr9RtHyd-6PwNdR7HJgd0CdVLGXqj-khVek0_ZBIglITAhzWp1FMx3Prk1KqoMZa-jLc";

    
    /**
     * El usuario del administrador por defecto
     */
    
    public static final String ADMIN="admin";
    /**
     * 
     */
    public static final int TAMANOCADENAMAX=80;
    
    /**
     * 
     */
    public static final int TAMANOCADENAMIN=2;
    
    /**
     * 
     */
    public static final int TAMANOCALLEMIN=3;
    
    /**
     * 
     */
    public static final int TAMANODIRECCIONMAX=30;
    
    /**
     * 
     */
    public static final int TAMANOEMAILMAX=50;
    
    /**
     * 
     */
    public static final int TAMANOUSUARIOMAX=20;
    
    /**
     * 
     */
    public static final int TAMANOCONTRASENAMINIMA=5;

    
    /**
     * 
     */
    public static final int TAMANOTELEFONOMIN=100000000;
    
    /**
     * 
     */
    public static final int TAMANOTELEFONOMAX=999999999;
    
    /**
     * 
     */
    public static final int TAMANONUMEROMIN=0;
    
    /**
     * 
     */
    public static final int TAMANONUMEROMAX=999;
    
    
    /**
     * 
     */
    public static final String ASUNTORESERVA="Reserva en el Hotel Rural Gran Maestre";
    
    /**
     * 
     */
    public static final String PAYPAL_NOTE = "1";
    
    /**
     * 
     */
    public static final String PAYPAL_SHIPPING = "0";
    
    /**
     * 
     */
    public static final String IMPUESTOS = "0";
    
    /**
     * Constante de conexion con la base de datos
     */
//	public static final String CONSTANTECONEXION = "jdbc:google:rdbms://hotelruralbelen:instanciapruebas/hotelruralbelen?user=root";
    public static final String CONSTANTECONEXION = "jdbc:google:rdbms://hotelruralbelen:hotelruralbelen/hotelruralbelen?user=root";
    
	
	/**
	 * 
	 */
    public static final String DEVOLVERTODOFECHA = "select * from ? where fecha>? and fecha<?"; 
    
    /**
     * 
     */
    public static final String DEVOLVERTODOPRECIO = "select * from ? where precio>? and precio<?"; 
    
    /**
     * 
     */
    public static final String DEVOLVERTODOTIPO = "select * from ? where tipo=?"; 
    
    /**
     * 
     */
    public static final String DEVOLVERTODOOCUPAMAX = "select * from ? where ocupacionMax>? and ocupacionMax<?"; 
    
    /**
     * 
     */
    public static final String DEVOLVERFILA = "select * from habitaciones where referencia=?"; 
    
    /**
     * 
     */
    public static final String DEVOLVERTODO = "select * from habitaciones"; 
    
    /**
     * 
     */
    public static final String DEVOLVERHABITACIONESDISPONIBLES ="select count(*) from reservas where referenciaHab=?";
    
    
    /**
     * 
     */
    public static final String DEVOLVERFECHASHABITACION = "select r.fechaInicio,r.fechaFin from reservas r JOIN habitaciones h ON (h.referencia=r.referenciaHab) WHERE h.referencia=? and r.fechaFin>CURDATE() order by r.fechaInicio asc"; 
    
    /**
     * 
     */
    public static final String INSERTARUSUARIO = "insert into usuarios (nombre,apellido1,apellido2,dni,calle,numero,ciudad,provincia,pais,telefono,usuario,contrasena,email,fecha) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
   
    /**
     * 
     */
    public static final String DEVOLVERUSUARIO = "select * from usuarios where usuario=? and contrasena=?";     
    
    /**
     * 
     */
    public static final String DEVOLVERUSUARIORESERVA = "select * from usuarios where usuario=?";     
    
    /**
     * 
     */
    public static final String DEVOLVERUSUARIORREFERENCIA = "select * from usuarios where referencia=?";     
    
    /**
     * 
     */
    public static final String DEVOLVERUSUARIOS = "select * from usuarios"; 
    
    /**
     * 
     */
    public static final String INSERTARESERVA = "insert into reservas (referenciaHab,referenciaUsu,fechaInicio,fechaFin,precio) values (?,?,?,?,?)"; 
    
    /**
     * 
     */
    public static final String SELECCIONARESERVA = "select DISTINCT * from reservas where referenciaHab=? and referenciaUsu=? and fechaInicio=? and fechaFin=? and precio=?  LIMIT 1"; 
    
    
    /**
     * 
     */
    public static final String DEVOLVERRESERVASORDENADAS = "select * from reservas where fechaFin>CURDATE() order by fechaInicio asc"; 
    
    /**
     * 
     */
    public static final String DEVOLVERTODASFACTURASADMINISTRADOR = "select f.referencia,c.nombre,pagada,enviada, sum(precio) from facturas f, correos c, reservas r where f.refCorreo=c.referencia and f.referencia=r.refFactura group by r.refFactura"; 
    
    /**
     * 
     */
    public static final String BORRARRESERVAREFERENCIA = "delete from reservas where referencia=? LIMIT 1"; 
    
    /**
     * 
     */
    public static final String BORRARRESERVA = "delete from reservas where referencia=? LIMIT 1"; 
    
    /**
     * 
     */
    public static final String BORRARRESERVAFACTURA = "delete from reservas where refFactura=?"; 
    
    /**
     * 
     */
    public static final String ACTUALIZARRESERVA = "update reservas set refFactura=? where referencia=?"; 
    
    
    /**
     * 
     */
    public static final String DEVOLVERREFCORREO = " select refCorreo from facturas where referencia=?"; 
   
    /**
     * 
     */
    public static final String INSERTACORREO = "insert into correos (email,asunto,mensaje,estado,nombre) values (?,?,?,?,?)"; 
    
    /**
     * 
     */
    public static final String ACTUALIZACORREO = "update correos set email=?,asunto=?,mensaje=?,estado=?,nombre=? where referencia=?"; 
    
    /**
     * 
     */
    public static final String BORRARCORREO = "delete from correos where referencia=?  LIMIT 1"; 
    
    /**
     * 
     */
    public static final String ENVIARCORREOADMINISTRADOR="select * from correos c where c.referencia=?";
    
    
    /**
     * 
     */
    public static final String NUMEROMAXIMOFACTURA = "select max(referencia) from facturas";     
   
    /**
     * 
     */
    public static final String INSERTAFACTURA = "insert into facturas (refCorreo,pagada,enviada) values (?,?,?)"; 
    
    /**
     * 
     */
    public static final String ACTUALIZARFACTURAPAGADA = "update facturas set pagada=? where referencia=?"; 
    
    /**
     * 
     */
    public static final String ACTUALIZARFACTURAENVIADA = "update facturas set enviada=? where referencia=?"; 
    
    /**
     * 
     */
    public static final String BORRARFACTURA = "delete from facturas where referencia=?  LIMIT 1"; 
    
    /**
     * 
     */
    public static final String DEVOLVERFACTURA = "select * from facturas where referencia=?  LIMIT 1"; 
    
    
    /**
     * 
     */
    public static final String SELECCIONACORREO = "select DISTINCT * from correos where email=? and asunto=? and mensaje=? and estado=? and nombre=?  LIMIT 1"; 
    
    
}
