package app.gui.view.trees;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;


/**
 * Class AreaInfoPanel. Panel that displays the info for a given area
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class AreaInfoPanel extends JPanel {
    private JLabel infoText;
    private JButton removeButton;
    private JButton addButton;
    private JPanel seatPanel;

    // SimpleAreaPanel
    private JPanel simpleAreaPanel;
    private JLabel price;
    private JTextField priceText;
    private JButton addPriceButton;

    // SittingAreaPanel
    private JLabel row;
    private JLabel column;
    private JTextField rowText;
    private JTextField columnText;
    private JButton selectSeatButton;
    private JLabel disableInfo;
    private JLabel beginInfo;
    private JLabel endInfo;
    private JTextField beginText;
    private JTextField endText;
    private JButton disableButton;

    /**
     * public constructor
     */
    public AreaInfoPanel() {
        this.setLayout(new GridLayout(4, 1));

        infoText = new JLabel("", SwingConstants.CENTER);
        infoText.setAlignmentX(CENTER_ALIGNMENT);
        infoText.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));

        removeButton = new JButton("Remove Area");
        removeButton.setPreferredSize(new Dimension(140, 25));
        addButton = new JButton("Add Area");
        addButton.setPreferredSize(new Dimension(140, 25));
        seatPanel = new JPanel(new GridLayout(3, 1));
        seatPanel.setVisible(false);

        simpleAreaPanel = new JPanel();
        simpleAreaPanel.setVisible(false);
        price = new JLabel("");
        priceText = new JTextField(6);
        addPriceButton = new JButton("Add price");

        row = new JLabel("Row");
        column = new JLabel("Column");
        rowText = new JTextField(4);
        columnText = new JTextField(4);
        selectSeatButton = new JButton("Select seat");
        disableInfo = new JLabel("Seat disabled between: ");
        beginInfo = new JLabel("yyyy-mm-dd");
        endInfo = new JLabel("yyyy-mm-dd");
        beginText = new JTextField(10);
        endText = new JTextField(10);
        disableButton = new JButton("Disable seat");

        setSeatPanel();
        placeComponents();
    }

    /**
     * @param removeAction     action performed when removeArea button is clicked
     * @param addAction        action performed when addArea button is clicked
     * @param selectSeatAction action performed when select ticket button is clicked
     * @param disableAction    action performed when disable button is clicked
     * @param addPriceAction   action performed when addPrice button is clicked
     */
    public void setController(ActionListener removeAction, ActionListener addAction, ActionListener selectSeatAction,
            ActionListener disableAction, ActionListener addPriceAction) {
        removeButton.addActionListener(removeAction);
        addButton.addActionListener(addAction);

        addPriceButton.addActionListener(addPriceAction);

        selectSeatButton.addActionListener(selectSeatAction);
        disableButton.addActionListener(disableAction);
    }

    /**
     * place components of the panel
     */
    private void placeComponents() {
        this.add(infoText);

        JPanel aux2 = new JPanel();
        aux2.add(addButton);
        aux2.add(removeButton);
        this.add(aux2);

        simpleAreaPanel.add(price);
        simpleAreaPanel.add(priceText);
        simpleAreaPanel.add(addPriceButton);
        this.add(simpleAreaPanel);

        this.add(seatPanel);
    }

    /**
     * sets set selection panel
     */
    private void setSeatPanel() {
        JPanel aux1 = new JPanel();
        JPanel aux2 = new JPanel();
        JPanel aux3 = new JPanel();

        aux1.add(row);
        aux1.add(rowText);
        aux1.add(column);
        aux1.add(columnText);
        aux1.add(selectSeatButton);

        aux2.add(disableInfo);
        aux2.add(beginInfo);
        aux2.add(endInfo);

        aux3.add(beginText);
        aux3.add(endText);
        aux3.add(disableButton);

        seatPanel.add(aux1);
        seatPanel.add(aux2);
        seatPanel.add(aux3);
    }

    /**
     * sets visibility of the seat selection panel
     * @param b visibility
     */
    public void setSeatPanelVisible(boolean b) {
        seatPanel.setVisible(b);
    }

    /**
     * sets visibility of the simple area panel
     * @param b visibility
     */
    public void setSimpleAreaPanelVisible(boolean b) {
        simpleAreaPanel.setVisible(b);
    }

    /**
     * Getter
     * @return RowText
     */
    public String getRowText() {
        return rowText.getText();
    }

    /**
     * Getter
     * @return ColumnText
     */
    public String getColumnText() {
        return columnText.getText();
    }

    /**
     * Getter
     * @return BeginText
     */
    public String getBeginText() {
        return beginText.getText();
    }

    /**
     * Getter
     * @return EndText
     */
    public String getEndText() {
        return endText.getText();
    }

    /**
     * Getter
     * @return PriceText
     */
    public String getPriceText() {
        return priceText.getText();
    }

    /**
     * updates the JLabels of the panel
     * @param areaString area string 
     * @param annualPassPrice annual pass price
     * @param beginDate begin date of restriction
     * @param endDate end date of restriction
     */
    public void update(String areaString, Double annualPassPrice, String beginDate, String endDate) {
        update(areaString, annualPassPrice, beginDate, endDate, true);
    }

    /**
     * updates the JLabels of the panel
     * @param areaString area string 
     * @param annualPassPrice annual pass price
     * @param beginDate begin date of restriction
     * @param endDate end date of restriction
     * @param clearSeat whether the seat is cleared
     */
    public void update(String areaString, Double annualPassPrice, String beginDate, String endDate, boolean clearSeat) {
        if (areaString != null)
            infoText.setText(areaString);
        if (annualPassPrice != null)
            price.setText(
                    "Current annual pass price: " + ((annualPassPrice < 0) ? "not established" : annualPassPrice));
        priceText.setText("");
        if (clearSeat) {
            rowText.setText("");
            columnText.setText("");
        }
        beginText.setText("");
        endText.setText("");
        if (beginDate != null)
            beginInfo.setText(beginDate);
        if (endDate != null)
            endInfo.setText(endDate);
    }
}
