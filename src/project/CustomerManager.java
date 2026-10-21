package project;

import java.util.Queue;
import java.util.Set;

public class CustomerManager {
    private static final Set<Integer> ARRIVALS = Set.of(1,4,7,10,13,16);
    private Customer spawnCustomer(Queue<Customer> q){
        Customer c = new Customer(); //add randomness here later
        return c;
    }
    public boolean processArrival(int turn, Queue<Customer> q){
        if (ARRIVALS.contains(turn)){
            spawnCustomer(q);
            return true;
        }
        return false;
    }
}
