
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;
import java.lang.Math;

/**
 *
 * @author Jrubin
 */
public class Enemy extends CharSprite {

    CurrentSprites sprites;
    GameEvents gEvents;
    int imgUpdate;
    final int RECOIL = 50;
    int reload;
    int AI;
    int reTime;
    /**
     * Instantiates Enemy plane that shoot opposing bullets at user plane, these
     * objects reset when destroyed and start all over
     * @param EID - ID of enemy to signature on fired bullets
     * @param img - Image used for enemy plane 
     * @param speed - speed of the plane, refresh every frame
     * @param gen - RNG for plane to decide when and where to spawn
     * @param curr - Current Sprites for enenmy plane to look through to find target
     * @param gameEvents - Game events that notify planes when they are hit
     * @param AIType - Determines the AI of the plane
     */
    Enemy(String EID, Image img, int speed, Random gen, CurrentSprites curr,
            GameEvents gameEvents, int AIType) {
        super(img, speed);
        gEvents = gameEvents;
        sprites = curr;
        this.gen = gen;
        this.show = true;
        width = img.getWidth(null);
        height = img.getHeight(null);
        imgUpdate = 1;
        reload = RECOIL;
        planeID = EID;
        AI = AIType;
        this.reset();
    }
    /**
     * Updates the plane every frame
     */
    public void update() {
        if (explode) {
            explode();
        } else if(reTime>0) {
            reTime--;
        } else{
            if (AI == 1) {
                ai1();
            } else if (AI == 4) {
                ai4();
            }
            //Spin propellers 
            imgUpdate++;
            try {
                String file = "Resources/enemy" + AI + "_" + ((imgUpdate % 3) + 1) + ".png";
                img = ImageIO.read(getClass().getResource(file));
            } catch (Exception e) {
                System.out.println("No resources are found dawg");
            }

            UserPlane[] users = sprites.getUserPlanes();
            int usernum = sprites.getUserLen();

            //Check Collisions with all planes
            for (int i = 0; i < usernum; i++) {
                if (users[i].collision(this.x, this.y, this.width, this.height)) {
                    users[i].reducehp();
                    explode = true;
                }
            }

            //If plane goes OB, reset
            if (y > 480 || y < -30 || x > 680 || x < -30) {
                this.reset();
            }
        }
    }
    /**
     * The first AI for plane, will track user plane, move slowly forward,
     * and fire periodically forward
     */
    public void ai1() {
        y += speed;
        int closePlane = 0;
        //Find Target
        if (sprites.userPlaneLen == 2) {
            if (Math.abs(sprites.userPlanes[1].x - this.x) < Math.abs(sprites.userPlanes[0].x - this.x)) {
                closePlane = 1;
            }
        }
        //Move to target
        if (this.x < (sprites.userPlanes[closePlane].x - 2)) {
            this.x += 2;
        } else if (this.x > (sprites.userPlanes[closePlane].x + 2)) {
            this.x -= 2;
        }

        //Fire
        if (reload > 0) {
            reload--;
        }
        if (reload == 0) {
            sprites.addBullet(new ProjectileSprite(x, y, 0, -8, false, sprites,
                    Integer.parseInt(planeID)));
            reload = RECOIL;
        }
    }
    /**
     * Second type of enemy, will move diagonally up from the bottom right.
     * Shoots bullets diagonally down back at plane 
     */
    public void ai4() {
        y -= (speed+1);
        x -= (speed+1);

        if (reload > 0) {
            reload--;
        }
        if (reload == 0) {
            sprites.addBullet(new ProjectileSprite(x, y, -8, -8, false, sprites,
                    Integer.parseInt(planeID)));
            reload = RECOIL;
        }
    }
    /**
     * Resets Enemy, depending on its AI tag, putting it in the right respawn location. 
     * If its AI4, it wil have a delayed respawn based on RNG
     */
    public void reset() {
        explode=false;
        if (AI == 1) {
            this.x = Math.abs(gen.nextInt() % (600 - 30));
            this.y = -10;
            reTime=0;
        }
        if(AI==4){
            this.x = 635;
            this.y = 475;
            reTime=200+Math.abs(gen.nextInt() %300);
        }
    }
    /**
     * Draws enemy plane based on its x,y coordinates 
     * @param obs - Image observer that will take the image.
     */
    public void draw(ImageObserver obs) {
        if (show) {
            WingmanExe.g2.drawImage(img, x, y, obs);
        }
    }
}
