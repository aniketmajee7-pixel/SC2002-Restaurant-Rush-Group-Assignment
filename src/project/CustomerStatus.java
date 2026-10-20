package project;

public enum CustomerStatus {
         WAITING, //in the queue, no table yet
         SEATED, //at a table,order not taken yet
         ORDERED, //order taken, food not served yet
         EATING, //food has been served
         READY_TO_PAY,
         PAID,
         LEFT //walked out unhappy
}
