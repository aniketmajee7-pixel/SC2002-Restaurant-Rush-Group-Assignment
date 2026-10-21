package project;

public class GameLoop {
    public static void main(String[] args) {
        final int TURNCOUNT = 18;
        LogicController l = new LogicController();
        while (l.wantsToLive()){
            l.updateTurn();
        }
        l.endGame();
    }
}
