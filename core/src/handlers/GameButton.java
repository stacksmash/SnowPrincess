/**
 * @author Dan Kestell
 * 
 * Framework based off of Tutorial by ForeignGuyMike
 * https://www.youtube.com/playlist?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK
 */

package handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.stacksmash.SnowPrincess.Game;

/**
 * Simple image button. Renders an images and detects mouse events.
 */
public class GameButton
{

  // center at x, y
  private float x;
  private float y;
  private float width;
  private float height;

  private TextureRegion reg;

  Vector3 vec;
  private OrthographicCamera cam;

  private boolean clicked;

  private String text;
  private TextureRegion[] font;

  public GameButton(TextureRegion reg, float x, float y, OrthographicCamera cam)
  {

    this.reg = reg;
    this.x = x;
    this.y = y;
    this.cam = cam;

    width = reg.getRegionWidth();
    height = reg.getRegionHeight();
    vec = new Vector3();

    Texture tex = Game.res.getTexture("font");
    font = new TextureRegion[11];

    for (int i = 0; i < 11; i++)
    {
      font[i] = new TextureRegion(tex, i * 14, 0, 14, 18);
    }

  }

  //Returns true if the button is clicked on screen.
  public boolean isClicked()
  {
    return clicked;
  }

  //Set text associated with a button.
  public void setText(String s)
  {
    text = s;
  }

  //Update the state of the button.
  public void update(float dt)
  {

    vec.set(MyInput.x, MyInput.y, 0);
    cam.unproject(vec);

    if (MyInput.isPressed() && vec.x > x - width / 2 && vec.x < x + width / 2
        && vec.y > y - height / 2 && vec.y < y + height / 2)
    {
      clicked = true;
    } else
    {
      clicked = false;
    }

  }

  //Draws the button
  public void render(SpriteBatch sb)
  {

    sb.begin();

    sb.draw(reg, x - width / 2, y - height / 2);

    if (text != null)
    {
      drawString(sb, text, x, y);
    }

    sb.end();

  }

  //Draws a string inside the button. This is used primarily in the level map.
  private void drawString(SpriteBatch sb, String s, float x, float y)
  {
    int len = s.length();
    float xo = len * font[0].getRegionWidth() / 2;
    float yo = font[0].getRegionHeight() / 2;
    for (int i = 0; i < len; i++)
    {
      char c = s.charAt(i);
      
      if (c == '/')
        c = 10;
      else if (c >= '0' && c <= '9')
      {
        c -= '0';
      }
      else
        continue;
      sb.draw(font[c], x + i * 11 - xo, y - yo);
    }
  }

}
