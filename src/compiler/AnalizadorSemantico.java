/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Adrián
 */
public class AnalizadorSemantico {
    
    private String codigo;
    private final ArrayList<String> operadores;
    private boolean [] posiciones;
    private int [] posicionesID;
    private StringBuilder mensajes;
    private StringBuilder mensajesError;
    
    public AnalizadorSemantico (String code) {
        this.codigo = code;
        operadores = getOperadores();
    }
    
    public void ejecutarAnalisisSemantico () {
        procesarCodigo();
        String[] tokens = codigo.split("[ ]+");
        posiciones = new boolean[tokens.length];
        posicionesID = new int[tokens.length];
        Arrays.fill(posiciones, true);
        mensajes = new StringBuilder();
        mensajesError = new StringBuilder();
        HashMap<String, Funcion> funciones = new HashMap();
        HashMap<String, Identificador> identificadores = new HashMap();
        HashMap<String, Identificador> parametros = new HashMap();
        agregarFunciones(tokens, mensajes, mensajesError, parametros, funciones);
        agregarIdentificadores(tokens, mensajes, mensajesError, identificadores);
        comprobarIdentificadores (tokens, mensajes, mensajesError, identificadores);
        //agregarMensajes(mensajes);
    }
    
    private void comprobarIdentificadores (String [] tokens, 
            StringBuilder mensajes, StringBuilder mensajesError, 
            HashMap<String, Identificador> identificadores) {
        
    }
    
    public void agregarIdentificadores(String [] tokens, StringBuilder mensajes,
            StringBuilder mensajesError, HashMap<String, Identificador> identificadores) {
        mensajes.append("Variables encontradas:\n");
        mensajesError.append("Errores en las variables:\n");
        for (int i = 0; i < tokens.length; i++) {
            if(tokens[i].trim().matches("[a-z ,A-Z][a-z, A-Z, 0-9, _]*") && posiciones[i]) {
                if(condiciones(tokens[i].trim())) {
                    if(!identificadores.containsKey(tokens[i].trim())) {
                        mensajes.append("Info: Variable ")
                                .append(tokens[i].trim())
                                .append(" encontrada.\n");
                        Identificador id = new Identificador(tokens[i].trim());
                        identificadores.put(id.getName(), id);
                    } else {
                        mensajesError.append("Error: Variable ")
                                .append(tokens[i].trim())
                                .append(" ya contenida.\n");
                    }
                }
            }
        }
    }
    
    private boolean condiciones(String token) {
        for (int i = 0; i < getPalabrasReservadas().size(); i++) {
            if(token.matches(getPalabrasReservadas().get(i))) {
                return false;
            }
        }
        return !token.matches(",");
    }
    
    public void agregarFunciones (String [] tokens, StringBuilder mensajes,
            StringBuilder mensajesError,
            HashMap<String, Identificador> parametros, 
            HashMap<String, Funcion> funciones) {
        int fin, finParametros, inicioParametros;
        mensajes.append("Funciones encontradas:\n");
        mensajesError.append("Errores en las funciones:\n");
        for (int i = 0; i < tokens.length; i++) {
            if(tokens[i].trim().equals("function")) {
                String nombreFuncion = tokens[i + 1];
                inicioParametros = i + 3;
                finParametros = encontrarFinParametros(tokens, i + 3);
                fin = encontrarFinFuncion(tokens, i + 4);
                agregarParametros(tokens, inicioParametros, finParametros, parametros, mensajes, nombreFuncion);
                Funcion funcion = new Funcion(nombreFuncion);
                funcion.setDefined(true);
                funcion.setParameters(parametros);
                HashMap<String, Identificador> auxParameters = funcion.getParameters();
                String functionParameters = "";
                functionParameters = getParametrosFuncion(auxParameters, functionParameters);
                if(!funciones.containsKey(funcion.getIdentifier())) {
                    mensajes.append("Info: Funcion ")
                            .append(funcion.getIdentifier())
                            .append(" agregada con parámetros ")
                            .append(functionParameters)
                            .append("\n");
                    funciones.put(funcion.getIdentifier(), funcion);
                } else {
                    mensajesError.append("Error: Funcion ")
                            .append(funcion.getIdentifier())
                            .append(" ya contenida en el programa.\n");
                }
                int aux = i;
                i = fin;
                Arrays.fill(posiciones, aux, i, false);
            } else if(otrasFunciones(tokens[i].trim())) {
                int inicio = i;
                i = encontrarFinFuncion(tokens, i + 1);
                Arrays.fill(posiciones, inicio, i, false);
            } else if(tokens[i].trim().equals("=")) {
                int inicio = i;
                i = encontrarSaltoLinea(tokens, i + 1);
                Arrays.fill(posiciones, inicio, i, false);
            }
        }
    }
    
    private int encontrarSaltoLinea(String [] tokens, int index) {
        for (int i = index; i < tokens.length; i++) {
            if(tokens[i].matches(";")) {
                return i;
            }
        }
        return 0;
    }
    
    private boolean otrasFunciones(String token) {
        if(token.matches("while")) {
            return true;
        }
        if(token.matches("for")) {
            return true;
        }
        return token.matches("if");
    }
    
    private String getParametrosFuncion(HashMap<String, Identificador> auxParameters, String parameters) {
        parameters = auxParameters.entrySet().stream().map((e) -> (Identificador) e.getValue())
                .map((id) -> id.getName() + " ").reduce(parameters, String::concat);
        return parameters.trim();
    }
    
    private void agregarParametros (String [] tokens, int inicio, int fin, 
            HashMap<String, Identificador> parametros, StringBuilder mensajes, String nombreFuncion) {
        parametros.clear();
        for (int i = inicio; i < fin; i++) {
            if(!tokens[i].trim().equals(",")) {
                String identificador = tokens[i].trim();
                Identificador id = new Identificador(identificador);
                if (!parametros.containsKey(id.getName())) {
                    parametros.put(id.getName(), id);
                } else {
                    mensajes.append("Parametro ya contenido en funcion ")
                            .append(nombreFuncion);
                }
            }
        }
    }
    
    private int encontrarFinFuncion(String [] tokens, int index) {
        for (int i = index; i < tokens.length; i++) {
            if(tokens[i].trim().equals("end")) {
                return i;
            }
        }
        return 0;
    }
    
    private int encontrarFinParametros (String [] tokens, int index) {
        for (int i = index; i < tokens.length; i++) {
            if(tokens[i].trim().equals(")")) {
                return i;
            }
        }
        return 0;
    }
    
    private void procesarCodigo() {
        codigo = codigo.replaceAll("\n", " ");
        codigo = codigo.replaceAll("\\(", " ( ");
        codigo = codigo.replaceAll("\\)", " ) ");
        codigo = codigo.replaceAll(",", " , ");
        codigo = codigo.replaceAll(";", " ;");
        codigo = codigo.replaceAll("\\{", " { ");
        codigo = codigo.replaceAll("\\}", " } ");
        operadores.stream().forEach((operator) -> {
            codigo = codigo.replaceAll(operator, " " + operator + " ");
        });
    }
    
    private static ArrayList<String> getOperadores() {
        ArrayList<String> basicOperators = new ArrayList();
        basicOperators.add("\\+");
        basicOperators.add("\\++");
        basicOperators.add("\\-");
        basicOperators.add("\\--");
        basicOperators.add("\\*");
        basicOperators.add("\\/");
        basicOperators.add("\\=");
        basicOperators.add("\\+=");
        basicOperators.add("\\-=");
        basicOperators.add("\\*=");
        basicOperators.add("\\/=");
        return basicOperators;
    }
    
    public static ArrayList<String> getPuntos() {
        ArrayList<String> puntos = new ArrayList();
        puntos.add(",");
        puntos.add(".");
        puntos.add(")");
        puntos.add("(");
        puntos.add("{");
        puntos.add("}");
        puntos.add("[");
        puntos.add("]");
        return puntos;
    }
    
    public static ArrayList<String> getPalabrasReservadas() {
        ArrayList<String> palabras = new ArrayList();
        palabras.add("and");
        palabras.add("break");
        palabras.add("do");
        palabras.add("else");
        palabras.add("elseif");
        palabras.add("end");
        palabras.add("false");
        palabras.add("for");
        palabras.add("function");
        palabras.add("if");
        palabras.add("in");
        palabras.add("local");
        palabras.add("nil");
        palabras.add("or");
        palabras.add("not");
        palabras.add("repeat");
        palabras.add("return");
        palabras.add("then");
        palabras.add("true");
        palabras.add("until");
        palabras.add("while");
        palabras.add("print");
        return palabras;
    }

    /**
     * @return the mensajes
     */
    public StringBuilder getMensajes() {
        return mensajes;
    }

    /**
     * @return the mensajesError
     */
    public StringBuilder getMensajesError() {
        return mensajesError;
    }
    
}
