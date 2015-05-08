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
 * Snowflake is an instance of a Box 2D sprite which allows animations and 
 * rendering in the Box2D world.
 */
public class Snowflake extends B2DSprite
{
  public Snowflake(Body body)
  {
    super(body);
    
    //Set animation from resource "snowflake"
    Texture tex = Game.res.getTexture("snowflake");
    TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
    
    setAnimation(sprites, 1 / 12f);
  }
}
