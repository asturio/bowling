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

    public void playFrame();
}
