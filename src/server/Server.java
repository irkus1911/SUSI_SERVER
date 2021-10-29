/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.sql.Timestamp;
import java.time.Instant;
import static java.time.Instant.now;
import java.util.Scanner;
import lib.dataModel.User;
import lib.dataModel.UserPrivilege;
import lib.dataModel.UserStatus;
import lib.exceptions.ConnectException;
import lib.exceptions.IncorrectEmailException;
import lib.exceptions.IncorrectPasswordException;
import lib.exceptions.IncorrectUserException;
import lib.exceptions.PasswordDontMatchException;
import lib.exceptions.UserDontExistException;
import lib.exceptions.UserExistException;
import server.controller.DAOableImplementation;

/**
 *
 * @author 2dam
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IncorrectUserException, IncorrectPasswordException, UserDontExistException, PasswordDontMatchException, ConnectException,IncorrectUserException, IncorrectPasswordException, IncorrectEmailException, UserExistException, PasswordDontMatchException, ConnectException {
        Scanner sc =new Scanner(System.in);
        User usu=null;
        int decision;
        System.out.println("Selecciona una opcion");
       decision=sc.nextInt();
                DAOableImplementation im=new DAOableImplementation();
        switch(decision){
            case 1:
                usu=new User();
                System.out.println("usuario");
                usu.setLogin(sc.next());
                System.out.println("email");
                usu.setEmail(sc.next());
                System.out.println("nombre completo");
                usu.setFullName(sc.next());
                usu.setStatus(UserStatus.ENABLED);
                usu.setPrivilege(UserPrivilege.USER);
                System.out.println("contraseña");
                usu.setPassword(sc.next());
                Instant instant = Instant.now();
                Timestamp timestamp = Timestamp.from(instant);
                usu.setLastPasswordChange(timestamp);
                im.signUp(usu);
                break;
            case 2:
                usu=new User();
                System.out.println("usuario");
                usu.setLogin(sc.next());
                System.out.println("contraseña");
                usu.setPassword(sc.next());
                im.signIn(usu);
                System.out.println("Hola"+usu.getLogin());
                break;
        }
        
        // TODO code application logic here
    }
    
}
