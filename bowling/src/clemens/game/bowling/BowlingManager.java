package clemens.game.bowling;

import java.util.ArrayList;

/* Some notes.
 * - 10 Pins
 * - Throw 
 * - 10 Frames
 * - 2 Throws per frame
 * - All pins = strike. Frame ends.
 * - All pins down on 2. throw = spare.
 * - strike score = last frame score + 10 + pins down of next 2 balls (next
 *      frame(s))
 * - spare score = last frame score + 10 + pins down of next 1 ball. (next
 *      frame)
 * - else = last frame + pins down.
 * - strike on 10. frame: 2 more balls to complete the score
 * - spare on 10. frame: 1 more balls to complete the score
 */

/** This class will manage a bowling game.
 * The manager will give all the players a chance to
 * play a frame.
 * Note: This Class makes some output to System.out, and this is not very nice.
 * Like an Observable, this class should call all players an let them get the
 * score off all other players, and display this information in their own way.
 * This is just the initial implementation. 
 */
public class BowlingManager {
    /** A list of players. */
    ArrayList < BowlingPlayer > playerList;

    /** Standard constructor. */
    public BowlingManager() {
        playerList = new ArrayList < BowlingPlayer >();
    }

    /** Adds a player to the list. 
     *  @param player The player to add to the list.
     */
    public void addPlayer(BowlingPlayer player) {
        playerList.add(player);
    }

    /** Play the game.
     *  @see BowlingFrame for details.
     */
    public void playGame() {
        for (int frame = 1; frame <= 10; frame++) {
            // Just informational.
            System.out.println("BM: Frame " + frame); 
            for (BowlingPlayer player : playerList) {
                player.playFrame();
                player.showFrameScore();
                player.setNextFrame();
            }
        }
    }

    /** Shows the final score of all players. */
    public void showFinalScore() {
        for (BowlingPlayer player : playerList) {
            player.showScore();
        }
        showWinner();
    }

    /** Tryies to get the winner.
     *  Note: FIXME This method won't detect a tie.
     */
    public void showWinner() {
        int max = 0;
        BowlingPlayer winner = null;
        for (BowlingPlayer player : playerList) {
            if (max < player.getScore()) {
                winner = player;
                max = winner.getScore();
            }
        }
        System.out.println("The winner is " + winner.getName());
    }
}

