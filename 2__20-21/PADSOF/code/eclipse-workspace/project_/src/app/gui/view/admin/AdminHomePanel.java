package app.gui.view.admin;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import app.gui.view.*;


/**
 * Class AdminHomePanel
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class AdminHomePanel extends JPanel {
	private JLabel admin = new JLabel("Admin Control Window");
	
    private JButton statsButton = new JButton("Stats");
    private JButton createCycleButton = new JButton("Create Cycle");
    private JButton areaButton = new JButton("Modify Areas");
    private JButton modifyParametersButton = new JButton("Modify Theater Parameters");
    private JButton createEventButton = new JButton("Create Event");

	/**
	 * Constructor of AdminHomePanel
	 */
    public AdminHomePanel(){
    	placeComponents();		
	}
    
    /**
     * Places the components of the panel
     */
    private void placeComponents() {
    	this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

		JPanel buttonsBox = new JPanel();
        buttonsBox.setLayout(new GridLayout(5,1));
        
		buttonsBox.setAlignmentX(CENTER_ALIGNMENT);

		Util.setFont(admin);
        
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.add(Box.createVerticalStrut(90));
		admin.setAlignmentX(CENTER_ALIGNMENT);
		this.add(admin);

    	buttonsBox.add(statsButton);
    	buttonsBox.add(createEventButton);
    	buttonsBox.add(areaButton);
    	buttonsBox.add(modifyParametersButton);
    	buttonsBox.add(createCycleButton);

    	buttonsBox.setPreferredSize(new Dimension(300,300));
    	this.add(Box.createVerticalStrut(80));
    	this.add(buttonsBox);
    }
    
    /** 
     * Set the controller of the panel
     * @param statsAction stats action 
     * @param createEventAction set the create event panel
     * @param areaAction set the area-modify panel
     * @param modifyParametersAction set the panel where parameters area modified
     * @param setCreateCyclePanelAction set the panel to create a cycle
     */
    public void setController(ActionListener statsAction, ActionListener createEventAction, 
			ActionListener areaAction, ActionListener modifyParametersAction, ActionListener setCreateCyclePanelAction) {
		statsButton.addActionListener(statsAction);
		createEventButton.addActionListener(createEventAction);
		areaButton.addActionListener(areaAction);
		modifyParametersButton.addActionListener(modifyParametersAction);
		createCycleButton.addActionListener(setCreateCyclePanelAction);
	}
}
