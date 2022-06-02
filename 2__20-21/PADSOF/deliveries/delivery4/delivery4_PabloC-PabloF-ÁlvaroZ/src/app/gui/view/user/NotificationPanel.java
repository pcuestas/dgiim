package app.gui.view.user;

import app.gui.view.tables.*;
import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.ActionListener;

/**
 * Class NotificationPanel. Panel which shows all the notifications of the user 
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class NotificationPanel extends TablePanel{

    private JButton removeButton;
    
    /**
     * public constructor
     */
    public NotificationPanel(){
        super();
        selectButton.setText("Go to performance");
        removeButton = new JButton("Remove notification");
        
        setSizes();

        selectButton.setAlignmentX(CENTER_ALIGNMENT);
        removeButton.setAlignmentX(CENTER_ALIGNMENT);        
        buttonPanel.removeAll();        

        buttonPanel.add(Box.createVerticalStrut(60));
        buttonPanel.add(selectButton);
        buttonPanel.add(Box.createVerticalStrut(35));
        buttonPanel.add(removeButton);
    }

    /**
     * Sets the controller for the panel
     * @param selectAction action performed when selectButton is clicked
     * @param removeAction action performed when remove button is clicked
     */
    public void setController(ActionListener selectAction, ActionListener removeAction){
        removeButton.addActionListener(removeAction);
        super.setController(selectAction);
    }
    
    /**
     * Set the sizes for the buttons
     */
    private void setSizes() {
    	selectButton.setPreferredSize(new Dimension(200,40));
    	removeButton.setPreferredSize(new Dimension (200,40));	
    }
}