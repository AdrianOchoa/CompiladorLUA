/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

/**
 *
 * @author Adrián
 */
public class Identificador {
    
    private final String name;

    /**
     * Constructs a new Identifier with data type and name
     * @param name the name
     */
    public Identificador(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
}
