import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class NormalEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NormalEnemy extends Enemy
{
    //declaring variables for the normal enemy's image
    public static final int NORM_WIDTH = Player.PLAYER_WIDTH;
    public static final int NORM_HEIGHT = Player.PLAYER_HEIGHT;
    
    //initializing constants
    public static final int NORM_SCORE = 100;
    public static final int NORM_HP = 3;
    public static final int NORM_DMG = 2;
    public static final int NORM_SHOOT_CD = 15;
    public static final int NORM_SHOOT_CHANCE = 20;
    public static final int NORM_SPEED = 3;

    public NormalEnemy(){
        super();
        //setting the image for the normal enemy
        image = drawNormalEnemy(NORM_WIDTH, NORM_HEIGHT, enemyGrey, Color.RED);
        setImage(image);
        //setting variables
        enemyScore = NORM_SCORE;
        enemyHp = NORM_HP;
        enemySpeed = NORM_SPEED;
    }

    public void act() 
    {
        super.act();
        shoot();
        if(enemyHp <= 0){
            //setting player score
            GameWorld world = (GameWorld)getWorld(); //(from Mr. Cohen)
            world.setScore(enemyScore);
            
            //removing the enemy from the world when it dies
            Explosion explosion = new Explosion(100);
            getWorld().addObject(explosion, getX(), getY());
            getWorld().removeObject(this);
            explosionSound.play();
        }
    }    
    
    //method to get the normal enemy to shoot
    private void shoot(){
        currShootCd--;
        if(currShootCd <= 0 && Greenfoot.getRandomNumber(NORM_SHOOT_CHANCE) == 0){
            //adds a laser at the tip of the left cannon
            Laser leftLaser = new Laser(NORM_DMG, enemyLaserColor);
            leftLaser.setRotation(getRotation() - 90);
            getWorld().addObject(leftLaser, getX(), getY());
            leftLaser.move(NORM_HEIGHT * 11 / 192);
            leftLaser.setRotation(getRotation());
            leftLaser.move(Laser.LASER_WIDTH / 2 + NORM_WIDTH * 13 / 64);
            //adds a laser at the tip of the right cannon
            Laser rightLaser = new Laser(NORM_DMG, enemyLaserColor);
            rightLaser.setRotation(getRotation() + 90);
            getWorld().addObject(rightLaser, getX(), getY());
            rightLaser.move(NORM_HEIGHT * 11 / 192);
            rightLaser.setRotation(getRotation());
            rightLaser.move(Laser.LASER_WIDTH / 2 + NORM_WIDTH * 13 / 64);
            
            //adding laser sound
            laserSounds[laserSoundsIndex].play();
            laserSoundsIndex++;
            if(laserSoundsIndex >= laserSounds.length){
                laserSoundsIndex = 0;
            }
            
            currShootCd = NORM_SHOOT_CD;
        }
    }
    
    //method to draw the normal enemy with the given parameters
    private static GreenfootImage drawNormalEnemy(int width, int height, Color baseColor, Color lightColor){
        GreenfootImage norm = new GreenfootImage(width + 1, height + 1); //creating the blank GreenfootImage used for the normal enemy
        
        //drawing the attachments
        norm.setColor(baseColor);
        int[] topAttX = {width * 13 / 32, width * 19 / 32, width * 33 / 64, width * 35 / 64, width * 29 / 64, width * 31 / 64};
        int[] topAttY = {0, 0, height * 3 / 16, height * 11 / 32, height * 11 / 32, height * 3 / 16};
        norm.fillPolygon(topAttX, topAttY, 6); //fills the top attachment
        norm.setColor(Color.BLACK);
        norm.drawPolygon(topAttX, topAttY, 6); //outlines the top attachment
        norm.drawLine(width * 31 / 64, height * 3 / 16, width * 33 / 64, height * 3 / 16);
        norm.drawLine(width * 31 / 64, 0, width * 31 / 64, height * 3 / 16);
        norm.drawLine(width * 33 / 64, 0, width * 33 / 64, height * 3 / 16);
        norm.drawLine(width / 2, height * 3 / 16, width / 2, height * 21 / 64);
        int[] botAttX = topAttX;  
        int[] botAttY = {height, height, height * 13 / 16, height * 21 / 32, height * 21 / 32, height * 13 / 16};
        norm.setColor(baseColor);
        norm.fillPolygon(botAttX, botAttY, 6); //fills the bottom attachment
        norm.setColor(Color.BLACK);
        norm.drawPolygon(botAttX, botAttY, 6); //outlines the bottom attachment
        norm.drawLine(width * 33 / 64, height * 13 / 16, width * 31 / 64, height * 13 / 16);
        norm.drawLine(width * 31 / 64, height, width * 31 / 64, height * 13 / 16);
        norm.drawLine(width * 33 / 64, height, width * 33 / 64, height * 13 / 16);
        norm.drawLine(width / 2, height * 13 / 16, width / 2, height * 43 / 64);
        
        //drawing the cockpit
        norm.setColor(lightColor);
        norm.fillOval(width * 21 / 32, height * 41 / 96, width * 3 / 64, height / 32);
        norm.fillOval(width * 21 / 32, height * 13 / 24, width * 3 / 64, height / 32);
        norm.setColor(baseColor);
        norm.fillOval(width * 21 / 64, height * 21 / 64, width * 11 / 32, height * 11 / 32); //fills the cockpit
        norm.setColor(Color.BLACK);
        norm.drawOval(width * 21 / 64, height * 21 / 64, width * 11 / 32, height * 11 / 32); //outlines the cockpit
        int[] vpX = {width * 5 / 8, width * 43 / 64, width * 43 / 64, width * 5 / 8};
        int[] vpY = {height * 49 / 128, height * 13 / 32, height * 19 / 32, height * 79 / 128};
        norm.setColor(baseColor);
        norm.fillPolygon(vpX, vpY, 4); //fills viewport
        norm.setColor(Color.BLACK);
        norm.drawPolygon(vpX, vpY, 4); //outlines viewport
        norm.drawOval(width * 25 / 64, height * 25 / 64, width * 7 / 32, height * 7 / 32); //outlines opening
        //draws designs
        int[] design1X = {width / 2, width * 17 / 32, width * 37 / 64, width / 2};
        int[] design1Y = {height * 13 / 32, height * 13 / 32, height * 7 / 16, height * 7 / 16};
        norm.fillPolygon(design1X, design1Y, 4);
        int[] design2X = {width * 31 / 64, width * 75 / 128, width * 19 / 32, width * 31 / 64};
        int[] design2Y = {height * 29 / 64, height * 29 / 64, height * 31 / 64, height * 31 / 64};
        norm.fillPolygon(design2X, design2Y, 4);
        int[] design3X = {width * 31 / 64, width * 75 / 128, width * 19 / 32, width * 31 / 64};
        int[] design3Y = {height * 35 / 64, height * 35 / 64, height * 33 / 64, height * 33 / 64};
        norm.fillPolygon(design3X, design3Y, 4);
        int[] design4X = {width / 2, width * 17 / 32, width * 37 / 64, width / 2};
        int[] design4Y = {height * 19 / 32, height * 19 / 32, height * 9 / 16, height * 9 / 16};
        norm.fillPolygon(design4X, design4Y, 4);
        norm.drawLine(width * 25 / 64, height / 2, width * 39 / 64, height / 2);
        
        //drawing the wings
        norm.setColor(baseColor);
        norm.fillRect(0, 0, width, height / 32); //fills top wing
        norm.setColor(Color.BLACK);
        norm.drawRect(0, 0, width, height / 32); //outlines top wing
        norm.setColor(baseColor);
        norm.fillRect(0, height * 31 / 32, width, height / 32); //fills bottom wing
        norm.setColor(Color.BLACK);
        norm.drawRect(0, height * 31 / 32, width, height / 32); //outlines bottom wing

        return norm;
    }
}
