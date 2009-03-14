package clemens.game.bowling.test;
import clemens.game.bowling.BowlingFrame;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Test class for BownlingFrame.
 */
public class BowlingFrameTest {
    /** The BowlingFrame used in all tests. */
    BowlingFrame frame;

    /** My fixture.
     */
    @Before public void setUp() {
        frame = new BowlingFrame();
    }

    /** Test if play work. */
    @Test public void testBowlingFramePlay() {
        assertTrue(frame.score() == 0);
        assertTrue(frame.isOpen());
        assertTrue(frame.playBall(3));
        assertTrue(frame.score() == 3);
        assertFalse(frame.finished());
        assertTrue(frame.isOpen());
        assertTrue(frame.playBall(5));
        assertTrue(frame.score() == 8);
        assertTrue(frame.finished());
        assertFalse(frame.isOpen());
        assertFalse(frame.isStrike());
        assertFalse(frame.isSpare());
        assertTrue(frame.ballsThrown() == 2);
    }

    /** Test if strike code works. */
    @Test public void testBowlingFrameStrike() {
        frame.playBall(4);
        assertFalse(frame.isStrike());
        frame.playBall(6);
        assertFalse(frame.isStrike());
        frame = new BowlingFrame();
        frame.playBall(10);
        assertTrue(frame.isStrike());
        assertTrue(frame.finished());
        assertFalse(frame.playBall(0));
    }

    /** Test if spare code works. */
    @Test public void testBowlingFrameSpare() {
        frame.playBall(10);
        assertFalse(frame.isSpare());
        frame = new BowlingFrame();
        frame.playBall(3);
        assertFalse(frame.finished());
        frame.playBall(7);
        assertTrue(frame.isSpare());
        assertTrue(frame.finished());
        assertFalse(frame.playBall(0));
    }

    /** Test if pins played are legal. */
    @Test public void testBowlingFrameWrongNumber() {
        assertFalse(frame.playBall(11));
        assertFalse(frame.playBall(-1));
        assertTrue(frame.ballsThrown() == 0);
        assertTrue(frame.playBall(3));
        assertFalse(frame.playBall(8));
        assertTrue(frame.ballsThrown() == 1);
    }

    /** Test if newNextFrame works properly. */
    @Test public void testNextFrame() {
        BowlingFrame next;
        next = frame.newNextFrame();
        assertTrue(next == null);
        frame.playBall(3);
        next = frame.newNextFrame();
        assertTrue(next == null);
        frame.playBall(3);
        next = frame.newNextFrame();
        assertFalse(next == null);
    }

    /** Compatibility method for JUnit3.
     * @return a Test.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(BowlingFrameTest.class);
    }

    /** The main method.
     * @param args won't be used.
     */
    public static void main(final String args[]) {
        org.junit.runner.JUnitCore.main(
            "clemens.game.bowling.test.BowlingFrameTest");
    }
}

    
