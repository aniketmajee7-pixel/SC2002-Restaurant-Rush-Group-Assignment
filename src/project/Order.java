package project;

public class Order {
    private OrderStatus status;
    private Dish contents;
    private int cookTimeRequired;
    private int toBeEarned;
    private int tableNo;

    public Order(Dish contents){
        status = OrderStatus.UNPLACED;
        this.contents = contents;
        cookTimeRequired = contents.prepUnits();
    }

    //Price is locked when the order is taken
    public void place(int tableNo, int price){
        status = OrderStatus.PREPARING;
        this.tableNo = tableNo;
        toBeEarned = price;
    }

    public int getBasePrice(){
        return contents.priceCents();
    }

    public int getPrice(){
        return toBeEarned;
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
        return getContents() + " (status: "+getStatus()+")";
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
