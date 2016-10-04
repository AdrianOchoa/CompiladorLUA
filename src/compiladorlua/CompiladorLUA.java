/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladorlua;

import controllers.FrameController;
import controllers.MenuController;
import controllers.PaneController;
import static java.awt.Color.*;
import menus.Menu;
import util.views.ScreenSplash;
import views.Frame;
import views.Pane;

/**
 *
 * @author Adrián
 */
public class CompiladorLUA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Aquí esta cargando: NADA
        new ScreenSplash().load(203, 398, 205, 378, 
                LIGHT_GRAY, BLACK, LIGHT_GRAY, BLUE);
        
        Pane pane = new Pane();

        MenuController menuController = new MenuController(pane);
        PaneController panelController = new PaneController(pane);
        FrameController frameController = new FrameController();

        pane.setPaneController(panelController);

        Menu menu = new Menu();

        menu.setController(menuController);

        Frame frame = new Frame("Compilador LUA", menu, pane);
        frame.setController(frameController);
        
    }
    
}
