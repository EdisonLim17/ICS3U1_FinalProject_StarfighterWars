import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Object;
import greenfoot.MouseInfo;

/**
 * Write a description of class Player1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends SuperSmoothMover
{
    //declaring variables for the player's image
    private GreenfootImage image;
    public static final int PLAYER_WIDTH = GameWorld.WORLD_WIDTH / 10;
    public static final int PLAYER_HEIGHT = PLAYER_WIDTH;
    public static final Color playerWhite = new Color(222, 221, 203);
    public static final Color playerGrey = new Color(160, 156, 143);
    public static final Color playerRed = new Color(245, 95, 73);
    public static final Color playerYellow = new Color(215, 206, 96);
    public static final Color transparent = new Color(0, 0, 0, 0);
    public static final Color playerBoostColor = new Color(254, 99, 172);
    public static final Color playerLaserColor = Laser.neonPink;
    
    //initializing constants
    //movement
    public static final double MAX_SPEED = 4.0;
    public static final double ACCEL = 0.2;
    public static final double DECEL = 0.1;
    //boost
    public static final double BOOST = 0.08;
    public static final double BOOST_DECAY = 0.95;
    public static final int MAX_BOOST = 3000;
    public static final int RESET_LIMIT = 500;
    public static final int REFILL_SPEED = 10;
    //stats
    public static final int PLAYER_MAX_HP = 100;
    public static final int PLAYER_LASER_DMG = 1;
    public static final int TORPEDO_DMG = Math.max(AdvancedEnemy.ADV_HP, NormalEnemy.NORM_HP);
    //laser shooting
    public static final int PLAYER_SHOOT_CD = 15;
    public static final int OVERHEAT_LIMIT = 200;
    public static final int COOLING_SPEED = 20;
    //torpedo launching
    public static final int MAX_TORPS = 3;
    //upgrades
    public static final int UPGRADE_SCORE = 1000;
    
    //declaring variable to store user information
    private UserInfo user;
    
    //declaring actors
    private StatBar hpBar, overheatBar, boostBar;
    private ProtonTorpedoDisplay torps;
    private UpgradeDisplay upgds;
    
    //declaring instance variables
    //sound (from Mr. Cohen)
    private GreenfootSound[] laserSounds, torpSounds;
    private int laserSoundsIndex, torpSoundsIndex;
    private GreenfootSound coolingSound =  new GreenfootSound("X-Wing Cooling.wav");
    private GreenfootSound boostSound = new GreenfootSound("X-Wing Boost.wav");
    private GreenfootSound explosionSound = new GreenfootSound("X-Wing Explosion.wav");
    private GreenfootSound newUpgdSound = new GreenfootSound("New Upgrade.wav");
    private GreenfootSound upgdUsedSound = new GreenfootSound("Upgrade Used.wav");
    //keybinds
    private String upKb, downKb, leftKb, rightKb, boostKb, torpKb, upgd1Kb, upgd2Kb, upgd3Kb;
    //mouse trackers
    private MouseInfo mouse;
    private boolean mouseDown = false;
    //movement
    private double xSpeed = 0.0;
    private double ySpeed = 0.0;
    private boolean facingUp, facingRight;
    //boost
    private double xBoostSpeed = 0.0;
    private double yBoostSpeed = 0.0;
    private double speed;
    private int boostLeft = MAX_BOOST;
    private int boostUsage = 10;
    private boolean boostReset = false;
    //stats
    private int playerHp = PLAYER_MAX_HP;
    //laser shooting
    private int currShootCd = 0;
    private int overheat = 0;
    private int overheatSpeed = 10;
    private boolean cooling = false;
    private boolean coolingSoundPlayed = false;
    //torpedo launching
    private int torpsLeft = MAX_TORPS;
    private boolean keyDown;
    //upgrades
    private int nextUpgd = 1;
    private int numUpgds = 0;
    private int boostUpgd = 2;
    private int overheatUpgd = 2;
    private int torpUpgd = 1;
    //score
    private static int playerScore;
    
    public Player(){
        image = new GreenfootImage(PLAYER_WIDTH + 1, PLAYER_HEIGHT + 1); //creating the blank GreenfootImage used for the player
        //drawing the player then setting the image for the player
        drawPlayer(PLAYER_WIDTH, PLAYER_HEIGHT, playerWhite, playerGrey, playerBoostColor, playerRed, playerYellow);
        setImage(image);
        setRotation(270); //making the image face up
        
        if(UserInfo.isStorageAvailable()){ //check if the user has information
            user = UserInfo.getMyInfo();
            setKeybinds();
        }
        
        //creating hp bar,weapons overheat bar, boost bar, proton torpedo display, and upgrade display
        hpBar = new StatBar(100, playerHp, GameWorld.WORLD_WIDTH / 2, GameWorld.WORLD_HEIGHT / 30, 0, Color.GREEN, Color.RED, false, Color.BLUE, GameWorld.WORLD_HEIGHT / 180);
        overheatBar = new StatBar(OVERHEAT_LIMIT, 0, GameWorld.WORLD_WIDTH / 2, GameWorld.WORLD_HEIGHT / 30, 0, Color.RED, transparent, false, Color.ORANGE, GameWorld.WORLD_HEIGHT / 180);
        boostBar = new StatBar(MAX_BOOST, MAX_BOOST, GameWorld.WORLD_WIDTH / 4, GameWorld.WORLD_HEIGHT / 30, 0, Color.CYAN, transparent, false, Color.WHITE, GameWorld.WORLD_HEIGHT / 180);
        torps = new ProtonTorpedoDisplay(torpsLeft);
        upgds = new UpgradeDisplay(0);
        
        playerScore = GameWorld.getScore(); //resetting player score
        
        //creating sound arrays (from Mr. Cohen)
        laserSoundsIndex = 0;
        laserSounds = new GreenfootSound[10];
        for(int i = 0; i < laserSounds.length; i++){
            laserSounds[i] = new GreenfootSound("X-Wing Laser.wav");
        }
        torpSoundsIndex = 0;
        torpSounds = new GreenfootSound[3];
        for(int i = 0; i < torpSounds.length; i++){
            torpSounds[i] = new GreenfootSound("X-Wing Proton Torpedo.wav");
        }
    }
    
    public void addedToWorld(World world){ //(from Mr. Cohen)
        //adding hp bar, weapons overheat bar, boost bar, proton torpedo display, and upgrade display to the world
        world.addObject(hpBar, world.getWidth() / 2, world.getHeight() - hpBar.getHeight() * 2);
        world.addObject(overheatBar, world.getWidth() / 2, world.getHeight() - overheatBar.getHeight());
        boostBar.setRotation(270);
        world.addObject(boostBar, boostBar.getHeight(), world.getHeight() / 2);
        world.addObject(torps, torps.getHeight() / 2 + torps.getWidth() / 2, world.getHeight() - torps.getWidth() / 2);
        world.addObject(upgds, world.getWidth() - upgds.getWidth() / 2, world.getHeight() - upgds.getHeight() / 2);
    }
    
    public void act()
    {
        mouse = Greenfoot.getMouseInfo(); //setting variable to track the mouse
        playerScore = GameWorld.getScore(); //updating player score
        if(mouse != null){ //making the player face the mouse
            turnTowards(mouse.getX(), mouse.getY());
        }
        checkKeys();
        checkMouse();
        collisionDetection();
        if(playerScore / UPGRADE_SCORE == nextUpgd){ //gives the player an upgrade whenever they reach the score
            newUpgdSound.play();
            numUpgds++;
            upgds.update(numUpgds);
            nextUpgd++;
        }
        if(playerHp <= 0){ //removing the player when it dies and ending the game by taking the user back to the main menu
            boostSound.stop();
            coolingSound.stop();
            newUpgdSound.stop();
            upgdUsedSound.stop();
            GameWorld.stopMusic();
            Explosion explosion = new Explosion(100);
            getWorld().addObject(explosion, getX(), getY());
            getWorld().removeObject(this);
            explosionSound.play();
            Greenfoot.setWorld(new MainMenu());
            MainMenu.playMusic();
        }
    }
    
    //method to check keyboard input
    private void checkKeys(){
        //player movement (acceleration and deceleration from Mr. Cohen)
        if(Greenfoot.isKeyDown(upKb)){ //move up
            if(!facingUp){
                ySpeed = 0.0;
            }
            facingUp = true;
            ySpeed = Math.min(ySpeed + ACCEL, MAX_SPEED);
            setLocation(getX(), getY() - ySpeed);
        }
        else if(Greenfoot.isKeyDown(downKb)){ //move down
            if(facingUp){
                ySpeed = 0.0;
            }
            facingUp = false;
            ySpeed = Math.min(ySpeed + ACCEL, MAX_SPEED);
            setLocation(getX(), getY() + ySpeed);
        }
        else{ //deceleration
            ySpeed = Math.max(0.0, ySpeed - DECEL);
            if(facingUp){
                setLocation(getX(), getY() - ySpeed);
            }
            else{
                setLocation(getX(), getY() + ySpeed);
            }
        }
        if(Greenfoot.isKeyDown(leftKb)){ //move to the left
            if(facingRight){
                xSpeed = 0.0;
            }
            facingRight = false;
            xSpeed = Math.min(xSpeed + ACCEL, MAX_SPEED);
            setLocation(getX() - xSpeed, getY());
        }
        else if(Greenfoot.isKeyDown(rightKb)){ //move to the right
            if(!facingRight){
                xSpeed = 0.0;
            }
            facingRight = true;
            xSpeed = Math.min(xSpeed + ACCEL, MAX_SPEED);
            setLocation(getX() + xSpeed, getY());
        }
        else{ //deceleration
            xSpeed = Math.max(0.0, xSpeed - DECEL);
            if(facingRight){
                setLocation(getX() + xSpeed, getY());
            }
            else{
                setLocation(getX() - xSpeed, getY());
            }
        }
        if(boostLeft > 0 && !boostReset && Greenfoot.isKeyDown(boostKb)){ //boost (from Mr. Cohen)
            xBoostSpeed += BOOST * Math.cos(getRotation() * (Math.PI / 180.0));
            yBoostSpeed -= BOOST * Math.sin(getRotation() * (Math.PI / 180.0));
            
            speed = Math.sqrt((xBoostSpeed * xBoostSpeed) + (yBoostSpeed * yBoostSpeed));
            
            if (speed > MAX_SPEED){
                xBoostSpeed *= MAX_SPEED / speed;
                yBoostSpeed *= MAX_SPEED / speed;
            }
            setLocation(getX() + xBoostSpeed, getY() - yBoostSpeed);
            
            boostSound.play();
            drawPlayer(PLAYER_WIDTH, PLAYER_HEIGHT, playerWhite, playerGrey, playerBoostColor, playerRed, playerYellow);
            setImage(image);
            
            boostLeft -= boostUsage;
            if(boostLeft < 0){
                boostLeft = 0; //prevents amount of boost left from going negative
            }
            boostBar.update((int)boostLeft);
        }
        else{ //boost deceleration
            boostSound.stop();
            drawPlayer(PLAYER_WIDTH, PLAYER_HEIGHT, playerWhite, playerGrey, playerBoostColor, playerRed, playerYellow);
            setImage(image);
            
            if(boostLeft > 0){
                xBoostSpeed *= BOOST_DECAY;
                yBoostSpeed *= BOOST_DECAY;
                setLocation(getX() + xBoostSpeed, getY() - yBoostSpeed);
            }
            
            if(boostLeft < MAX_BOOST){
                if(boostLeft <= 0){ //prevents player from boosting when it is at zero
                    boostReset = true;
                }
                boostLeft += REFILL_SPEED;
                if(boostLeft > MAX_BOOST){ //prevents amount of boost left from exceeding the boost limit
                    boostLeft = MAX_BOOST;
                }
                if(boostLeft >= RESET_LIMIT){ //allows player to start boosting again when the boost reaches the reset limit
                    boostReset = false;
                }
                boostBar.update((int)boostLeft);
            }
        }
        if(keyDown != Greenfoot.isKeyDown(torpKb)){
            //method to check if key is released (from "danpost" on Greenfoot)
            keyDown = !keyDown;
            if(!keyDown && torpsLeft > 0){
                ProtonTorpedo torpedo = new ProtonTorpedo();
                torpedo.setRotation(getRotation());
                getWorld().addObject(torpedo, getX(), getY());
                torpedo.move(PLAYER_WIDTH / 2 + 2);
                
                //playing torpedo sound
                torpSounds[torpSoundsIndex].play();
                torpSoundsIndex++;
                if(torpSoundsIndex >= torpSounds.length){
                    torpSoundsIndex = 0;
                }
                
                torpsLeft--;
                torps.update(torpsLeft);
            }
        }
        if(numUpgds > 0 && Greenfoot.isKeyDown(upgd1Kb) && boostUsage > 0){ //upgrade 1 (increase boost limit)
            boostUsage -= boostUpgd;
            if(boostUsage < 0){ //prevents boost usage from going negative
                boostUsage = 0;
            }
            upgdUsedSound.play();
            numUpgds--;
            upgds.update(numUpgds);
        }
        else if(numUpgds > 0 && Greenfoot.isKeyDown(upgd2Kb) && overheatSpeed > 0){ //upgrade 2 (increase overheat limit)
            overheatSpeed -= overheatUpgd;
            if(overheatSpeed < 0){ //prevents overheat from going negative
                overheatSpeed = 0;
            }
            upgdUsedSound.play();
            numUpgds--;
            upgds.update(numUpgds);
        }
        else if(numUpgds > 0 && Greenfoot.isKeyDown(upgd3Kb) && torpsLeft < MAX_TORPS){ //upgrade 3 (adds a proton torpedo if the player has less than max)
            torpsLeft += torpUpgd;
            if(torpsLeft > MAX_TORPS){ //prevents number of torpedoes from exceeding the limit
                torpsLeft = MAX_TORPS;
            }
            torps.update(torpsLeft);
            upgdUsedSound.play();
            numUpgds--;
            upgds.update(numUpgds);
        }
    }
    
    //method to check mouse inputs
    private void checkMouse(){
        mouse = Greenfoot.getMouseInfo();
        //count down on the shoot cooldown
        currShootCd--;
        if(Greenfoot.mousePressed(null)){ //checks if the mouse is down (from "danpost" on Greenfoot)
            mouseDown = true;
        }
        else if(Greenfoot.mouseClicked(null)){
            mouseDown = false;
        }
        
        if(currShootCd <= 0){    
            if(mouseDown && !cooling){ //shoot only when it is not on cooldown and the left click is pressed/held
                //adds a laser at the tip of the left cannon
                Laser leftLaser = new Laser(playerLaserColor);
                leftLaser.setRotation(getRotation() - 90);
                getWorld().addObject(leftLaser, getX(), getY());
                leftLaser.move(PLAYER_HEIGHT * 31 / 64);
                leftLaser.setRotation(getRotation());
                leftLaser.move(Laser.LASER_WIDTH / 2 - 2);
                //adds a laser at the tip of the right cannon
                Laser rightLaser = new Laser(playerLaserColor);
                rightLaser.setRotation(getRotation() + 90);
                getWorld().addObject(rightLaser, getX(), getY());
                rightLaser.move(PLAYER_HEIGHT * 31 / 64);
                rightLaser.setRotation(getRotation());
                rightLaser.move(Laser.LASER_WIDTH / 2 - 2);
                
                //adding laser sound
                laserSounds[laserSoundsIndex].play();
                laserSoundsIndex++;
                if(laserSoundsIndex >= laserSounds.length){
                    laserSoundsIndex = 0;
                }
                
                //overheating, and turning on cooling if it reached the overheat limit
                overheat += overheatSpeed;
                if(overheat > OVERHEAT_LIMIT){
                    overheat = OVERHEAT_LIMIT;
                }
                overheatBar.update((int)overheat);
                if(overheat >= OVERHEAT_LIMIT){
                    cooling = true;
                }
                
                currShootCd = PLAYER_SHOOT_CD;
            }
            else if (cooling){ //rapidly resets overheating, allowing the player to shoot once cooling is finished
                if(!coolingSoundPlayed){
                    coolingSound.play();
                }
                coolingSoundPlayed = true;
                overheat -= COOLING_SPEED;
                overheatBar.update((int)overheat);
                if(overheat == 0){
                    cooling = false;
                    coolingSoundPlayed = false;
                }
                
                currShootCd = PLAYER_SHOOT_CD;
            }
            else{ //slowly decreases overheating when the player isn't shooting
                if(overheat > 0){
                    overheat -= COOLING_SPEED / 2;
                    if(overheat < 0){
                        overheat = 0;
                    }
                    overheatBar.update((int)overheat);
                }
                
                currShootCd = PLAYER_SHOOT_CD;
            }
        }
    }
    
    //method to check if the player collided with another actor
    private void collisionDetection(){
        Actor enemy = getOneIntersectingObject(Enemy.class);
        //check if the player collided with an enemy and remove them both if it did
        if(enemy != null){
            playerHp = 0;
            hpBar.update(playerHp);
            getWorld().removeObject(enemy);
        }
    }
    
    //method to set the saved keybinds for the user
    private void setKeybinds(){
        if(user != null){
            //if the user does not have saved keybinds, use default keybinds
            if(user.getInt(0) != 0){
                upKb = SettingsMenu.getKeybind(user.getInt(0));
            }
            else{
                upKb = "w";
            }
            if(user.getInt(1) != 0){
                downKb = SettingsMenu.getKeybind(user.getInt(1));
            }
            else{
                downKb = "s";
            }
            if(user.getInt(2) != 0){
                leftKb = SettingsMenu.getKeybind(user.getInt(2));
            }
            else{
                leftKb = "a";
            }
            if(user.getInt(3) != 0){
                rightKb = SettingsMenu.getKeybind(user.getInt(3));
            }
            else{
                rightKb = "d";
            }
            if(user.getInt(4) != 0){
                boostKb = SettingsMenu.getKeybind(user.getInt(4));
            }
            else{
                boostKb = "shift";
            }
            if(user.getInt(5) != 0){
                torpKb = SettingsMenu.getKeybind(user.getInt(5));
            }
            else{
                torpKb = "space";
            }
            if(user.getInt(6) != 0){
                upgd1Kb = SettingsMenu.getKeybind(user.getInt(6));
            }
            else{
                upgd1Kb = "1";
            }
            if(user.getInt(7) != 0){
                upgd2Kb = SettingsMenu.getKeybind(user.getInt(7));
            }
            else{
                upgd2Kb = "2";
            }
            if(user.getInt(8) != 0){
                upgd3Kb = SettingsMenu.getKeybind(user.getInt(8));
            }
            else{
                upgd3Kb = "3";
            }
        }
    }
    
    //public method to deal damage to the player
    public void dealDmg(int dmg){
        playerHp -= dmg;
        hpBar.update(playerHp);
    }
    
    //setter method
    public void setScore(int score){
        playerScore = score;
    }
    
    //method to draw the player with the given parameters
    private void drawPlayer(int width, int height, Color baseColor, Color partsColor, Color boostColor, Color color_1, Color color_2){
        //drawing the wing
        image.setColor(baseColor);
        int[] wingX = {width * 3 / 16, width * 19 / 64, width * 19 / 64, width * 3 / 16, width * 5 / 64};
        int[] wingY = {0, 0, height, height, height / 2};
        image.fillPolygon(wingX, wingY, 5); //fills the base of the wing
        image.setColor(color_2);
        int[] wingDesign1X = {width * 363 / 2048, width * 19 / 64, width * 19 / 64, width * 157 / 1024};
        int[] wingDesign1Y = {height * 3 / 64, height * 3 / 64, height * 5 / 32, height * 5 / 32};
        image.fillPolygon(wingDesign1X, wingDesign1X, 4); //fills the top color_2 design
        int[] wingDesign2X = {width * 363 / 2048, width * 19 / 64, width * 19 / 64, width * 157 / 1024};
        int[] wingDesign2Y = {height * 61 / 64, height * 61 / 64, height * 27 / 32, height * 27 /32};
        image.fillPolygon(wingDesign2X, wingDesign2Y, 4); //fills the bottom color_2 design
        image.setColor(color_1);
        int[] wingDesign3X = {width * 667 / 3072, width * 19 / 64, width * 19 / 64, width * 667 / 3072, width * 20413 / 98304, width * 2395 / 12288};
        int[] wingDesign3Y = {height * 3 / 64, height * 3 / 64, height * 35 / 256, height * 35 / 256, height * 51 / 256, height * 51 / 256};
        image.fillPolygon(wingDesign3X, wingDesign3Y, 6); //fills the top color_1 design
        int[] wingDesign4X = {width * 667 / 3072, width * 19 / 64, width * 19 / 64, width * 667 / 3072, width * 20413 / 98304, width * 2395 / 12288};
        int[] wingDesign4Y = {height * 61 / 64, height * 61 / 64, height * 221 / 256, height * 221 / 256, height * 205 / 256, height * 205 / 256};
        image.fillPolygon(wingDesign4X, wingDesign4Y, 6); //fills the bottome color_1 design
        //fills the top stripes
        int[] topStripe1X = {width * 1207 / 8192, width * 6053 / 32768, width * 5969 / 32768, width * 1179 / 8192};
        int[] topStripe1Y = {height * 47 / 256, height * 47 / 256, height * 51 / 256, height * 51 / 256};
        image.fillPolygon(topStripe1X, topStripe1Y, 4);
        int[] topStripe2X = {width * 157 / 1024, width * 775 / 4096, width * 1529 / 8192, width * 307 / 2048};
        int[] topStripe2Y = {height * 5 / 32, height * 5 / 32, height * 11 / 64, height * 11 / 64};
        image.fillPolygon(topStripe2X, topStripe2Y, 4);
        int[] topStripe3X = {width * 1305 / 8192, width * 6347 / 32768, width * 6263 / 32768, width * 1277 / 8192};
        int[] topStripe3Y = {height * 33 / 256, height * 33 / 256, height * 37 / 256, height * 37 / 256};
        image.fillPolygon(topStripe3X, topStripe3Y, 4);
        int[] topStripe4X = {width * 677 / 4096, width * 3247 / 16384, width * 3205 / 16384, width * 663 / 4096};
        int[] topStripe4Y = {height * 13 / 128, height * 13 / 128, height * 15 / 128, height * 15 / 128};
        image.fillPolygon(topStripe4X, topStripe4Y, 4);
        int[] topStripe5X = {width * 1403 / 8192, width * 6641 / 32768, width * 6557 / 32768, width * 1375 / 8192};
        int[] topStripe5Y = {height * 19 / 256, height * 19 / 256, height * 23 / 256, height * 23 / 256};
        image.fillPolygon(topStripe5X, topStripe5Y, 4);
        //fills the bottom stripes
        int[] botStripe1X = {width * 1207 / 8192, width * 6053 / 32768, width * 5969 / 32768, width * 1179 / 8192};
        int[] botStripe1Y = {height * 209 / 256, height * 209 / 256, height * 205 / 256, height * 205 / 256};
        image.fillPolygon(botStripe1X, botStripe1Y, 4);
        int[] botStripe2X = {width * 157 / 1024, width * 775 / 4096, width * 1529 / 8192, width * 307 / 2048};
        int[] botStripe2Y = {height * 27 / 32, height * 27 / 32, height * 53 / 64, height * 53 / 64};
        image.fillPolygon(botStripe2X, botStripe2Y, 4);
        int[] botStripe3X = {width * 1305 / 8192, width * 6347 / 32768, width * 6263 / 32768, width * 1277 / 8192};
        int[] botStripe3Y = {height * 223 / 256, height * 223 / 256, height * 219 / 256, height * 219 / 256};
        image.fillPolygon(botStripe3X, botStripe3Y, 4);
        int[] botStripe4X = {width * 677 / 4096, width * 3247 / 16384, width * 3205 / 16384, width * 663 / 4096};
        int[] botStripe4Y = {height * 115 / 128, height * 115 / 128, height * 113 / 128, height * 113 / 128};
        image.fillPolygon(botStripe4X, botStripe4Y, 4);
        int[] botStripe5X = {width * 1403 / 8192, width * 6641 / 32768, width * 6557 / 32768, width * 1375 / 8192};
        int[] botStripe5Y = {height * 237 / 256, height * 237 / 256, height * 233 / 256, height * 233 / 256};
        image.fillPolygon(botStripe5X, botStripe5Y, 4);
        image.setColor(Color.BLACK);
        image.drawPolygon(wingX, wingY, 5); //outlines the base of the wing
        image.drawLine(width * 265 / 2048, height * 17 / 64, width * 19 / 64, height * 17 / 64); //draws top flap line
        image.drawLine(width * 265 / 2048, height * 47 / 64, width * 19 / 64, height * 47 / 64); //draws bottom flap line

        //drawing the body
        image.setColor(baseColor);
        int[] bodyX = {width / 16, width * 19 / 64, width, width * 19 / 64, width / 16};
        int[] bodyY = {height * 7 / 16, height * 7 / 16, height / 2, height * 9 / 16, height * 9 / 16};
        image.fillPolygon(bodyX, bodyY, 5); //fills the base of the body
        image.setColor(partsColor);
        image.fillRect(width / 8, height * 15 / 32, width * 15 / 64, height / 16); // fills the engine
        image.setColor(Color.BLACK);
        int[] cpX = {width * 25 / 64, width * 33 / 64, width * 33 / 64, width * 25 / 64};
        int[] cpY = {height * 15 / 32, height * 691 / 1440, height * 749 / 1440, height * 17 / 32};
        image.fillPolygon(cpX, cpY, 4); //fills the cockpit
        image.setColor(color_1);
        int[] bodyDesignX = {width * 19 / 64, width, width * 19 / 64, width * 19 / 64, width, width * 19 / 64};
        int[] bodyDesignY = {height * 7 / 16, height / 2, height * 9 / 16, height * 35 / 64, height / 2, height * 29 / 64};
        image.fillPolygon(bodyDesignX, bodyDesignY, 6); //fills the color_1 design
        image.setColor(Color.BLACK);
        image.drawPolygon(bodyX, bodyY, 5); //outlines the base of the body
        image.drawOval(width * 5 / 16, height * 31 / 64, width / 32, height / 32); //draws the droid seat

        //drawing the turbine engines
        image.setColor(partsColor);
        int[] topEgX = {0, width * 3 / 16, width * 21 / 64, width * 21 / 64, width * 3 / 16, 0};
        int[] topEgY = {height * 11 / 32, height * 21 / 64, height * 21 / 64, height * 13 / 32, height * 13 / 32, height * 25 / 64};
        image.fillPolygon(topEgX, topEgY, 6); //fills the base of the top turbine engine
        int[] botEgX = topEgX;
        int[] botEgY = {height * 21 / 32, height * 43 / 64, height * 43 / 64, height * 19 / 32, height * 19 / 32, height * 39 / 64};
        image.fillPolygon(botEgX, botEgY, 6); //fills the base of the bottom turbine engine
        image.setColor(color_1);
        image.fillRect(width * 19 / 64, height * 21 / 64, width / 64, height * 5 / 64); //fills the top color_1 design
        image.fillRect(width * 19 / 64, height * 19 / 32, width / 64, height * 5 / 64); //fills the bottom color_1 design
        if(boostSound.isPlaying()){ //fills boost design
            image.setColor(boostColor);
            int[] topBoostX = {0, width * 3 / 16, width * 3 / 16, 0};
            int[] topBoostY = {height * 11 / 32, height * 21 / 64, height * 13 / 32, height * 25 / 64};
            image.fillPolygon(topBoostX, topBoostY, 4); //fills top boost engine
            int[] botBoostX = {0, width * 3 / 16, width * 3 / 16, 0};
            int[] botBoostY = {height * 21 / 32, height * 43 / 64, height * 19 / 32, height * 39 / 64};
            image.fillPolygon(botBoostX, botBoostY, 4); //fills bottom boost engine
        }
        image.setColor(Color.BLACK);
        image.drawPolygon(topEgX, topEgY, 6); //outlines the base of the top turbine engine
        image.drawPolygon(botEgX, botEgY, 6); //outlines the base of the bottom turbine engine
        image.drawLine(width * 3 / 16, height * 21 / 64, width * 3 / 16, height * 13 / 32);
        image.drawLine(width * 3 / 16, height * 43 / 64, width * 3 / 16, height * 19 / 32);

        //drawing the cannons
        image.setColor(partsColor);
        int[] topCanX = {width * 9 / 64, width * 5 / 16, width * 21 / 32, width * 5 / 16, width * 9 / 64};
        int[] topCanY = {0, 0, height / 64, height / 32, height / 32};
        image.fillPolygon(topCanX, topCanY, 5); //fills the base of the top cannon
        int[] botCanX = topCanX;
        int[] botCanY = {height, height, height * 63 / 64, height * 31 / 32, height * 31 / 32};
        image.fillPolygon(botCanX, botCanY, 5); //fills the base of the bottom cannon
        image.setColor(color_2);
        image.fillRect(width * 19 / 64, 0, width / 64, height / 32); //fills the top color_2 design
        image.fillRect(width * 19 / 64, height * 31 / 32, width / 64, height / 32); //fills the bottom color_2 design
        image.setColor(Color.BLACK);
        image.drawPolygon(topCanX, topCanY, 5); //outlines the top cannon
        image.drawPolygon(botCanX, botCanY, 5); //outlines the bottom cannon
    }
}
