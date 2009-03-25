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
    private BowlingFrame frame;

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

    private void playNormal(int count) {
        for (int i = 0; i < count; i++) {
            frame.playBall(3);
            frame.playBall(4);
            if (i < count - 1) {
                frame = frame.newNextFrame();
            }
        }
    }

    private void playStrikes(int count) {
        for (int i = 0; i < count; i++) {
            frame.playBall(10);
            if (i < count - 1) {
                frame = frame.newNextFrame();
            }
        }
    }

    private void playSpares(int count) {
        for (int i = 0; i < count; i++) {
            frame.playBall(6);
            frame.playBall(4);
            if (i < count - 1) {
                frame = frame.newNextFrame();
            }
        }
    }

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
