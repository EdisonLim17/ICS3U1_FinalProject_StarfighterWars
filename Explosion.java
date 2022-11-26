import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Explosion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Explosion extends Actor
{
    //declaring variables for the explosion's image
    private GreenfootImage image;
    public static final int START_RED = 251;
    public static final int START_GREEN = 244;
    public static final int START_BLUE = 4;
    public static final int START_TRANS = 255;
    public static final int END_RED = 234;
    public static final int END_GREEN = 101;
    public static final int END_BLUE = 21;
    public static final int END_TRANS = 0;
    
    //declaring instance variables
    //size
    private int maxRadius;
    private int radius = 1;
    //color
    private int currRed = START_RED;
    private int currGreen = START_GREEN;
    private int currBlue = START_BLUE;
    private int currTrans = START_TRANS;
    private Color color;
    
    public Explosion(int size){
        maxRadius = size;
        image = drawExplosion();
        setImage(image);
    }
    
    public void act() 
    {
        //setting the image for the explosion
        image = drawExplosion();
        setImage(image);
        //increase the size of the explosion image and remove it from the world when it reaches its max size
        radius += 3;
        if(radius > maxRadius){
            getWorld().removeObject(this);
        }
    }
    
    //method to draw the explosion
    public GreenfootImage drawExplosion(){
        GreenfootImage explosion = new GreenfootImage(maxRadius * 2 + 1, maxRadius * 2 + 1); //creating the blank GreenfootImage used for the explosion
        
        //drawing the explosion
        color = new Color(currRed, currGreen, currBlue, currTrans);
        explosion.setColor(color);
        explosion.fillOval(maxRadius - radius, maxRadius - radius, radius * 2, radius * 2);
        
        //updating the red, green, blue, and tranparency values of the color to create a gradient animation
        currRed -= (START_RED - END_RED) / (maxRadius / 3);
        currGreen -= (START_GREEN - END_GREEN) / (maxRadius / 3);
        currBlue -= (START_BLUE - END_BLUE) / (maxRadius / 3);
        currTrans -= (START_TRANS - END_TRANS) / (maxRadius / 3);
        
        return explosion;
    }
}
