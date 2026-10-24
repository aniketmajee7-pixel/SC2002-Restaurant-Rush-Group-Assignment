package project;

public class Cashier implements Staff{
    public int bill(Customer c){
        return c.bill();
    }
    @Override
    public String getName() {
        return "Cashier";
    }
}
