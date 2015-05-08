/**
 * @author Dan Kestell
 * 
 * Framework based off of Tutorial by ForeignGuyMike
 * https://www.youtube.com/playlist?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK
 */

package states;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import handlers.GameButton;
import handlers.GameStateManager;
import com.stacksmash.SnowPrincess.Game;

/*
 * This gameState sets up a grid of game buttons from which you select
 * the level you wish to play. There are currently only 5 levels so the 
 * remaining buttons are rendered with a lock image which disallows any 
 * presses of locked/non-existent levels.
 */
public class LevelSelect extends GameState
{

  private TextureRegion reg;

  private GameButton[][] buttons;

  public LevelSelect(GameStateManager gsm)
  {

    super(gsm); //Set the global gamestate manager.

    reg = new TextureRegion(Game.res.getTexture("cloud"), 0, 0, 1280, 1280);

    TextureRegion buttonReg = new TextureRegion(Game.res.getTexture("frame"), 0, 0, 64, 64);
    TextureRegion lockReg = new TextureRegion(Game.res.getTexture("lockframe"), 0, 0, 64, 64);
    
    buttons = new GameButton[5][5];
    for (int row = 0; row < buttons.length; row++)
    {
      for (int col = 0; col < buttons[0].length; col++)
      {
        if(row == 0)
        {
          buttons[row][col] = new GameButton(buttonReg, 80 + col * 80, 320 - row * 70, cam);
          buttons[row][col].setText(row * buttons[0].length + col + 1 + "");
        }
        else
        {
          buttons[row][col] = new GameButton(lockReg, 80 + col * 80, 320 - row * 70, cam);
        }
      }
    }
    
    cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
  }

  public void handleInput()
  {
  }

  //Determine if any button is clicked each clock cycle dt.
  public void update(float dt)
  {

    handleInput();

    for (int row = 0; row < buttons.length; row++)
    {
      for (int col = 0; col < buttons[0].length; col++)
      {
        buttons[row][col].update(dt);
        if (buttons[row][col].isClicked())
        {
          if(row == 0)
          {
            Play.level = row * buttons[0].length + col + 1;
            Game.res.getSound("snowflake").play();
            gsm.setState(GameStateManager.PLAY);
          }
        }
      }
    }

  }

  //Draw each button.
  public void render()
  {

    sb.setProjectionMatrix(cam.combined);

    sb.begin();
    sb.draw(reg, 0, 0);
    sb.end();

    for (int row = 0; row < buttons.length; row++)
    {
      for (int col = 0; col < buttons[0].length; col++)
      {
        buttons[row][col].render(sb);
      }
    }

  }

  public void dispose()
  {
    // everything is in the resource manager
  }

}
