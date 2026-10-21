package project;

public enum CustomerStatus {
    QUEUED, //in the queue, no table yet
    READY_TO_ORDER, //at a table,order not taken yet
    ORDERED,//order taken, food not served yet
    SERVED,
    EATING,//Eating
    READY_TO_PAY,
    PAID,
    KILLED //walked out unhappy
}
