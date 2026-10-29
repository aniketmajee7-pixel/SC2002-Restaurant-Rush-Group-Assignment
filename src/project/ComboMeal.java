package project;

public class ComboMeal implements Dish {
    private static final double COMBO_DISCOUNT = 0.9;

    private final MenuItem first;
    private final MenuItem second;

    public ComboMeal(MenuItem first, MenuItem second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String getName() {
        return first.getName() + " + " + second.getName() + " Combo";
    }

    @Override
    public int priceCents() {
        return (int) Math.rint((first.priceCents() + second.priceCents()) * COMBO_DISCOUNT);
    }

    @Override
    public int prepUnits() {
        return first.prepUnits() + second.prepUnits();
    }
}
