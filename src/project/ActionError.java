package project;

public enum ActionError {
    NO_READY_CUSTOMER("No customer is ready to order! Please select a different option!"),
    EMPTY_QUEUE("There's no queued customer waiting to be served!\nPlease select a different option!"),
    NO_VACANT_TABLE("There's no vacant table in which to seat a queued customer!\nPlease select a different option!"),
    NO_READY_ORDER("There are no orders ready to be served!\nPlease select a different option!"),
    NOTHING_TO_COOK("There's nothing to cook! You've no choice now but to wait!");

    private final String message;

    ActionError(String message) {
        this.message = message;
    }

    //This is really cool, we can just put it in print directly
    @Override
    public String toString() {
        return message;
    }
}
