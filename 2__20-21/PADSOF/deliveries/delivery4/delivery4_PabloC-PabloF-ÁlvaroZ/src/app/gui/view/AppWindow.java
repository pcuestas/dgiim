package app.gui.view;

import javax.swing.*;
import java.awt.*;


import app.gui.controller.*;
import app.gui.controller.user.tickets.SetPurchaseTicketsPanelAction;
import app.gui.view.admin.*;
import app.gui.view.areas.*;
import app.gui.view.tables.*;
import app.gui.view.things.*;
import app.gui.view.trees.*;
import app.gui.view.user.*;

/**
 * Class AppWindow
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class AppWindow extends JFrame {
	private static AppWindow INSTANCE;   
	private Container container;
    private JPanel contentPane;
    
    /*views*/
    private LoginPanel loginPanel = new LoginPanel();
    private TopPanel topPanel = new TopPanel();
	private NotificationPanel notificationPanel = new NotificationPanel();
	private MyTicketsPanel myTicketsPanel = new MyTicketsPanel();
    private SearchPanel searchPanel = new SearchPanel();
    private TablePanel tablePanel = new TablePanel();
	private ClientHomePanel clientHomePanel = new ClientHomePanel();
	private AdminHomePanel adminHomePanel = new AdminHomePanel();
    private EventPanel eventPanel = new EventPanel();
    private PerformancePanel performancePanel = new PerformancePanel();
    private CreateEventPanel createEventPanel = new CreateEventPanel();
    private AreaViewPanel areaViewPanel = new AreaViewPanel();
	private CreateAreaPanel createAreaPanel = new CreateAreaPanel();
    private ModifyParametersPanel modifyParametersPanel = new ModifyParametersPanel();
    private CreateCyclePanel createCyclePanel = new CreateCyclePanel();
    private EventPricesPanel setEventPricesPanel = new EventPricesPanel(areaViewPanel.getAreaTreePanel().getDataModel());
    private EstablishCyclePricePanel establishCyclePricePanel = new EstablishCyclePricePanel(areaViewPanel.getAreaTreePanel().getDataModel());
	private PurchaseAnnualPassPanel purchaseAnnualPassPanel = new PurchaseAnnualPassPanel(areaViewPanel.getAreaTreePanel().getDataModel());
    private PurchaseCyclePassPanel purchaseCyclePassPanel = new PurchaseCyclePassPanel(areaViewPanel.getAreaTreePanel().getDataModel());
	private SearchStatsPanel searchStatsPanel = new SearchStatsPanel();
    private StatsTablePanel statsTablePanel = new StatsTablePanel();
	private PurchaseTicketsPanel purchaseTicketsPanel = new PurchaseTicketsPanel(areaViewPanel.getAreaTreePanel().getDataModel());
	private PerformancesTablePanel performancesTablePanel = new PerformancesTablePanel();
    
	
    /** 
	 * Gets the intances of app window (Singleton)
     * @return AppWindow
     */
    public static AppWindow getInstance() {
		return INSTANCE;
	}
	
	/**
     * constructor
     * @param title name of the window    
	 */
    public AppWindow(String title) {
		super(title); 
        INSTANCE = this;
		
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        container = this.getContentPane();
        container.setLayout(new BorderLayout());
        
        contentPane = new JPanel();
        contentPane.setLayout(new CardLayout());
        container.add(contentPane, BorderLayout.CENTER);
		
		// add components to container
        container.add(topPanel, BorderLayout.NORTH);
        contentPane.add(loginPanel, "loginPanel");
        contentPane.add(searchPanel, "searchPanel");
        contentPane.add(tablePanel, "tablePanel");
		contentPane.add(clientHomePanel, "clientHomePanel");
		contentPane.add(adminHomePanel, "adminHomePanel");
  		contentPane.add(eventPanel, "eventPanel");
		contentPane.add(performancePanel, "performancePanel");
		contentPane.add(createEventPanel, "createEventPanel");
        contentPane.add(areaViewPanel, "areaViewPanel");
        contentPane.add(createAreaPanel, "createAreaPanel");
        contentPane.add(modifyParametersPanel, "modifyParametersPanel");
        contentPane.add(setEventPricesPanel,"setEventPricesPanel");
        contentPane.add(createCyclePanel,"createCyclePanel");
        contentPane.add(establishCyclePricePanel,"establishCyclePricePanel");
        contentPane.add(purchaseAnnualPassPanel, "purchaseAnnualPassPanel");
        contentPane.add(purchaseCyclePassPanel, "purchaseCyclePassPanel");
		contentPane.add(notificationPanel,"notificationPanel");
		contentPane.add(searchStatsPanel, "searchStatsPanel");
		contentPane.add(statsTablePanel, "statsTablePanel");
		contentPane.add(myTicketsPanel,"myTicketsPanel");
		contentPane.add(purchaseTicketsPanel, "purchaseTicketsPanel");
		contentPane.add(performancesTablePanel,"performancesTablePanel");
		
		this.pack();
		this.setVisible(true);
		
		showPanel("loginPanel");
	}
    
    
    /** 
     * set the controller
     * @param controller main controller
     */
    public void setController(MainController controller) {
        this.addWindowListener(controller.getCloseWindowOperation());

    	loginPanel.setController(controller.getLoginAction(), controller.getRegisterAction());
    	
    
    	topPanel.setController(controller.getSetLoginPanelAction(), controller.getSetSearchPanelAction(), 
    							controller.getHomeAction(), controller.getLogoutAction());
    	
    
    	searchPanel.setController(controller.getSearchAction());

	
        clientHomePanel.setController(controller.getSetTicketsPanelAction(), controller.getSetNotificationsPanelAction(),
        		controller.getSetPurchaseCyclePassAction(), controller.getSetPurchaseAnnualPassAction());
    	
        adminHomePanel.setController(controller.getSetSearchStatsPanelAction(), controller.getSetCreateEventPanelAction(), 
				controller.getAreaViewPanelAction(), controller.getSetModifyParametersPanelAction(),
				controller.getSetCreateCyclePanel());

        statsTablePanel.setController(controller.getSetSearchStatsPanelAction());

        createEventPanel.setController(controller.getCreateConcertAction(), 
				controller.getCreatePlayAction(), controller.getCreateDanceAction());

        createAreaPanel.setController(controller.getCreateStandingAreaAction(), 
				controller.getCreateSittingAreaAction(),controller.getCreateCompositeAreaAction());
    	  	

        eventPanel.setController(
				controller.getEventShowPerformancesAction(), 
				controller.getRestrictEventAction(), controller.getSetEventPricesPanelAction(),
				controller.getCreatePerformanceAction());
        
        performancePanel.setController(new SetPurchaseTicketsPanelAction(), 
				controller.getCancelPerformanceAction(), controller.getPostponePerformanceAction());
        

		areaViewPanel.setController(controller
				.getRemoveAreaAction(), controller.getAddAreaAction(),controller.getAreasTreeSelection(),controller.getSelectSeatAction(),controller.getDisableAction(),controller.getAnnualPriceAction());
	
		setEventPricesPanel.setController(controller.getAddEventPricesAction(), controller.getSelecTreeEventPriceAction());
		
		modifyParametersPanel.setController(controller.getSetTimeLimitAction(), controller.getSetReservationsLimitAction(), controller.getSetPurchaseLimitAction());
		
	    createCyclePanel.setController(controller.getAddEventToCycleAction(),  controller.getCreateCycleAction());
	    
	    establishCyclePricePanel.setController( controller.getAddPriceCycleAction(), controller.getAddCycleAction(),  controller.getSelecTreeCyclePriceAction());
	    
		purchaseAnnualPassPanel.setController(controller.getPurchaseAnnualPassAction());
	
        purchaseCyclePassPanel.setController(controller.getPurchaseCyclePassAction());

		notificationPanel.setController(controller.getSetPerformancePanelFromNotificationAction(), controller.getRemoveNotificationAction());

		searchStatsPanel.setController(controller.getStatsAction());
		
		myTicketsPanel.setController(controller.getSelectTicketAction(),
									controller.getConfirmReservationCardAction(),
									controller.getConfirmReservationPassAction(),
									controller.getCancelReservationAction());

		purchaseTicketsPanel.setController(controller.getTicketsTreeSelection(),
										controller.getAddTicketsSeatedAction(),
										controller.getAddTicketsStandingAction(),
										controller.getAddTicketsAutomaticAction(),
										controller.getReserveTicketsAction(),
										controller.getPayTicketsCardAction(),
										controller.getPayTicketsPassAction());
		
		performancesTablePanel.setController(controller.getFilterPerformancesAction(), controller.getShowAllPerformancesFromSearchAction());
    }
    
    
    /** 
	* Getter
     * @return LoginPanel
     */
    public LoginPanel getLoginPanel() {
		return loginPanel;
	}

	
    /** 
	* Getter
     * @param card name of the panel
     */
    public void showPanel(String card) {
    	CardLayout c = (CardLayout)contentPane.getLayout();
    	c.show(contentPane,  card);
    }

	
    /** 
	* Getter
     * @return SearchPanel
     */
    public SearchPanel getSearchPanel() {
		return searchPanel;
	}

	
    /**
	* Getter 
     * @return TopPanel
     */
    public TopPanel getTopPanel() {
		return topPanel;
	}	

	
    /** 
     * Getter
     * @return ClientHomePanel
     */
    public ClientHomePanel getClientHomePanel() {
		return clientHomePanel;
	}
	
	
    /** 
     * Getter
     * @return AdminHomePanel
     */
    public AdminHomePanel getAdminHomePanel() {
		return adminHomePanel;
	}

	
    /** 
     * Getter
     * @return TablePanel
     */
    public TablePanel getTablePanel(){
		return tablePanel;
	}

	
    /** 
     * Getter
     * @return SearchStatsPanel
     */
    public SearchStatsPanel getSearchStatsPanel(){
		return searchStatsPanel;
	}

    
    /** 
     * Getter
     * @return EventPanel
     */
    public EventPanel getEventPanel() {
    	return eventPanel;
    }

	
    /** 
     * Getter
     * @return PerformancePanel
     */
    public PerformancePanel getPerformancePanel() {
		return performancePanel;
	}
	
    /** 
     * Getter
     * @return CreateEventPanel
     */
    public CreateEventPanel getCreateEventPanel() {
		return createEventPanel;
	}

	
    /** 
     * Getter
     * @return AreaViewPanel
     */
    public AreaViewPanel getAreaViewPanel() {
		return areaViewPanel;
	}

	
    /** 
     * Getter
     * @return CreateAreaPanel
     */
    public CreateAreaPanel getCreateAreaPanel() {
		return createAreaPanel;
	}
	
	
    /** 
     * Getter
     * @return EventPricesPanel
     */
    public EventPricesPanel getEventPricesPanel() {
		return setEventPricesPanel;
	}
	
	
    /** 
     * Getter
     * @return CreateCyclePanel
     */
    public CreateCyclePanel getCreateCyclePanel() {
		return createCyclePanel;
	}
	
	
    /** 
     * Getter
     * @return EstablishCyclePricePanel
     */
    public EstablishCyclePricePanel getEstablishCyclePricePanel() {
		return establishCyclePricePanel;
	}

	
    /** 
     * Getter
     * @return NotificationPanel
     */
    public NotificationPanel getNotificationPanel(){
		return notificationPanel;
	}
	
	
    /** 
     * Getter
     * @return MyTicketsPanel
     */
    public MyTicketsPanel getMyTicketsPanel() {
		return myTicketsPanel;
	}

	
    /** 
     * Getter
     * @return PurchaseTicketsPanel
     */
    public PurchaseTicketsPanel getPurchaseTicketsPanel(){
		return purchaseTicketsPanel;
	}

	
    /** 
     * Getter
     * @return ModifyParametersPanel
     */
    public ModifyParametersPanel getModifyParametersPanel() {
		return modifyParametersPanel;
	}
	
    /** 
     * Getter
     * @return PurchaseAnnualPassPanel
     */
    public PurchaseAnnualPassPanel getPurchaseAnnualPassPanel() {
		return purchaseAnnualPassPanel;
	}
	
	
    /**
     * Getter 
     * @return PurchaseCyclePassPanel
     */
    public PurchaseCyclePassPanel getPurchaseCyclePassPanel() {
		return purchaseCyclePassPanel;
	}

    
    /** 
     * Getter
     * @return StatsTablePanel
     */
    public StatsTablePanel getStatsTablePanel(){
        return statsTablePanel;
    }

    /**
     * getter
     * @return performances table panel
     */
	public PerformancesTablePanel getPerformancesTablePanel() {
		return performancesTablePanel;
	}

}
