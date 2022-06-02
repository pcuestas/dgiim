package app.gui.view.things;


import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Class PerformancePanel. Panel that displays the info of a given performance
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class PerformancePanel extends ThingPanel {
    protected JButton cancelButton = new JButton("Cancel Performance");
    protected JTextField postponeDateText = new JTextField("YYYY-MM-DD", 10);
    protected JButton postponeButton = new JButton("Postpone Performance");
    
    

    /**
     * public constructor
     */
	public PerformancePanel(){
		super("Get tickets");
		
        this.addAdminComponents();
	}
	

    /**
     * Adds the admin management components
     */
    private void addAdminComponents(){

        JPanel cancelPanel = new JPanel();
        JPanel postponePanel = new JPanel();

        JLabel dateLabel = new JLabel("New Date: ");
        
        cancelPanel.add(cancelButton);
        postponePanel.add(dateLabel);
        postponePanel.add(postponeDateText);
        postponePanel.add(postponeButton);

        sideAdminPanel.add(cancelPanel);
        sideAdminPanel.add(postponePanel);
        
    }

    /**
     * Getter
     * @return the modify text
     */
    public String getModifyText(){
        return this.postponeDateText.getText();
    }

    /**
     * Sets the controller for the panel
     * @param selectAction action performed when select button is clicked
     * @param cancelAction action performed when cancel button is clicked
     * @param postponeAction action performed when postpone button is clicked
     */
    public void setController(ActionListener selectAction,
                ActionListener cancelAction, ActionListener postponeAction)
    {   
        cancelButton.addActionListener(cancelAction);
        postponeButton.addActionListener(postponeAction);
        super.setController(selectAction);
    }
    
    
    /**
     * updates the panel and sets visibility of adminPanel if required
     * @param d object to be displayed
     * @param adminPanelVisibility boolean value for the visibility
     */
    public void update(IDisplayable d, boolean adminPanelVisibility){
    	super.update(d,adminPanelVisibility);
    	selectButton.setVisible(!adminPanelVisibility);
    }
    
    
 
 
}
