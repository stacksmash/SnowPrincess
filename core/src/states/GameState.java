/**
 * @author Dan Kestell
 * 
 * Framework based off of Tutorial by ForeignGuyMike
 * https://www.youtube.com/playlist?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK
 */

package states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import handlers.GameStateManager;
import com.stacksmash.SnowPrincess.Game;

/*
 * An abstract class used to define the base features of each game state.
 */
public abstract class GameState 
{
  protected GameStateManager gsm;
  protected Game game;
  
  protected SpriteBatch sb;
  protected OrthographicCamera cam;
  
  protected OrthographicCamera hudCam;
  
  //Set up the gamestate manager.
  protected GameState(GameStateManager gsm)
  {
    this.gsm = gsm;
    game = gsm.game();
    sb = game.getSpriteBatch();
    cam = game.getCamera();
    hudCam = game.getHUDCamera();
  }
  
  //Default classes in each gameState.
  public abstract void handleInput();
  public abstract void update(float dt);
  public abstract void render();
  public abstract void dispose();
}
