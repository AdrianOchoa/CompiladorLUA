/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.swing.JOptionPane;

/**
 *
 * @author Adrián
 */
public class Message {
    
    public static void showInfoMessage(String message) {
        showMessage(message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void showErrorMessage(String message) {
        showMessage(message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private static void showMessage(String message, String type, int messageType) {
        JOptionPane.showMessageDialog(null, message, type, messageType);
    }
    
}
