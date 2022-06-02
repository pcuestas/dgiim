package app.gui.controller;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import app.gui.controller.admin.SetModifyParametersPanelAction;
import app.gui.controller.admin.SetParameterAction;
import app.gui.controller.admin.areas.*;
import app.gui.controller.admin.cycles.*;
import app.gui.controller.admin.events.*;
import app.gui.controller.admin.events.createEvent.CreateConcertAction;
import app.gui.controller.admin.events.createEvent.CreateDanceAction;
import app.gui.controller.admin.events.createEvent.CreatePlayAction;
import app.gui.controller.admin.performances.*;
import app.gui.controller.things.*;
import app.gui.controller.user.*;
import app.gui.controller.user.payments.*;
import app.gui.controller.user.tickets.AddTicketsAutomaticAction;
import app.gui.controller.user.tickets.AddTicketsSeatedAction;
import app.gui.controller.user.tickets.AddTicketsStandingAction;
import app.gui.controller.user.tickets.CancelReservationAction;
import app.gui.controller.user.tickets.ReserveTicketsAction;
import app.gui.controller.user.tickets.TicketsTreeSelection;
import app.gui.view.*;
import app.theater.*;
import app.theater.events.*;
import app.theater.events.cycles.Cycle;
import app.theater.performances.Performance;
import app.theater.performances.tickets.Reservation;
import app.theater.users.Client;
import app.theater.areas.*;
import app.gui.controller.areas.*;
import app.gui.controller.login.*;
import app.gui.controller.search.*;
import app.gui.controller.admin.*;

/**
 * Class MainController
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class MainController {
    private static MainController INSTANCE;

	private AppWindow frame;
	private Application app;
	
	private LoginAction loginAction = new LoginAction();
	private RegisterAction registerAction = new RegisterAction();
	private LogoutAction logoutAction = new LogoutAction();
	private HomeAction homeAction = new HomeAction();

	private SetSearchPanelAction setSearchPanelAction = new SetSearchPanelAction();
	private SetPanelAction setHomePanelAction = new SetPanelAction("homePanel");
	private SetLoginPanelAction setLoginPanelAction = new SetLoginPanelAction();
    private SetEventPanelAction setEventPanelAction = new SetEventPanelAction();
    private SetPanelAction setPerformancePanelAction = new SetPerformancePanelAction();
    private SetCreateEventPanelAction setCreateEventPanelAction = new SetCreateEventPanelAction();
    private EventShowPerformancesAction eventShowPerformancesAction = new EventShowPerformancesAction();
    private SetPanelAction setAreaViewPanelAction = new SetPanelAction("areaViewPanel");
	private SetPanelAction setCreateAreaPanelAction = new SetPanelAction("createAreaPanel");
	private SetModifyParametersPanelAction setModifyParametersPanelAction = new SetModifyParametersPanelAction();
    private SetPanelAction setEventPricesPanelAction = new SetPanelAction("setEventPricesPanel");
	private SearchAction searchAction = new SearchAction();
	private SetNotificationsPanelAction setNotificationsPanelAction = new SetNotificationsPanelAction();
	private SetTicketsPanelAction setTicketsPanelAction = new SetTicketsPanelAction();
	private SetPanelAction setSearchStatsPanelAction = new SetPanelAction("searchStatsPanel");
	
	private CreatePlayAction createPlayAction = new CreatePlayAction();
	private CreateConcertAction createConcertAction = new CreateConcertAction();
	private CreateDanceAction createDanceAction = new CreateDanceAction();
    private CancelPerformanceAction cancelPerformanceAction = new CancelPerformanceAction();
    private PostponePerformanceAction postponePerformanceAction = new PostponePerformanceAction();
    private RestrictEventAction restrictEventAction = new RestrictEventAction();
    private SetParameterAction setTimeLimitAction = SetParameterAction.TIME_LIMIT;
    private SetParameterAction setPurchaseLimitAction = SetParameterAction.PURCHASE_LIMIT;
    private SetParameterAction setReservationsLimitAction = SetParameterAction.RESERVATIONS_LIMIT;

	private CreateSittingAreaAction createSittingAreaAction = new CreateSittingAreaAction();
	private CreateStandingAreaAction createStandingAreaAction = new CreateStandingAreaAction();
	private CreateCompositeAreaAction createCompositeAreaAction = new CreateCompositeAreaAction();

	private AddAreaAction addAreaAction = new AddAreaAction();
    private RemoveAreaAction removeAreaAction = new RemoveAreaAction();
    private AreasTreeSelection areasTreeSelection = new AreasTreeSelection();
    private SelectSeatAction selectSeatAction = new SelectSeatAction(); 
    private DisableAction disableAction = new DisableAction();
    private AddAnnualPriceAction addAnnualPriceAction = new AddAnnualPriceAction();
    
    private AddEventPricesAction addEventPricesAction = new AddEventPricesAction();
    private SelecTreeEventPriceAction selecTreeEventPriceAction = new SelecTreeEventPriceAction(1);
    
    private AddEventToCycleAction addEventToCycleAction = new AddEventToCycleAction();
    private CreateCycleAction createCycleAction = new CreateCycleAction();

    
    private AddCycleAction addCycleAction = new AddCycleAction();
    private SelecTreeEventPriceAction selecTreeCyclePriceAction = new SelecTreeEventPriceAction(2);
    private AddReductionCycleAreaAction addPriceCycleAction = new AddReductionCycleAreaAction();
    
    private SetCreateCyclePanelAction setCreateCyclePanel = new SetCreateCyclePanelAction();
    private PurchaseCyclePassAction purchaseCyclePassAction = new PurchaseCyclePassAction();
    private PurchaseAnnualPassAction purchaseAnnualPassAction = new PurchaseAnnualPassAction();
    private SetPurchaseAnnualPassAction setPurchaseAnnualPassAction = new SetPurchaseAnnualPassAction();
    private SetPurchaseCyclePassAction setPurchaseCyclePassAction = new SetPurchaseCyclePassAction();

	private RemoveNotificationAction removeNotificationAction = new RemoveNotificationAction();
	private SetPerformancePanelFromNotificationAction setPerformancePanelFromNotificationAction = new SetPerformancePanelFromNotificationAction();
	
	private StatsAction statsAction = new StatsAction();
	
	private SelectTicketAction selectTicketAction = new SelectTicketAction();
	private ConfirmReservationCardAction confirmReservationCardAction = new ConfirmReservationCardAction();
	private ConfirmReservationPassAction confirmReservationPassAction = new ConfirmReservationPassAction();
    
	private TicketsTreeSelection ticketsTreeSelection = new TicketsTreeSelection();
	private AddTicketsStandingAction addTicketsStandingAction = new AddTicketsStandingAction();
	private AddTicketsSeatedAction addTicketsSeatedAction = new AddTicketsSeatedAction();
	private AddTicketsAutomaticAction addTicketsAutomaticAction = new AddTicketsAutomaticAction();
	private ReserveTicketsAction reserveTicketsAction = new ReserveTicketsAction();
	private CreatePerformanceAction createPerformanceAction = new CreatePerformanceAction();
	
	private PayTicketsPassAction payTicketsPassAction = new PayTicketsPassAction();
	private PayTicketsCardAction payTicketsCardAction = new PayTicketsCardAction();
	
    private CloseWindowOperation closeWindowOperation = new CloseWindowOperation();
    private CancelReservationAction cancelReservationAction = new CancelReservationAction();
    
    private FilterPerformancesAction filterPerformancesAction = new FilterPerformancesAction();
    private ShowAllPerformancesFromSearchAction showAllPerformancesFromSearchAction = new ShowAllPerformancesFromSearchAction();

    private Event currentEvent;
    private Area currentArea;
    private Performance currentPerformance;
	private Client currentClient;
	private Cycle currentCycle;
	private Reservation currentReservation;
    private List<Performance> resultSearchPerformances;


    
    /** 
	 * Gets the instance of the main controller (Singleton)
     * @return MainController
     */
    public static MainController getInstance(){
        if(INSTANCE == null){
            INSTANCE = new MainController();
        }
        return INSTANCE;
    }

	/**
	 * Constructor of MainController
	 */
	private MainController() {
		this.frame = AppWindow.getInstance();
		this.app = Application.getInstance();
	}

    
    /** 
	 * Getter
     * @return PurchaseCyclePassAction
     */
    public PurchaseCyclePassAction getPurchaseCyclePassAction(){
        return purchaseCyclePassAction;
    }

	
    /** 
	 * Getter
     * @return LoginAction
     */
    public LoginAction getLoginAction() {
		return loginAction;
	}
	
	
    /** 
	 * Getter
     * @return RegisterAction
     */
    public RegisterAction getRegisterAction() {
		return registerAction;
	}

	
    /**
	 * Getter 
     * @return SetSearchPanelAction
     */
    public SetSearchPanelAction getSetSearchPanelAction() {
		return setSearchPanelAction;
	}

	
    /** 
	 * Getter
     * @return SetPanelAction
     */
    public SetPanelAction getSetHomePanelAction() {
		return setHomePanelAction;
	}

	
    /** 
	 * Getter
     * @return SetLoginPanelAction
     */
    public SetLoginPanelAction getSetLoginPanelAction() {
		return setLoginPanelAction;
	}
	
	
    /** 
	 * Getter
     * @return SetPanelAction
     */
    public SetPanelAction getSetCreateEventPanelAction() {
		return setCreateEventPanelAction;
	}
	
	
    /** 
	 * Getter
     * @return SetPanelAction
     */
    public SetPanelAction getSetEventPricesPanelAction(){
		return setEventPricesPanelAction;
	}

	
    /** 
	 * Getter
     * @return SearchAction
     */
    public SearchAction getSearchAction() {
		return searchAction;
	}

	
    /** 
	 * Getter
     * @return LogoutAction
     */
    public LogoutAction getLogoutAction() {
		return logoutAction;
	}
	
	
    /** 
	 * Getter
     * @return HomeAction
     */
    public HomeAction getHomeAction() {
		return homeAction;
	}

    
    /** 
	 * Getter
     * @return SetEventPanelAction
     */
    public SetEventPanelAction getSetEventPanelAction(){
        return setEventPanelAction;
    }

    
    /** 
	 * Getter
     * @return SetPanelAction
     */
    public SetPanelAction getSetPerformancePanelAction(){
        return setPerformancePanelAction;
    }

	
    /**
	 * Getter 
     * @return SetPanelAction
     */
    public SetPanelAction getSetSearchStatsPanelAction(){
        return setSearchStatsPanelAction;
    }

	
    /** 
	 * Getter
     * @return EventShowPerformancesAction
     */
    public EventShowPerformancesAction getEventShowPerformancesAction() {
		return eventShowPerformancesAction;
	}

	
    /**
	 * Getter 
     * @return CreatePlayAction
     */
    public CreatePlayAction getCreatePlayAction() {
		return createPlayAction;
	}

	
    /** 
	 * Getter
     * @return CreateConcertAction
     */
    public CreateConcertAction getCreateConcertAction() {
		return createConcertAction;
	}

	
    /** 
	 * Getter
     * @return CreateDanceAction
     */
    public CreateDanceAction getCreateDanceAction() {
		return createDanceAction;
	}

	
    /** 
	 * Getter
     * @return Area
     */
    public Area getCurrentArea() {
		return currentArea;
	}

	
    /** 
	 * Setter
     * @param currentArea CurrentArea
     */
    public void setCurrentArea(Area currentArea) {
		this.currentArea = currentArea;
	}

	
    /** 
	 * Getter
     * @return Event
     */
    public Event getCurrentEvent() {
		return currentEvent;
	}

	
    /** 
	 * Setter
     * @param currentEvent CurrentEvent
     */
    public void setCurrentEvent(Event currentEvent) {
		this.currentEvent = currentEvent;
	}

    
    /** 
	 * Getter
     * @return SetPanelAction
     */
    public SetPanelAction getAreaViewPanelAction() {
        return setAreaViewPanelAction;
    }

	
    /** 
	 * Getter
     * @return Performance
     */
    public Performance getCurrentPerformance() {
		return currentPerformance;
	}

	
    /** 
	 * setter
     * @param currentPerformance CurrentPerformance
     */
    public void setCurrentPerformance(Performance currentPerformance) {
		this.currentPerformance = currentPerformance;
	}

    
    /**
	 * Getter 
     * @return CancelPerformanceAction
     */
    public CancelPerformanceAction getCancelPerformanceAction(){
        return cancelPerformanceAction;
    }

	
    /** 
	 * Getter
     * @return PostponePerformanceAction
     */
    public PostponePerformanceAction getPostponePerformanceAction() {
		return postponePerformanceAction;
	}

	
    /** 
     * Getter 
     * @return CreateCompositeAreaAction
     */
    public CreateCompositeAreaAction getCreateCompositeAreaAction() {
		return createCompositeAreaAction;
	}

	
    /** 
     * Getter 
     * @return CreateSittingAreaAction
     */
    public CreateSittingAreaAction getCreateSittingAreaAction() {
		return createSittingAreaAction;
	}

	
    /** 
     * Getter 
     * @return CreateStandingAreaAction
     */
    public CreateStandingAreaAction getCreateStandingAreaAction() {
		return createStandingAreaAction;
	}

    
    /** 
     * Getter 
     * @return RestrictEventAction
     */
    public RestrictEventAction getRestrictEventAction(){
        return restrictEventAction;
    }

	
    /** 
     * @return SetPanelAction
     */
    public SetPanelAction getSetCreateAreaPanelAction() {
		return setCreateAreaPanelAction;
	}
    
    
    /** 
     * Getter 
     * @return AreasTreeSelection
     */
    public AreasTreeSelection getAreasTreeSelection() {
    	return areasTreeSelection;
    }
    
    
    /** 
     * Getter 
     * @return RemoveAreaAction
     */
    public RemoveAreaAction getRemoveAreaAction(){
        return removeAreaAction;
    }

	
    /** 
     * Getter 
     * @return SetModifyParametersPanelAction
     */
    public SetModifyParametersPanelAction getSetModifyParametersPanelAction() {
		return setModifyParametersPanelAction;
	}

	
    /** 
     * Getter 
     * @return SetParameterAction
     */
    public SetParameterAction getSetTimeLimitAction() {
		return setTimeLimitAction;
	}
	
	
    /** 
     * Getter 
     * @return AddAreaAction
     */
    public AddAreaAction getAddAreaAction() {
		return addAreaAction;
	}

	
    /** 
     * Getter 
     * @return SetParameterAction
     */
    public SetParameterAction getSetPurchaseLimitAction() {
		return setPurchaseLimitAction;
	}

	
    /** 
     * Getter 
     * @return SetParameterAction
     */
    public SetParameterAction getSetReservationsLimitAction() {
		return setReservationsLimitAction;
	}

	
    /** 
     * Getter 
     * @return SelectSeatAction
     */
    public SelectSeatAction getSelectSeatAction() {
		return selectSeatAction;
	}
	
	
    /** 
     * Getter 
     * @return DisableAction
     */
    public DisableAction getDisableAction() {
		return disableAction;
	}
	
	
    /** 
     * Getter 
     * @return AddAnnualPriceAction
     */
    public AddAnnualPriceAction getAnnualPriceAction() {
		return addAnnualPriceAction;
	}
	
	
    /** 
     * Getter 
     * @return AddEventPricesAction
     */
    public AddEventPricesAction getAddEventPricesAction() {
		return addEventPricesAction;
	}
	
	
    /** 
     * Getter 
     * @return SelecTreeEventPriceAction
     */
    public SelecTreeEventPriceAction getSelecTreeEventPriceAction() {
		return selecTreeEventPriceAction;
	}
	
    
    /** 
     * Getter 
     * @return AddEventToCycleAction
     */
    public AddEventToCycleAction getAddEventToCycleAction() {
        return addEventToCycleAction;
    }

    
    /** 
     * Getter 
     * @return CreateCycleAction
     */
    public CreateCycleAction getCreateCycleAction() {
        return createCycleAction;
    }

    
    /** 
     * Getter 
     * @return AddCycleAction
     */
    public AddCycleAction getAddCycleAction() {
        return addCycleAction;
    }

    
    /** 
     * Getter 
     * @return SelecTreeEventPriceAction
     */
    public SelecTreeEventPriceAction getSelecTreeCyclePriceAction() {
        return selecTreeCyclePriceAction;
    }

    
    /** 
     * Getter 
     * @return AddReductionCycleAreaAction
     */
    public AddReductionCycleAreaAction getAddPriceCycleAction() {
        return addPriceCycleAction;
    }

	
    /** 
     * Getter 
     * @return RemoveNotificationAction
     */
    public RemoveNotificationAction getRemoveNotificationAction(){
	    return removeNotificationAction;
	}

	
    /** 
     * Set the current client
     * @param user current client
     */
    public void setCurrentClient(Client user) {
		this.currentClient = user;
	}
	
    /** 
     * Getter 
     * @return current Client
     */
    public Client getCurrentClient() {
		return this.currentClient;
	}

	
    /**
     * Getter 
     * @return SetNotificationsPanelAction
     */
    public SetNotificationsPanelAction getSetNotificationsPanelAction() {
		return setNotificationsPanelAction;
	}

	
    /** 
     * Getter
     * @return SetTicketsPanelAction
     */
    public SetTicketsPanelAction getSetTicketsPanelAction() {
		return setTicketsPanelAction;
	}

	
    /** 
     * Getter
     * @return PurchaseAnnualPassAction
     */
    public PurchaseAnnualPassAction getPurchaseAnnualPassAction() {
		return purchaseAnnualPassAction;
	}

	
    /** 
     * Getter
     * @return SetPurchaseAnnualPassAction
     */
    public SetPurchaseAnnualPassAction getSetPurchaseAnnualPassAction() {
		return setPurchaseAnnualPassAction;
	}

	
    /** 
     * Getter
     * @return SetPurchaseCyclePassAction
     */
    public SetPurchaseCyclePassAction getSetPurchaseCyclePassAction() {
		return setPurchaseCyclePassAction;
	}

	
    /** 
     * Setter of the current cycle
     * @param cycle current cycle
     */
    public void setCurrentCycle(Cycle cycle) {
		this.currentCycle = cycle;
	}
	
	
    /** 
     * Getter of the current cycle
     * @return Cycle
     */
    public Cycle getCurrentCycle() {
		return currentCycle;
	}

	
    /** 
     * Getter
     * @return StatsAction
     */
    public StatsAction getStatsAction() {
		return statsAction;
	}	    
	
	
    /** 
     * Getter
     * @return SetPerformancePanelFromNotificationAction
     */
    public SetPerformancePanelFromNotificationAction getSetPerformancePanelFromNotificationAction() {
		return setPerformancePanelFromNotificationAction;
	}
	
	
    /** 
     * Getter
     * @return SelectTicketAction
     */
    public SelectTicketAction getSelectTicketAction() {
		return selectTicketAction;
	}
	
	
    /** 
     * Getter
     * @return ConfirmReservationCardAction
     */
    public ConfirmReservationCardAction getConfirmReservationCardAction() {
		return confirmReservationCardAction;
	}
	
	
    /** 
     * Getter
     * @return ConfirmReservationPassAction
     */
    public ConfirmReservationPassAction getConfirmReservationPassAction() {
		return confirmReservationPassAction;
	}

	
    /** 
     * Getter
     * @return TicketsTreeSelection
     */
    public TicketsTreeSelection getTicketsTreeSelection() {
		return ticketsTreeSelection ;
	}
	
	
    /** 
     * Getter
     * @return AddTicketsStandingAction
     */
    public AddTicketsStandingAction getAddTicketsStandingAction(){
		return addTicketsStandingAction;
	}
	
	
    /** 
     * Getter
     * @return AddTicketsSeatedAction
     */
    public AddTicketsSeatedAction getAddTicketsSeatedAction() {
		return addTicketsSeatedAction;
	}
	
	
    /** 
     * Getter
     * @return ReserveTicketsAction
     */
    public ReserveTicketsAction getReserveTicketsAction() {
		return reserveTicketsAction;
	}

	
    /** 
     * Getter
     * @return AddTicketsAutomaticAction
     */
    public AddTicketsAutomaticAction getAddTicketsAutomaticAction() {
		return addTicketsAutomaticAction;
	}

	
    /** 
     * Getter
     * @return PayTicketsCardAction
     */
    public PayTicketsCardAction getPayTicketsCardAction() {
		return payTicketsCardAction;
	}
    
	
    /** 
     * Getter
     * @return PayTicketsPassAction
     */
    public PayTicketsPassAction getPayTicketsPassAction() {
		return payTicketsPassAction;
	}

    
    /** 
     * Getter
     * @return WindowListener
     */
    public WindowListener getCloseWindowOperation() {
        return closeWindowOperation;
    }

    
    /** 
     * Set the current reservation 
     * @param r current reservation 
     */
    public void setCurrentReservation(Reservation r) {
        currentReservation = r;
    }

    
    /** 
     * Current reservation
     * @return Reservation
     */
    public Reservation getCurrentReservation(){
        return currentReservation;
    }

    
    /** 
     * Getter
     * @return CancelReservationAction
     */
    public CancelReservationAction getCancelReservationAction(){
        return cancelReservationAction;
    }
    
    /**
     * 
     * @return FilterPerformancesAction
     */
    public FilterPerformancesAction getFilterPerformancesAction() {
    	return filterPerformancesAction;
    }

    /**
     * Getter
     * @return CreatePerformanceAction
     */
	public CreatePerformanceAction getCreatePerformanceAction() {
		return createPerformanceAction ;
	}
	/**
     * Getter
     * @return SetCreateCyclePanel
     */
	public SetCreateCyclePanelAction getSetCreateCyclePanel() {
		return setCreateCyclePanel ;
	}

    /**
     * set performances from the search
     * @param list the new list
     */
    public void setResultPerformances(List<Performance> list){
        resultSearchPerformances = new ArrayList<>(list);
    }

    /**
     * get performances from the search (a copy)
     * @return the list of performances
     */
    public List<Performance> getResultPerformances(){
        return new ArrayList<>(resultSearchPerformances);
    }

    /**
     * getter
     * @return ShowAllPerformancesFromSearchAction
     */
    public ShowAllPerformancesFromSearchAction getShowAllPerformancesFromSearchAction() {
        return showAllPerformancesFromSearchAction;
    }
}
