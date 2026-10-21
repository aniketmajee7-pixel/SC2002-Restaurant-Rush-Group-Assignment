package project;

public class Order {
    private OrderStatus status;
    private MenuItem contents;
    private int cookTimeRequired;
    private int toBeEarned;

    public Order(MenuItem contents){
        status = OrderStatus.UNPLACED;
        this.contents = contents;
        cookTimeRequired = contents.prepUnits();
        toBeEarned = contents.priceCents();
    }

    public void place(){
        status = OrderStatus.PREPARING;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getContents(){
        return contents.getName();
    }

    public int finish(){
        status = OrderStatus.PAID;
        return toBeEarned;
    }

    public void kill(){
        status = OrderStatus.KILLED;
    }

    public void cook(){
        if (--cookTimeRequired <= 0){
            status = OrderStatus.READY;
        }
    }

    public void serve(){
        if (status == OrderStatus.READY){
            status = OrderStatus.SERVED;
        }
    }
}
