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
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.message.Message;

/**
 *
 * @author 2dam
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    private final static int PORT = 9999; 
    public static void main(String[] args) {
        // TODO code application logic here
            ServerSocket serverSocket = null;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    
        try {
            serverSocket = new ServerSocket(PORT);
            Socket clientSocket = null;
            
            while(true){
                clientSocket = serverSocket.accept();
                
                ois = new ObjectInputStream(clientSocket.getInputStream());
                oos = new ObjectOutputStream(clientSocket.getOutputStream());
                
                Message msg = (Message) ois.readObject();
                
                String opc = msg.getMsg().toString();
                switch(opc){
                    case "SIGNUP":
                        
                        break;
                    case "SIGNIN":
                        
                        break;
                }
            }
        } catch (IOException ex) {
            
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
