
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Observable;
import java.util.Observer;
import javax.sound.sampled.*;

/**
 * Superclass for all Character/Moving sprites, AI and Player controlled
 */
public class CharSprite implements Observer {

    Image img;
    int x, y, height, width, speed;
    Random gen;
    boolean show;
    Rectangle bbox;
    int explosionTimer;
    boolean explode;
    String planeID;
    boolean invincible;
    AudioInputStream stream;
    AudioFormat format;
    DataLine.Info info;
    Clip clip;
    AudioClip c;

    /**
     * Initializes the common variables for all char sprites
     *
     * @param img - image for plane
     * @param speed - speed of plane
     */
    CharSprite(Image img, int speed) {
        this.img = img;
        this.speed = speed;
        explosionTimer = 34;
        explode = false;
    }

    /**
     * Checks the collision of the current object by making a rectangle out of
     * its current coordinates, then check against the given coordinates
     *
     * @param x - x most position of sprite to check against
     * @param y - y most position of sprite to check against
     * @param w - width of sprite to check against
     * @param h - height of sprite to check against
     * @return returns true if there is a collision, false otherwise
     */
    public boolean collision(int x, int y, int w, int h) {
        if (Math.abs(x - this.x) < this.width + w + 60 && Math.abs(y - this.y) < this.height + h + 60) {
            bbox = new Rectangle(this.x, this.y, this.width, this.height);
            Rectangle otherBBox = new Rectangle(x, y, w, h);
            if (this.bbox.intersects(otherBBox) && !explode && !invincible) {
                try {
                    sound();
                } catch (Exception e) {
                    System.out.println("Audio not found");
                }
                return true;
            }
            return false;
        }
        return false;
    }
    
    public void explosionSound() {
        System.out.println("Its happening");
        try {
            stream = AudioSystem.getAudioInputStream(getClass().getResource("Resources/snd_explosion1.wav"));
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Audio not found");
        }
    }
    void sound(){
        try{
        AudioClip c = Applet.newAudioClip(getClass().getResource("Resources/snd_explosion1.wav"));
        c.play();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean safeColl(int x, int y, int w, int h) {
        if (Math.abs(x - this.x) < this.width + w + 60 && Math.abs(y - this.y) < this.height + h + 60) {
            bbox = new Rectangle(this.x, this.y, this.width, this.height);
            Rectangle otherBBox = new Rectangle(x, y, w, h);
            if (this.bbox.intersects(otherBBox) && !explode) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Draws Character object at obs
     *
     * @param obs - Image Observer to draw to.
     */
    public void draw(ImageObserver obs) {
        GameExe.g2.drawImage(img, x, y, obs);
    }

    /**
     * Skeleton for update
     *
     * @param obj - Observable object for subclasses which are observers
     * @param arg - argument of notification
     */
    public void update(Observable obj, Object arg) {
    }

    /**
     * Skeleton for enemy reset
     */
    public void reset() {
    }

    /**
     * Sets the plane to explode by changing the sprite and explosion timer.
     * Relies on update calling it until it is through.
     */
    public void explode() {
        String file = "def";
        try {
            //Figure out who exploded

            if (planeID.equals("1") || planeID.equals("2")) {
                file = "Resources/explosion2_" + (7 - (explosionTimer / 5)) + ".png";
            } else {
                file = "Resources/explosion1_" + (7 - (explosionTimer / 5)) + ".png";
            }
            img = ImageIO.read(getClass().getResource(file));
        } catch (Exception e) {
            System.out.println(file + " not found");
        }
        explosionTimer--;
        explode = true;
        //Appropriate explosion response...
        if (explosionTimer == 0) {
            if (planeID.equals("1") || planeID.equals("2")) {
                this.reset();
                explode = false;
                explosionTimer = 34;
            } else {
                this.reset();
                explode = false;
                explosionTimer = 34;
            }
        }
    }
}
