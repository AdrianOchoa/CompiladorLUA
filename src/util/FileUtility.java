/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import views.Pane;

/**
 *
 * @author Adrián
 */
public class FileUtility {
    
    public static void saveToFile(String code) throws FileNotFoundException, 
            UnsupportedEncodingException {
        String [] lines = code.split("[\n]+");
        JFileChooser chooser = new JFileChooser();
        int option = chooser.showSaveDialog(null);
        if(option == JFileChooser.APPROVE_OPTION) {
            String url = chooser.getSelectedFile().getAbsolutePath() + ".txt";
            PrintWriter pw = new PrintWriter(new File(url), "ISO-8859-1");
            for (String line : lines) {
                pw.println(line);
            }
            pw.flush();
            Message.showInfoMessage("Archivo guardado correctamente");
        }
    }
    
    public static void saveToFile(String code, String url) throws FileNotFoundException, 
            UnsupportedEncodingException {
        String [] lines = code.split("[\n]+");
        PrintWriter pw = new PrintWriter(new File(url).getAbsoluteFile(), "ISO-8859-1");
        for (String line : lines) {
            pw.println(line);
        }
        pw.flush();
    }
    
    public static void openFile(Pane pane) throws FileNotFoundException, IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos", "TXT");
        chooser.setFileFilter(filter);
        int option = chooser.showOpenDialog(null);
        if(option == JFileChooser.APPROVE_OPTION) {
            String url = chooser.getSelectedFile().getAbsolutePath();
            InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(url)), "ISO-8859-1");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder code = new StringBuilder();
            do {
                code.append(br.readLine()).append("\n");
            } while(br.ready());
            pane.getCe().getCodeArea().setText(code.toString().trim());
        }
    }
    
}
