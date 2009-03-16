package clemens.game.bowling;

/** The BowlingFrame class.
 * This class saves the state of a game an count the points played.
 */
public class BowlingFrame {
    /** The number of played balls. */
    private int thrownBalls;
    /** The result of the balls. */
    private int balls[];
    /** The reference to the previous frame. */
    private BowlingFrame previousFrame;
    /** The reference to the next frame. */
    private BowlingFrame nextFrame;
    /** The score of the played balls in this frame. */
    private int score;
    /** The score of the played balls and any later balls needed for strikes or
     * spares. */
    private int finalScore;
    /** The score of all previous frames together. */
    private int lastScore;
    /** Index of the frame (1 to 10). */
    private int index;

    /** Creates a new BowlingFrame. */
    public BowlingFrame() {
        balls = new int[3];
        for (int i = 0; i < 3; i++) {
            balls[i] = -1;
        }
        index = 1;
        finalScore = -1;
    }
    
    /** Plays a ball in this frame.
     * @param pins the number of fallen pins.
     * @return true, if the ball is legal, false else.
     */
    public boolean playBall(final int pins) {
        if (thrownBalls < 2 && !isStrike() && !isSpare() 
                && pins >= 0 && pins <= 10) {
            if (pins + score() <= 10) {
                balls[thrownBalls++] = pins;
                return true;
            }
        }
        return false;
    }

    /** Check if this is a strike frame.
     * @return true if strike, false else.
     */
    public boolean isStrike() {
        return (thrownBalls == 1) && (balls[0] == 10);
    }

    /** Check if this is a spare frame.
     * @return true if spare, false else.
     */
    public boolean isSpare() {
        return (thrownBalls == 2) && (balls[0] + balls[1] == 10);
    }

    /** This method checks, if the final score of this frame can be counted.
     * Normaly a score of a strike and a spare depends on the balls played in
     * the next frame(s).
     * @return true, if the score can be counted, false else.
     */
    public boolean isOpen() {
        boolean ret = false;
        if (!finished()) {
            ret = true;
        } else if (isSpare()) {
            // The frame is finished. 
            ret = (thrownBalls == 3) || (nextBalls(1));
        } else if (isStrike()) {
            ret = (thrownBalls == 3) || (nextBalls(2));
        }
        return ret;
    }

    /** Find if the is so many balls played after this frame.
     * @param needBalls the number of balls we need.
     * @return true, if there is at least needBalls played.
     */
    public boolean nextBalls(final int needBalls) {
        boolean ret = false;
        if (nextFrame != null) {
            if (nextFrame.ballsThrown() >= needBalls) {
                return true;
            }
            ret = nextFrame.nextBalls(needBalls - nextFrame.ballsThrown());
        }
        return ret;
    }

    /** Checks if this frame is finished, or if there more balls to play.
     * @return true, if we can't play any ball, false, if there is more balls
     * to play.
     */
    public boolean finished() {
        boolean ret = false;
        if (index < 10) {
            ret = (isStrike()) || (thrownBalls == 2);
        } else {
            if (isStrike() || isSpare()) {
                ret = thrownBalls == 3;
            } else {
                ret = thrownBalls == 2;
            }
        }
        return ret;
    }

    /** Count the score of this frame.
     * @return the score.
     */
    public int score() {
        score = 0;
        for (int i : balls) {
            if (i >= 0) {
                score += i;
            }
        }
        return score;
    }

    /** Create the next frame for this frame.
     * @return the next frame, or null on error.
     */
    public BowlingFrame newNextFrame() {
        if (index < 10 && this.finished()) {
            final BowlingFrame next = new BowlingFrame();
            next.index = this.index + 1;
            this.nextFrame = next;
            next.previousFrame = this;
            return next;
        }
        return null;
    }

    /** Gets the number of balls played.
     * @return the number of played balls.
     */
    public int ballsThrown() {
        return thrownBalls;
    }

    private int getNextBalls(int count) {
        int nextBalls = -1;
        int frameBalls = 0;
        int nFrame;
        if (nextFrame != null && nextFrame.finished()) {
            for (int i = 0; i < nextFrame.ballsThrown(); ++i) {
                frameBalls += nextFrame.balls[i];
                --count;
            }
        }
        if (count == 0) {
            nextBalls = frameBalls;
        } else {
            nFrame = nextFrame.getNextBalls(count);
            if (nFrame == -1) {
                nextBalls = -1;
            } else {
                nextBalls = frameBalls + nFrame;
            }
        }
        return nextBalls;
    }

    public int getFinalScore() {
        if (!isOpen()) {
            if (isStrike()) {
                finalScore = score() + getNextBalls(2);
            } else if (isSpare()) {
                finalScore = score() + getNextBalls(1);
            } else {
                finalScore = score();
            }
        }
        return finalScore;
    }

    private BowlingFrame getOldestFrame() {
        BowlingFrame frame;
        frame = this;
        while (frame.previousFrame != null) {
            frame = frame.previousFrame;
        }
        return frame;
    }

    private boolean closeFrame() {
        // close a frame if this is possible. (Set finalScore)
    }

    private static int cleanFramesEmbed(BowlingFrame frame) {
        // Close all frames...
    }

    public int cleanFrames() {
        BowlingFrame frame = getOldestFrame();
        return cleanFramesEmbed(frame);
    }

    public int getIndex() {
        return index;
    }
}
