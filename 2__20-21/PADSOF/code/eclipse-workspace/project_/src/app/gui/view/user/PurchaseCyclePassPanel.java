package app.gui.view.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

import app.gui.view.tables.TablePanel;
import app.gui.view.things.CyclePanel;
import app.gui.view.things.ThingPanel;

import java.util.*;


/**
 * Class PurchaseCyclePassPanel. 
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class PurchaseCyclePassPanel extends JPanel {
	private JLabel title = new JLabel("Purchase a cycle pass");
	
	private JPanel centerPanel = new JPanel();
	private JPanel infoPanel = new JPanel();
	private JPanel cardPanel = new JPanel();
	private static final String INFO_TEXT = "Your purchased cycle passes: ";
	
	private JButton purchaseButton = new JButton("Purchase cycle pass");
	private JLabel cardLabel = new JLabel("Credit card: ");
	private JTextField cardField = new JTextField(20);
	private JLabel infoLabel = new JLabel("");
	
	private TablePanel cyclesTable = new TablePanel();
	private CyclePanel cyclePanel = new CyclePanel();
	
	private JTree tree;

	private JPanel cyclePanelContainer = new JPanel();
	
    /**
     * public constructor
     * 
     * @param dataModel treeModel with the areas of the theater
     */
    public PurchaseCyclePassPanel(DefaultTreeModel dataModel) {
        tree = new JTree(dataModel);
        JScrollPane scrollBar = new JScrollPane(tree);
        scrollBar.setViewportView(tree);

        this.setLayout(new BorderLayout());
        this.add(title, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(infoPanel, BorderLayout.WEST);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(scrollBar);
        cyclesTable.setPreferredSize(400, 200);
        centerPanel.add(cyclesTable);
        centerPanel.add(cardPanel);
        centerPanel.add(purchaseButton);

        cardPanel.add(cardLabel);
        cardPanel.add(cardField);

       
        cyclePanelContainer.add(cyclePanel);
        cyclePanel.setPreferredSize(new Dimension(400, 600));
        cyclePanel.setMaximumSize(new Dimension(400, 600));

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(Box.createHorizontalStrut(100));
        infoLabel.setAlignmentX(RIGHT_ALIGNMENT);
        infoPanel.add(infoLabel);
        infoPanel.add(Box.createVerticalGlue());
        JLabel headerLabel = new JLabel("Selected cycle: ", SwingConstants.CENTER);
        headerLabel.setAlignmentX(RIGHT_ALIGNMENT);
        infoPanel.add(headerLabel);
        infoPanel.add(cyclePanelContainer);
    }

    /**
     * sets the controller for the panel
     * @param purchaseCyclePassAction action performed when button buy is clicked
     */
    public void setController(ActionListener purchaseCyclePassAction) {
        purchaseButton.addActionListener(purchaseCyclePassAction);
    }

    /**
     * updates the panel
     * @param list list with the info
     */
    public void update(List<String> list) {
        setInfo(list);
    }
	
    /** 
     * Getter
     * @return CyclesTable
     */
	public TablePanel getCyclesTable() {
		return this.cyclesTable;
	}	
	
    /** 
     * Getter
     * @return CyclePanel
     */
    public ThingPanel getCyclePanel() {
		return cyclePanel;
	}
    
    /** 
     * Getter
     * @param list set the info
     */
    private void setInfo(List<String> list) {
		if(list.isEmpty()) {
			infoLabel.setText("You have no cycle passes yet");
			return;
		}
		String s = "<html>" + INFO_TEXT;
		for(String b: list)
			s += "<br>" + b;
		s += "</html>";
		infoLabel.setText(s);
	}

    /** 
     * Getter
     * @return the Tree
     */
	public JTree getTree() {
		return tree;
    }

    /** 
     * Getter
     * @return Card
     */
	public String getCard() {
		return cardField.getText();
	}
}
