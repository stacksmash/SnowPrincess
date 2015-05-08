/**
 * @author Dan Kestell
 * 
 * Framework based off of Tutorial by ForeignGuyMike
 * https://www.youtube.com/playlist?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK
 */

package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import handlers.Animation;
import handlers.B2DVars;
/*
 * This class handles Box 2D elements associasted with the character sprite. 
 * This includes animations, rendering and updating.
 */
public class B2DSprite
{
  protected Body body;
  protected Animation animation;
  protected float width;
  protected float height;

  public B2DSprite(Body body)
  {
    this.body = body;
    animation = new Animation();
  }

  /*
   * Set new animation for the sprite.
   */
  public void setAnimation(TextureRegion reg, float delay)
  {
    setAnimation(new TextureRegion[] { reg }, delay);
  }

  /*
   * Set new animation based on splitting the frames of a single image.
   */
  public void setAnimation(TextureRegion[] reg, float delay)
  {
    animation.setFrames(reg, delay);
    width = reg[0].getRegionWidth();
    height = reg[0].getRegionHeight();
  }

  /*
   * go to the next frame
   */
  public void update(float dt)
  {
    animation.update(dt);
  }

  /*
   * Draw the animation on screen.
   */
  public void render(SpriteBatch sb)
  {
    sb.begin();
    sb.draw(animation.getFrame(), body.getPosition().x * B2DVars.PPM - width
        / 2, body.getPosition().y * B2DVars.PPM - width / 2);
    sb.end();
  }

  public Body getBody()
  {
    return body;
  }

  public Vector2 getPosition()
  {
    return body.getPosition();
  }

  public float getWidth()
  {
    return width;
  }

  public float getHeight()
  {
    return height;
  }
}
