package clemens.game.bowling;

public class BowlingFrame {
    private int thrownBalls;
    private int balls[];
    private BowlingFrame previousFrame;
    private BowlingFrame nextFrame;
    private int score;
    private int scoreUntilLastFrame;
    /** Index of the frame (1 to 10). */
    private int index;

    public BowlingFrame() {
        balls = new int[3];
        for (int i = 0; i < 3; i++) {
            balls[i] = -1;
        }
    }
    
    public boolean playBall(int pins) {
        if (thrownBalls < 2 && !isStrike() && !isSpare()) {
            if (pins >= 0 && pins <= 10) {
                if (pins + score() <= 10) {
                    balls[thrownBalls++] = pins;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isStrike() {
        return ((thrownBalls == 1) && (balls[0] == 10));
    }

    public boolean isSpare() {
        return ((thrownBalls == 2) && (balls[0] + balls[1] == 10));
    }

    public boolean isOpen() {
        if (thrownBalls == 0) {
            return true;
        }
        if (isSpare()) {
            return nextBalls(1);
        } else if (isStrike()) {
            return nextBalls(2);
        } else if (thrownBalls < 2) {
            return true;
        }
        return false;
    }

    public boolean finished() {
        if (index < 10) {
            return (isStrike() || thrownBalls == 2);
        } else {
            if (isStrike() || isSpare()) {
                return (thrownBalls == 3);
            } else {
                return (thrownBalls == 2);
            }
        }
    }

    public int score() {
        score = 0;
        for (int i: balls) {
            if (i >= 0) {
                score += i;
            }
        }
        return score;
    }

    /** Find if the is so many balls played after this frame.
     */
    public boolean nextBalls(int needBalls) {
        if (nextFrame != null) {
            if (nextFrame.ballsThrown() >= needBalls) {
                return true;
            }
            return nextFrame.nextBalls(needBalls - nextFrame.ballsThrown());
        }
        return false;
    }

    public int ballsThrown() {
        return thrownBalls;
    }
}
