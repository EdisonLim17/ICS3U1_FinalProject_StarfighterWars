import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class UpgradeDisplay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UpgradeDisplay extends Actor
{
    //declaring variables for the upgrade display's image
    private GreenfootImage image;
    public static final int UPGD_DISPLAY_WIDTH = GameWorld.WORLD_WIDTH / 5;
    public static final int UPGD_DISPLAY_HEIGHT = GameWorld.WORLD_HEIGHT / 12;
    public static final Color upgdColor = MainMenu.titleColor;
    public static final Font upgdFont = MainMenu.scoreFont;
    
    //declaring instance variables
    private int numUpgds;
    private String upgds;;
    
    public UpgradeDisplay(int numUpgrs){
        //setting the number of upgrades available
        this.numUpgds = numUpgrs;
        upgds = "UPGRADES: " + numUpgds;
        //creating and setting the image for the upgrade display
        image = new GreenfootImage(UPGD_DISPLAY_WIDTH + 1, UPGD_DISPLAY_HEIGHT + 1);
        drawUpgradeDisplay();
        setImage(image);
    }
    
    //method to update the number of upgrades available
    public void update(int numUpgrs){
        this.numUpgds = numUpgrs;
        upgds = "UPGRADES: " + numUpgrs;
        drawUpgradeDisplay();
        setImage(image);
    }
    
    //method to draw the upgrade display
    private void drawUpgradeDisplay(){
        //drawing the display
        image.clear();
        image.setColor(upgdColor);
        image.setFont(upgdFont);
        if(numUpgds > 0){
            image.drawString(upgds, (image.getWidth() - (int)(upgds.length() * upgdFont.getSize() * 0.58)) / 2, (image.getHeight() + upgdFont.getSize() / 2) / 2);
        }
        else{ //clears the image if there are no upgrades
            image.clear();
        }
    }
    
    //getter methods
    public int getWidth(){
        return UPGD_DISPLAY_WIDTH;
    }
    
    public int getHeight(){
        return UPGD_DISPLAY_HEIGHT;
    }
}
