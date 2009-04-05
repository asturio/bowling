package clemens.game.bowling.test;

import clemens.game.bowling.BowlingFrame;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Test class for BownlingFrame.
 */
public class BowlingFrameTest {
    /** The BowlingFrame used in all tests. */
    private BowlingFrame frame;

    /** My fixture.
     */
    @Before public void setUp() {
        frame = new BowlingFrame();
    }

    /** Test if play work. */
    @Test public void testBowlingFramePlay() {
        assertTrue("Bad score.", frame.score() == 0);
        assertTrue("Frame is NOT open.", frame.isOpen());
        assertTrue("Can't play 3.", frame.playBall(3));
        assertTrue("Invalid score.", frame.score() == 3);
        assertFalse("Finished too early.", frame.finished());
        assertTrue("Frame already closed.", frame.isOpen());
        assertTrue("Can't play 5.", frame.playBall(5));
        assertTrue("Score not correct.", frame.score() == 8);
        assertTrue(frame.finished());
        assertFalse(frame.isOpen());
        assertFalse(frame.isStrike());
        assertFalse(frame.isSpare());
        assertTrue(frame.ballsThrown() == 2);
        assertEquals("toString not working", 
                     "Frame no. 1 [3, 5] "
                     + "(s: 8, final: 8, last: 0, total: 8)", 
                     frame.toString()); 
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

    /** Tests if cleanFrames() work. */
    @Test public void testCleanFrames() {
        /* 3 + 3 = 6 */
        frame.playBall(3);
        frame.playBall(3);
        assertTrue(frame.score() == 6);
        assertTrue(frame.totalScore() == 6);

        /* Strike (+10 +7) = 27 */
        BowlingFrame next = frame.newNextFrame();
        assertTrue(next != null);
        frame = next;
        frame.playBall(10);
        assertTrue(frame.cleanFrames() == 1);
        assertTrue(frame.score() == 10);
        assertTrue(frame.getFinalScore() == -1);
        assertTrue(frame.getLastScore() == 6);
        assertTrue(frame.totalScore() == -1);

        /* Strike (+7 +3) = 20 */
        next = frame.newNextFrame();
        assertTrue(next != null);
        frame = next;
        frame.playBall(10);
        assertTrue(frame.ballsThrown() == 1);
        assertTrue(frame.cleanFrames() == 0);
        assertTrue(frame.score() == 10);
        assertTrue(frame.getFinalScore() == -1);
        assertTrue(frame.getLastScore() == -1);
        assertTrue(frame.totalScore() == -1);
        
        /* Sparse (+4)*/
        next = frame.newNextFrame();
        assertTrue(next != null);
        frame = next;
        assertTrue(frame.getIndex() == 4);
        frame.playBall(7);
        frame.playBall(3);
        assertTrue(frame.cleanFrames() == 2);
        assertTrue(frame.score() == 10);
        assertTrue(frame.getFinalScore() == -1);
        assertTrue(frame.getLastScore() == 53);
        assertTrue(frame.totalScore() == -1);

        /* 4 + 5 */
        next = frame.newNextFrame();
        assertTrue(next != null);
        frame = next;
        frame.playBall(4);
        frame.playBall(5);
        assertTrue(frame.cleanFrames() == 1);
        assertTrue(frame.score() == 9);
        assertTrue(frame.getFinalScore() == 9);
        assertTrue(frame.getLastScore() == 67);
        assertTrue(frame.totalScore() == 76);
    }

    /** Help method for playing no Strikes/Spares for count times.
     *  @param count how many frames are to be played.
     */
    private void playNormal(int count) {
        for (int i = 0; i < count; i++) {
            frame.playBall(3);
            frame.playBall(4);
            if (i < count - 1) {
                frame = frame.newNextFrame();
            }
        }
    }

    /** Help method for playing Strikes for count times.
     *  @param count how many frames are to be played.
     */
    private void playStrikes(int count) {
        for (int i = 0; i < count; i++) {
            frame.playBall(10);
            if (i < count - 1) {
                frame = frame.newNextFrame();
            }
        }
    }

    /** Help method for playing Spares for count times.
     *  @param count how many frames are to be played.
     */
    private void playSpares(int count) {
        for (int i = 0; i < count; i++) {
            frame.playBall(6);
            frame.playBall(4);
            if (i < count - 1) {
                frame = frame.newNextFrame();
            }
        }
    }

    /** Test the behaviour till the end of a game. 
     *  This test tests using only normal frames. */
    @Test public void testEndGame() {
        playNormal(10);
        assertTrue(frame.cleanFrames() == 9);
        assertTrue(frame.getIndex() == 10);
        assertTrue(frame.score() == 7);
        assertTrue(frame.getFinalScore() == 7);
        assertTrue(frame.getLastScore() == 63);
        assertTrue(frame.totalScore() == 70);
        assertFalse(frame.newNextFrame() != null);
    }

    /** Test the behaviour till the end of a game. 
     *  This test tests using only strike frames. */
    @Test public void testEndGameStrike() {
        playStrikes(10);
        assertTrue(frame.playBall(10));
        assertTrue(frame.playBall(10));
        assertTrue(frame.finished());
        assertFalse(frame.isOpen());
        assertTrue(frame.cleanFrames() == 9);
        assertTrue(frame.getIndex() == 10);
        assertTrue(frame.score() == 30);
        assertTrue(frame.getFinalScore() == 30);
        assertTrue(frame.getLastScore() == 270);
        assertTrue(frame.totalScore() == 300);
        assertFalse(frame.newNextFrame() != null);
    }

    /** Test the behaviour till the end of a game. 
     *  This test tests using only spare frames. */
    @Test public void testEndGameSpare() {
        playSpares(10);
        assertTrue(frame.playBall(5));
        assertTrue(frame.finished());
        assertFalse(frame.isOpen());
        assertTrue(frame.cleanFrames() == 9);
        assertTrue(frame.getIndex() == 10);
        assertTrue(frame.score() == 15);
        assertTrue(frame.getFinalScore() == 15);
        assertTrue(frame.getLastScore() == 144);
        assertTrue(frame.totalScore() == 159);
        assertFalse(frame.newNextFrame() != null);
    }

    /** This test will check if the example given by conject works. */
    @Test public void testConjectFrames() {
        assertTrue("Bad ball 1-1", frame.playBall(1));
        assertTrue("Bad ball 1-2", frame.playBall(4));
        assertTrue("Next frame problem 2", 
                   (frame = frame.newNextFrame()) != null);
        assertTrue("Bad ball 2-1", frame.playBall(4));
        assertTrue("Bad ball 2-2", frame.playBall(5));
        assertTrue("Next frame problem 3", 
                   (frame = frame.newNextFrame()) != null);
        assertTrue("Bad ball 3-1", frame.playBall(6));
        assertTrue("Bad ball 3-2", frame.playBall(4));
        assertTrue("Next frame problem 4", 
                   (frame = frame.newNextFrame()) != null);
        assertTrue("Bad ball 4-1", frame.playBall(5));
        assertTrue("Bad ball 4-2", frame.playBall(5));
        assertTrue("Next frame problem 5", 
                   (frame = frame.newNextFrame()) != null);
        assertTrue("Bad ball 5", frame.playBall(10));
        assertTrue("Next frame problem 6", 
                   (frame = frame.newNextFrame()) != null);
        assertTrue("Bad ball 6-1", frame.playBall(0));
        assertTrue("Bad ball 6-2", frame.playBall(1));
        assertTrue("Next frame problem 7", 
                   (frame = frame.newNextFrame()) != null);
        assertTrue("Bad ball 7-1", frame.playBall(7));
        assertTrue("Bad ball 7-2", frame.playBall(3));
        assertTrue("Next frame problem 8", 
                   (frame = frame.newNextFrame()) != null);
        assertTrue("Bad ball 8-1", frame.playBall(6));
        assertTrue("Bad ball 8-2", frame.playBall(4));
        assertTrue("Next frame problem 9", 
                   (frame = frame.newNextFrame()) != null);
        assertTrue("Bad ball 9", frame.playBall(10));
        assertTrue("Next frame problem 10", 
                   (frame = frame.newNextFrame()) != null);
        assertTrue("Bad ball 10-1", frame.playBall(2));
        assertTrue("Bad ball 10-2", frame.playBall(8));
        assertTrue("Bad ball 10-3", frame.playBall(6));
        assertFalse("Still open?", frame.isOpen());
        assertTrue("Not finished", frame.finished());
        assertTrue("Wrong score", frame.totalScore() == 133);
    }

    /** Tests if nextBalls() work. */
    @Test public void testNextBalls() {
        assertTrue(frame.nextBalls(0));
        frame.playBall(2);
        frame.playBall(3);
        assertTrue(frame.nextBalls(0));
        BowlingFrame next = frame.newNextFrame();
        next.playBall(4);
        assertTrue(frame.nextBalls(1));
        assertFalse(frame.nextBalls(2));
        next.playBall(5);
        assertTrue(frame.nextBalls(2));
        assertFalse(frame.nextBalls(3));
    }

    /** Tests if hasBalls() work. */
    @Test public void testHasBalls() {
        assertFalse(frame.hasBalls(1));
        frame.playBall(2);
        frame.playBall(3);
        assertTrue(frame.hasBalls(2));
        BowlingFrame next = frame.newNextFrame();
        next.playBall(4);
        assertTrue(frame.hasBalls(3));
        assertFalse(frame.hasBalls(4));
        next.playBall(5);
        assertTrue(frame.hasBalls(4));
        assertFalse(frame.hasBalls(5));

        assertTrue(frame.countBallsScore(0) == 0);
        assertTrue(frame.countBallsScore(1) == 2);
        assertTrue(frame.countBallsScore(2) == 5);
        assertTrue(frame.countBallsScore(3) == 9);
        assertTrue(frame.countBallsScore(4) == 14);
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
