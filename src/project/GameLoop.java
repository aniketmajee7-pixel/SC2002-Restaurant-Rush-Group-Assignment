package project;

public class GameLoop {
    public static void main(String[] args) {
        final int TURNCOUNT = 18;
        LogicController l = new LogicController();

        for (int i = 0; i < TURNCOUNT; i++) l.updateTurn();

        l.endGame();
    }
}
