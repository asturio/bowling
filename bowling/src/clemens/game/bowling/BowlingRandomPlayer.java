package clemens.game.bowling;

import java.util.Random;

/** This is a BowlingPlayer implementation, playing random balls. */
public class BowlingRandomPlayer extends BowlingPlayer {
    /** Use the random class. */
    Random rand;

    /** A constructor setting a default name. */
    public BowlingRandomPlayer() {
        this("BowlingRandomPlayer");
    }

    /** A constructor setting a given name.
     *  @param name the name of the random player.
     */
    public BowlingRandomPlayer(String name) {
        super();
        setName(name);
        rand = new Random();
    }

    /** This method plays a random frame. */
    public void playFrame() {
        while (!frame.finished()) {
            frame.playBall(rand.nextInt(11));
            moan();
        }
    }
}
