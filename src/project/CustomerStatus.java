package project;

public enum CustomerStatus {
    QUEUED, //in the queue, no table yet
    ORDERABLE, //at a table,order not taken yet
    ORDERED, //order taken, food not served yet
    EATING, //food has been served
    PAYABLE,
    PAID,
    KILLED //walked out unhappy
}
