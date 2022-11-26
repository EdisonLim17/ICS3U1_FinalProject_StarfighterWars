import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Projectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Projectile extends SuperSmoothMover
{
    //declaring variable for the projectile's image
    protected GreenfootImage image;
    
    //declaring instance variables
    protected int speed, dmg;
    protected boolean explosive;
    protected int explosionRange;
    protected Class targetClass; //target class tracking (from Mr. Cohen)
    
    public Projectile(){
        
    }
    
    public void act() 
    {
        move(speed);
        if(collisionDetection() || isAtEdge()){ //removes laser if when it hits an object or leaves the screen
            getWorld().removeObject(this);
        }
    }
    
    //method to check if the laser has collided with another actor
    private boolean collisionDetection(){ //(from Mr. Cohen)
        Actor target = getOneIntersectingObject(targetClass);
        if(target != null){
            if(target instanceof Player){ //deals damage to player if the laser hits the player
                Player player = (Player) target;
                player.dealDmg(dmg);
            }
            else if(target instanceof Enemy){
                Enemy enemy;
                if(explosive){ //deals damage to all enemies in the area of the torpedo if it is a torpedo
                    List<Enemy> enemies = getObjectsInRange(explosionRange, Enemy.class); //(from "danpost" on Greenfoot)
                    for(var i = 0; i < enemies.size(); i++){
                        enemy = enemies.get(i);
                        enemy.dealDmg(dmg);
                    }
                }
                else{ //deals damage to the enemy if the laser hits the enemy
                    enemy = (Enemy) target;
                    enemy.dealDmg(dmg);
                }
            }
            return true;
        }
        return false;
    }
}
