package clemens.game.bowling;

import java.util.Random;

public class BowlingRandomPlayer extends BowlingPlayer {
    Random rand;

    public BowlingRandomPlayer() {
        this("BowlingRandomPlayer");
    }

    public BowlingRandomPlayer(String name) {
        super();
        setName(name);
        rand = new Random();
    }

    public void playFrame() {
        while (!frame.finished()) {
            frame.playBall(rand.nextInt(11));
        }
    }
}
