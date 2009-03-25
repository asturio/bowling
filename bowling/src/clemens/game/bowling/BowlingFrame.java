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
        lastScore = -1;
    }
    
    /** Plays a ball in this frame.
     * @param pins the number of fallen pins.
     * @return true, if the ball is legal, false else.
     */
    public boolean playBall(final int pins) {
        int maxBalls = 2;
        if (isStrike() || isSpare()) {
            maxBalls = 0; // No balls more to play, but...
            if (index == 10) {
                maxBalls = 3;
            }
        } else {
            if (score() + pins > 10) { // Normal throws can't score more than 10
                maxBalls = 0;
            }
        }
       
        if (thrownBalls < maxBalls && pins >= 0 && pins <= 10) {
            balls[thrownBalls++] = pins;
            return true;
        }
        return false;
    }

    /** Check if this is a strike frame.
     * @return true if strike, false else.
     */
    public boolean isStrike() {
        return (balls[0] == 10);
    }

    /** Check if this is a spare frame.
     * @return true if spare, false else.
     */
    public boolean isSpare() {
        return (balls[0] + balls[1] == 10);
    }

    /** Checks if this frame is finished, or if there more balls to play.
     * @return true, if we can't play any ball, false, if there is more balls
     * to play.
     */
    public boolean finished() {
        boolean ret = (thrownBalls == 2);
        if (index < 10) {
            ret = isStrike() || ret;
        } else {
            if (isStrike() || isSpare()) {
                ret = thrownBalls == 3;
            }
        }
        return ret;
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
            ret = (thrownBalls != 3) && (!nextBalls(1));
        } else if (isStrike()) {
            ret = (thrownBalls != 3) && (!nextBalls(2));
        }
        return ret;
    }

    /** Find if there is so many balls played after this frame.
     * @param needBalls the number of balls we need.
     * @return true, if there is at least needBalls played.
     * XXX Test fehlt
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

    /** See if there are at least num balls played (this frame and nexts).
     * XXX Test fehlt
     */
    public boolean hasBalls(final int num) {
        return (num <= thrownBalls) || nextBalls(num - thrownBalls);
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

    /** gets the score of num balls (in this frame and consequent frames. No test XXX */
    public int countBallsScore(final int num) {
        int localScore = 0;
        int ballsCounted = 0;
        if (hasBalls(num)) {
            for (int i = 0; ballsCounted < num && i < thrownBalls; i++) {
                localScore += balls[i];
                ballsCounted++;
            }
            if (ballsCounted < num && nextFrame != null) {
                localScore += nextFrame.countBallsScore(num - ballsCounted);
            }
            return localScore;
        }
        return -1;
    }

    /** Calculates the final score of THIS frame. 
     * This will sum the balls thrown in this frame and any balls needed to complete this score.
     * @return the final score of this frame or -1 if still not possible.
     */
    public int getFinalScore() {
        if (finalScore >= 0) {
            return finalScore;
        }
        if (!isOpen()) {
            if (isStrike() || isSpare()) {
                finalScore = countBallsScore(3);
            } else {
                finalScore = score();
            }
        }
        return finalScore;
    }

    /** Gets and sets the lastScore if this frame.
     * This is only possible, the the previous frame is closed.
     * @return the lastScore, or  -1 if not possible.
     */
    public int getLastScore() {
        if (lastScore >= 0) {
            return lastScore;
        }
        if (previousFrame == null) {
            lastScore = 0;
        } else if (!previousFrame.isOpen()) {
            lastScore = previousFrame.totalScore();
        }
        return lastScore;
    }

    /** Return the score of the frame, summing lastScore and the finalScore.
     * @return the totalScore of until this frame. -1 if not possible.
     */
    public int totalScore() {
        if (!isOpen()) {
            return getFinalScore() + getLastScore();
        }
        return -1;
    }

    /** Sums the score off all previous frames and delete them if this is possible.
     * Sums the score og the last frames and delete them, as they aren't needed any more.
     * lastCore of this frame will be set.
     * @return the number of closed frames.
     */
    public int cleanFrames() {
        // close a frame if this Vddis possible. (Set finalScore and lastScore)
        int cleanedFrames = 0;

        if (previousFrame != null) {
            cleanedFrames = previousFrame.cleanFrames();
            if (!previousFrame.isOpen()) {
                getLastScore();
                previousFrame = null;
                cleanedFrames++;
            }
        }
        return cleanedFrames;
    }

    /** Gets the index of this frame. 
     * @return the index. */
    public int getIndex() {
        return index;
    }


}
