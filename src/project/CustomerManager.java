package project;

import java.util.List;
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

    private Customer spawnCustomer(int turn){
        String name = CUSTOMERNAMES[random.nextInt(CUSTOMERNAMES.length)];
        //hardcoded special customers on certain turns;
        if (turn == 7){
            return new CriticCustomer(name, random);
        }
        if (turn == 13){
            return new VIPCustomer(name, random);
        }
        //dice roll for special customers on other turns;
        int rand = random.nextInt(20); //roll a d20
        if (rand == 0) return new CriticCustomer(name, random); //nat 1
        if (rand == 19) return new VIPCustomer(name, random); //nat 20
        return new Customer(name, random);
    }
    public Customer processArrival(int turn){
        if (ARRIVALS.contains(turn)){
            return spawnCustomer(turn);
        }
        return null;
    }
}
