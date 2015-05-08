/**
 * @author Dan Kestell
 */
package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.stacksmash.SnowPrincess.Game;

/*
 * WinFlag is simply a Box2d body with a sensor that is placed at the end of 
 * each level in order to confirm a player has traveled to the location 
 * neccessary to win.
 */
public class WinFlag extends B2DSprite
{
  public WinFlag(Body body)
  {
    super(body);
    
    //Set animation (I never animated the WinFlag unfortunately). This simply
    //sets an image to be rendered.
    Texture tex = Game.res.getTexture("flag");
    TextureRegion[] sprites = TextureRegion.split(tex, 40, 66)[0];
    
    setAnimation(sprites, 1 / 12f);
  }
}
