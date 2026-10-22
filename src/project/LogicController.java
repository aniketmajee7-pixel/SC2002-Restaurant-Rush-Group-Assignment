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
        cm = new CustomerManager(state.getCustomerRecord());
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
        boolean arrived = cm.processArrival(currentTurn, state.getCustomerQueue());
        if (arrived) ui.announce();
        ui.startOfTurn(currentTurn, state.getRevenue(), state.getAverageSatisfaction(),
                state.getCustomerQueue(), state.getTables(), state.getActiveOrders());
        manageWaiter();
        manageChef();
        manageCashier();
        cm.update();
        reap();
    }

    public void manageWaiter() {
        boolean worked = false;
        while (!worked){
            switch(ui.query(waiter)){
                case 1:
                    ActionError test = waiter.seat(state.getCustomerQueue(), state.getTables());
                    if (test != null){
                        ui.displayError(test);
                        break;
                    }
                    worked = true;
                    break;
                case 2:
                    List<Table> temp = new ArrayList<>();
                    for (Table t : state.getTables()){
                        if(t.isOccupied() && t.getCustomer().getStatus() == CustomerStatus.READY_TO_ORDER) {
                            temp.add(t);
                        }
                    }
                    if (temp.isEmpty()){
                        ui.displayError(ActionError.NO_READY_CUSTOMER);
                        break;
                    }
                    waiter.order(temp.get(ui.takeOrder(temp) - 1), state.getActiveOrders());
                    worked = true;
                    break;
                case 3:
                    List<Order> ready = new ArrayList<>();
                    for (Order o : state.getActiveOrders()){
                        if (o.getStatus() == OrderStatus.READY)
                            ready.add(o);
                    }
                    if (ready.isEmpty()){
                        ui.displayError(ActionError.NO_READY_ORDER);
                        break;
                    }
                    waiter.serve(ready.get(ui.serveOrder(ready) - 1), state.getTables(), state.getActiveOrders());
                    worked = true;
                    break;
                default:
                    worked = true;
            }
        }
    }

    public void manageChef(){

    }

    public void manageCashier(){

    }

    public void reap(){
        for (Table t : state.getTables()){
            if (t.isOccupied() && t.getCustomer().isEnraged()){
                t.getCustomer().kill();
                t.free();
            }
        }
        //I couldn't think of a different way to implement this, probably suboptimal
        Queue<Customer> queue = state.getCustomerQueue();
        Customer[] queueArray = queue.toArray(new Customer[0]);
        queue.clear();
        for (int i = 0; i < queueArray.length; i++) {
            Customer c = queueArray[i];
            if (c.isEnraged()) {
                c.kill();
            } else {
                queue.add(c);
            }
        }
        List<Order> orders = state.getActiveOrders();
        for (int i = orders.size() - 1; i >= 0; i--) {
            Order o = orders.get(i);
            if (o.getStatus() == OrderStatus.KILLED) {
                orders.remove(i);
            }
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

