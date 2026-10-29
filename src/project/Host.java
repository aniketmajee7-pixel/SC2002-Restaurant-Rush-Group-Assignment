package project;

public class Host implements ControllableStaff {
    private static final String[] CHOICES = new String[]{"Seat Customer", "Wait"};

    public void seat(Customer customer, Table table) {
        table.seat(customer);
        customer.makeOrderable();
    }

    @Override
    public String getName() {
        return "Host";
    }

    @Override
    public String[] getChoices() {
        return CHOICES;
    }
}
