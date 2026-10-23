package project;


public class Customer {
    private final String name;
    private Order desire;
    private CustomerStatus status;
    private int satisfaction;
    private final int IRRITATION = 8;

    public Customer(Order desire, String n) {
        this.desire = desire;
        status = CustomerStatus.QUEUED;
        satisfaction = 100;
        name = n;
    }

    public void irritate(){
        satisfaction -= IRRITATION;
        if (satisfaction < 0) satisfaction = 0;
    }

    public String getName() {
        return name;
    }

    public Order getOrder(){
        return desire;
    }

    public boolean isEnraged(){
        return status != CustomerStatus.KILLED && satisfaction <= 0;
    }

    public int getSatisfaction() {
        return satisfaction;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public int finish(){
        setStatus(CustomerStatus.PAID);
        return desire.finish();
    }

    public void makeOrderable(){
        setStatus(CustomerStatus.READY_TO_ORDER);
    }

    public void takeOrder(){
        setStatus(CustomerStatus.ORDERED);
    }

    public void serveOrder(){
        setStatus(CustomerStatus.SERVED);
    }

    public void kill(){
        status = CustomerStatus.KILLED;
        desire.kill();
    }

    // Inside Customer.java
    public void advanceMeal() {
        if (status == CustomerStatus.SERVED) {
            status = CustomerStatus.EATING;
        } else if (status == CustomerStatus.EATING) {
            status = CustomerStatus.READY_TO_PAY;
        }
    }

    public void tickPatience() {
        if (status == CustomerStatus.QUEUED ||
                status == CustomerStatus.READY_TO_ORDER ||
                status == CustomerStatus.ORDERED) {
            irritate();
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
