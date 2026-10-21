package project;

import java.util.Queue;

public class Customer {
    private Order desire;
    private CustomerStatus status;
    private int irritation;
    private final int MAXIRRITATION = 8;

    public Customer(Order desire) {
        this.desire = desire;
        status = CustomerStatus.QUEUED;
        irritation = 0;
    }

    public void irritate(){
        irritation++;
    }

    public boolean isEnraged(){
        return irritation > MAXIRRITATION;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }
}
