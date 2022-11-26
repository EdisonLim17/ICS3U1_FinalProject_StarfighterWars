import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameMenu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MainMenu extends World
{
    //declaring variables for the background
    private GreenfootImage background;
    public static final Color titleColor = new Color(245, 236, 61);
    public static Font titleFont = new Font("Courier New", true, false, GameWorld.WORLD_HEIGHT / 10);
    private String title = "STARFIGHTER WARS";
    public static final Color scoreColor = new Color(245, 236, 61);
    public static Font scoreFont = new Font("Courier New", false, false, GameWorld.WORLD_HEIGHT / 22);
    private String highScoreDisplay;
    
    //declaring variable to store user information
    private UserInfo user;
    
    //declaring actors
    private Button startButton, instructionsButton, settingsButton, exitButton;
    
    //declaring instance variables
    //private static GreenfootSound menuMusic = new GreenfootSound("Star Wars Main Theme.mp3");
    private GreenfootSound clickSound = new GreenfootSound("Menu Click.wav");
    private GreenfootSound entranceSound = new GreenfootSound("X-Wing Flyby.wav");
    private MouseInfo mouse;
    
    public MainMenu()
    {    
        // Create a new world with WORLD_WIDTH * WORLD_HEIGHT cells with a cell size of 1x1 pixels and set its background
        super(GameWorld.WORLD_WIDTH, GameWorld.WORLD_HEIGHT, 1);
        background = new GreenfootImage(GameWorld.drawBackground(GameWorld.WORLD_WIDTH, GameWorld.WORLD_HEIGHT, Color.WHITE));
        background.setColor(titleColor);
        background.setFont(titleFont);
        background.drawString(title, (getWidth() - (int)(title.length() * titleFont.getSize() * 0.58)) / 2, getHeight() / 7);
        if(UserInfo.isStorageAvailable()){ //update highscore (from Greenfoot UserInfo API)
            user = UserInfo.getMyInfo();
            if(GameWorld.getScore() > user.getScore()){
                user.setScore(GameWorld.getScore());
                user.store();
            }
        }
        highScoreDisplay = "HIGHSCORE: " + user.getScore();
        background.setColor(scoreColor);
        background.setFont(scoreFont);
        background.drawString(highScoreDisplay, (getWidth() - (int)(highScoreDisplay.length() * scoreFont.getSize() * 0.58)) / 2, getHeight() * 2 / 7);
        setBackground(background);
        
        //adding the buttons
        startButton = new Button("Start Game");
        addObject(startButton, getWidth() / 2, getHeight() * 3 / 7);
        instructionsButton = new Button("How To Play");
        addObject(instructionsButton, getWidth() / 2, getHeight() * 4 / 7);
        settingsButton = new Button("Settings");
        addObject(settingsButton, getWidth() / 2, getHeight() * 5 / 7);
        exitButton = new Button("Exit");
        addObject(exitButton, getWidth() / 2, getHeight() * 6 / 7);
    }
    
    public void started(){
        //playing menu music
        //menuMusic.playLoop();
    }
    
    public void act(){
        //checking if the user clicked any of the buttons and take them to the respective world
        mouse = Greenfoot.getMouseInfo();
        if(Greenfoot.mouseClicked(startButton)){
            //menuMusic.stop();
            clickSound.play();
            entranceSound.play();
            Greenfoot.setWorld(new GameWorld());
        }
        else if(Greenfoot.mouseClicked(instructionsButton)){
            clickSound.play();
            Greenfoot.setWorld(new InstructionsMenu());
        }
        else if(Greenfoot.mouseClicked(settingsButton)){
            clickSound.play();
            Greenfoot.setWorld(new SettingsMenu());
        }
        else if(Greenfoot.mouseClicked(exitButton)){
            //menuMusic.stop();
            clickSound.play();
            Greenfoot.stop();
        }
    }
    
    public void stopped(){
        //menuMusic.pause();
    }
    
    //public method to start playing the music
    public static void playMusic(){
        //menuMusic.playLoop();
    }
    
    //public method to pause the music
    public static void pauseMusic(){
        //menuMusic.pause();
    }
}
