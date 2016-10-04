/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import util.FileUtility;
import util.Message;

/**
 *
 * @author Adrián
 */
public class PanelResultados extends JPanel {
    
    private JButton jbGuardarSintactico;
    private JButton jbGuardarSemantico;
    
    private JTextArea jpResultadoSintactico;
    private JTextArea jpResultadoSemantico;
    
    private final String resultadoSintactico;
    private final String resultadoSemantico;
    
    public PanelResultados(String sintactico, String semantico) {
        resultadoSintactico = sintactico;
        resultadoSemantico = semantico;
        setLayout(new GridLayout(2, 1));
        setBorder(BorderFactory.createLineBorder(getBackground(), 10));
        addComponents();
        addEventos();
    }
    
    private void addComponents() {
        jpResultadoSintactico = new JTextArea(10, 20);
        jpResultadoSintactico.setText(resultadoSintactico);
        jpResultadoSintactico.setEditable(false);
        jpResultadoSemantico = new JTextArea(10, 20);
        jpResultadoSemantico.setText(resultadoSemantico);
        jpResultadoSemantico.setEditable(false);
        JScrollPane sPaneSintactico = new JScrollPane(jpResultadoSintactico);
        sPaneSintactico.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sPaneSintactico.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane sPaneSemantico = new JScrollPane(jpResultadoSemantico);
        sPaneSemantico.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sPaneSemantico.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jbGuardarSemantico = new JButton("Guardar");
        jbGuardarSemantico.setIcon(new ImageIcon(getClass().getResource("/icons/guardar.png")));
        jbGuardarSintactico = new JButton("Guardar");
        jbGuardarSintactico.setIcon(new ImageIcon(getClass().getResource("/icons/guardar.png")));
        JPanel pNorte = new JPanel(new BorderLayout());
        JPanel pSur = new JPanel(new BorderLayout());
        pNorte.add(new JLabel("Resultado semantico: "), BorderLayout.NORTH);
        pNorte.add(sPaneSemantico, BorderLayout.CENTER);
        JPanel pS = new JPanel();
        pS.add(jbGuardarSemantico);
        pNorte.add(pS, BorderLayout.SOUTH);
        pSur.add(new JLabel("Resultado sintactico: "), BorderLayout.NORTH);
        pSur.add(sPaneSintactico, BorderLayout.CENTER);
        JPanel pN = new JPanel();
        pN.add(jbGuardarSintactico);
        pSur.add(pN, BorderLayout.SOUTH);
        add(pNorte);
        add(pSur);
    }
    
    private void addEventos() {
        jbGuardarSemantico.addActionListener((ActionEvent e) -> {
            try {
                FileUtility.saveToFile(jpResultadoSemantico.getText());
            } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                Message.showErrorMessage("Error al guardar el archivo");
            }
        });
        jbGuardarSintactico.addActionListener((ActionEvent e) -> {
            try {
                FileUtility.saveToFile(jpResultadoSintactico.getText());
            } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                Message.showErrorMessage("Error al guardar el archivo");
            }
        });
    }
    
}
