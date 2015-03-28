
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;

/**
 *
 * @author Jrubin
 */
public class HealthBar {

    Image img;
    int xpos, ypos;
    int health;
    boolean gameOver = false;
    int score;
    CharSprite plane;

    /**
     * Health bar class, given to plane to represent its health and score on
     * screen.
     *
     * @param user - ID of its plane
     * @param dest - destination to draw to the current graphics
     */
    HealthBar(int user, int sthp, int x, int y) {
        try {
            img = ImageIO.read(getClass().getResource("Resources/health.png"));
        } catch (Exception e) {
            System.out.print("No resources are found");
        }

//        if (user == 1) {
//            xpos = 50;
//        }else{
//            xpos = fdest.getw() - (150);
//        }
        xpos = x;
        ypos = y;
        health = 4;
        gameOver = false;
        score = 0;
    }

    /**
     * Reduce health of the bar
     *
     * @return - returns false if health reaches 0, true otherwise
     */
    public boolean reduceHealth() {
        health--;
        if (health < 1) {
            return false;
        }
        try {
            String file = "Resources/health" + (4 - health) + ".png";
            img = ImageIO.read(getClass().getResource(file));
        } catch (Exception e) {
            System.out.print("No resources are found");
        }
        return true;
    }

    /**
     * Add to current score to be displayed at next draw
     *
     * @param points -amount of points to add to score
     */
    public void addScore(int points) {
        score += points;

    }
    int getHealth(){
        return health;
    }

    Image getImg() {
        return img;
    }
    
    void reset(){
        try {
            img = ImageIO.read(getClass().getResource("Resources/health.png"));
        } catch (Exception e) {
            System.out.print("No resources are found");
        }  
        health=4;
    }

    /**
     * Draws health bar based on x,y location. Also draws current score of the
     * plane.
     *
     * @param obs - image observer to draw image
     */
    public void draw(ImageObserver obs) {
        GameExe.g2.drawImage(img, xpos, ypos, obs);
        GameExe.g2.setColor(Color.white);
        GameExe.g2.fillRoundRect(xpos, ypos - 35, 150, 25, 5, 5);
        GameExe.g2.setColor(Color.blue);
        GameExe.g2.setFont(new Font("Calibri", Font.BOLD, 20));
        GameExe.g2.drawString("Score: " + score, xpos, ypos - 18);
    }

    public void drawUI(ImageObserver obs) {
        GameExe.gUI.drawImage(img, xpos, ypos, obs);
        GameExe.gUI.setColor(Color.white);
        GameExe.gUI.fillRoundRect(xpos, ypos - 35, 150, 25, 5, 5);
        GameExe.gUI.setColor(Color.blue);
        GameExe.gUI.setFont(new Font("Calibri", Font.BOLD, 20));
        GameExe.gUI.drawString("Score: " + score, xpos, ypos - 18);
    }
}
