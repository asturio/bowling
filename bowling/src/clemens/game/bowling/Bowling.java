package clemens.game.bowling;

/** A simple class for playing the bowling game.
 */
public class Bowling {
    /** The main method.
     *  @param args won't be processed.
     */
    public static void main(String args[]) {
        BowlingManager manager = new BowlingManager();
        manager.addPlayer(new BowlingConsolePlayer());
        manager.addPlayer(new BowlingRandomPlayer("Ted"));
        manager.playGame();
        manager.showFinalScore();
    }
}
