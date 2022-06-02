package app.main;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.theater.Application;

/**
 * Main class. Launches the application.
 * 
 * On the application: the admin username is "admin", and its password is "pass"
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class Main{
    /** 
     * Main function that launches the application
     * @param args not used
     */
    public static void main(String[] args) {		
        // get the Application from the predetermined file:
        // if the file aux_files/saveFile/saveFile.bin is removed, the application starts blank
		Application.getInstanceFromFile();

        // create the main window:
		AppWindow frame = new AppWindow(Application.getInstance().getName());
		
        // set the controller of the main window
		frame.setController(MainController.getInstance());
	}
}
