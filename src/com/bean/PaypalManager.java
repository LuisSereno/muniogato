package com.bean;
 
import java.io.Serializable;
import java.util.Properties;

import com.constantes.CONSTANTES;
import com.paypal.wpstoolkit.services.EWPServices;
 
public class PaypalManager implements Serializable{
     
    /**
	 * 
	 */
	private static final long serialVersionUID = -2682322632188228881L;

	public String getButton(float total, String lc) throws Exception {
    	String myPublicCert = getClass().getResource("/com/autentia/tutorial/conf/cred.p12").getPath();
        String paypalPublicCert = getClass().getResource("/com/autentia/tutorial/conf/paypal_cert_pem.txt").getPath();
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream("/com/autentia/tutorial/conf/paypal.properties"));
 
        EWPServices ewp = new EWPServices();
        StringBuffer buffer = new StringBuffer("cmd=_xclick\n");
        buffer.append("business=" + properties.getProperty("business") + "\n");
        buffer.append("cert_id=" + properties.getProperty("certId") + "\n");
        buffer.append("charset=UTF-8\n");
        buffer.append("item_name=" + "Reserva" + "\n");
        buffer.append("item_number=" + "Hotel Rural Gran Maestre" + "\n");
        buffer.append("amount=" + total + "\n");
        buffer.append("currency_code=" + properties.getProperty("currency") + "\n");
        buffer.append("return=" + properties.getProperty("returnUrl") + "\n");
        buffer.append("cancel_return=" + properties.getProperty("cancelReturn") + "\n");
        buffer.append("no_shipping=" + CONSTANTES.PAYPAL_SHIPPING + "\n");
        buffer.append("no_note=" + CONSTANTES.PAYPAL_NOTE + "\n");
        buffer.append("tax_rate=" + CONSTANTES.IMPUESTOS + "\n");
        buffer.append("lc=" + lc + "\n");
 
 
        return ewp.encryptButton(buffer.toString().getBytes("UTF-8"),myPublicCert, properties.getProperty("certPassword"), paypalPublicCert, properties.getProperty("service"),properties.getProperty("buttonImage"));
    }
}