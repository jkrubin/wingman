
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.Observable;

/**
 * User controlled plane class. Extends CharSprite
 *
 * @author Jrubin
 */
public class UserPlane extends CharSprite {

    boolean boom;
    HealthBar hp;
    CurrentSprites sprites;
    final int RECOIL = 10;
    int refire;
    int left, right, up, down, fire;
    int score;
/**
 * Creates a user plane that the keyboard controls. Class extends Char sprite and
 * is sibling to Enemy. Each plane has their own ID, image, healthBar, and controls
 * @param ID - give ID 1 for P1, 2 for P2, and 3 for an AI controlled plane ID
 * @param img - the image file of the plane. Will be drawn every frames
 * @param x - starting x position of the plane
 * @param y - starting y position of the plane
 * @param speed - Speed at which the plane will moves; how many px per refresh
 * @param hp - Starting HP for the plane
 * @param curr - Class which holds all current sprites in the game. needed to instantiate bullets
 * @param ukey - key code to move plane up
 * @param dkey - key code to move plane down
 * @param lkey - key code to move plane left
 * @param rkey - key code to move plane right 
 * @param fkey - key to fire new bullet
 */
    UserPlane(String ID, Image img, int x, int y, int speed, HealthBar hp, CurrentSprites curr,
            int ukey, int dkey, int lkey, int rkey, int fkey) {
        super(img, speed);
        this.x = x;
        this.y = y;
        width = img.getWidth(null);
        height = img.getHeight(null);
        boom = false;
        this.hp = hp;
        sprites = curr;
        refire = RECOIL;
        left = lkey;
        right = rkey;
        up = ukey;
        down = dkey;
        fire = fkey;
        planeID = ID;
        score = 0;


    }
    /**
     * Updates plane whenever there is a key event 
     * @param obj - The observable object that sent the notification
     * @param arg - key message sent to update()
     */
    public void update(Observable obj, Object arg) {
        GameEvents ge = (GameEvents) arg;

        if (ge.type == 2) {
            String msg = (String) ge.event;
            if (msg.equals(planeID)) {
                this.reducehp();

            }
        }
    }
    /**
     * Updates user plane every frame, checks controls and moves plane accordingly
     */
    public void update() {

        if (explode == true) {
            explode();
        } else {
            if (refire > 0) {
                refire--;
            }
            if (GameEvents.keyMatch(left)) {
                x -= speed;
            }
            if (GameEvents.keyMatch(right)) {
                x += speed;
            }
            if (GameEvents.keyMatch(up)) {
                y -= speed;
            }
            if (GameEvents.keyMatch(down)) {
                y += speed;
            }
            if (GameEvents.keyMatch(fire)) {
                if (refire == 0) {
                    sprites.addBullet(new ProjectileSprite(x + 17, y, 0, 10, true,
                            sprites, Integer.parseInt(planeID)));
                    refire = RECOIL;
                }
            }
        }
    }
    /**
     * Reduces HP of the plane when hit if dead, plane calls itself to explode
     */
    public void reducehp() {
        if (!hp.reduceHealth()) {
            explode = true;
        }
    }
    /**
     * add to the score of this plane
     */
    void PlusScore() {
        hp.addScore(50);
    }
    
    public void reset(){
        GameExe.GameOver();
    }
}
