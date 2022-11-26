import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Enemy extends SuperSmoothMover
{
    //declaring variables for the enemy's image
    protected GreenfootImage image;
    public static final Color enemyGrey = new Color(169, 189, 205);
    public static final Color enemyLaserColor = Laser.neonGreen;
    
    //declaring instance variables
    protected Player player;
    protected int enemyScore, enemyHp, enemySpeed; //enemy stats
    protected int currShootCd = 0;
    //sound (from Mr. Cohen)
    protected GreenfootSound explosionSound = new GreenfootSound("TIE-Fighter Explosion.wav");
    protected GreenfootSound[] laserSounds;
    protected int laserSoundsIndex;
    
    public Enemy(){
        //creating laser sound array (from Mr. Cohen)
        laserSoundsIndex = 0;
        laserSounds = new GreenfootSound[10];
        for(int i = 0; i < laserSounds.length; i++){
            laserSounds[i] = new GreenfootSound("TIE-Fighter Laser.wav");
        }
    }
    
    public void addedToWorld(){
        //turn towards the player
        player = getWorld().getObjects(Player.class).get(0); //(from Mr. Cohen)
        turnTowards(player.getX(), player.getY());
    }
    
    public void act() 
    {
        player = getWorld().getObjects(Player.class).get(0); //(from Mr. Cohen)
        if(isAtEdge()){ //turn towards the player if it flew into the edge of the world
            turnTowards(player.getX(), player.getY());
        }
        move(enemySpeed);
    }
    
    //public method to deal damage to the enemy
    public void dealDmg(int dmg){
        enemyHp -= dmg;
    }
}
