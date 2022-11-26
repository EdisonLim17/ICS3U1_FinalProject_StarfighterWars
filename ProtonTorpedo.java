import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ProtonTorpedo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ProtonTorpedo extends Projectile
{
    //declaring variables for the proton torpedo's image
    public static final int TORP_WIDTH = GameWorld.WORLD_WIDTH / 50;
    public static final int TORP_HEIGHT = GameWorld.WORLD_HEIGHT / 50;
    public static final Color whitePink = new Color(255, 140, 172);
    public static final Color blazingRed = new Color(240, 77, 146);
    
    //initializing constants
    public static final int TORP_SPEED = 10;
    public static final int EXPLOSION_RANGE = 200;
    
    //declaring instance variables
    private boolean real;
    
    public ProtonTorpedo(){
        //setting the image for the proton torpedo
        image = drawProtonTorpedo(TORP_WIDTH, TORP_HEIGHT);
        setImage(image);
        //setting variables for the proton torpedo
        targetClass = Enemy.class;
        speed = TORP_SPEED;
        super.dmg = Player.TORPEDO_DMG;
        explosive = true;
        explosionRange = EXPLOSION_RANGE;
    }
    
    public void act()
    {
        super.act();
    }
    
    //public method to draw the proton torpedo with the given parameters
    public static GreenfootImage drawProtonTorpedo(int width, int height){
        GreenfootImage torp = new GreenfootImage(width + 1, height + 1); //creating the blank GreenfootImage used for the torpedo
        
        //drawing the torpedo
        int[] triX = {0, width - height / 2, width - height / 2};
        int[] triY = {height / 2, 0, height};
        torp.setColor(whitePink);
        torp.fillPolygon(triX, triY, 3);
        torp.setColor(blazingRed);
        torp.drawPolygon(triX, triY, 3);
        torp.setColor(Color.WHITE);
        torp.fillOval(width - height, 0, height, height);
        torp.setColor(whitePink);
        torp.drawOval(width - height, 0, height, height);
        
        return torp;
    }
}
