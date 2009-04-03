package clemens.game.bowling;

import java.io.Console;

/** XXX: comments.
*/
public class BowlingConsolePlayer extends BowlingPlayer {
    Console cons;

    public BowlingConsolePlayer() {
        cons = System.console();
        if (cons != null) {
            name = cons.readLine("Please enter your name: ");
        }
    }

    public void playFrame() {
        while (!frame.finished()) {
            int pins = Integer.parseInt(
                            cons.readLine("How many pins were knocked down? "));
            if (!frame.playBall(pins)) {
                System.err.println("This number of pins are not possible: " + pins);
            }

            if (frame.isStrike()) {
                System.out.println("<" + name + "> Strike!!! Yeah!");
            } else if (frame.isSpare()) {
                System.out.println("<" + name + "> Spare!!! I'm feeling good!");
            }
        }
    }
}
