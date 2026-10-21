package project;

import java.util.List;
import java.util.Queue;

public class Waiter implements Staff{
    // Returns false if nobody is queued or no table is free, so nobody is removed from the queue.
    public boolean seat(Queue<Customer> queue, List<Table> tables){
        if (queue.isEmpty()) return false;
        for (Table t : tables){
            if (!t.isOccupied()){
                Customer c = queue.poll();
                t.seat(c);
                c.setStatus(CustomerStatus.READY_TO_ORDER);
                return true;
            }
        }
        return false;
    }

    public void order(Table t, List<Order> active){
        t.getCustomer().setStatus(CustomerStatus.ORDERED);
        Order o = t.getCustomer().takeOrder();
        o.place(t.tableNo);
        active.add(o);
    }

    // Returns false if the order is not READY, leaving the order and customer unchanged.
    public boolean serve(Order o, List<Table> tables, List<Order> active){
        if (o.getStatus() != OrderStatus.READY) return false;
        o.serve();
        int tableNo = o.getTableNo();
        for (Table t : tables){
            if (t.tableNo == tableNo){
                t.getCustomer().setStatus(CustomerStatus.SERVED);
                break;
            }
        }
        active.remove(o);
        return true;
    }
    @Override
    public String getName() {
        return "Waiter";
    }
}
