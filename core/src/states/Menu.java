/**
 * @author Dan Kestell
 * 
 * Framework based off of Tutorial by ForeignGuyMike
 * https://www.youtube.com/playlist?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK
 */
package states;

import static handlers.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import handlers.Animation;
import handlers.Background;
import handlers.GameButton;
import handlers.GameStateManager;

import com.stacksmash.SnowPrincess.Game;

/*
 * Simple gameState. Meant to be an intro screen. Displays graphics and a 
 * single button that prompts the user to begin and select a level.
 */
public class Menu extends GameState
{

  private boolean debug = false;

  //Set up background graphics.
  private Background bg;
  private Background bg2;
  private Background bg3;
  private Animation animation;
  private GameButton playButton;

  private World world;
  private Box2DDebugRenderer b2dRenderer;

  public Menu(GameStateManager gsm)
  {

    super(gsm); //Set global gameStateManager.

    Texture tex = Game.res.getTexture("mountains");
    Texture tex2 = Game.res.getTexture("cloud");
    bg = new Background(new TextureRegion(tex), cam, 1f);
    bg2 = new Background(new TextureRegion(tex2), cam, 0f);

    tex = Game.res.getTexture("title");
    bg3 = new Background(new TextureRegion(tex), cam, 0f);

    bg.setVector(-20, 0);

    tex = Game.res.getTexture("Ivy");
    TextureRegion[] reg = new TextureRegion[4];
    for (int i = 0; i < reg.length; i++)
    {
      reg[i] = new TextureRegion(tex, i * 64, 0, 64, 64);
    }
    animation = new Animation(reg, 1 / 12f);

    tex = Game.res.getTexture("play");
    playButton = new GameButton(new TextureRegion(tex, 0, 0, 72, 34), 240, 200, cam);

    cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);

    world = new World(new Vector2(0, -9.8f * 5), true);
    b2dRenderer = new Box2DDebugRenderer();

  }

  //Detect mouse/key events
  public void handleInput()
  {

    // mouse/touch input
    if (playButton.isClicked())
    {
      Game.res.getSound("snowflake").play();
      gsm.setState(GameStateManager.LEVEL_SELECT);
    }

  }

  //Update the state to register touches as well as step forward in the animations.
  /*
   * (non-Javadoc)
   * @see states.GameState#update(float)
   */
  public void update(float dt)
  {

    handleInput();

    world.step(dt / 5, 8, 3);

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    bg.update(dt);
    bg2.update(dt);
    bg3.update(dt);

    animation.update(dt);

    playButton.update(dt);

  }
  
  /*
   * (non-Javadoc)
   * @see states.GameState#render()
   */
  //Draw the world
  public void render()
  {

    sb.setProjectionMatrix(cam.combined);

    // draw background
    bg2.render(sb);
    bg.render(sb);
    bg3.render(sb);

    // draw button
    playButton.render(sb);

    // draw Ivy
    sb.begin();
    sb.draw(animation.getFrame(), 208, 31);
    sb.end();

    // debug draw box2d
    if (debug)
    {
      cam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
      b2dRenderer.render(world, cam.combined);
      cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
    }

  }

  public void dispose()
  {

  }

}
