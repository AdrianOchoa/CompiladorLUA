/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import util.FileUtility;
import util.Message;
import views.Pane;

/**
 *
 * @author Adrián
 */
public class MenuController implements ActionListener {

    private final Pane pane;

    public MenuController(Pane pane) {
        this.pane = pane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String label = e.getActionCommand();
        switch (label) {
            case "Abrir": {
                try {
                    FileUtility.openFile(pane);
                    pane.getJbCompile().setEnabled(true);
                    updateLines();
                } catch (IOException ex) {
                    Message.showErrorMessage("Error al abrir el archivo\n" + ex.getMessage());
                }
            }
            break;
            case "Guardar": {
                try {
                    FileUtility.saveToFile(pane.getCe().getCodeArea().getText());
                } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                    Message.showErrorMessage("Error al guardar\n" + ex.getMessage());
                }
            }
            break;
        }
    }
    
    private void updateLines() {
        int linesCount = pane.getCe().getCodeArea().getText().split("\n").length;
        String lines = "";
        for (int i = 0; i < linesCount; i++) {
            lines += (i + 1) + "\n";
        }
        pane.getCe().getNumberLineArea().setText(lines);
    }

}
