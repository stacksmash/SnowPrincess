/**
 * @author Dan Kestell
 * 
 * Framework based off of Tutorial by ForeignGuyMike
 * https://www.youtube.com/playlist?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK
 */

package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stacksmash.SnowPrincess.Game;

/*
 * HUD is responsible for all the Heads-Up-Display elements of the game. 
 * Including counting snowflakes and displaying how many are neccessary to beat
 * a given level. This camera is stationary, and does not move relative to the 
 * world. It simple shows what needs to be seen on screen.
 */
public class HUD
{
  private Player player;
  private TextureRegion snowflake;
  private TextureRegion num;
  private TextureRegion[] font;

  public HUD(Player player)
  {
    this.player = player;

    Texture tex = Game.res.getTexture("font");
    font = new TextureRegion[11];

    //Split up number elements of the font
    for (int i = 0; i < 11; i++)
    {
      font[i] = new TextureRegion(tex, i * 14, 0, 14, 18);
    }
    
    //To draw image of snowflake.
    tex = Game.res.getTexture("snowflakeHUD");
    snowflake = new TextureRegion(tex, 0, 0, 32, 32);
    //Display number of snowlfakes needed.
    tex = Game.res.getTexture("numneeded");
    num = new TextureRegion(tex, 0, 0, 157, 18);
  }

  /*
   * Draw the HUD on screen.
   */
  public void render(SpriteBatch sb)
  {
    sb.begin();
    drawString(sb,
        player.getNumSnowflakes() + " / " + player.getSnowflakesGoal(), 40, 330);
    sb.draw(snowflake, 5, 322);
    sb.draw(num, 114, 328);
    sb.end();
  }

  /*
   * Convert a string into a drawable image for the HUD.
   */
  private void drawString(SpriteBatch sb, String s, float x, float y)
  {
    for (int i = 0; i < s.length(); i++)
    {
      char c = s.charAt(i);
      if (c == '/')
      {
        c = 10;
      }
      else if (c >= '0' && c <= '9')
      {
        c -= '0';
      }
      else continue;
      sb.draw(font[c], x + i * 9, y);
    }
  }
}
