package app.gui.view.things;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.*;



import javax.imageio.ImageIO;


/**
 * Class ThingsPanel. Panel that displays info of objects that implements
 * the interface IDisplayable

 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public  class ThingPanel extends JPanel {
    protected JButton selectButton = new JButton("");
    private JLabel infoText = new JLabel(); 
    protected JLabel labelImage = new JLabel();
    protected JPanel rightCol;
    protected JPanel leftCol = new JPanel();

    protected JPanel sideAdminPanel = new JPanel(new GridLayout (2,1));
    
    /**
     * public constructor
     * @param selectButtonText string for the select button
     */
    public ThingPanel(String selectButtonText) {
    	this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    
        selectButton.setText(selectButtonText);
        placeComponents(); 
        
        infoText.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
        
    }
    
    /**
     * place components of the panel
     */
    private void placeComponents() {
    	leftCol.setLayout(new BoxLayout(leftCol,BoxLayout.Y_AXIS));
    	leftCol.add(Box.createVerticalStrut(65));
    	labelImage.setPreferredSize(new Dimension(600,300));
    	labelImage.setAlignmentX(RIGHT_ALIGNMENT);
    	leftCol.add(labelImage);
    	
    	JPanel right = new JPanel(new GridLayout(2,1));
    	
    	rightCol = new JPanel();
    	rightCol.setLayout(new BoxLayout(rightCol,BoxLayout.Y_AXIS));
    	rightCol.add(Box.createVerticalGlue());
    	rightCol.add(infoText);
    	rightCol.add(Box.createVerticalStrut(25));
    	rightCol.add(selectButton);
    	rightCol.add(Box.createVerticalStrut(25));
    	
    	right.add(rightCol);
    	right.add(Box.createVerticalStrut(40));
    	right.add(sideAdminPanel);
    	
    	rightCol.setAlignmentX(CENTER_ALIGNMENT);
    	sideAdminPanel.setAlignmentX(CENTER_ALIGNMENT);
    	right.setAlignmentX(RIGHT_ALIGNMENT);

    	this.add(Box.createHorizontalStrut(70));
    	this.add(leftCol);
    	this.add(Box.createHorizontalStrut(70));
    	this.add(right);
    }

    /**
     * updates the panel and sets visibility of adminPanel if required
     * @param d object to be displayed
     * @param adminPanelVisibility boolean value for the visibility
     */
    public void update(IDisplayable d, boolean adminPanelVisibility){
    	selectButton.setVisible(true);
        try {
            Image image = ImageIO.read(new File(d.getPicture()));
            image = resizeImage(image, 400.);

            labelImage.setIcon(new ImageIcon(image));
        } catch (IOException e1) {
            System.out.println(e1);
        }
        
        infoText.setText(d.getInfo());
        
        sideAdminPanel.setVisible(adminPanelVisibility);
    }

    /**
     * Sets the controller for the panel
     * @param selectAction action performed when select button is clicked
     */
    public void setController(ActionListener selectAction)
    {
        selectButton.addActionListener(selectAction);
    }


    /**
     * resizes an image
     * @param img the image
     * @param newSize size of the image
     * @return the resized image
     */
    public Image resizeImage(Image img, double newSize){
        double width = (double)img.getWidth(null);   
        double height = (double)img.getHeight(null); 
        double ratio = width/height; 

        width=newSize*ratio;
        height=newSize;

        return img.getScaledInstance((int)Math.round(width), (int)Math.round(height), Image.SCALE_SMOOTH);
    }
}
