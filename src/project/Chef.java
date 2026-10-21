package project;

public class Chef implements Staff{
    public void cook(Order o){
        o.cook();
    }
    @Override
    public String getName() {
        return "Chef";
    }
}
