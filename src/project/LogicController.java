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
    private CustomerManager cm;
    private SystemUI ui;
    private GameState state;


    public LogicController() {
        currentTurn = 0;
        chef = new Chef();
        waiter = new Waiter();
        cashier = new Cashier();
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
                state.getCustomerQueue(), state.getTables(), state.getActiveOrders());
        manageWaiter();
        manageChef();
        manageCashier();
        cm.update(state.getCustomerRecord());
        ui.endOfTurn(currentTurn, state.getServedCount(), state.reap(), state.getRevenue(), state.getAverageSatisfaction());
    }

    public void manageWaiter() {
        boolean worked = false;
        while (!worked){
            switch(ui.query(waiter)){
                case 1:
                    Table table = state.getEmptyTable();
                    if (table == null){
                        ui.displayError(ActionError.NO_VACANT_TABLE);
                        break;
                    }
                    Customer customer = state.deQueue();
                    if (customer == null){
                        ui.displayError(ActionError.EMPTY_QUEUE);
                        break;
                    }
                    waiter.seat(customer,table);
                    worked = true;
                    break;
                case 2:
                    List<Table> temp = state.getReadyTables();
                    if (temp.isEmpty()){
                        ui.displayError(ActionError.NO_READY_CUSTOMER);
                        break;
                    }
                    Order newOrder = waiter.order(temp.get(ui.takeOrder(temp) - 1));
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

