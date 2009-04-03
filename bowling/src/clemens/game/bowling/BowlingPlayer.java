package clemens.game.bowling;

/** XXX: comments.
*/
public abstract class BowlingPlayer {
    protected String name;
    protected BowlingFrame frame;

    public BowlingPlayer() {
        frame = new BowlingFrame();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNextFrame() {
        if (frame.getIndex() < 10) {
            frame = frame.newNextFrame();
        }
    }

    public void showScore() {
        System.out.println(name + " score Frame: " + frame.score() 
                           + " History: " + frame.totalScore() 
                           + " Final: " + frame.getFinalScore());
        
    };

    abstract public void playFrame();
}
