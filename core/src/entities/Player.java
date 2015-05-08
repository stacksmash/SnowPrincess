/**
 * @author Dan Kestell
 * 
 * Framework based off of Tutorial by ForeignGuyMike
 * https://www.youtube.com/playlist?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK
 */

package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.stacksmash.SnowPrincess.Game;

/*
 * Player is a specific instance of a character sprite and handles updating
 * snowflake collection, setting the goal for each level, and setting the 
 * resource image
 */
public class Player extends B2DSprite
{
  private int numSnowflakes;
  private int snowflakeGoal;
  
  public Player(Body body)
  {
    super(body);
    
    //Set up animation from resource image "Ivy"
    Texture tex = Game.res.getTexture("Ivy");
    TextureRegion[] sprites = TextureRegion.split(tex, 64, 64)[0];
    
    setAnimation(sprites, 1 / 12f);
  }
  
  public void collectSnowflake()
  {
    numSnowflakes++;
  }
  
  public int getNumSnowflakes()
  {
    return numSnowflakes;
  }
  
  public void setSnowflakesGoal(int i)
  {
    snowflakeGoal = i;
  }
  
  public int getSnowflakesGoal()
  {
    return snowflakeGoal;
  }
}
