
import java.awt.Color;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import javax.imageio.ImageIO;

public class ProjectileSprite {

    Image img;
    int x, y, height, width, xspeed, yspeed;
    boolean show;
    boolean friendly;
    CurrentSprites sprites;
    int ID;

    /**
     * Projectile class. Makes a bullet that is either friendly or not friendly
     * @param nx - new x position of bullet 
     * @param ny - new y position of bullet
     * @param nxspeed - new x speed of bullet 
     * @param nyspeed - new y speed of bullet 
     * @param user - Is the firing plane the user or not
     * @param curr - List of current sprites for bullet to check contact
     * @param ShooterID - ID of who shot the bullet
     */
    ProjectileSprite(int nx, int ny, int nxspeed, int nyspeed, boolean user,
            CurrentSprites curr, int ShooterID) {
        x = nx;
        y = ny;
        friendly = user;
        xspeed = nxspeed;
        yspeed = nyspeed;
        try {
            if (friendly) {
                img = ImageIO.read(getClass().getResource("Resources/bullet.png"));
            }else{
                img = ImageIO.read(getClass().getResource("Resources/bigBullet.png"));
            }
            width = img.getWidth(null);
            height = img.getHeight(null);
        } catch (Exception e) {
            System.out.println("No bullet resources are found");
        }
        sprites = curr;
        ID = ShooterID;
    }
    /**
     * Update the bullet, moving it based on its speed. Checks collision with 
     * its enemies to see if its hit 
     * @return false if the bullet has made contact and needs to be despawned 
     */
    boolean update() {
        if (y > -10 && y < GameExe.geth()) {
            x += xspeed;
            y -= yspeed;
        } else {
            return false;
        }

        if (friendly) {
            Enemy[] a = sprites.getEnemies();
            int len = sprites.getEnemyLen();
            for (int i = 0; i < len; i++) {
                if (a[i].collision(x, y, width, height)) {
                    a[i].explode = true;
                    sprites.userPlanes[ID - 1].PlusScore();
                    return false;
                }
            }
        } else {
            UserPlane[] u = sprites.getUserPlanes();
            int len = sprites.getUserLen();
            for (int i = 0; i < len; i++) {
                if (u[i].collision(x, y, width, height)) {
                    u[i].reducehp();
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Draw Bullet based on its x and y position
     * @param obs - image observer 
     */
    public void draw(ImageObserver obs) {
        GameExe.g2.drawImage(img, x, y, obs);
    }
    public void drawMap(ImageObserver obs) {
    }
}
