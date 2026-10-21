package project;

import java.util.List;
import java.util.Queue;

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

    public void reap(){
        for (Table t : state.getTables()){
            if (t.isOccupied() && t.getCustomer().isEnraged()){
                t.getCustomer().kill();
                t.free();
            }
        }
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
        //work on this
    }

    public void execute(){
        boolean arrived = cm.processArrival(currentTurn, state.getCustomerQueue());
        if (arrived) ui.announce();
    }
}
