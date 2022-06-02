package app.gui.view.user;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;

import app.gui.controller.MainController;
import app.gui.view.Util;
import app.gui.view.areas.SeatPanel;
import app.gui.view.payments.PaymentMethodsPanel;
import app.gui.view.tables.ITabulizable;
import app.theater.passes.Pass;
import app.theater.performances.AutomaticSelectionType;

/**
 * Class PurchaseTicketPanel. This Panel allows the purchase or reservation of
 * tickets for all the areas of the theater. It is divided in 4 subpanels
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class PurchaseTicketsPanel extends JPanel {
    private JTree tree;

    private JPanel selectionPanel;
    private JTabbedPane sittingSelection;

    private JTextField standingText;
    private JButton standingButton;

    private SeatPanel seatPanel;

    private JTextField numberTicketsAutomatic;
    private JButton applyAutomaticSelectionButton = new JButton("Apply automatic selection");

    // List panel
    private DefaultListModel<ITabulizable> listModel;

    private JButton reserveButton = new JButton("Reseve tickets");
    private JButton clearButton;

    // AreaViewPanel

    private PaymentMethodsPanel paymentMethodsPanel = new PaymentMethodsPanel();

    private final JRadioButton centeredUpper = new JRadioButton("Centered Upper");
    private final JRadioButton centeredLower = new JRadioButton("Centered Lower");
    private final JRadioButton centered = new JRadioButton("Centered");
    private final JRadioButton furthest = new JRadioButton("Furthest");

    private JPanel mainPanel;

    private JPanel standingSelection;

    private JPanel listPanel;

    private JList list;

    /**
     * Constructor of the PurchaseTicketsPanel
     * @param dataModel the data model
     */
    public PurchaseTicketsPanel(DefaultTreeModel dataModel) {
        mainPanel = new JPanel(new GridLayout(2, 2));
        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);

        setTreePanel(dataModel); // left top
        setPurchasePanel();
        setSelectionPanel();
        setListPanel();
    }

    /**
     * sets the purchase panel (displayed in the right top corner).
     */
    private void setPurchasePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Purchase or reserve selected tickets:", SwingConstants.CENTER));
        panel.add(paymentMethodsPanel);
        panel.add(Box.createVerticalGlue());
        reserveButton.setAlignmentX(CENTER_ALIGNMENT);
        reserveButton.setAlignmentY(CENTER_ALIGNMENT);

        panel.add(reserveButton);
        panel.add(Box.createVerticalStrut(20));
        mainPanel.add(panel);
    }

    /**
     * sets the tree Panel displayed at the left top corner.
     * 
     * @param dataModel info of the tree to be displayed
     */
    private void setTreePanel(DefaultTreeModel dataModel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        tree = new JTree(dataModel);
        JScrollPane scrollbar = new JScrollPane(tree);
        scrollbar.setViewportView(tree);
        panel.add(new JLabel("Select area:", SwingConstants.CENTER));
        panel.add(scrollbar);
        mainPanel.add(panel);
    }

    /**
     * sets the selectionPanel (it is a card panel)
     */
    private void setSelectionPanel() {
        selectionPanel = new JPanel(new CardLayout());
        mainPanel.add(selectionPanel);
        setStandingPanel();
        setSittingPanel();
    }

    /**
     * sets the standing panel (one card of the selction panel)
     */
    private void setStandingPanel() {
        standingSelection = new JPanel();

        JLabel label = new JLabel("Number of tickets: ");
        standingButton = new JButton("Add tickets");
        standingText = new JTextField(10);

        standingSelection.add(label);
        standingSelection.add(standingText);
        standingSelection.add(standingButton);
        selectionPanel.add(standingSelection, "standingSelection");
    }

    /**
     * sets the sitingPanel which is a tabbed panel. It is one tab of the selection
     * panel.
     */
    private void setSittingPanel() {
        sittingSelection = new JTabbedPane();
        selectionPanel.add(sittingSelection, "sittingSelection");

        setManualPanel();
        setAutomaticPanel();
    }

    /**
     * sets the automatic panel wich is one tab of the sittingSelectionPanel
     */
    private void setAutomaticPanel() {
        JPanel automaticPanel = new JPanel();
        sittingSelection.addTab("Automatic", automaticPanel);
        automaticPanel.setLayout(new BoxLayout(automaticPanel, BoxLayout.Y_AXIS));

        JLabel manualButtonsLabel = new JLabel("Automatic selection type:");
        centered.setSelected(true);
        JPanel automaticButtonsPanel = SearchPanel.newRadioButtonsPanel(true, manualButtonsLabel, centered,
                centeredLower, centeredUpper, furthest);
        JPanel applyPanel = new JPanel();
        JLabel applyLabel = new JLabel("Number of tickets:");
        numberTicketsAutomatic = new JTextField(10);
        applyPanel.add(applyLabel);
        applyPanel.add(numberTicketsAutomatic);

        automaticPanel.add(automaticButtonsPanel);
        automaticPanel.add(applyPanel);
        automaticPanel.add(applyAutomaticSelectionButton);
    }

    /**
     * sets the manual panel wich is one tab of the sittingSelectionPanel
     */
    private void setManualPanel() {
        seatPanel = new SeatPanel();
        sittingSelection.addTab("Manual", seatPanel);
    }

    
    /** 
     * gets the indexes of the seats
     * @return List<SittingPair>
     */
    public java.util.List<Util.SittingPair> getIndexes() {
        return seatPanel.getIndexes();
    }

    /**
     * sets the list panel (displayed in the right bottom corner)
     */
    private void setListPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        listPanel = new JPanel();

        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);

        JScrollPane jsc = new JScrollPane(list);

        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(8);

        JPanel buttonsPanel = new JPanel();

        clearButton = new JButton("Clear");

        buttonsPanel.add(clearButton);

        listPanel.add(jsc);
        listPanel.add(buttonsPanel);
        panel.add(new JLabel("Selected tickets:", SwingConstants.CENTER));
        panel.add(listPanel);
        mainPanel.add(panel);
    }

    /**
     * sets controllers for the buttons of each panel and the tree
     * @param treeSelectionListener tree listener
     * @param selectSeatAction select seat button listener
     * @param standing standing selection button listener
     * @param automatic automatic selection button listener
     * @param reserve reserve button listener
     * @param cardPurchase card purchase button listener
     * @param passPurchase pass purchase button listener
     */
    public void setController(TreeSelectionListener treeSelectionListener, ActionListener selectSeatAction,
            ActionListener standing, ActionListener automatic, ActionListener reserve, ActionListener cardPurchase,
            ActionListener passPurchase) {

        paymentMethodsPanel.setController(cardPurchase, passPurchase);

        clearButton.addActionListener(e -> listModel.removeAllElements());

        tree.addTreeSelectionListener(treeSelectionListener);
        standingButton.addActionListener(standing);
        seatPanel.setController(selectSeatAction);
        applyAutomaticSelectionButton.addActionListener(automatic);

        reserveButton.addActionListener(reserve);
    }

    /**
     * updates the panel
     * 
     * @param passes list of passes of the user
     */
    public void update(java.util.List<? extends ITabulizable> passes) {
        listModel.removeAllElements();
        setSelectionVisibility(false);
        paymentMethodsPanel.update(passes);
        clear();
        MainController.getInstance().getTicketsTreeSelection().valueChanged(null);
    }

    /**
     * adds an item to the list if it is not repeated
     * @param item item to add
     * @return true iff the item was added
     */
    public boolean addToList(ITabulizable item) {
        if (listModel.contains(item))
            return false;
        listModel.addElement(item);
        return true;
    }

    /**
     * removes an element from the list
     * @param l list with the elements to remove
     */
    public void removeFromList(java.util.List<? extends ITabulizable> l) {
        for (ITabulizable item : l) {
            listModel.removeElement(item);
        }
    }

    /**
     * Stablish the visibility of the selection panel
     * 
     * @param b boolean value for the visibility
     */
    public void setSelectionVisibility(boolean b) {
        selectionPanel.setVisible(b);
    }

    /**
     * clears all the JTextFields
     */
    public void clear() {
        standingText.setText("");
        numberTicketsAutomatic.setText("");
        paymentMethodsPanel.clear();
    }
    
    /**
     * clears the list
     */
    public void clearList() {
        listModel.removeAllElements();
    }

    /**
     * changes the cards of the selectionPanel
     * 
     * @param card name of the panel to be displayed
     */
    public void setPanel(String card) {
        CardLayout c = (CardLayout) selectionPanel.getLayout();
        c.show(selectionPanel, card);
        setSelectionVisibility(true);
    }

    /************ GETTERS *********************************** */
    /**
     * Get the tree
     * @return the tree of the panel
     */
    public JTree getTree() {
        return tree;
    }

    
    /** 
     * Getter
     * @return (number of tickets for standing selection)
     */
    public String getStandingText() {
        return this.standingText.getText();
    }

    
    /** 
     * Getter
     * @return AutomaticText (number of tickets for automatic selection)
     */
    public String getAutomaticText() {
        return this.numberTicketsAutomatic.getText();
    }

    
    /** 
     * Getter
     * @return the list of ITabulizable
     */
    public java.util.List<? extends ITabulizable> getElements() {
        java.util.List<ITabulizable> list = new ArrayList<>();

        for (int i = 0; i < listModel.getSize(); i++) {
            list.add(listModel.getElementAt(i));
        }

        return list;
    }

    
    /** 
     * Getter
     * @return selected AutomaticSelectionType
     */
    public AutomaticSelectionType getAutomaticSelectionType() {
        if (centeredUpper.isSelected())
            return AutomaticSelectionType.CENTEREDUPPER;
        if (centeredLower.isSelected())
            return AutomaticSelectionType.CENTEREDLOWER;
        if (centered.isSelected())
            return AutomaticSelectionType.CENTERED;
        else
            return AutomaticSelectionType.FURTHEST;
    }

    
    /** 
     * Getter
     * @return selected Pass
     */
    public Pass getPassSelected() {
        return (Pass) paymentMethodsPanel.getPassSelected();
    }

    
    /** 
     * Getter
     * @return the credit card
     */
    public String getCreditCard() {
        return paymentMethodsPanel.getCreditCard();
    }

    /**
     * Updates the sitting panel.
     * 
     * @param rows         number of rows of the amtrix of checkboxes
     * @param columns      number of column of the amtrix of checkboxes
     * @param nonAvailable list of indexes of the checkboxes to be marked as non
     *                     checkable
     */
    public void updateSitting(int rows, int columns, java.util.List<Util.SittingPair> nonAvailable) {
        seatPanel.update(rows, columns, nonAvailable);
    }
}
