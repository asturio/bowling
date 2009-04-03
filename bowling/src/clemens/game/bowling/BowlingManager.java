package clemens.game.bowling;

import java.util.ArrayList;

/* 
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
/** XXX: omments.
*/
public class BowlingManager {
    ArrayList<BowlingPlayer> playerList;

    public BowlingManager() {
        playerList = new ArrayList<BowlingPlayer>();
    }

    public void addPlayer(BowlingPlayer player) {
        playerList.add(player);
    }

    public void playGame() {
        for (int frame = 1; frame <= 10; frame++) {
            System.out.println("Frame " + frame);
            for (BowlingPlayer player: playerList) {
                player.playFrame();
                player.setNextFrame();
            }
        }
    }

    public void showFinalScore() {
        for (BowlingPlayer player: playerList) {
            player.showScore();
        }
    }

    
}

