package project;

import java.util.List;
import java.util.Queue;

public class Waiter implements Staff{
    public void seat(Queue<Customer> queue, List<Table> tables){
        Customer c = queue.poll();
        for (Table t : tables){
            if (!t.isOccupied()){
                t.seat(c);
                c.setStatus(CustomerStatus.READY_TO_ORDER);
                break;
            }
        }
    }

    public void order(Table t, List<Order> active){
        t.getCustomer().setStatus(CustomerStatus.ORDERED);
        Order o = t.getCustomer().takeOrder();
        o.place(t.tableNo);
        active.add(o);
    }

    public void serve(Order o, List<Table> tables, List<Order> active){
        o.serve();
        int tableNo = o.getTableNo();
        for (Table t : tables){
            if (t.tableNo == tableNo){
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
}
