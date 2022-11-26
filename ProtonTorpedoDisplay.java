import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ProtonTorpedoDisplay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ProtonTorpedoDisplay extends Actor
{
    //declaring variables for the proton torpedo display's image
    private GreenfootImage image;
    public static final int TORP_DISPLAY_WIDTH = GameWorld.WORLD_HEIGHT / 15;
    public static final int TORP_DISPLAY_HEIGHT = GameWorld.WORLD_WIDTH / 5;
    
    //declaring instance variables
    private int torpsLeft;
    
    public ProtonTorpedoDisplay(int numTorps){
        //setting the number of torpedoes left
        torpsLeft = numTorps;
        //creating and setting image for the display
        image = new GreenfootImage(TORP_DISPLAY_WIDTH + 1, TORP_DISPLAY_HEIGHT + 1);
        drawTorpedoDisplay();
        setImage(image);
        setRotation(270);
    }
    
    //method to update the number of torpedoes left
    public void update(int numTorps){
        torpsLeft = numTorps;
        drawTorpedoDisplay();
        setImage(image);
    }
    
    //method to draw the proton torpedo display
    private void drawTorpedoDisplay(){
        //drawing the display
        image.clear();
        for(int i = 0; i < torpsLeft; i++){
            GreenfootImage torpedo = ProtonTorpedo.drawProtonTorpedo(ProtonTorpedo.TORP_WIDTH, ProtonTorpedo.TORP_HEIGHT);
            image.drawImage(torpedo, TORP_DISPLAY_WIDTH / 2, TORP_DISPLAY_HEIGHT / 10 * i);
        }
    }
    
    //getter methods
    public int getWidth(){
        return TORP_DISPLAY_WIDTH;
    }
    
    public int getHeight(){
        return TORP_DISPLAY_HEIGHT;
    }
}
