import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ScoreDisplay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScoreDisplay extends Actor
{
    //declaring variables for the score display's image
    private GreenfootImage image;
    public static final int SCORE_DISPLAY_WIDTH = GameWorld.WORLD_WIDTH / 4;
    public static final int SCORE_DISPLAY_HEIGHT = GameWorld.WORLD_HEIGHT / 11;
    public static final Color scoreColor = MainMenu.titleColor;
    public static final Font scoreFont = MainMenu.scoreFont;
    
    //declaring instance variables
    private int score;
    private String display;
    
    public ScoreDisplay(int score){
        //setting the player's score
        this.score = score;
        display = "SCORE: " + this.score;
        //creating and setting the image for the score display
        image =  new GreenfootImage(SCORE_DISPLAY_WIDTH + 1, SCORE_DISPLAY_HEIGHT + 1);
        drawScoreDisplay();
        setImage(image);
    }
    
    //method to update the player's score
    public void update(int score) 
    {
        //updating the score and the display
        this.score = score;
        display = "SCORE: " + this.score;
        drawScoreDisplay();
        setImage(image);
    }
    
    //method to draw the score display
    private void drawScoreDisplay(){
        //drawing the display
        image.clear();
        image.setColor(scoreColor);
        image.setFont(scoreFont);
        image.drawString(display, SCORE_DISPLAY_WIDTH / 20, (SCORE_DISPLAY_HEIGHT + scoreFont.getSize() / 2) / 2);
    }
}
