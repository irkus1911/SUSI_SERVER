/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import java.sql.Connection;
import java.sql.DriverManager;
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
import server.pool.Pool;

/**
 * Esta clase maneja la logica de los metodos de sign in y signUp
 *
 * @author Irkus de la Fuente
 */
public class DAOableImplementation implements Logicable {

    private final static Logger logger = Logger.getLogger("server.controller.Dao");

    public DAOableImplementation() {

    }
    private Connection con;
    private Pool pool;
    private ResultSet rs;
    private PreparedStatement stmt;

    private final String insertarUsuario = "insert into user (login,email,fullname,status,privilege,password,lastPasswordChange) values(?,?,?,?,?,?,?)";
    private final String buscarUsuario = "select * from user where login=?";
    private final String procedimientoSignIn = "CALL `last_signs_in`(?)";

    /**
     * Este metodo loguea a un usuario
     *
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
        logger.info("SignIn started");
        User usu = user;
        User usua;
        
          con = pool.getConnection();

        usua = buscarUser(usu);
        if (usua == null) {
            throw new UserDontExistException("Usuario no existe");
           
        } else {
            if (usu.getPassword().equals(usua.getPassword())) {
                try {
                    stmt = con.prepareStatement(procedimientoSignIn);
                    stmt.setString(1, usu.getLogin());
                    stmt.executeUpdate();
                } catch (SQLException ex) {
                  throw new ConnectException("error de conexion a base de datos");
                }
            } else {
                throw new PasswordDontMatchException("Contraseña incorrecta");
            }

        }
        try {
            con.close();
        } catch (SQLException ex) {
            throw new ConnectException("error de conexion a base de datos");
        }
        pool.releaseConnection(con);
        return usu;
    }

    /**
     * Este metodo registra un usuario en la base de datos
     *
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
        logger.info("SignUp started");
        User usu = user;
        User usua;
       
        con = pool.getConnection();
        usua = buscarUser(usu);
        if (usua==null) {
            try {
                //login,email,fullname,status,privilege,password,lastPasswordChange
                stmt = con.prepareStatement(insertarUsuario);
                stmt.setString(1, usu.getLogin());
                stmt.setString(2, usu.getEmail());
                stmt.setString(3, usu.getFullName());
                stmt.setString(4, usu.getStatus().toString());
                stmt.setString(5, usu.getPrivilege().toString());
                stmt.setString(6, usu.getPassword());
                stmt.setTimestamp(7, usu.getLastPasswordChange());
                stmt.executeUpdate();

                stmt = con.prepareStatement(procedimientoSignIn);
                stmt.setString(1, usu.getLogin());
                stmt.executeUpdate();

            } catch (ConnectException ex) {
                System.out.println("error conexion");
            } catch (SQLException ex) {
               throw new ConnectException("error de conexion a base de datos");
            }
        } else {
            throw new UserExistException("Usuario ya existe");
        }
        try {
            con.close();
        } catch (SQLException ex) {
            throw new ConnectException("error de conexion a base de datos");
        }
          pool.releaseConnection(con);
        return usu;
    }

    /**
     * Este metodo busca un usuario determinado y lo devuelve
     *
     * @param user
     * @return objeto User
     */
    private User buscarUser(User user) throws ConnectException {
        logger.info("Find User started");
        con = pool.getConnection();
        User usu = user;
      
      
        try {
            
            stmt = con.prepareStatement(buscarUsuario);
            stmt.setString(1, usu.getLogin());
            rs = stmt.executeQuery();
            
             usu=null;
                 while (rs.next()) {
                     
                    usu=new User();
                     usu.setId(rs.getInt("id"));
                     usu.setLogin(rs.getString("login"));
                     usu.setEmail(rs.getString("email"));
                     usu.setPassword(rs.getString("password"));
                     usu.setFullName(rs.getString("fullName"));
                     usu.setPrivilege(UserPrivilege.USER);
                     usu.setStatus(UserStatus.ENABLED);
                     
                 }

        } catch (SQLException ex) {
           throw new ConnectException("error de conexion a base de datos");
        }
        pool.releaseConnection(con);
        return usu;
    }

}
