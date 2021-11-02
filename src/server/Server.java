/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.message.Message;
import server.serverSocket.Hilo;

/**
 *
 * @author Adrian Franco
 */
public class Server {
    
    private ResourceBundle configFile;
    private final static int PORT = 5001;
    private String port;
    private int maxConnection;
    public static void main(String[] args) {
        // TODO code application logic here
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            Socket clientSocket = null;

            while (true) {
                clientSocket = serverSocket.accept();
                Hilo hilo = new Hilo(clientSocket);
                hilo.start();
                
               
            }
        } catch (IOException ex) {

            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void readFile(){
        this.configFile=ResourceBundle.getBundle("server.serverSocket.ServerProperties");
        this.port=this.configFile.getString("PORT");
        this.maxConnection=Integer.parseInt(this.configFile.getString("MAXCONNECTIONS"));
    }
}
