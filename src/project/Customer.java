package project;


public class Customer {
    private final String name;
    private Order desire;
    private CustomerStatus status;
    private int satisfaction;
    private final int IRRITATION = 8;
    private static final String[] CUSTOMERNAMES = new String[]{"Michael",
            "George", "Lucille", "Buster", "Oscar", "Lindsay", "Tobias", "Mae", "Tracy"};
    private static int count = 0;

    public Customer(Order desire) {
        this.desire = desire;
        status = CustomerStatus.QUEUED;
        satisfaction = 100;
        name = CUSTOMERNAMES[count%9];
        count++;
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

    public int finish(){ return desire.finish(); }

    public void kill(){
        status = CustomerStatus.KILLED;
        desire.kill();
    }

    @Override
    public String toString() {
        return name;
    }
}
