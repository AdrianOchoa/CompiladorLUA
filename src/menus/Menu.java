/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menus;

import controllers.MenuController;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Adrián
 */
public class Menu extends JMenuBar {
    
    private JMenu jmMenu;
    private JMenuItem jmiOpen;
    private JMenuItem jmiSave;
    
    public Menu() {
        addComponentes();
    }
    
    private void addComponentes() {
        jmMenu = new JMenu("Archivo");
        String url = "/icons/archivo.png";
        ImageIcon imgIcon = new ImageIcon(getClass().getResource(url));
        jmMenu.setIcon(imgIcon);
        jmiOpen = new JMenuItem("Abrir");
        url = "/icons/abrir.png";
        jmiOpen.setIcon(new ImageIcon(getClass().getResource(url)));
        jmiSave = new JMenuItem("Guardar");
        url = "/icons/guardar.png";
        jmiSave.setIcon(new ImageIcon(getClass().getResource(url)));
        jmMenu.add(jmiOpen);
        jmMenu.addSeparator();
        jmMenu.add(jmiSave);
        this.add(jmMenu);
    }
    
    public void setController(MenuController menuController) {
        jmiOpen.addActionListener(menuController);
        jmiSave.addActionListener(menuController);
    }
    
}
