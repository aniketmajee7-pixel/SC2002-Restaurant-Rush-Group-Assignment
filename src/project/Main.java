package project;

public class Main {
    public static void main(String[] args){
        GameLoop g = new GameLoop();
        try{
            g.loop();
        }
        catch (Exception e) {
            System.out.println("Whoopsie, seems there's been a crash.");
            throw new RuntimeException(e);
        }
    }
}
