import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @Edison Lim (your name) 
 * @06/23/2021 (a version number or a date)
 * 
 * Instructions:
 *  - Goal of the game is to try to get the highest score by destroying enemy ships
 *  - The game ends when you have 0 HP or when you collide with an enemy
 *  - Click "WASD" to move around
 *  - Click or hold your mouse button to shoot lasers
 *  - Click "space" to launch a proton torpedo that does area of effect damage
 *  - Hold "shift" to boost in the direction you are facing
 *  - Click "1", "2", and "3" to upgrade boots, overheat, and proton torpedoes respectively
 *  - You can change your keybinds in the settings menu by clicking the keybind and click the new key you want to change it to
 *    or clicking the new key you want then click the keybind you want to change (only saves 64 keyboard keys)  
 *  - Have Fun!
 * 
 * Sound Credits:
 *  - "Star Wars Main Theme" from John Williams at https://open.spotify.com/search/star%20wars%20main%20title
 *  - "The Imperial March" from John Williams at https://open.spotify.com/search/the%20imperial%20march
 *  - "Menu Click" from Christopherderp at https://freesound.org/people/Christopherderp/sounds/333046/
 *  - "Menu Move" from Christopherderp at https://freesound.org/people/Christopherderp/sounds/333042/
 *  - "X-Wing Flyby" from https://www.soundboard.com/sb/starwarsfx
 *  - "X-Wing Laser" from https://www.soundboard.com/sb/starwarsfx
 *  - "X-Wing Proton Torpedo" from https://www.trekcore.com/audio/
 *  - "X-Wing Cooling" from Marregheriti at https://freesound.org/people/Marregheriti/sounds/266102/
 *  - "X-Wing Boost" from https://www.videvo.net/royalty-free-sound-effects/turbo/
 *  - "X-Wing Explosion" from https://www.soundboard.com/sb/starwarsfx
 *  - "New Upgrade" from EminYILDIRIM at https://freesound.org/people/EminYILDIRIM/sounds/566502/
 *  - "Upgrade Used" from EminYILDIRIM at https://freesound.org/people/EminYILDIRIM/sounds/570425/
 *  - "TIE-Fighter Laser" from https://www.soundboard.com/sb/starwarsfx
 *  - "TIE-Fighter Explosion" from https://www.soundboard.com/sb/starwarsfx
 *  
 * Code Credits:
 *  - StatBar Class from Mr. Cohen
 *  - SuperSmoothMover Class from Mr. Cohen
 *  - sound arrays from Mr. Cohen
 *  - drawBackground(int width, int height, Color starColor) method (GameWorld) from Mr. Cohen
 *  - highscore update code (MainMenu) from https://www.greenfoot.org/files/javadoc/
 *  - mouse over object detection code (Button) from Mr. Cohen
 *  - get player code (Enemy) from Mr. Cohen
 *  - get world code (AdvancedEnemy, NormalEnemy) from Mr. Cohen
 *  - boost code (Player) from Mr. Cohen
 *  - check key released code (Player) from danpost at https://www.greenfoot.org/topics/57378/0
 *  - check mouse down code (Player) from danpost at https://www.greenfoot.org/topics/8197/0
 *  - track class code (Projectile) from Mr. Cohen
 *  - collisionDetection() (Projectile) from Mr. Cohen
 *  - get enemies in range of explosion code (Projectile) from danpost on Greenfoot
 *  
 * Bugs:
 *  - one uncommon bug with the menu/background music's sound file giving an error, whether it is in mp3 or wav format (currently in wav format)
 */
public class GameWorld extends World
{
    //declaring variables for the background and its dimensions
    private GreenfootImage background;
    public static final int WORLD_WIDTH = 960;
    public static final int WORLD_HEIGHT = 540;
    
    //initializing constants
    public static final int START_NUM_POOL = 180;
    public static final int END_NUM_POOL = 90;
    public static final int POOL_DECR_DELAY = 60;
    public static final int ADV_CHANCE = 4; //lower = higher chance
    
    //declaring actors
    private Player player;
    private ScoreDisplay scoreDisplay;
    
    //declaring instance variables
    //private static GreenfootSound backgroundMusic;
    private int numPool = START_NUM_POOL;
    private int decrTimer = POOL_DECR_DELAY;
    private static int playerScore = 0;
    
    public GameWorld()
    {    
        //creating a new world with WORLD_WIDTH * WORLD_HEIGHT cells with a cell size of 1x1 pixels and set background
        super(WORLD_WIDTH, WORLD_HEIGHT, 1);
        background = new GreenfootImage(drawBackground(WORLD_WIDTH, WORLD_HEIGHT, Color.WHITE));
        setBackground(background);
        
        //setting playing the background music in a loop
        //backgroundMusic = new GreenfootSound("The Imperial March.mp3");
        //backgroundMusic.playLoop();
        
        //creating a new player at the specified location
        player = new Player();
        addObject(player, getWidth() / 2, getHeight() / 2);
        
        playerScore = 0; //resetting player score
        
        //adding the score display
        scoreDisplay = new ScoreDisplay(playerScore);
        addObject(scoreDisplay, scoreDisplay.SCORE_DISPLAY_WIDTH / 2, scoreDisplay.SCORE_DISPLAY_HEIGHT / 2);
    }
    
    public void started(){
        //backgroundMusic.playLoop();
    }
    
    public void act(){
        spawnEnemies();
        //increases the chance of enemies spawning when the timer hits 0
        if(numPool > END_NUM_POOL && decrTimer == 0){
            numPool--;
        }
        decrTimer--;
        if(decrTimer < 0){
            decrTimer = POOL_DECR_DELAY;
        }
        
        scoreDisplay.update(playerScore); //update the score display
    }
    
    public void stopped(){
        //backgroundMusic.pause();
    }
    
    //method to spawn enemies
    private void spawnEnemies(){
        int rdnX = Greenfoot.getRandomNumber(WORLD_WIDTH);
        int rdnY = Greenfoot.getRandomNumber(WORLD_HEIGHT);
        int rdnNum = Greenfoot.getRandomNumber(4);
        Actor enemy;
        
        if(Greenfoot.getRandomNumber(numPool) == 0){
            //chooses type of enemy
            if(Greenfoot.getRandomNumber(ADV_CHANCE) == 0){
                enemy = new AdvancedEnemy();
            }
            else{
                enemy = new NormalEnemy();
            }
            //spawns enemy at a random location at the edge of the world
            if(rdnNum == 0){
                addObject(enemy, rdnX, 0);
            }
            else if(rdnNum == 1){
                addObject(enemy, rdnX, WORLD_HEIGHT);
            }
            else if(rdnNum == 2){
                addObject(enemy, 0, rdnY);
            }
            else if(rdnNum == 3){
                addObject(enemy, WORLD_WIDTH, rdnY);
            }
            enemy.turnTowards(player.getX(), player.getY());
        }
    }
    
    public static void stopMusic(){
        //backgroundMusic.stop();
    }
    
    //method to draw a space background
    public static GreenfootImage drawBackground(int width, int height, Color starColor){ //(from Mr.Cohen)
        GreenfootImage image = new GreenfootImage(width, height); //creating the blank GreenfootImage used for the background
        
        //drawing the background
        image.setColor(Color.BLACK);
        image.fill();
        image.setColor(starColor);
        
        //fills the space with stars randomly
        for(int i = 0; i < 600; i++){
            int randSize = Greenfoot.getRandomNumber (2) + 2;
            int randX = Greenfoot.getRandomNumber(width);
            int randY = Greenfoot.getRandomNumber(height);
            image.fillOval(randX, randY, randSize, randSize);
        }
        
        return image;
    }
    
    //getter and setter methods
    public static int getScore(){
        return playerScore; 
    }
    
    public void setScore(int score){
        playerScore += score;
    }
}
