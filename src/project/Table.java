package project;

public class Table {
    private boolean isOccupied;
    private Customer customer;
    private final int tableNo;

    public Table(int tableNo) {
        this.isOccupied = false;
        this.customer = null;
        this.tableNo = tableNo;
    }

    public Customer getCustomer() {
        if(isOccupied) return customer;
        return null;
    }

    public int getTableNo() {
        return tableNo;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean seat(Customer c){
        if (isOccupied){
            return false;
        }
        customer = c;
        isOccupied = true;
        return true;
    }

    public Customer free(){
        isOccupied = false;
        Customer temp = customer;
        customer = null;
        return temp;
    }
}
