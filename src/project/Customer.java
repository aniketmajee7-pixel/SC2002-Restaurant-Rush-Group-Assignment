package project;


import java.util.Random;

public class Customer {
    private final String name;
    private Order desire;
    private CustomerStatus status;
    private int satisfaction;
    private static final Dish[] menu = new Dish[]{
            MenuItem.SALAD, MenuItem.BURGER, MenuItem.PASTA,
            new ComboMeal(MenuItem.SALAD, MenuItem.BURGER),
            new ComboMeal(MenuItem.SALAD, MenuItem.PASTA),
            new ComboMeal(MenuItem.BURGER, MenuItem.PASTA)};

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

    public int calculatePrice(){
        return (int)(Math.rint(desire.getBasePrice()*getDiscount()));
    }

    public int bill(){
        setStatus(CustomerStatus.PAID);
        return desire.finish();
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

    private boolean isWaiting() {
        return status == CustomerStatus.QUEUED ||
                status == CustomerStatus.READY_TO_ORDER ||
                status == CustomerStatus.ORDERED;
    }

    public void tickPatience() {
        if (isWaiting()) {
            irritate();
        }
    }

    public void recover(int amount) {
        if (isWaiting()) {
            satisfaction = Math.min(100, satisfaction + amount);
        }
    }

    @Override
    public String toString() {
        return "[" + getType() + "] " + name;
    }
}
