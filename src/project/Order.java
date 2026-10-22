package project;

public class Order {
    private OrderStatus status;
    private MenuItem contents;
    private int cookTimeRequired;
    private int toBeEarned;
    private int tableNo;

    public Order(MenuItem contents){
        status = OrderStatus.UNPLACED;
        this.contents = contents;
        cookTimeRequired = contents.prepUnits();
        toBeEarned = contents.priceCents();
    }

    public void place(int tableNo){
        status = OrderStatus.PREPARING;
        this.tableNo = tableNo;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getContents(){
        return contents.getName();
    }

    public int getCookTimeRequired(){
        return cookTimeRequired;
    }

    @Override
    public String toString() {
        return getContents();
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

    public int getTableNo() {
        return tableNo;
    }

    public void serve(){
        if (status == OrderStatus.READY){
            status = OrderStatus.SERVED;
        }
    }
}
