package project;

public class Chef implements ControllableStaff{
    private static final String[] CHOICES = new String[]{"Cook", "Wait"};
    public void cook(Order o){
        o.cook();
    }
    @Override
    public String getName() {
        return "Chef";
    }

    @Override
    public String[] getChoices() {
        return CHOICES;
    }
}
