package project;

import java.util.List;

public class Cashier implements Staff{
    public int bill(Customer c){
        return c.finish();
    }
    @Override
    public String getName() {
        return "Cashier";
    }
}
