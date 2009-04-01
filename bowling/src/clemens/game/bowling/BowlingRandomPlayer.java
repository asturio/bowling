package clemens.game.bowling;

public class BowlingRandomPlayer extends BowlingPlayer() {

    public BowlingRandomPlayer() {
        this("BowlingRandomPlayer");
    }

    public BowlingRandomPlayer(String name) {
        super();
        setName(name);
    }

    public void playFrame() {
        while (!frame.finished()) {
            frame.playBall("RANDOM from 0 to 10");
        }
    }

    public void showScore() {
        // show my total score;
    }

    public setNextFrame() {
        frame = frame.newNextFrame();
    }
}
