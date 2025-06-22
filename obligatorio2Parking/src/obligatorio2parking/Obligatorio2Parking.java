/*
AUTORES - ESTUDIANTES
 JHONATAN ADALID (320368)
 LORENZO ALDAO (307239)
*/
package obligatorio2parking;

import dominio.*;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Obligatorio2Parking {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Sistema sistema = new Sistema();
        VentanaSistema vs = new VentanaSistema(sistema); 
        vs.setVisible(true);
    }
}
