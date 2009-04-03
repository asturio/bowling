package clemens.game.bowling;

public class Bowling {
    public static void main(String args[]) {
        BowlingManager manager = new BowlingManager();
        manager.addPlayer(new BowlingRandomPlayer("Ted"));
        manager.addPlayer(new BowlingConsolePlayer());
        manager.playGame();
        manager.showFinalScore();
    }
}
