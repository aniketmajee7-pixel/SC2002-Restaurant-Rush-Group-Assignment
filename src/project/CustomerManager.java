package project;

import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class CustomerManager {
    private static final Set<Integer> ARRIVALS = Set.of(1,4,7,10,13,16);
    private static final long SEED = 2002; // fixed seed so every run gets the same food choices
    private final Random random = new Random(SEED);
    private List<Customer> record;

    public CustomerManager(List<Customer> rec){
        record = rec;
    }

    public void update(){
        for (Customer c : record){
            if (c.getStatus() == CustomerStatus.EATING){
                c.setStatus(CustomerStatus.READY_TO_PAY);
            } else if (c.getStatus() == CustomerStatus.SERVED){
                c.setStatus(CustomerStatus.EATING);
            }
            if (c.getStatus() == CustomerStatus.QUEUED ||
                c.getStatus() == CustomerStatus.READY_TO_ORDER||
                c.getStatus() == CustomerStatus.ORDERED){
                c.irritate();
            }
        }
    }

    private Customer spawnCustomer(Queue<Customer> queue){
        MenuItem[] menu = MenuItem.values();
        Order o = new Order(menu[random.nextInt(menu.length)]);
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
