/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import compiler.AnalizadorSemantico;
import controllers.PaneController;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;
import util.views.CodeEditor;
import util.views.styles.CustomDocumentFilter;

/**
 *
 * @author Adrián
 */
public class Pane extends JPanel {

    private JButton jbCompile;
    private JButton jbClear;
    private JButton jbSaveResults;
    private JTextPane jpResultsArea;
    private PaneController paneController;
    private CodeEditor ce;

    public Pane() {
        setLayout(new GridLayout(2, 1));
        setBorder(BorderFactory.createLineBorder(getBackground(), 10));
        addComponents();
    }

    private void addComponents() {

        jpResultsArea = new JTextPane();
        jpResultsArea.setEditable(false);

        jbCompile = new JButton("Compilar");
        jbClear = new JButton("Limpiar");
        jbCompile.setEnabled(true);
        jbCompile.setIcon(new ImageIcon(getClass().getResource("/icons/compila.png")));
        jbClear.setIcon(new ImageIcon(getClass().getResource("/icons/limpiar.png")));

        JPanel centerPane = new JPanel(new BorderLayout());

        JPanel centerSouthPane = new JPanel();

        centerSouthPane.add(jbCompile);

        centerSouthPane.add(new JLabel("      "));
        centerSouthPane.add(jbClear);

        ce = new CodeEditor();

        ArrayList<String> arrayReservedWords = AnalizadorSemantico.getPalabrasReservadas();
        ((AbstractDocument) ce.getCodeArea().getDocument()).setDocumentFilter(
                new CustomDocumentFilter(ce.getCodeArea(), arrayReservedWords));
        centerPane.add(ce, BorderLayout.CENTER);
        centerPane.add(centerSouthPane, BorderLayout.SOUTH);

        JScrollPane resultPane = new JScrollPane(jpResultsArea);
        resultPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel resultSouthPane = new JPanel();

        jbSaveResults = new JButton("Guardar Resultados");

        jbSaveResults.setEnabled(false);
        jbSaveResults.setIcon(new ImageIcon(getClass().getResource("/icons/guardar.png")));
        resultSouthPane.add(jbSaveResults);

        JPanel southPane = new JPanel(new BorderLayout());
        southPane.setBorder(BorderFactory.createLineBorder(getBackground(), 10));
        southPane.setLayout(new BorderLayout());

        southPane.add(new JLabel("Resultado Lexico:"), BorderLayout.NORTH);
        southPane.add(resultPane, BorderLayout.CENTER);
        southPane.add(resultSouthPane, BorderLayout.SOUTH);

        add(centerPane);
        add(southPane);
    }

    /**
     * @return the controladorP
     */
    public PaneController getPaneController() {
        return paneController;
    }

    /**
     * @param paneController the controladorP to set
     */
    public void setPaneController(PaneController paneController) {
        this.paneController = paneController;
        jbCompile.addActionListener(paneController);
        jbSaveResults.addActionListener(paneController);
        jbClear.addActionListener(paneController);
    }

    /**
     * @return the ce
     */
    public CodeEditor getCe() {
        return ce;
    }

    /**
     * @return the jbCompile
     */
    public JButton getJbCompile() {
        return jbCompile;
    }

    /**
     * @return the jtResultsArea
     */
    public JTextPane getJpResultsArea() {
        return jpResultsArea;
    }

    /**
     * @return the jbSaveResults
     */
    public JButton getJbSaveResults() {
        return jbSaveResults;
    }

}
