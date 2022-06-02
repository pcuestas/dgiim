package app.gui.view.admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Class CreateEventPanel
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class CreateEventPanel extends JPanel {
    private JButton createConcertButton = new JButton("Create");
    private JButton createPlayButton = new JButton("Create");
    private JButton createDanceButton = new JButton("Create");

    private JLabel labelTitle = new JLabel("Title: ");
    private final JTextField title = new JTextField(20);
    private JLabel labelDuration = new JLabel("Duration: ");
    private final JTextField duration = new JTextField(10);
    private JLabel labelAuthor = new JLabel("Author: ");
    private final JTextField author = new JTextField(10);
    private JLabel labelDirector = new JLabel("Director: ");
    private final JTextField director = new JTextField(10);
    private JLabel labelPicture = new JLabel("Picture: ");
    private final JTextField picture = new JTextField(10);
    
	private JLabel labelOrchestra = new JLabel("Orchestra: "); //para dance y para concert
    private final JTextField orchestra = new JTextField(10);    

	private JLabel labelDescription = new JLabel("Description: "); //para dance y para concert
    private final JTextField description = new JTextField(10);

    //concert
    private JLabel labelProgram = new JLabel("Program: ");
    private final JTextField program = new JTextField(10);
	private JLabel labelSoloists = new JLabel("Soloists: ");
    private final JTextField soloists = new JTextField(10);

    //play
    private JLabel labelActors = new JLabel("Actors: ");
    private final JTextField actors = new JTextField(10);

    //dance
    private JLabel labelDancers = new JLabel ("Dancers: ");
    private final JTextField dancers = new JTextField(10);
	private JLabel labelConductor= new JLabel ("Conductor: ");
    private final JTextField conductor = new JTextField(10);

    /**
     * Constructor of CreateEventPanel
     */
    public CreateEventPanel() {
        this.setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridLayout(6, 2));
        
        
        centerPanel.add(labelTitle);
        centerPanel.add(title);
        centerPanel.add(labelDuration);
        centerPanel.add(duration);
        centerPanel.add(labelDescription);
        centerPanel.add(description);
        centerPanel.add(labelAuthor);
        centerPanel.add(author);
        centerPanel.add(labelDirector);
        centerPanel.add(director);
        centerPanel.add(labelPicture);
        centerPanel.add(picture);
        this.add(centerPanel, BorderLayout.CENTER);       
        

        JTabbedPane tabs = new JTabbedPane();
        // Add panels to container using the method addTab(<title>,<panel>)
        tabs.addTab("Concert", tabConcertPanel());
        tabs.addTab("Play", tabPlayPanel());
        // se pueden añadir imagenes, esto no va pq esta copiado de las diapos pero se puede hacer queda bien
        tabs.addTab("Dance", tabPanelDance());
        // A tab from the container can be selected by means of setSelectedIndex(<index>)
        tabs.setSelectedIndex(1);

        this.add(tabs, BorderLayout.SOUTH);
		
		this.setSize(new Dimension(400,50));
	}
    
    /**
     * Clears the panel
     */
    public void clear() {
        title.setText("");
        duration.setText("");
        description.setText("");
        author.setText("");
        director.setText("");
        picture.setText("");
        orchestra.setText("");
        dancers.setText("");
        conductor.setText("");
        actors.setText("");
        orchestra.setText("");
        program.setText("");
        soloists.setText("");
    }

    /**
     * Creates the tab for dance
     * @return the tab
     */
    public JPanel tabPanelDance(){
        JPanel dance = new JPanel();
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(3,2));
        center.add(labelOrchestra);
        center.add(orchestra);    
        center.add(labelDancers);
        center.add(dancers);
        center.add(labelConductor);
        center.add(conductor);
        dance.add(center);
        dance.add(createDanceButton);
        dance.setPreferredSize(new Dimension(300, 100));

        return dance;
    }

    /**
     * Creates the tab for play
     * @return the tab
     */
	public JPanel tabPlayPanel(){
        JPanel play = new JPanel();
        play.add(labelActors);
        play.add(actors);
        play.add(createPlayButton);
        play.setPreferredSize(new Dimension(300, 100));

        return play;
    }

    /**
     * Creates the tab for concert
     * @return the tab
     */
    public JPanel tabConcertPanel(){
        JPanel concert = new JPanel();
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(2,2));
        center.add(labelOrchestra);
        center.add(orchestra);
        center.add(labelProgram);
        center.add(program);
        center.add(labelSoloists);
        center.add(soloists);
        concert.add(center);
        concert.add(createConcertButton);
        concert.setPreferredSize(new Dimension(300, 100));

        return concert;
    }
    
    /**
     * Sets the controller for the panel
     * @param createConcertAction action for creating a concert
     * @param createPlayAction action for creating a play
     * @param createDanceAction action for creating a dance
     */
    public void setController(ActionListener createConcertAction, ActionListener createPlayAction, 
    		ActionListener createDanceAction) {
        createConcertButton.addActionListener(createConcertAction);
        createPlayButton.addActionListener(createPlayAction);
        createDanceButton.addActionListener(createDanceAction);
    }

    /**
     * Gets the picutre of the event
     * @return the picture
     */
    public String getPicture() {
		return String.valueOf(this.picture.getText());
	}
	
    /**
     * Gets the title of the event
     * @return the title
     */
	public String getTitle() {
		return String.valueOf(this.title.getText());
	}
	
    /**
     * Gets the duration of the event
     * @return the duration
     */
	public int getDuration() {
		try {
			return Integer.parseInt((this.duration.getText()));
		}catch(NumberFormatException x) {
			JOptionPane.showMessageDialog(null, "Invalid number (duration).");
			return -1;
		}
	}
    
    /**
     * Gets the author of the event
     * @return the author
     */
    public String getAuthor() {
		return String.valueOf(this.author.getText());
	}
	
    /**
     * Gets the director of the event
     * @return the director
     */
	public String getDirector() {
		return String.valueOf(this.director.getText());
	}

    /**
     * Gets the description of the event
     * @return the description
     */
    public String getDescription() {
		return String.valueOf(this.description.getText());
	}
	
    /**
     * Gets the orchestra of the concert
     * @return the orchestra
     */
	public String getOrchestra() {
		return String.valueOf(this.orchestra.getText());
	}

    /**
     * Gets the program of the concert
     * @return the program
     */
    public String getProgram() {
		return String.valueOf(this.program.getText());
	}
	
    /**
     * Gets the soloists of the concert
     * @return the soloists
     */
	public String getSoloists() {
		return String.valueOf(this.soloists.getText());
	}

    /**
     * Gets the actors of the play
     * @return the actors
     */
    public String getActors() {
		return String.valueOf(this.actors.getText());
	}
	
    /**
     * Gets the dancers of the dance
     * @return the dancers
     */
	public String getDancers() {
		return String.valueOf(this.dancers.getText());
	}

    /**
     * Gets the conductor of the event
     * @return the conductor
     */
    public String getConductor() {
		return String.valueOf(this.conductor.getText());
	}
}
