package clemens.game.bowling;

import java.io.Console;

/** This is a class for a player playing in the console.
 */
public class BowlingConsolePlayer extends BowlingPlayer {
    /** A console object. 
     * Note this works only with java &gt;= 6.
     */
    Console cons;

    /** A constructor. Sets the player name reading from the console. */
    public BowlingConsolePlayer() {
        cons = System.console();
        if (cons != null) {
            name = cons.readLine("Please enter your name: ");
        }
    }

    /** Plays a frame. The number of dropped pins will be asked. This method
     * won't prevent you from cheating, so make sure there is a robot inputing
     * the numbers. */
    public void playFrame() {
        while (!frame.finished()) {
            try {
                int pins = Integer.parseInt(
                                cons.readLine("How many pins were knocked down? "));
                if (!frame.playBall(pins)) {
                    System.err.println("This number of pins are not possible: " 
                                        + pins);
                }
            } catch (NumberFormatException e) {
                System.err.println("I mean the NUMBER of pins.");
            }
        }
        moan();
    }
}
