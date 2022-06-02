package app.gui.view.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

import app.gui.view.Util;

import java.util.*;

/**
 * Class PurchaseAnnualPassPanel. Panel to buy an annual pass
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class PurchaseAnnualPassPanel extends JPanel {
	private JLabel title = new JLabel("Purchase an annual pass", SwingConstants.CENTER);

	private JPanel centerPanel = new JPanel();
	private JPanel purchasePanel = new JPanel();
	private JPanel infoPanel = new JPanel();
	private JPanel cardPanel = new JPanel();
	private static final String INFO_TEXT = "Your purchased annual passes: ";

	private JButton purchaseButton = new JButton("Purchase annual pass");
	private JLabel yearLabel = new JLabel("Year: ");
	private JTextField yearField = new JTextField(20);
	private JLabel cardLabel = new JLabel("Credit card: ");
	private JTextField cardField = new JTextField(20);
	private JLabel infoLabel = new JLabel();

	private JTree tree;

    private JLabel areasPricesLabel = new JLabel();

    /**
     * public constructor
     * 
     * @param dataModel treeModel with the areas of the theater
     */
    public PurchaseAnnualPassPanel(DefaultTreeModel dataModel) {
        tree = new JTree(dataModel);

        this.setLayout(new BorderLayout());
        Util.setFont(title);
        this.add(title, BorderLayout.NORTH);

        setCenterPanel();

        this.add(centerPanel, BorderLayout.CENTER);

        infoPanel.add(infoLabel);
    }

    /**
     * sets the center panel
     */
    public void setCenterPanel() {
        JScrollPane scrollBar = new JScrollPane(tree);
        JPanel treePanel = new JPanel();
        scrollBar.setViewportView(tree);
        JScrollBar bar = scrollBar.getVerticalScrollBar();
        bar.setPreferredSize(new Dimension(40, 0));
        scrollBar.setSize(new Dimension(100, 300));

        treePanel.setLayout(new BoxLayout(treePanel, BoxLayout.Y_AXIS));

        centerPanel.setLayout(new GridLayout(1, 3));
        treePanel.add(scrollBar);
        centerPanel.add(treePanel);

        purchasePanel.add(yearLabel);
        purchasePanel.add(yearField);
        cardPanel.add(cardLabel);
        cardPanel.add(cardField);

        JPanel aux = new JPanel();
        aux.setLayout(new BoxLayout(aux, BoxLayout.Y_AXIS));
        purchasePanel.setAlignmentX(CENTER_ALIGNMENT);
        cardPanel.setAlignmentX(CENTER_ALIGNMENT);
        purchaseButton.setAlignmentX(CENTER_ALIGNMENT);
        aux.add(Box.createVerticalStrut(300));
        aux.add(purchasePanel);
        aux.add(Box.createVerticalGlue());
        aux.add(cardPanel);
        aux.add(Box.createVerticalGlue());
        aux.add(purchaseButton);
        aux.add(Box.createVerticalStrut(300));

        centerPanel.add(aux);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        infoPanel.setAlignmentY(CENTER_ALIGNMENT);
        areasPricesLabel.setAlignmentY(CENTER_ALIGNMENT);
        info.add(Box.createVerticalStrut(300));
        info.add(areasPricesLabel);
        info.add(Box.createVerticalGlue());
        info.add(infoPanel);
        info.add(Box.createVerticalStrut(300));
        centerPanel.add(info);
    }

    /**
     * updates the panel JLabels
     * @param areasPrices text for the areas prices lable
     * @param list list with the passes
     */
    public void update(String areasPrices, List<String> list) {
        yearField.setText("");
        setInfo(list);
        areasPricesLabel.setText(areasPrices);
    }

    /**
     * sets the info of the panel
     * @param list list with the info
     */
    private void setInfo(List<String> list) {
        if (list.isEmpty()) {
            infoLabel.setText("You have no annual passes yet");
            return;
        }
        String s = "<html>" + INFO_TEXT;
        for (String b : list)
            s += "<br>" + b;
        s += "</html>";
        infoLabel.setText(s);
    }

    /**
     * sets the controller for the panel
     * @param purchaseAnnualPassAction action performed when buy button is clicked
     */
	public void setController(ActionListener purchaseAnnualPassAction) {
		purchaseButton.addActionListener(purchaseAnnualPassAction);
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
     * @return year
     */
    public String getYear() {
		return yearField.getText();
	}
	
    /** 
     * Getter 
     * @return Card
     */
    public String getCard() {
		return cardField.getText();
	}
}
