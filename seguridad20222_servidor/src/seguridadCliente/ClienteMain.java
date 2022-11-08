package seguridadCliente;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner; 

public class ClienteMain {
    private static int puerto = 4030; // podria hacerlo con static pero asi se ve mas bonito

    public static void main(String[] args) {


        System.out.println("Ingrese el numero de clientes a crear (4,16,32): ");
        Scanner in = new Scanner(System.in);
        int numclientes = in.nextInt();

        for(int i =0; i < numclientes; ++i){
            Socket socket = null;
        
            try{
                socket = new Socket("Localhost", puerto);
                CteThread miThread = new CteThread(socket,i);
                miThread.start();
            } catch (UnknownHostException ex) {
    
                System.out.println("Server not found: " + ex.getMessage());
    
            } catch (IOException ex) {
    
                System.out.println("I/O error: " + ex.getMessage());
            }
        }
    }
}