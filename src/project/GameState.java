package project;

import java.util.*;

public class GameState {
    private final Queue<Customer> customerQueue;
    private final List<Table> tables;
    private final List<Customer> customerRecord;
    private final List<Order> activeOrders;
    private int revenue;

    public GameState(){
        customerQueue = new ArrayDeque<Customer>();
        tables = new ArrayList<Table>();
        tables.add(new Table(1)); tables.add(new Table(2));
        customerRecord = new ArrayList<Customer>();
        activeOrders = new ArrayList<Order>();
        revenue = 0;
    }

    public Queue<Customer> getCustomerQueue() {
        return customerQueue;
    }

    public List<Customer> getCustomerRecord() {
        return customerRecord;
    }

    public List<Order> getActiveOrders() {
        return activeOrders;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void makeMoney(int a){
        revenue+=a;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getPaidCount(){
        int count = 0;
        for (Customer c : customerRecord){
            if (c.getStatus() == CustomerStatus.PAID) 
                count++;
        }
        return count;
    }

    public void getDepartures(){
        int count = 0;

    }

    // Average satisfaction of paid customers, or -1 if nobody has paid yet.
    // Paid customers stopped losing satisfaction once served, so this is the value they paid with.
    public double getAverageSatisfaction(){
        int total = 0;
        int count = 0;
        for (Customer c : customerRecord){
            if (c.getStatus() == CustomerStatus.PAID){
                total += c.getSatisfaction();
                count++;
            }
        }
        if (count == 0) return -1;
        return (double) total / count;
    }
}
