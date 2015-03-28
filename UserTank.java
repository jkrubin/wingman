
import java.awt.Color;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.math.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jrubin
 */
public class UserTank extends CharSprite {

    boolean boom;
    HealthBar hp;
    CurrentSprites sprites;
    final int RECOIL = 10;
    int refire;
    int left, right, up, down, fire;
    int score;
    int angle;
    double dx, dy;
    double rex, rey;
    Image defImg;
    int bubble;

    UserTank(String ID, Image img, int x, int y, int speed, HealthBar hp, CurrentSprites curr,
            int ukey, int dkey, int lkey, int rkey, int fkey) {
        super(img, speed);
        this.dx = x;
        this.dy = y;
        rex = x;
        rey = y;
        width = 62;
        height = 62;
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
        angle = 0;
        defImg = img;
        bubble=0;
    }

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
     * Updates user plane every frame, checks controls and moves plane
     * accordingly
     */
    public void update() {
        if(bubble>0){
            bubble--;
            if(bubble==0){
                invincible=false;
            }
        }
        if (explode == true) {
            explode();
        } else {
            if (refire > 0) {
                refire--;
            }
            if (GameEvents.keyMatch(left)) {
                angle += 1;
                angle = angle % 59;
            }
            if (GameEvents.keyMatch(right)) {
                angle -= 1;
                if (angle < 0 || angle == 0) {
                    angle = 59;
                }
            }
            if (GameEvents.keyMatch(up)) {
                double newy = dy - (speed * Math.sin((double) angle * Math.PI / 30));
                double newx = dx + (speed * Math.cos((double) angle * Math.PI / 30));

                Wall[] walls = sprites.getWalls();
                int len = sprites.getWallLen();
                boolean xcoll = false;
                boolean ycoll = false;
                for (int i = 0; i < len; i++) {
                    if (walls[i].safeColl((int) dx, (int) newy, width, height)) {
                        ycoll = true;
                    }
                    if (walls[i].safeColl((int) newx, (int) dy, width, height)) {
                        xcoll = true;
                    }
                }

                if (newy > 30 && newy < GameExe.geth() - height - 30 && !ycoll) {
                    dy -= (speed * Math.sin((double) angle * Math.PI / 30));
                }
                if (newx > 30 && newx < GameExe.getw() - height - 33 && !xcoll) {
                    dx += (speed * Math.cos((double) angle * Math.PI / 30));
                }


            }
            if (GameEvents.keyMatch(down)) {
                double newy = dy + (speed * Math.sin((double) angle * Math.PI / 30));
                double newx = dx - (speed * Math.cos((double) angle * Math.PI / 30));

                Wall[] walls = sprites.getWalls();
                int len = sprites.getWallLen();
                boolean xcoll = false;
                boolean ycoll = false;

                for (int i = 0; i < len; i++) {
                    if (walls[i].safeColl((int) dx, (int) newy, width, height)) {
                        ycoll = true;
                    }
                    if (walls[i].safeColl((int) newx, (int) dy, width, height)) {
                        xcoll = true;
                    }
                }
                if (newy > 30 && newy < GameExe.geth() - height - 30 && !ycoll) {
                    dy += (speed * Math.sin((double) angle * Math.PI / 30));
                }
                if (newx > 30 && newx < GameExe.getw() - height - 33 && !xcoll) {
                    dx -= (speed * Math.cos((double) angle * Math.PI / 30));
                }

            }
            if (GameEvents.keyMatch(fire)) {
                if (refire == 0) {
                    double speedx = (13 * Math.cos((double) angle * Math.PI / 30));
                    double speedy = (13 * Math.sin((double) angle * Math.PI / 30));

                    sprites.addBullet(new TankBullet((int) dx + 20 + (2 * (int) speedx),
                            (int) dy + 18 - (2 * (int) speedy), speedx, speedy, true,
                            sprites, Integer.parseInt(planeID), angle));
                    refire = RECOIL;
                }
            }
        }
        x = (int) dx;
        y = (int) dy;
    }

    /**
     * Reduces HP of the plane when hit if dead, plane calls itself to explode
     */
    public void reducehp() {
        if (!hp.reduceHealth()) {
            this.explode();
        }
    }

    /**
     * add to the score of this plane
     */
    void PlusScore() {
        hp.addScore(50);
    }

    public void reset() {
        dx = rex;
        dy = rey;
        img = defImg;
        hp.reset();
        invincible=true;
        bubble=100;
    }

    int getX() {
        return (int) dx + 32;
    }

    int getY() {
        return (int) dy + 32;
    }

    public void draw(ImageObserver obs) {
        if (explode) {
            GameExe.g2.drawImage(img, x, y, obs);
        } else {
            GameExe.g2.drawImage(img, (int) dx, (int) dy, (int) dx + 64, (int) dy + 64, angle * 64, 0, (angle + 1) * 64, 64, obs);
        }
    }

    public void drawMap(ImageObserver obs) {

        if (planeID.equals("1")) {
            GameExe.gMap.setColor(Color.GREEN);
        } else if (planeID.equals("2")) {
            GameExe.gMap.setColor(Color.yellow);
        }
        if (explode) {
            GameExe.gMap.fillRect(x, y, width, height);
        } else {
            GameExe.gMap.fillRect(x, y, width, height);
        }
    }
}
