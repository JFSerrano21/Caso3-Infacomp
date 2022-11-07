package seguridadCliente;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class ClienteMain {
    private static int puerto = 4030; // podria hacerlo con static pero asi se ve mas bonito

    public static void main(String[] args) {

        try (Socket socket = new Socket("Localhost", puerto)) {

            CteThread miThread = new CteThread(socket,0);
            miThread.run();
        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
