package project;

import java.util.Queue;

public class Customer {
    private Order desire;
    private CustomerStatus status;
    private int satisfaction;
    private final int IRRITATION = 8;

    public Customer(Order desire) {
        this.desire = desire;
        status = CustomerStatus.QUEUED;
        satisfaction = 100;
    }

    public void irritate(){
        satisfaction -= IRRITATION;
        if (satisfaction < 0) satisfaction = 0;
    }

    public Order takeOrder(){
        return desire;
    }

    public boolean isEnraged(){
        return status != CustomerStatus.KILLED && satisfaction <= 0;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public int finish(){ return desire.finish(); }

    public void kill(){
        status = CustomerStatus.KILLED;
        desire.kill();
    }
}
