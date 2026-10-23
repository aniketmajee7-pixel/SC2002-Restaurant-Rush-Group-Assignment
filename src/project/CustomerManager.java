package project;

import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class CustomerManager {
    private static final Set<Integer> ARRIVALS = Set.of(1,4,7,10,13,16);
    private static final long SEED = 2002; // fixed seed so every run gets the same food choices
    private final Random random = new Random(SEED);
    private static final String[] CUSTOMERNAMES = new String[]{"Michael",
            "George", "Lucille", "Buster", "Oscar", "Lindsay", "Tobias", "Mae", "Tracy"};

    public CustomerManager(){}

    public void update(List<Customer> rec){
        for (Customer c : rec){
            c.advanceMeal();
            c.tickPatience();
        }
    }

    private Customer spawnCustomer(){
        MenuItem[] menu = MenuItem.values();
        Order o = new Order(menu[random.nextInt(menu.length)]);
        String name = CUSTOMERNAMES[random.nextInt(CUSTOMERNAMES.length)];
        return new Customer(o, name);
    }
    public Customer processArrival(int turn){
        if (ARRIVALS.contains(turn)){
            return spawnCustomer();
        }
        return null;
    }
}
