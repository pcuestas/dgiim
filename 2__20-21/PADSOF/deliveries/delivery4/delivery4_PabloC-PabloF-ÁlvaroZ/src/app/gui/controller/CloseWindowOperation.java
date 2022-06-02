package app.gui.controller;

import javax.swing.JOptionPane;

import app.gui.view.AppWindow;
import app.theater.Application;

/**
 * Class CloseWindowOperation
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class CloseWindowOperation extends java.awt.event.WindowAdapter
{
    
    /** 
     * Operation made on trying to close the window.
     * Asks for confirmation from the user, and, if positive, 
     * logs the user out and writes the application to the file.
     * @param windowEvent the closing of the application.
     */
    @Override
    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        Application app = Application.getInstance();

        if (JOptionPane.showConfirmDialog(AppWindow.getInstance(), 
           "Are you sure you want to close the application?"
           + (app.getCurrentUser() != null ? "\nYou will be logged out." : ""), 
           "Exit application?",
           JOptionPane.YES_NO_OPTION,
           JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
        {
            app.logout();
            app.writeToFile();
            System.exit(0);
        }
    }
}
