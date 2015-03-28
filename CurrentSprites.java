
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class CurrentSprites {
    int minix;
    int miniy;

    Enemy[] enemyPlanes = new Enemy[10];
    int enemyPlaneLen = 0;
    ProjectileSprite[] bullets = new ProjectileSprite[100];
    int bulletLen = 0;
    UserPlane[] userPlanes = new UserPlane[2];
    int userPlaneLen = 0;
    WingmanIsland[] islands = new WingmanIsland[150];
    int islandLen = 0;
    //Tank Sprites
    UserTank[] tanks = new UserTank[2];
    int tankLen = 2;
    Wall[] walls = new Wall[100];
    int wallLen = 0;
    GameExe drawDest;

    /**
     * Initializes current sprites
     *
     * @param game - the game that the sprites Belong to
     */
    CurrentSprites(GameExe game) {
        enemyPlaneLen = 0;
        bulletLen = 0;
        userPlaneLen = 0;
        islandLen = 0;
        tankLen = 0;
        drawDest = game;
        wallLen = 0;
                
    }

    /**
     * returns list of current enemies
     *
     * @return - array of enemies
     */
    public Enemy[] getEnemies() {
        return enemyPlanes;
    }

    /**
     * Gets the length of the enemy array
     *
     * @return -int representing current length of array
     */
    public int getEnemyLen() {
        return enemyPlaneLen;
    }

    /**
     * Returns a list of current User Planes
     *
     * @return - array of planes
     */
    public UserPlane[] getUserPlanes() {
        return userPlanes;
    }

    public Wall[] getWalls() {
        return walls;
    }

    /**
     * Gets the length of the user plane array int representing current length
     * of array
     *
     * @return
     */
    public int getUserLen() {
        return userPlaneLen;
    }

    public UserTank[] getTanks() {
        return tanks;
    }

    public int getTanksLen() {
        return tankLen;
    }

    public int getWallLen() {
        return wallLen;
    }

    /**
     * Adds an enemy to the current enemy array
     *
     * @param newEnemy - new enemy to add to array
     */
    void addEnemy(Enemy newEnemy) {
        enemyPlanes[enemyPlaneLen] = newEnemy;
        enemyPlaneLen++;
    }

    /**
     * Add a bullet to the current bullet array
     *
     * @param newBullet - new bullet to be added
     */
    void addBullet(ProjectileSprite newBullet) {
        bullets[bulletLen] = newBullet;
        bulletLen++;
    }

    /**
     * add user plane to the user plane array
     *
     * @param NewUserPlane - plane to be added
     */
    void addUserPlane(UserPlane NewUserPlane) {
        userPlanes[userPlaneLen] = NewUserPlane;
        userPlaneLen++;
    }

    /**
     * add island to island array
     *
     * @param newIsland - island to be added
     */
    void addIsland(WingmanIsland newIsland) {
        islands[islandLen] = newIsland;
        islandLen++;
    }

    void addTank(UserTank newTank) {
        tanks[tankLen] = newTank;
        tankLen++;
    }

    void addWall(Wall newWall) {
        walls[wallLen] = newWall;
        wallLen++;
    }

    void rmBullet(int i) {
        for (int j = i; j < (bulletLen - 1); j++) {
            bullets[j] = bullets[j + 1];
        }
        bulletLen--;
    }

    void rmWall(int i) {
        for (int j = i; j < (wallLen - 1); j++) {
            walls[j] = walls[j + 1];
        }
        wallLen--;
    }

    /**
     * Updates all current sprites
     */
    void update() {
        //Update enemies 
        for (int i = 0; i < enemyPlaneLen; i++) {
            enemyPlanes[i].update();
        }
        //Update Islands
        for (int i = 0; i < islandLen; i++) {
            islands[i].update();
        }
        //Update bullets. if a bullet returns false, remove it
        for (int i = 0; i < bulletLen; i++) {
            if (!bullets[i].update()) {
                rmBullet(i);
            }
        }
        //Update user planes
        for (int i = 0; i < userPlaneLen; i++) {
            userPlanes[i].update();
        }
    }

    /**
     * Draws all current Sprites
     */
    void draw() {
        //Draw enemy planes 
        for (int i = 0; i < enemyPlaneLen; i++) {
            enemyPlanes[i].draw(drawDest);
        }
        //draw islands 
        for (int i = 0; i < islandLen; i++) {
            islands[i].draw(drawDest);
        }
        //draw user planes 
        for (int i = 0; i < userPlaneLen; i++) {
            userPlanes[i].draw(drawDest);
            userPlanes[i].hp.draw(drawDest);
        }
        //draw bullets 
        for (int i = 0; i < bulletLen; i++) {
            bullets[i].draw(drawDest);
        }
        //if the game is over draw the last image on screen
        if (WingmanExe.isGameOver) {
            int tx, ty;
            Image endImg = null;
            try {
                endImg = ImageIO.read(getClass().getResource("Resources/gameOver.png"));
            } catch (Exception e) {
                System.out.print("No resources are found");
            }
            tx = ((drawDest.w / 2) - (endImg.getWidth(drawDest)) / 2);
            ty = ((drawDest.h / 2) - (endImg.getHeight(drawDest)) / 2);
            drawDest.g2.drawImage(endImg, tx, ty, drawDest);
        }
    }

    void updateTank() {

        //Update user Tanks
        for (int i = 0; i < tankLen; i++) {
            tanks[i].update();
        }

        //Update bullets. if a bullet returns false, remove it
        for (int i = 0; i < bulletLen; i++) {
            if (!bullets[i].update()) {
                rmBullet(i);
            }
        }
        for (int i = 0; i < wallLen; i++) {
            walls[i].update();
        }
    }

    void drawTank() {
        GameExe.gMap.setColor(Color.decode("#7f602e"));
        GameExe.gMap.fillRect(0, 0, GameExe.getw(), GameExe.geth());
        //draw user Tanks 
        for (int i = 0; i < tankLen; i++) {
            tanks[i].draw(drawDest);
            tanks[i].hp.drawUI(drawDest);
            tanks[i].drawMap(drawDest);
        }
        //draw bullets 
        for (int i = 0; i < bulletLen; i++) {
            bullets[i].draw(drawDest);
            bullets[i].drawMap(drawDest);
            
        }

        for (int i = 0; i < wallLen; i++) {
            walls[i].draw(drawDest);
            walls[i].drawMap();
        }

        for (int i = 0; i < islandLen; i++) {
            islands[i].draw(drawDest);
            islands[i].drawMap();
        }
    }

}