package project;

public class LogicController {
    private int currentTurn;
    private Chef chef;
    private Waiter waiter;
    private Cashier cashier;
    private CustomerManager cm;
    private SystemUI ui;
    private GameState state;


    public LogicController() {
        currentTurn = 0;
        chef = new Chef();
        waiter = new Waiter();
        cashier = new Cashier();
        state = new GameState();
        cm = new CustomerManager(state.getCustomerRecord());
        ui = new SystemUI();
        ui.welcome();
    }

    public boolean wantsToLive(){
        return (currentTurn<19);
    }

    public void updateTurn(){
        currentTurn++;
        execute();
    }

    public void endGame(){
        //work on this
    }

    public void execute(){

    }

    public void manageWaiter(){

    }
}
