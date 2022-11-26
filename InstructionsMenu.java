import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class InstructionsMenu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InstructionsMenu extends World
{
    //declaring variables for the background
    private GreenfootImage background;
    public static final Color titleColor = MainMenu.titleColor;
    public static final Font titleFont = MainMenu.titleFont;
    private String title = "How To Play";
    public static final Color instructionsColor = MainMenu.scoreColor;
    public static final Font instructionsFont = MainMenu.scoreFont;
    private String goal = "GOAL: Achieve The Highest Score";
    private String enemySpawn = "More Enemies Spawn As The Game Goes On";
    private String upgrades = "However You Get Upgrades By Destroying Enemies";
    private String ending = "GAME OVER CONDITION: 0 HP / Collide With An Enemy";
    private String controls = "(Controls Are In Settings)";
    private String kbChange1 = "Click The Button Then Press The New Key Or Vice Versa To Change";
    private String kbChange2 = "Keybinds (Only Saves 64 Keyboard Keys)";
    
    //declaring actors
    private Button backButton;
    
    //declaring instance variables
    private GreenfootSound clickSound = new GreenfootSound("Menu Click.wav");
    
    public InstructionsMenu()
    {    
        //Create a new world with the specified sizes and set its background
        super(GameWorld.WORLD_WIDTH, GameWorld.WORLD_HEIGHT, 1);
        background = new GreenfootImage(GameWorld.drawBackground(GameWorld.WORLD_WIDTH, GameWorld.WORLD_HEIGHT, Color.WHITE));
        background.setColor(titleColor);
        background.setFont(titleFont);
        background.drawString(title, (getWidth() - (int)(title.length() * titleFont.getSize() * 0.58)) / 2, getHeight() / 7);
        background.setColor(instructionsColor);
        background.setFont(instructionsFont);
        background.drawString(goal, (getWidth() - (int)(goal.length() * instructionsFont.getSize() * 0.58)) / 2, getHeight() * 5 / 18);
        background.drawString(enemySpawn, (getWidth() - (int)(enemySpawn.length() * instructionsFont.getSize() * 0.58)) / 2, getHeight() * 7 / 18);
        background.drawString(upgrades, (getWidth() - (int)(upgrades.length() * instructionsFont.getSize() * 0.58)) / 2, getHeight() * 9 / 18);
        background.drawString(ending, (getWidth() - (int)(ending.length() * instructionsFont.getSize() * 0.58)) / 2, getHeight() * 11 / 18);
        background.drawString(controls, (getWidth() - (int)(controls.length() * instructionsFont.getSize() * 0.58)) / 2, getHeight() * 13 / 18);
        background.drawString(kbChange1, (getWidth() - (int)(kbChange1.length() * instructionsFont.getSize() * 0.58)) / 2, getHeight() * 15 / 18);
        background.drawString(kbChange2, (getWidth() - (int)(kbChange2.length() * instructionsFont.getSize() * 0.58)) / 2, getHeight() * 17 / 18);
        setBackground(background);
        
        //add button
        backButton = new Button("Back");
        addObject(backButton, getWidth() / 10, getHeight() * 13 / 14);
    }
    
    public void started(){
        MainMenu.playMusic();
    }
    
    public void act(){
        if(Greenfoot.mouseClicked(backButton)){ //takes the user back to the main menu
            clickSound.play();
            Greenfoot.setWorld(new MainMenu());
        }
    }
    
    public void stopped(){
        MainMenu.pauseMusic();
    }
}
