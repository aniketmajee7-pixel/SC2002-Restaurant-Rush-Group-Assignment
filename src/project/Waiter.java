package project;

import java.util.List;
import java.util.Queue;

public class Waiter implements ControllableStaff{
    private static final String[] CHOICES = new String[]{"Seat Customer",
                    "Take Order",
                    "Serve Order",
                    "Wait"};

    public void seat(Customer customer, Table table){
        table.seat(customer);
        customer.makeOrderable();
    }
    public Order order(Table t){
        t.getCustomer().takeOrder();
        Order o = t.getCustomer().getOrder();
        o.place(t.getTableNo());
        return o;
    }
    public void serve(Order o, Table t){
        o.serve();
        t.getCustomer().serveOrder();
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
