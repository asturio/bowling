package clemens.game.bowling;

/** An abstract class for BowlingPlayers.
 * Note: the player classes are not well designed. At least method for
 * displaying informations should be implemented on each subclass.
 */
public abstract class BowlingPlayer {
    /** The name of the player. */
    protected String name;
    /** The frame(s) of the player. */
    protected BowlingFrame frame;

    /** A constructor. */
    public BowlingPlayer() {
        frame = new BowlingFrame();
    }

    /** Setter: for the name.
     *  @param lname the name to set the player.
     */
    public void setName(String lname) {
        this.name = lname;
    }

    /** Getter: the name.
     * @return the name of the player.
     */
    public String getName() {
        return name;
    }

    /** This method will set the frame to be the next frame, if there is one.
     */
    public void setNextFrame() {
        if (frame.getIndex() < 10) {
            frame = frame.newNextFrame();
        }
    }

    /** This method shows the score on System.out. 
     * This method should be abstract and implemented in the subclasses. 
     */
    public void showScore() {
        System.out.println(name + " Total: " + frame.totalScore());
        
    };

    /** This method shows the score of the actual frame. 
     * This method should be abstract and implemented in the subclasses.
     */
    public void showFrameScore() {
        System.out.println(name + ": " + frame);
        
    }

    /** A method for expressing emotions :-).
     * This method should be abstract and implemented in the subclasses.
     */
    public void moan() {
        if (frame.isStrike() && frame.ballsThrown() == 1) {
            System.out.println("<" + name + "> Strike!!! Yeah!");
        } else if (frame.isSpare() && frame.ballsThrown() == 2) {
            System.out.println("<" + name + "> Spare!!! I'm feeling good!");
        } else if (frame.score() < 2) {
            System.out.println("<" + name + "> Damm!");
        }
    }

    /** Just a passtrough method for getting the score of the frame.
     *  @return the totalScore (till now).
     */
    public int getScore() {
        return frame.totalScore();
    }

    /** This method should be implemented on all subclasses.
     * This method is responsible for playing a complete frame.
     */
    abstract public void playFrame();
}
