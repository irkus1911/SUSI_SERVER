/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.factory;

import lib.interfaces.Logicable;
import server.controller.DAOableImplementation;

/**
 *
 * @author UnaiUrti
 */
public class LogicableFactory {
    
    /**
     * Metodo de la factoria de la parte del servidor para implementar la clase 
     * DAOableImplementation
     * @return Devuelve un objeto de la interfaz Logicable el cual va a servir
     * para luego implementar la siguiente clase
     */
    
    public Logicable getDao(){
        
        Logicable dao = new DAOableImplementation();
        
        return dao;
    }
    
}