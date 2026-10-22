package project;

import java.util.List;
import java.util.Queue;

public class Waiter implements Staff{
    private static final String[] CHOICES = new String[]{"Seat Customer",
                    "Take Order",
                    "Serve Order",
                    "Wait"};

    public ActionError seat(Queue<Customer> queue, List<Table> tables){
        if (queue.isEmpty()) return ActionError.EMPTY_QUEUE;
        for (Table t : tables){
            if (!t.isOccupied()){
                Customer c = queue.poll();
                t.seat(c);
                c.setStatus(CustomerStatus.READY_TO_ORDER);
                return null;
            }
        }
        return ActionError.NO_VACANT_TABLE;
    }
    public Order order(Table t, List<Order> active){
        t.getCustomer().setStatus(CustomerStatus.ORDERED);
        Order o = t.getCustomer().getOrder();
        o.place(t.getTableNo());
        active.add(o);
        return o;
    }
    public void serve(Order o, List<Table> tables, List<Order> active){
        o.serve();
        int tableNo = o.getTableNo();
        for (Table t : tables) {
            if (t.getTableNo() == tableNo) {
                t.getCustomer().setStatus(CustomerStatus.SERVED);
                break;
            }
        }
        active.remove(o);
    }
    @Override
    public String getName() {
        return "Waiter";
    }
    @Override
    public String[] getChoices() {
        return CHOICES;
    }
}
