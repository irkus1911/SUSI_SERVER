/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.serverSocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.dataModel.User;
import lib.exceptions.ConnectException;
import lib.exceptions.IncorrectEmailException;
import lib.exceptions.IncorrectPasswordException;
import lib.exceptions.IncorrectUserException;
import lib.exceptions.PasswordDontMatchException;
import lib.exceptions.TooManyUsersException;
import lib.exceptions.UserDontExistException;
import lib.exceptions.UserExistException;
import lib.interfaces.Logicable;
import lib.message.Message;
import lib.message.Msg;
import server.Server;
import server.factory.LogicableFactory;

/**
 *
 * @author Adrian Franco
 */
public class Hilo extends Thread {

    private Socket socket;
    private Message msg;
    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;
    private User usu;

    public Hilo(Socket clientSocket) {
        this.socket = clientSocket;

    }

    @Override
    public void run() {

           
       try {

            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            msg = (Message) ois.readObject();

            String opc = msg.getMsg().toString();
            LogicableFactory log = new LogicableFactory();
            switch (opc) {
                case "SIGNUP":
                 usu=   log.getDao().signUp(msg.getUser());
                    break;
                case "SIGNIN":
                   usu= log.getDao().signIn(msg.getUser());
                    break;
            }
            msg.setMsg(Msg.OK);
            msg.setUser(usu);
            oos.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IncorrectUserException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            msg.setMsg(Msg.INCORRECTUSEREXCEPTION);
            msg.setUser(null);

        } catch (IncorrectPasswordException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            msg.setMsg(Msg.INCORRECTPASSWORDEXCEPTION);
            msg.setUser(null);

        } catch (IncorrectEmailException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            msg.setMsg(Msg.INCORRECTEMAILEXCEPTION);
            msg.setUser(null);

        } catch (UserExistException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            msg.setMsg(Msg.USEREXISTEXCEPTION);
            msg.setUser(null);

        } catch (PasswordDontMatchException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            msg.setMsg(Msg.PASSWORDDONTMATCHEXCEPTION);
            msg.setUser(null);

        } catch (TooManyUsersException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            msg.setMsg(Msg.TOOMANYUSERSEXCEPTION);
            msg.setUser(null);

        } catch (ConnectException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            msg.setMsg(Msg.CONNECTEXCEPTION);
            msg.setUser(null);

        } catch (UserDontExistException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            msg.setMsg(Msg.USERDONTEXISTEXCEPTION);
            msg.setUser(null);
        } finally {
            try {
                oos.writeObject(msg);
            } catch (IOException ex) {
                Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
