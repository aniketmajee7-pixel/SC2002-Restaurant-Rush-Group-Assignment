package project;

import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class SystemUI {
    private final Scanner sc;

    public SystemUI() {
        sc = new Scanner(System.in);
    }

    public void welcome() {
        System.out.println("Welcome to RestaurantRush!");
    }

    public void announce() {
        System.out.println("There is a new customer waiting in the queue!");
    }

    // Money is stored in cents; this turns 2430 into "$24.30".
    public static String convert(int cents) {
        return String.format("$%d.%02d", cents / 100, cents % 100);
    }

    public void displayError(ActionError e) {
        System.out.println(e);
    }

    public void clear(){
        for (int i = 0; i < 50; i++)
            System.out.println();
    }

    public void prompt(){
        System.out.println("Press enter to continue!");
        sc.nextLine();
    }

    public void showResult(int paid, int revenue, double avgSatisfaction, boolean victory) {
        clear();
        String avg = (avgSatisfaction >= 0) ? String.format("%.1f", avgSatisfaction) : "N/A";
        System.out.println();
        System.out.println("========== GAME OVER ==========");
        System.out.println("Paid customers:   " + paid + " (target 4)");
        System.out.println("Revenue:          " + convert(revenue) + " (target $45.00)");
        System.out.println("Avg satisfaction: " + avg + " (target 60)");
        System.out.println(victory ? "YOU WIN!" : "YOU LOSE!");
        sc.close();
    }

    public void startOfTurn(int turnNumber, int revenue, double avgSatisfaction,
                            Queue<Customer> customerQueue, List<Table> tables, List<Order> orders) {
        clear();
        String avg = (avgSatisfaction >= 0) ? String.format("%.1f", avgSatisfaction) : "N/A";
        System.out.print("\nStart of turn " + turnNumber +
                "!\tRevenue: " + convert(revenue) + "\tAverage Satisfaction (Paid Customers): " + avg);
        System.out.print("\nCustomer Queue: [");
        for (Customer c : customerQueue) {
            System.out.print("| " + c.getName() + "  (Satisfaction: " + c.getSatisfaction() + ") |");
        }
        System.out.println("]\nTotal Waiting: " + customerQueue.size() + " people");
        System.out.println("Tables:");
        for (Table t : tables) {
            if (t.isOccupied()) {
                Customer c = t.getCustomer();
                System.out.println("Table " + t.getTableNo() + ": Occupied by " + c.getName() + " (Satisfaction: " + c.getSatisfaction() + ", Status: " + c.getStatus() + ")");
                System.out.println("Order Status: "+t.getCustomer().getOrder().getStatus());
            } else {
                System.out.println("Table " + t.getTableNo() + ": Empty");
            }
        }

        System.out.println("\nActive Orders:");
        if (orders.isEmpty()) {
            System.out.println("None");
        } else {
            for (Order o : orders) {
                System.out.println("Order of " + o.getContents() + " for Table " + o.getTableNo() + ": Status - " + o.getStatus() + ", Prep Units Remaining - " + o.getCookTimeRequired());
            }
        }
        System.out.println();

        System.out.println("Available staff actions: Waiter [1], Chef [1], Cashier [1]");
        prompt();
    }

    public void endOfTurn(int turn, int served, List<Customer> dead, int revenue, double avgSatisfaction) {
        clear();
        String avg = (avgSatisfaction >= 0) ? String.format("%.1f", avgSatisfaction) : "N/A";
        System.out.println("Turns used: " + turn);
        System.out.println("=====================RESULTS=====================");
        System.out.println("Customers served (lifetime): " + served +
                "\tRevenue: " + convert(revenue) + "\tAverage Satisfaction: " + avg + "\n");
        for (Customer c : dead) {
            System.out.println(c + " got so angry they left (without paying)!");
        }
        prompt();
    }

    public int takeOrder(List<Table> tables) {
        System.out.println("Select a table to take order!");
        int i = 1;
        for (Table t : tables) {
            System.out.println(i + ": table " + t.getTableNo() + ", seating " + t.getCustomer() + " (Satisfaction: " + t.getCustomer().getSatisfaction() + ")");
            i++;
        }
        return takeChoice(1, --i);
    }

    public void showOrder(Order o) {
        System.out.println("The customer at table "+o.getTableNo()+" has placed an order of "+o+"!");
    }

    public int serveOrder(List<Order> orders) {
        System.out.println("Which order should be served?");
        int i = 1;
        for (Order o : orders) {
            System.out.println(i + ": " + o.getContents() + " for table " + o.getTableNo());
            i++;
        }
        return takeChoice(1, --i);
    }

    public int cook(List<Order> orders) {
        System.out.println("Which order would you like to progress?");
        int i = 1;
        for (Order o : orders) {
            System.out.println(i + ": " + o.getContents() + " for table " + o.getTableNo());
            i++;
        }
        return takeChoice(1, --i);
    }

    public void bill(Customer customer){
        System.out.println("The cashier has succcessfully billed " + customer + "!");
    }

    public int query(ControllableStaff staff) {
        System.out.println("Manage " + staff.getName() + "! Your choices are:");
        for (int i = 1; i <= staff.getChoices().length; i++) {
            System.out.println(i + ": " + staff.getChoices()[i - 1]);
        }
        return takeChoice(1, staff.getChoices().length);
    }

    public int takeChoice(int min, int max) {
        if (max < min) return -1;
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
                if (choice < min || choice > max) {
                    System.out.println("Invalid choice! Try again");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Your choice must be a number! Try again!");
            }
        }
        return choice;
    }
}
