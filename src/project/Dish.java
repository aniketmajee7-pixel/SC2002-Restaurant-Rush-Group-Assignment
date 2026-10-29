package project;

//Anything that can be ordered: a single MenuItem or a ComboMeal
public interface Dish {
    public String getName();
    public int priceCents();
    public int prepUnits();
}
