import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class AdvancedEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AdvancedEnemy extends Enemy
{
    //declaring variables for the advanced enemy's image
    public static final int ADV_WIDTH = NormalEnemy.NORM_WIDTH * 11 / 8;
    public static final int ADV_HEIGHT = NormalEnemy.NORM_HEIGHT;
    
    //initializing constants
    public static final int ADV_SCORE = 200;
    public static final int ADV_HP = 4;
    public static final int ADV_DMG = 3;
    public static final int ADV_SHOOT_CD = 10;
    public static final int ADV_SHOOT_CHANCE = 20; //lower = higher chance
    public static final int ADV_SPEED = 4;
    
    public AdvancedEnemy(){
        super();
        //setting the image for the advanced enemy
        image = drawAdvancedEnemy(ADV_WIDTH, ADV_HEIGHT, enemyGrey, Color.RED);
        setImage(image);
        //setting variables
        enemyScore = ADV_SCORE;
        enemyHp = ADV_HP;
        enemySpeed = ADV_SPEED;
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
    
    //method to get the advanced enemy to shoot
    private void shoot(){
        currShootCd--;
        if(currShootCd <= 0 && Greenfoot.getRandomNumber(ADV_SHOOT_CHANCE) == 0){
            //adds a laser at the tip of the left cannon
            Laser leftLaser = new Laser(ADV_DMG, enemyLaserColor);
            leftLaser.setRotation(getRotation() - 90);
            getWorld().addObject(leftLaser, getX(), getY());
            leftLaser.move(ADV_HEIGHT * 63 / 128);
            leftLaser.setRotation(getRotation());
            leftLaser.move(Laser.LASER_WIDTH / 2 + ADV_WIDTH / 2);
            //adds a laser at the tip of the right cannon
            Laser rightLaser = new Laser(ADV_DMG, enemyLaserColor);
            rightLaser.setRotation(getRotation() + 90);
            getWorld().addObject(rightLaser, getX(), getY());
            rightLaser.move(ADV_HEIGHT * 63 / 128);
            rightLaser.setRotation(getRotation());
            rightLaser.move(Laser.LASER_WIDTH / 2 + ADV_WIDTH / 2);
            
            //adding laser sound
            laserSounds[laserSoundsIndex].play();
            laserSoundsIndex++;
            if(laserSoundsIndex >= laserSounds.length){
                laserSoundsIndex = 0;
            }
            
            currShootCd = ADV_SHOOT_CD;
        }
    }
    
    //method to draw the advanced enemy with the given parametres
    private static GreenfootImage drawAdvancedEnemy(int width, int height, Color baseColor, Color lightColor){
        GreenfootImage adv = new GreenfootImage(width + 1, height + 1); //creating the blank GreenfootImage used for the advanced enemy
        
        //drawing the lower wings
        adv.setColor(baseColor);
        int[] tlCanX = {width * 19 / 22, width * 85 / 88, width * 19 / 22};
        int[] tlCanY = {height * 3 / 64, height * 7 / 128, height / 16};
        adv.fillPolygon(tlCanX, tlCanY, 3); //fills the top lower cannon
        int[] blCanX = {width * 19 / 22, width * 85 / 88, width * 19 / 22};
        int[] blCanY = {height * 61 / 64, height * 121 / 128, height * 15 / 16};
        adv.fillPolygon(blCanX, blCanY, 3); //fills the bottom lower cannon
        adv.setColor(Color.BLACK);
        adv.drawPolygon(tlCanX, tlCanY, 3); //outlines the top lower cannon
        adv.drawPolygon(blCanX, blCanY, 3); //outlines the bottom lower cannon
        adv.setColor(baseColor);
        int[] tlWingX = {width * 5 / 88, width * 10 / 11, width * 7 / 22, width * 2 / 11};
        int[] tlWingY = {height * 3 / 64, height * 3 / 64, height / 4, height / 4};
        adv.fillPolygon(tlWingX, tlWingY, 4); //fills top lower wing
        int[] blWingX = {width * 5 / 88, width * 10 / 11, width * 7 / 22, width * 2 / 11};
        int[] blWingY = {height * 61 / 64, height * 61 / 64, height * 3 / 4, height * 3 / 4};
        adv.fillPolygon(blWingX, blWingY, 4); //fills the bottom lower wing
        adv.setColor(Color.BLACK);
        adv.drawPolygon(tlWingX, tlWingY, 4); //outlines the top lower wing
        adv.drawPolygon(blWingX, blWingY, 4); //outlines the bottom lower wing
        //draws designs
        int[] lwDesign1X = {width * 3 / 33, width * 3 / 11, width * 3 / 11, width * 17 / 88};
        int[] lwDesign1Y = {height / 16, height / 16, height * 31 / 128, height * 31 / 128};
        adv.fillPolygon(lwDesign1X, lwDesign1Y, 4);
        int[] lwDesign2X = {width * 7 / 22, width * 37 / 44, width * 7 / 22};
        int[] lwDesign2Y = {height / 16, height / 16, height * 31 / 128};
        adv.fillPolygon(lwDesign2X, lwDesign2Y, 3);
        int[] lwDesign3X = {width * 3 / 33, width * 3 / 11, width * 3 / 11, width * 17 / 88};
        int[] lwDesign3Y = {height * 15 / 16, height * 15 / 16, height * 97 / 128, height * 97 / 128};
        adv.fillPolygon(lwDesign3X, lwDesign3Y, 4);
        int[] lwDesign4X = {width * 7 / 22, width * 37 / 44, width * 7 / 22};
        int[] lwDesign4Y = {height * 15 / 16, height * 15 / 16, height * 97 / 128};
        adv.fillPolygon(lwDesign4X, lwDesign4Y, 3);
        
        //drawing the attachments
        adv.setColor(baseColor);
        int[] topAttX = {width /4, width * 13 / 44, width * 7 / 22, width * 5 / 22};
        int[] topAttY = {height * 3 / 16, height * 3 / 16, height * 11 / 32, height * 11 / 32};
        adv.fillPolygon(topAttX, topAttY, 4); //fills the top attachment
        int[] botAttX = {width /4, width * 13 / 44, width * 7 / 22, width * 5 / 22};
        int[] botAttY = {height * 13 / 16, height * 13 / 16, height * 21 / 32, height * 21 / 32};
        adv.fillPolygon(botAttX, botAttY, 4); //fills the bottom attachment
        adv.setColor(Color.BLACK);
        adv.drawPolygon(topAttX, topAttY, 4); //outlines the top attachment
        adv.drawPolygon(botAttX, botAttY, 4); //outlines the bottom attachment
        //draws designs
        adv.drawLine(width / 4, height * 3 / 16, width / 4, height / 4);
        adv.drawLine(width * 13 / 44, height * 3 / 16, width * 13 / 44, height / 4);
        adv.drawLine(width / 4, height / 4, width * 21 / 88, height * 11 / 32);
        adv.drawLine(width * 13 / 44, height / 4, width * 27 / 88, height * 11 / 32);
        adv.drawLine(width * 47 / 176, height * 3 / 16, width * 47 / 176, height * 9 / 32);
        adv.drawLine(width * 49 / 176, height * 3 / 16, width * 49 / 176, height * 9 / 32);
        adv.drawLine(width * 23 / 88, height * 9 / 32, width * 25 / 88, height * 9 / 32);
        adv.drawLine(width * 23 / 88, height * 9 / 32, width * 23 / 88, height * 11 / 32);
        adv.drawLine(width * 25 / 88, height * 9 / 32, width * 25 / 88, height * 11 / 32);
        adv.drawLine(width / 4, height * 13 / 16, width / 4, height * 3 / 4);
        adv.drawLine(width * 13 / 44, height * 13 / 16, width * 13 / 44, height * 3 / 4);
        adv.drawLine(width / 4, height * 3 / 4, width * 21 / 88, height * 21 / 32);
        adv.drawLine(width * 13 / 44, height * 3 / 4, width * 27 / 88, height * 21 / 32);
        adv.drawLine(width * 47 / 176, height * 13 / 16, width * 47 / 176, height * 23 / 32);
        adv.drawLine(width * 49 / 176, height * 13 / 16, width * 49 / 176, height * 23 / 32);
        adv.drawLine(width * 23 / 88, height * 23 / 32, width * 25 / 88, height * 23 / 32);
        adv.drawLine(width * 23 / 88, height * 23 / 32, width * 23 / 88, height * 21 / 32);
        adv.drawLine(width * 25 / 88, height * 23 / 32, width * 25 / 88, height * 21 / 32);
        
        //drawing the cockpit
        adv.setColor(lightColor);
        adv.fillOval(width * 67 / 176, height * 41 / 96, width * 3 / 88, height / 32);
        adv.fillOval(width * 67 / 176, height * 13 / 24, width * 3 / 88, height / 32);
        adv.setColor(baseColor);
        adv.fillOval(width * 13 / 88, height * 21 / 64, width / 4, height * 11 / 32); //fills the cockpit
        adv.setColor(Color.BLACK);
        adv.drawOval(width * 13 / 88, height * 21 / 64, width / 4, height * 11 / 32); //outlines the cockpit
        adv.setColor(baseColor);
        int[] backCpX = {width * 13 / 88, width * 2 / 11, width * 2 / 11, width * 13 / 88};
        int[] backCpY = {height * 13 / 32, height * 49 / 128, height * 79 / 128, height * 19 / 32};
        adv.fillPolygon(backCpX, backCpY, 4); //fills the back of the cockpit
        adv.setColor(Color.BLACK);
        adv.drawPolygon(backCpX, backCpY, 4); //outlines the back of the cockpit
        adv.drawOval(width * 17 / 88, height * 25 / 64, width * 7 / 44, height * 7 / 32); //outlines the opening
        //draws designs
        int[] cpDesign1X = {width * 3 / 11, width / 4, width * 19 / 88, width * 3 / 11};
        int[] cpDesign1Y = {height * 13 / 32, height * 13 / 32, height * 7 / 16, height * 7 / 16};
        adv.fillPolygon(cpDesign1X, cpDesign1Y, 4);
        int[] cpDesign2X = {width * 25 / 88, width * 37 / 176, width * 9 / 44, width * 25 / 88};
        int[] cpDesign2Y = {height * 29 / 64, height * 29 / 64, height * 31 / 64, height * 31 / 64};
        adv.fillPolygon(cpDesign2X, cpDesign2Y, 4);
        int[] cpDesign3X = {width * 25 / 88, width * 37 / 176, width * 9 / 44, width * 25 / 88};
        int[] cpDesign3Y = {height * 35 / 64, height * 35 / 64, height * 33 / 64, height * 33 / 64};
        adv.fillPolygon(cpDesign3X, cpDesign3Y, 4);
        int[] cpDesign4X = {width * 3 / 11, width / 4, width * 19 / 88, width * 3 / 11};
        int[] cpDesign4Y = {height * 19 / 32, height * 19 / 32, height * 9 / 16, height * 9 / 16};
        adv.fillPolygon(cpDesign4X, cpDesign4Y, 4);
        adv.drawLine(width * 17 / 88, height / 2, width * 31 / 88, height / 2);
        adv.drawLine(width * 13 / 88, height * 63 / 128, width * 2 / 11, height * 63 / 128);
        adv.drawLine(width * 13 / 88, height * 65 / 128, width * 2 / 11, height * 65 / 128);
        
        //drawing the wings
        adv.setColor(baseColor);
        int[] topCanX = {width * 39 / 44, width, width * 39 / 44};
        int[] topCanY = {0, height / 128, height / 64};
        adv.fillPolygon(topCanX, topCanY, 3); //fills the top cannon
        int[] botCanX = {width * 39 / 44, width, width * 39 / 44};
        int[] botCanY = {height, height * 127 / 128, height * 63 / 64};
        adv.fillPolygon(botCanX, botCanY, 3); //fills the bottom cannon
        adv.setColor(Color.BLACK);
        adv.drawPolygon(topCanX, topCanY, 3); //outlines the top cannon
        adv.drawPolygon(botCanX, botCanY, 3); //outlines the bottom cannon
        adv.setColor(baseColor);
        int[] topWingX = {0, width * 41 / 44, width * 3 / 11, width / 11};
        int[] topWingY = {0, 0, height * 13 / 64, height * 13 / 64};
        adv.fillPolygon(topWingX, topWingY, 4); //fills the top wing
        int[] botWingX = {0, width * 41 / 44, width * 3 / 11, width / 11};
        int[] botWingY = {height, height, height * 51 / 64, height * 51 / 64};
        adv.fillPolygon(botWingX, botWingY, 4); //fills the bottom wing
        adv.setColor(Color.BLACK);
        adv.drawPolygon(topWingX, topWingY, 4); //outlines the top wing
        adv.drawPolygon(botWingX, botWingY, 4); //outlines the bottom wing
        //draws designs
        int[] wingDesign1X = {width * 2 / 55, width * 5 / 22, width * 12 / 55, width * 5 / 44};
        int[] wingDesign1Y = {height / 64, height / 64, height * 3 / 16, height * 3 / 16};
        adv.fillPolygon(wingDesign1X, wingDesign1Y, 4);
        int[] wingDesign2X = {width * 27 / 88, width * 75 / 88, width * 25 / 88};
        int[] wingDesign2Y = {height / 64, height / 64, height * 3 / 16};
        adv.fillPolygon(wingDesign2X, wingDesign2Y, 3);
        int[] wingDesign3X = {width * 2 / 55, width * 5 / 22, width * 12 / 55, width * 5 / 44};
        int[] wingDesign3Y = {height * 63 / 64, height * 63 / 64, height * 13 / 16, height * 13 / 16};
        adv.fillPolygon(wingDesign3X, wingDesign3Y, 4);
        int[] wingDesign4X = {width * 27 / 88, width * 75 / 88, width * 25 / 88};
        int[] wingDesign4Y = {height * 63 / 64, height * 63 / 64, height * 13 / 16};
        adv.fillPolygon(wingDesign4X, wingDesign4Y, 3);
        adv.drawLine(width * 23 / 88, 0, width / 4, height * 3 / 16);
        adv.drawLine(width * 3 / 11, 0, width / 4, height * 3 / 16);
        adv.drawLine(width * 23 / 88, height, width / 4, height * 13 / 16);
        adv.drawLine(width * 3 / 11, height, width / 4, height * 13 / 16);
        
        return adv;
    }
}
