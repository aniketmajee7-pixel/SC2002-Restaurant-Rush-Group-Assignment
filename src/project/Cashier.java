package project;

import java.util.List;

public class Cashier implements Staff{
    public BillResult bill(List<Table> tables, List<Customer> record){
        Customer chosen = null;
        //MUst first iterate through records to establish seniority
        for (Customer c: record){
            if (c.getStatus() == CustomerStatus.READY_TO_PAY){
                chosen = c;
                break;
            }
        }
        //If a customer is READY_TO_PAY, they MUST be at a table. This is guaranteed by customermanager, which is the only thing allowed to set READY_TO_PAY
        if (chosen == null){ return null; }
        for (Table t : tables){
            if (t.getCustomer() == chosen){
                t.free();
                break;
            }
        }
        chosen.setStatus(CustomerStatus.PAID);
        return new BillResult(chosen, chosen.finish());
    }
    @Override
    public String getName() {
        return "Cashier";
    }

    @Override
    public String[] getChoices() {
        return new String[0];
    }
}
