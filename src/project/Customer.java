package project;


import java.util.Random;

public class Customer {
    private final String name;
    private Order desire;
    private CustomerStatus status;
    private int satisfaction;
    private static final MenuItem[] menu = MenuItem.values();

    //Random object is borrowed from Customer Manager so we don't have
    //to keep changig the seed in both classes
    public Customer(String n, Random random) {
        status = CustomerStatus.QUEUED;
        satisfaction = 100;
        name = n;
        desire = makeDesire(random);
    }

    //Methods to be overwritten by subclasses

    protected int getIrritation(){
        return 8;
    }

    protected double getDiscount(){
        return 1;
    }

    public String getType(){
        return "Regular";
    }

    protected Order makeDesire(Random random){
        return new Order(menu[random.nextInt(menu.length)]);
    }

    public void serveOrder(){
        setStatus(CustomerStatus.SERVED);
    }


    //Regular methods

    public void irritate(){
        satisfaction -= getIrritation();
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

    protected void setSatisfaction(int satisfaction) {
        this.satisfaction = satisfaction;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public int bill(){
        setStatus(CustomerStatus.PAID);
        return (int)(Math.rint(desire.finish()*getDiscount()));
    }

    public void makeOrderable(){
        setStatus(CustomerStatus.READY_TO_ORDER);
    }

    public void takeOrder(){
        setStatus(CustomerStatus.ORDERED);
    }



    public void kill(){
        status = CustomerStatus.KILLED;
        desire.kill();
    }

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
        return "[" + getType() + "] " + name;
    }
}
