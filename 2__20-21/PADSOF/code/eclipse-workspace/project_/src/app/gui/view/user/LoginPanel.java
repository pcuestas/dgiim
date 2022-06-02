package app.gui.view.user;


import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class LoginPanel. Panel that allows login and registration 
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class LoginPanel extends JPanel{
	
	private JButton loginButton = new JButton("Login");
	private JButton regButton  = new JButton("Register");

    private JLabel labelUsername = new JLabel("username: ");
    private JLabel labelPassword = new JLabel("password: ");
    
    private final JTextField username = new JTextField(10);
    private final JPasswordField password = new JPasswordField(10);

	/**
     * public constructor
     */
    public LoginPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        placeComponents();
        this.setSize(new Dimension(400, 50));
    }

    /**
     * place the components of the panel
     */
    private void placeComponents() {
        JPanel leftCol = new JPanel();
        leftCol.setLayout(new BoxLayout(leftCol, BoxLayout.Y_AXIS));
        leftCol.add(Box.createVerticalStrut(100));
		
		try {
			BufferedImage image =ImageIO.read(new File("./aux_files/img/theater.png"));
			JLabel label = new JLabel (new ImageIcon(image));
			label.setPreferredSize(new Dimension(600,300));
			label.setAlignmentX(RIGHT_ALIGNMENT);
			leftCol.add(label);
			leftCol.add(Box.createVerticalStrut(100));
		}catch(IOException e1){
			return;
		}
		
		JPanel rightCol = new JPanel();
		rightCol.setLayout(new BoxLayout(rightCol,BoxLayout.Y_AXIS));
		JPanel aux1 = new JPanel();
		aux1.add(labelUsername);
		aux1.add(username);
		
		JPanel aux2 = new JPanel();
		aux2.add(labelPassword);
		aux2.add(password);
		
		JPanel aux3 = new JPanel();
		aux3.add(regButton);
		regButton.setPreferredSize(new Dimension(120,25));
		aux3.add(loginButton);
		loginButton.setPreferredSize(new Dimension(120,25));
		
		rightCol.add(Box.createVerticalStrut(300));
		rightCol.add(Box.createVerticalGlue());
		rightCol.add(aux1);
		rightCol.add(aux2);
		rightCol.add(aux3);
		rightCol.add(Box.createVerticalGlue());
		rightCol.add(Box.createVerticalStrut(300));
		
		this.add(leftCol);
		this.add(rightCol);		
	}
	
    /**
     * @param loginAction    action performed when login button is clicked
     * @param registerAction action performed when register button is clicked
     */
    public void setController(ActionListener loginAction, ActionListener registerAction) {
        loginButton.addActionListener(loginAction);
        regButton.addActionListener(registerAction);
    }
    
    /** 
     * getter
     * @return username
     */
    public String getUsername() {
        return String.valueOf(this.username.getText());
    }
		
    /** 
     * getter
     * @return password
     */
    public String getPassword() {
		return String.copyValueOf(this.password.getPassword());
	}

	/**
	 * clears the JTextFields of the panel
	 */
	public void update() {
		username.setText("");
		password.setText("");
	}
}
