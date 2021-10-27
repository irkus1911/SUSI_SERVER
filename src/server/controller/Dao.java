/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import lib.interfaces.Logicable;

/**
 * Esta clase maneja la logica de los metodos de sign in y signUp
 * @author Irkus de la Fuente
 */
public class Dao implements Logicable {
    
    
    public Dao(){
        this.pool=getInstance();
    }
    private Connection con;
    private Pool pool;
    private ResultSet rs;
    private PreparedStatement stmt;
    
    private final String buscarUsuario = "select * from user where login=?";
    private final String procedimientoSignIn = "CALL `last_signs_in`('?')";

    /**
     * Este metodo loguea a un usuario
     * @param user
     * @return objeto User
     * @throws IncorrectUserException
     * @throws IncorrectPasswordException
     * @throws UserDontExistException
     * @throws PasswordDontMatchException
     * @throws ConnectException
     */
    @Override
    public User signIn(User user) throws IncorrectUserException, IncorrectPasswordException, UserDontExistException, PasswordDontMatchException, ConnectException {
        User usu = user;
        return usu;
    }

    /**
     * Este metodo registra un usuario en la base de datos
     * @param user
     * @return objeto User
     * @throws IncorrectUserException
     * @throws IncorrectPasswordException
     * @throws IncorrectEmailException
     * @throws UserExistException
     * @throws PasswordDontMatchException
     * @throws ConnectException
     */
    @Override
    public User signUp(User user) throws IncorrectUserException, IncorrectPasswordException, IncorrectEmailException, UserExistException, PasswordDontMatchException, ConnectException {
        User usu = user;

        return usu;
    }

    /**
     * Este metodo busca un usuario determinado y lo devuelve
     * @param user
     * @return objeto User
     */
    private User buscarUser(User user) {
        User usu = user;
     
        con=pool.getConnection();
        try {
            stmt=con.prepareStatement(buscarUsuario);
            stmt.setString(1, usu.getLogin());
            rs=stmt.executeQuery();
            usu=null;
            while(rs.next()){
                usu.setId(rs.getInt("id"));
                usu.setLogin(rs.getString("login"));
                usu.setEmail(rs.getString("email"));
                usu.setPassword(rs.getString("password"));
                usu.setFullName(rs.getString("fullName"));
                usu.setPrivilege(UserPrivilege.USER);
                usu.setStatus(UserStatus.ENABLED);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        pool.releaseConnection(con);
        
        
        return usu;
    }

}
