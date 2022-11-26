import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Laser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Laser extends Projectile
{
    //declaring variables for the laser's image
    public static final int LASER_WIDTH = GameWorld.WORLD_WIDTH / 15;
    public static final int LASER_HEIGHT = GameWorld.WORLD_HEIGHT / 400;
    public static final Color neonPink = new Color(255, 106, 177);
    public static final Color neonGreen = new Color(105, 254, 84);
    
    //initializing constants
    public static final int LASER_SPEED = 15;
    
    public Laser(Color color){
        //setting the image for the laser
        image = drawLaser(LASER_WIDTH, LASER_HEIGHT, color);
        setImage(image);
        //setting variables for the laser
        targetClass = Enemy.class;
        speed = LASER_SPEED;
        super.dmg = Player.PLAYER_LASER_DMG;
        explosive = false;
    }
    
    public Laser(int dmg, Color color){
        //setting the image for the laser
        image = drawLaser(LASER_WIDTH, LASER_HEIGHT, color);
        setImage(image);
        //setting variables for the laser
        targetClass = Player.class;
        speed = LASER_SPEED;
        super.dmg = dmg;
        explosive = false;
    }
    
    public void act()
    {
        super.act();
    }
    
    //method to draw the laser with the given parameters
    private static GreenfootImage drawLaser(int width, int height, Color color){
        GreenfootImage laser = new GreenfootImage(width + 1, height + 1); //creating the blank GreenfootImage used for the laser
        
        //drawing the laser
        laser.setColor(color);
        laser.fillOval(0, 0, height, height);
        laser.fillOval(width - height, 0, height, height);
        laser.fillRect(height, 0, width - height, height);
        
        return laser;
    }
}
