/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.FrameController;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import static javax.swing.UIManager.getSystemLookAndFeelClassName;
import javax.swing.UnsupportedLookAndFeelException;
import menus.Menu;

/**
 *
 * @author Adrián
 */
public class Frame extends JFrame {

    public Frame(String title, Menu menu, Pane pane) {
        super(title);
        try {
            initFrame(menu, pane);
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println(ex + "\n" + ex.getMessage());
        }
    }

    private void initFrame(Menu menu, Pane pane) throws
            ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(getSystemLookAndFeelClassName());
        setSize(900, 700);
        setLocationRelativeTo(null);
        setJMenuBar(menu);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setContentPane(pane);
        String url = "/icons/LUA.png";
        setIconImage(new ImageIcon(getClass().getResource(url)).getImage());
        setVisible(true);
        toFront();
    }

    public void setController(FrameController frameController) {
        addWindowListener(frameController);
    }

}
