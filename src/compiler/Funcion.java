/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.HashMap;

/**
 *
 * @author Adrián
 */
public class Funcion {

    private String identifier;
    private HashMap<String, Identificador> parameters;
    private HashMap<String, Identificador> localVariables;
    private boolean defined;

    /**
     * Constructs a new Function already defined with parameters and local variables
     * @param identifier the name of the function
     * @param parameters the parameters of the function
     * @param localVariables the variables of the function
     * @param defined true if is already defined, false otherwise
     */
    
    public Funcion(String identifier,
            HashMap<String, Identificador> parameters,
            HashMap<String, Identificador> localVariables, boolean defined) {
        this.identifier = identifier;
        this.parameters = parameters;
        this.localVariables = localVariables;
        this.defined = defined;
    }

    /**
     * Constructs a new Function with only type and identifier
     * @param identifier the name of the function
     */
    
    public Funcion (String identifier) {
        this(identifier, new HashMap(), new HashMap(), false);
    }

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the parameters
     */
    public HashMap<String, Identificador> getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(HashMap<String, Identificador> parameters) {
        this.parameters = parameters;
    }

    /**
     * @return the localVariables
     */
    public HashMap<String, Identificador> getLocalVariables() {
        return localVariables;
    }

    /**
     * @param localVariables the localVariables to set
     */
    public void setLocalVariables(HashMap<String, Identificador> localVariables) {
        this.localVariables = localVariables;
    }

    /**
     * @return the defined
     */
    public boolean isDefined() {
        return defined;
    }

    /**
     * @param defined the defined to set
     */
    public void setDefined(boolean defined) {
        this.defined = defined;
    }

}
