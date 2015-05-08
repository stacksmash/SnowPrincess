/**
 * @author Dan Kestell
 * 
 * Framework based off of Tutorial by ForeignGuyMike
 * https://www.youtube.com/playlist?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK
 */

package handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stacksmash.SnowPrincess.Game;

/*
 * Background handles the setting and rendering of background images. In 
 * addition, because my game implements a parallax effect, each background has a
 * scale by which to move to achieve that effect.
 */
public class Background
{

  private TextureRegion image;
  private OrthographicCamera gameCam;
  private float scale;

  private float x;
  private float y;
  private int numDrawX;

  private float dx;
  private float dy;

  public Background(TextureRegion image, OrthographicCamera gameCam, float scale)
  {
    this.image = image;
    this.gameCam = gameCam;
    this.scale = scale;
    numDrawX = Game.V_WIDTH / image.getRegionWidth() + 2;
  }

  //Sets the vector associated with on screen display
  public void setVector(float dx, float dy)
  {
    this.dx = dx;
    this.dy = dy;
  }

  //updates the position of the background based on a scaling factor.
  public void update(float dt)
  {
    x += (dx * scale) * dt;
    y += (dy * scale) * dt;
  }

  /*
   * Draw the background
   */
  public void render(SpriteBatch sb)
  {

    float x = ((this.x + gameCam.viewportWidth / 2 - gameCam.position.x) * scale)
        % image.getRegionWidth();
    float y = ((this.y + gameCam.viewportHeight / 2 - gameCam.position.y) * scale)
        % image.getRegionHeight();

    sb.begin();

    int colOffset = x > 0 ? -1 : 0;
    for (int col = 0; col < numDrawX; col++)
    {
      sb.draw(image, x + (col + colOffset) * image.getRegionWidth(), y );
    }
      
    sb.end();

  }

}
