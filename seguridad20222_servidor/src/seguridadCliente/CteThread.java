package seguridadCliente;

import seguridad20222_servidor.SecurityFunctions;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random; 

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.lang.Integer;

public class CteThread extends Thread {
    private Socket sc = null;
    private int id;
    private String dlg;
    private BigInteger p;
    private BigInteger g;
    private SecurityFunctions f;

    CteThread(Socket csP, int idP) {

        sc = csP;
        dlg = new String("Client " + idP + ": ");
        id = idP;
    }

    public void run() {

        try {
            // Cositas para que funcione to do
            PrintWriter writer = new PrintWriter(sc.getOutputStream() , true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            f = new SecurityFunctions();
            PublicKey publicaServidor = f.read_kplus("seguridad20222_servidor/datos_asim_srv.pub",dlg);

            // Iniciamos conexion
            System.out.println(dlg + "starting.");
            String text = "SECURE INIT";
            writer.println(text); // le pide al server que genere los valores de G,P y Gx

            // Recibimos G
            String linea = "";
            linea = reader.readLine();
            g = new BigInteger(linea);
            System.out.println(dlg + "Recieved G");

            // Recibimos P
            linea = reader.readLine();
            p = new BigInteger(linea);
            System.out.println(dlg + "Recieved P");

            // Recibimos Gx
            linea = reader.readLine();
            BigInteger Gx = new BigInteger(linea);
            System.out.println(dlg + "Recieved Gx");

            // Recibimos mensaje firmado
            linea = reader.readLine();
            byte[] firma = str2byte( linea);
            System.out.println(dlg + "Recieved Signature");

            // verificamos mensaje firmado y repsondemos acordemente
            String mensaje = g.toString()+","+p.toString()+","+Gx;

            boolean respuesta = f.checkSignature(publicaServidor, firma, mensaje);

            if(respuesta == true){
                // Si es el mensaje correcto
                System.out.println(dlg + "Verified Signature OK");
                writer.println("OK");

            }
            else{
                // La firma es erroena o.o
                System.out.println(dlg + "Verified Signature ERROR");
                writer.println("ERROR");
            }
            // Se que deberia poner el resto del codigo dentro de ese if si es true pero no se si eso dane cosas

            // Generamos Gy
            SecureRandom r = new SecureRandom();
            int y = Math.abs(r.nextInt());

            Long longy = Long.valueOf(y);
            BigInteger biy = BigInteger.valueOf(longy);
            BigInteger Gy = G2Y(g,biy,p);
            System.out.println(dlg + "G2Y: "+ Gy.toString());

            // Mandamos Gy al servidor
            writer.println(Gy.toString());

            // Calculamos llave maestra
            BigInteger llave_maestra = calcular_llave_maestra(Gx,biy,p);
            String str_llave = llave_maestra.toString();
            System.out.println(dlg + " llave maestra: " + str_llave);

            //Crea las dos llaves K_AB1 y K_AB2  
			SecretKey sk_srv = f.csk1(str_llave);

			SecretKey sk_mac = f.csk2(str_llave);
			
            //Generamos el numero de consulta aleatorio 

            Random rd = new Random();
            byte[] arr = new byte[16];
            rd.nextBytes(arr);
            IvParameterSpec ivSpec1 = new IvParameterSpec(arr);

            //Ciframos el numero de consulta con la llave K_AB1
            byte[] rta_consulta = f.senc(generateIvBytes(), sk_srv, ivSpec1, Integer.toString(id));

            //Envia el numero de consulta cifrado
            String str_Consulta = byte2str(rta_consulta);
            writer.println(str_Consulta);
 
            //Generamos el codigo de autenticacion del numero de consulta 
            byte [] rta_mac = f.hmac(arr, sk_mac);

            //Envia el codigo de autenticacion
            String str_aut = byte2str(rta_mac);
            writer.println(str_aut);


            //Create the Initialization Vector iv1
            byte[] iv1 = new byte[16];
	        new SecureRandom().nextBytes(iv1);

            //Sends the iv1 vector to the server
            String siv1 = byte2str(iv1);
            writer.println(siv1);





            String str_consultaServidor = reader.readLine();
			String str_macServidor = reader.readLine();
			String str_iv2Servidor = reader.readLine();
			byte[] byte_consultaServidor = str2byte(str_consultaServidor);
			byte[] byte_macServidor = str2byte(str_macServidor);
			byte[] iv2 = str2byte(str_iv2Servidor);
			IvParameterSpec ivSpec2 = new IvParameterSpec(iv2);

			//Decifran y verifican 
	    	byte[] descifrado = f.sdec(byte_consultaServidor, sk_srv,ivSpec2);
	    	boolean verificar = f.checkInt(descifrado, sk_mac, byte_macServidor);
			System.out.println(dlg + "Integrity check:" + verificar);    		

	    	if (verificar) {
	        	writer.println("OK");
	        	
	    	} else {
	    		// In this case, a client send query and MAC that do not check
	    		String mensaje2 = "ERROR";
	        	writer.println(mensaje);
	    	}

            sc.close(); 
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return; 
    }

    private BigInteger G2Y(BigInteger base, BigInteger y, BigInteger modulo) {
        return base.modPow(y,modulo);
    }

    private BigInteger calcular_llave_maestra(BigInteger base, BigInteger exponente, BigInteger modulo) {
        return base.modPow(exponente, modulo);
    }

    public byte[] str2byte( String ss)
    {
        // Encapsulamiento con hexadecimales
        byte[] ret = new byte[ss.length()/2];
        for (int i = 0 ; i < ret.length ; i++) {
            ret[i] = (byte) Integer.parseInt(ss.substring(i*2,(i+1)*2), 16);
        }
        return ret;
    }

    public String byte2str( byte[] b )
    {
        // Encapsulamiento con hexadecimales
        String ret = "";
        for (int i = 0 ; i < b.length ; i++) {
            String g = Integer.toHexString(((char)b[i])&0x00ff);
            ret += (g.length()==1?"0":"") + g;
        }
        return ret;
    }

    private byte[] generateIvBytes() {
	    byte[] iv = new byte[16];
	    new SecureRandom().nextBytes(iv);
	    return iv;
	}
}
