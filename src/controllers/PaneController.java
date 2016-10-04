/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import compiler.AnalizadorLUA;
import compiler.AnalizadorSemantico;
import compiler.ParseException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.JFrame;
import util.FileUtility;
import util.Message;
import views.Pane;
import views.PanelResultados;

/**
 *
 * @author Adrián
 */
public class PaneController implements ActionListener {

    private final Pane pane;

    public PaneController(Pane pane) {
        this.pane = pane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String label = e.getActionCommand();
        switch (label) {
            case "Compilar": {
                try {
                    compile();
                } catch (FileNotFoundException | UnsupportedEncodingException | ParseException ex) {
                    Message.showErrorMessage("Error al compilar\n" + ex.getMessage());
                }
            }
            break;
            case "Limpiar":
                clearTexts();
                break;
            case "Guardar Resultados":
                saveResults();
                break;
        }
    }

    private void compile() throws FileNotFoundException,
            UnsupportedEncodingException,
            ParseException {
        clearResults();
        String url = "C:\\Users\\Public\\Documents\\prueba.txt";
        String code = pane.getCe().getCodeArea().getText();
        FileUtility.saveToFile(code, url);
        FileInputStream fis = new FileInputStream(url);
        AnalizadorLUA all = new AnalizadorLUA(fis);
        all.ejecutarAnalisis();
        int errores = AnalizadorLUA.contadorErrores;
        Message.showInfoMessage("Compilación terminada con " + errores + " errores sintacticos.");
        showResults();
        if (errores > 0) {
            lanzarVentanaResultados(AnalizadorLUA.getErrores().toString(), "Resultado del analisis semantico:");
            AnalizadorLUA.getErrores().delete(0, AnalizadorLUA.getErrores().length());
        } else {
            AnalizadorSemantico as = new AnalizadorSemantico(code);
            as.ejecutarAnalisisSemantico();
            lanzarVentanaResultados(AnalizadorLUA.getErrores().toString(),
                    as.getMensajes().toString() + as.getMensajesError());
            AnalizadorLUA.getErrores().delete(0, AnalizadorLUA.getErrores().length());
        }
        AnalizadorLUA.contadorErrores = 0;
        pane.getJbSaveResults().setEnabled(true);
    }

    private void lanzarVentanaResultados(String sintactico, String semantico) {
        PanelResultados pR = new PanelResultados(sintactico, semantico);
        JFrame f = new JFrame("Resultados");
        f.setSize(800, 600);
        f.setLocationRelativeTo(null);
        f.setContentPane(pR);
        f.setVisible(true);
        try {
            String ruta = "C:\\Users\\Public\\Documents\\ErroresSemanticos.txt";
            PrintWriter pw = new PrintWriter(ruta);
            pw.print(semantico);
            pw.flush();
        } catch (FileNotFoundException ex) {
            Message.showErrorMessage("Error al guardar.\n" + ex.getMessage());
        }
        try {
            String ruta = "C:\\Users\\Public\\Documents\\ErroresSintacticos";
            PrintWriter pw = new PrintWriter(ruta);
            pw.print(sintactico);
            pw.flush();
        } catch (FileNotFoundException ex) {
            Message.showErrorMessage("Error al guardar.\n" + ex.getMessage());
        }
    }

    private void clearResults() {
        pane.getJpResultsArea().setText("");
    }

    private void showResults() {
        StringBuilder sb = new StringBuilder();
        sb.append("Resultado del analisis lexico:\n");
        for (int i = 0; i < AnalizadorLUA.getLista().size(); i++) {
            sb.append(AnalizadorLUA.getLista().get(i)).append("\n");
        }
        AnalizadorLUA.getLista().clear();
        pane.getJpResultsArea().setText(sb.toString());
        try {
            String ruta = "C:\\Users\\Public\\Documents\\ErroresLexicos.txt";
            PrintWriter pw = new PrintWriter(ruta);
            pw.print(sb.toString());
            pw.flush();
        } catch (FileNotFoundException ex) {
            Message.showErrorMessage("Error al guardar.\n" + ex.getMessage());
        }
    }

    private void saveResults() {
        try {
            FileUtility.saveToFile(pane.getJpResultsArea().getText());
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Message.showErrorMessage("Error al guardar resultados\n" + ex.getMessage());
        }
    }

    private void clearTexts() {
        pane.getCe().getCodeArea().setText("");
        pane.getCe().getNumberLineArea().setText("");
        clearResults();
        pane.getJbCompile().setEnabled(false);
        pane.getJbSaveResults().setEnabled(false);
    }

}
