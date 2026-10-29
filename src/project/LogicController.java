package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class LogicController {
    private static final int TARGET_PAID = 4;
    private static final int TARGET_REVENUE = 4500; // cents
    private static final int TARGET_SATISFACTION = 60;

    private int currentTurn;
    private Chef chef;
    private Waiter waiter;
    private Cashier cashier;
    private Host host;
    private HappyHour happyHour;
    private CustomerManager cm;
    private SystemUI ui;
    private GameState state;


    public LogicController() {
        currentTurn = 0;
        chef = new Chef();
        waiter = new Waiter();
        cashier = new Cashier();
        host = new Host();
        happyHour = new HappyHour();
        state = new GameState();
        cm = new CustomerManager();
        ui = new SystemUI();
        ui.welcome();
    }

    public boolean wantsToLive(){
        return (currentTurn<18);
    }

    public void updateTurn(){
        currentTurn++;
        execute();
    }

    public void execute() {
        Customer arrived = cm.processArrival(currentTurn);
        if (arrived!=null){
            ui.announce();
            state.addCustomer(arrived);
        }
        ui.startOfTurn(currentTurn, state.getRevenue(), state.getAverageSatisfaction(),
                state.getCustomerQueue(), state.getTables(), state.getActiveOrders(),
                happyHour.getTurnsLeft(), happyHour.canActivate());
        //This does NOT violate OOP as it only passes immutable versions or copied objects
        manageHappyHour();
        manageHost();
        manageWaiter();
        manageChef();
        manageCashier();
        cm.update(state.getCustomerRecord());
        happyHour.endTurn();
        //READY_TO_PAY set after cashier, since new ready to pay customers can't pay till next turn
        ui.endOfTurn(currentTurn, state.getServedCount(), state.reap(), state.getRevenue(), state.getAverageSatisfaction());
        //reap is a mutator method that kills enranged customers and removes killed orders
    }

    public void manageHappyHour() {
        if (happyHour.canActivate() && ui.askHappyHour()) {
            happyHour.activate();
        }
        if (happyHour.isActive()) {
            for (Customer c : state.getCustomerRecord()) {
                c.recover(HappyHour.RECOVERY);
            }
            ui.happyHourActive(happyHour.getTurnsLeft());
        }
    }

    //Returns null if a queued customer can be seated, otherwise the reason they can't
    private ActionError seatingProblem() {
        if (state.getEmptyTable() == null) return ActionError.NO_VACANT_TABLE;
        if (state.getCustomerQueue().isEmpty()) return ActionError.EMPTY_QUEUE;
        return null;
    }

    public void manageHost() {
        boolean worked = false;
        while (!worked){
            switch(ui.query(host)){
                case 1:
                    ActionError problem = seatingProblem();
                    if (problem != null){
                        ui.displayError(problem);
                        break;
                    }
                    host.seat(state.deQueue(), state.getEmptyTable());
                    worked = true;
                    break;
                default:
                    worked = true;
            }
        }
    }

    public void manageWaiter() {
        boolean worked = false;
        while (!worked){
            switch(ui.query(waiter)){
                case 1:
                    ActionError problem = seatingProblem();
                    if (problem != null){
                        ui.displayError(problem);
                        break;
                    }
                    waiter.seat(state.deQueue(), state.getEmptyTable());
                    worked = true;
                    break;
                case 2:
                    List<Table> temp = state.getReadyTables();
                    if (temp.isEmpty()){
                        ui.displayError(ActionError.NO_READY_CUSTOMER);
                        break;
                    }
                    Order newOrder = waiter.order(temp.get(ui.takeOrder(temp) - 1), happyHour);
                    ui.showOrder(newOrder);
                    state.addOrder(newOrder);
                    worked = true;
                    break;
                case 3:
                    List<Order> ready = state.getReadyOrders();
                    if (ready.isEmpty()){
                        ui.displayError(ActionError.NO_READY_ORDER);
                        break;
                    }
                    Order prepared = ready.get(ui.serveOrder(ready) - 1);
                    waiter.serve(prepared, state.getTableNO(prepared.getTableNo()));
                    state.markServed(prepared);
                    worked = true;
                    break;
                default:
                    worked = true;
            }
        }
    }

    public void manageChef(){
        boolean worked = false;
        while (!worked) {
            switch (ui.query(chef)) {
                case 1:
                    List<Order> cookable = state.getCookableOrders();
                    if (cookable.isEmpty()) {
                        ui.displayError(ActionError.NOTHING_TO_COOK);
                        break;
                    }
                    chef.cook(cookable.get(ui.cook(cookable) - 1));
                    //NO BREAK NEEDED, THIS IS THE LITERAL USE CASE OF FALLTHROUGH
                default:
                    worked = true;
            }
        }
    }

    public void manageCashier(){
        Table billable = state.getBillable();
        if (billable != null){
            ui.bill(billable.getCustomer());
            state.makeMoney(cashier.bill(billable.getCustomer()));
            state.freeTable(billable);
        }
    }

    public void endGame(){
        int paid = state.getPaidCount();
        int revenue = state.getRevenue();
        double avgSatisfaction = state.getAverageSatisfaction();

        boolean victory = paid >= TARGET_PAID
                && revenue >= TARGET_REVENUE
                && avgSatisfaction >= TARGET_SATISFACTION;

        ui.showResult(paid, revenue, avgSatisfaction, victory);
    }
}

