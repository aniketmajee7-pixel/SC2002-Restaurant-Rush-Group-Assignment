package project;

import java.util.Scanner;

public class SystemUI {
    Scanner sc;

    public SystemUI() {
        sc = new Scanner(System.in);
    }

    public void welcome(){
        System.out.println("Welcome to RestaurantRush!");
    }

    public void announce(){
        System.out.println("There is a new customer waiting in the queue!");
    }

    public int query(Staff staff, String[] choices) {
        System.out.println("Manage " + staff.getName() + "! Your choices are:");
        for (int i = 1; i <= choices.length; i++) {
            System.out.println(i + ": " + choices[i-1]);
        }
        return takeChoice(1, choices.length);
    }

    public int takeChoice(int min, int max){
        int choice;
        while (true) {
            try { //game keeps crashing if I enter 'a', I think this will fix that
                choice = Integer.parseInt(sc.nextLine().trim());
                if (choice < min || choice > max) {
                    System.out.println("Invalid choice! Try again");
                    continue;
                }
                break;
            }
            catch (NumberFormatException e){
                System.out.println("Your choice must be a number! Try again!");
            }
        }
        return choice;
    }
}
