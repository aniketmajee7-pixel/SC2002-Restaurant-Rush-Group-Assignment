package project;

import java.util.List;
import java.util.Queue;
import java.util.Set;

public class CustomerManager {
    private static final Set<Integer> ARRIVALS = Set.of(1,4,7,10,13,16);
    private List<Customer> record;

    public CustomerManager(List<Customer> rec){
        record = rec;
    }

    private Customer spawnCustomer(Queue<Customer> queue){
        Order o = null;
        switch ((int)(Math.random()*3)){
            case 0:
                o = new Order(MenuItem.BURGER);
                break;
            case 1:
                o = new Order(MenuItem.PASTA);
                break;
            case 2:
                o = new Order(MenuItem.SALAD);
        }
        Customer c = new Customer(o);
        record.add(c);
        queue.add(c);
        return c;
    }
    public boolean processArrival(int turn, Queue<Customer> queue){
        if (ARRIVALS.contains(turn)){
            spawnCustomer(queue);
            return true;
        }
        return false;
    }
}
