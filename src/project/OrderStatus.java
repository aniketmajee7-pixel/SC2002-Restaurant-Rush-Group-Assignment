package project;

public enum OrderStatus {
    UNPLACED("Unplaced"),
    PREPARING("Preparing"),
    READY("Ready"),
    SERVED("Served"),
    PAID("Paid"),
    KILLED("Killed");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}