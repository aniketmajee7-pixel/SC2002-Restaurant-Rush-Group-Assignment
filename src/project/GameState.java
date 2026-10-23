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
        return new ArrayDeque<>(customerQueue);
    }

    public List<Customer> getCustomerRecord() {
        return Collections.unmodifiableList(customerRecord);
    }

    public List<Order> getActiveOrders() {
        return Collections.unmodifiableList(activeOrders);
    }

    public List<Table> getTables() {
        return Collections.unmodifiableList(tables);
    }

    public void addCustomer(Customer c){
        customerRecord.add(c);
        customerQueue.add(c);
    }

    public Table getTableNO(int no){
        for (Table t : tables){
            if (t.getTableNo() == no)
                return t;
        }
        return null;
    }

    public Table getEmptyTable(){
        for (Table t : tables){
            if (!t.isOccupied()){
                return t;
            }
        }
        return null;
    }

    public Table getBillable(){
        //MUst first iterate through records to establish seniority
        Customer chosen = null;
        for (Customer c: customerRecord){
            if (c.getStatus() == CustomerStatus.READY_TO_PAY){
                chosen = c;
                break;
            }
        }
        if (chosen == null) return null;
        //If a customer is READY_TO_PAY, they MUST be at a table. This is guaranteed by customermanager, which is the only thing allowed to set READY_TO_PAY
        for (Table t : tables){
            if (t.getCustomer() == chosen){
                return (t);
            }
        }
        return null; //Unreachable
    }

    public void freeTable(Table t){
        t.free();
    }
    public Customer deQueue(){
        return customerQueue.poll();
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

    public int getServedCount(){
        int count = 0;
        for (Customer c : customerRecord){
            if (c.getStatus() == CustomerStatus.SERVED ||
                    c.getStatus() == CustomerStatus.PAID ||
                    c.getStatus() == CustomerStatus.EATING)
                count++;
        }
        return count;
    }
    public List<Table> getReadyTables() {
        List<Table> temp = new ArrayList<>();
        for (Table t : tables) {
            if (t.isOccupied() && t.getCustomer().getStatus() == CustomerStatus.READY_TO_ORDER)
                temp.add(t);
        }
        return temp;
    }
    public List<Order> getReadyOrders() {
        List<Order> ready = new ArrayList<>();
        for (Order o : getActiveOrders()){
            if (o.getStatus() == OrderStatus.READY)
                ready.add(o);
        }
        return ready;
    }
    public void addOrder(Order o){
        activeOrders.add(o);
    }
    public List<Order> getCookableOrders() {
        List<Order> ready = new ArrayList<>();
        for (Order o : getActiveOrders()){
            if (o.getStatus() == OrderStatus.PREPARING)
                ready.add(o);
        }
        return ready;
    }
    public void markServed(Order o){
        activeOrders.remove(o);
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

    public List<Customer> reap(){
        List<Customer> newlyDead = new ArrayList<>();
        for (Table t : tables){
            if (t.isOccupied() && t.getCustomer().isEnraged()){
                t.getCustomer().kill();
                newlyDead.add(t.getCustomer());
                t.free();
            }
        }
        Customer[] queueArray = customerQueue.toArray(new Customer[0]);
        customerQueue.clear();
        for (int i = 0; i < queueArray.length; i++) {
            Customer c = queueArray[i];
            if (c.isEnraged()) {
                c.kill();
                newlyDead.add(c);
            } else {
                customerQueue.add(c);
            }
        }
        for (int i = activeOrders.size() - 1; i >= 0; i--) {
            Order o = activeOrders.get(i);
            if (o.getStatus() == OrderStatus.KILLED) {
                activeOrders.remove(i);
            }
        }
        return newlyDead;
    }
}
