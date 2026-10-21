package project;

import java.util.List;

public class Cashier implements Staff{
    public int bill(List<Table> tables, List<Customer> record){
        Customer chosen = null;
        for (Customer c: record){
            if (c.getStatus() == CustomerStatus.READY_TO_PAY){
                chosen = c;
                break;
            }
        }
        if (chosen == null){ return 0; }
        for (Table t : tables){
            if (t.getCustomer() == chosen){
                t.free();
                break;
            }
        }
        chosen.setStatus(CustomerStatus.PAID);
        return chosen.finish();
    }
    @Override
    public String getName() {
        return "Cashier";
    }
}
