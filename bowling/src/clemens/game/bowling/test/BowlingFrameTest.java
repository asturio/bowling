package clemens.game.bowling.test;

import org.junit.*;
import static org.junit.Assert.*;

import clemens.game.bowling.*;

public class BowlingFrameTest {
    BowlingFrame frame;

    /** My fixture.
     */
    @Before public void setUp() {
        frame = new BowlingFrame();
    }

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

    @Test public void testBowlingFrameWrongNumber() {
        assertFalse(frame.playBall(11));
        assertFalse(frame.playBall(-1));
        assertTrue(frame.ballsThrown() == 0);
        assertTrue(frame.playBall(3));
        assertFalse(frame.playBall(8));
        assertTrue(frame.ballsThrown() == 1);
    }

    // Compatible to junit3
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(BowlingFrameTest.class);
    }

    public static void main(String args[]) {
        org.junit.runner.JUnitCore.main("clemens.game.bowling.test.BowlingFrameTest");
    }
}

    
