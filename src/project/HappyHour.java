package project;

public class HappyHour {
    private static final int DURATION = 2; //the turn it is activated and the next
    private static final double DISCOUNT = 0.8;
    public static final int RECOVERY = 15;

    private int turnsLeft = 0;
    private boolean used = false;

    public boolean canActivate() {
        return !used;
    }

    public void activate() {
        used = true;
        turnsLeft = DURATION;
    }

    public boolean isActive() {
        return turnsLeft > 0;
    }

    public int getTurnsLeft() {
        return turnsLeft;
    }

    public int applyTo(int price) {
        if (!isActive()) return price;
        return (int) Math.rint(price * DISCOUNT);
    }

    public void endTurn() {
        if (turnsLeft > 0) turnsLeft--;
    }
}
