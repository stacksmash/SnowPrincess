/**
 * @author Dan Kestell
 * 
 * Framework based off of Tutorial by ForeignGuyMike
 * https://www.youtube.com/playlist?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK
 */

package com.stacksmash.SnowPrincess;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import handlers.BoundedCamera;
import handlers.Content;
import handlers.GameStateManager;
import handlers.MyInput;
import handlers.MyInputProcessor;

/*
 * This class is where everything is initiated and sets up and starts the game.
 */
public class Game extends ApplicationAdapter
{
  public static final String TITLE = "Snow Princess";
  public static final int V_WIDTH = 480;
  public static final int V_HEIGHT = 360;
  public static final int SCALE = 2;

  public static final float STEP = 1 / 120f;
  private float accum;

  private SpriteBatch sb;
  private BoundedCamera cam;
  private OrthographicCamera hudCam;

  private GameStateManager gsm;
  
  public static Content res;

  @Override
  public void create()
  {
    Gdx.input.setInputProcessor(new MyInputProcessor());
    
    res = new Content();
    //Load all resources images, Music, SFX Etc.
    res.loadTexture("images/Ivy.png", "Ivy");
    res.loadTexture("images/snowflake.png", "snowflake");
    res.loadTexture("images/cloud.jpg", "cloud");
    res.loadTexture("images/mountains3.png", "mountains");
    res.loadTexture("images/Title.png", "title");
    res.loadTexture("images/play.png", "play");
    res.loadTexture("images/frame.png", "frame");
    res.loadTexture("images/lockframe.png", "lockframe");
    res.loadTexture("images/font.png", "font");
    res.loadTexture("images/snowflakeHUD.png", "snowflakeHUD");
    res.loadTexture("images/forward.png", "forward");
    res.loadTexture("images/backward.png", "backward");
    res.loadTexture("images/numneeded.png", "numneeded");
    res.loadTexture("images/flag.png", "flag");
    
    res.loadTexture("images/snowman/eyes.png", "eyes");
    res.loadTexture("images/snowman/l-arm.png", "l-arm");
    res.loadTexture("images/snowman/mouth.png", "mouth");
    res.loadTexture("images/snowman/nose.png", "nose");
    res.loadTexture("images/snowman/r-arm.png", "r-arm");
    res.loadTexture("images/snowman/snowball.png", "bottom");
    res.loadTexture("images/snowman/snowball2.png", "middle");
    res.loadTexture("images/snowman/snowball3.png", "head");
    
    res.loadSound("sounds/fail.mp3", "fail");
    res.loadSound("sounds/jump.mp3", "jump");
    res.loadSound("sounds/snowflake.mp3","snowflake");
    res.loadSound("sounds/success.mp3", "success");
    res.loadSound("sounds/failure.mp3", "failure");
    
    res.loadMusic("sounds/Music.mp3","music");
    res.getMusic("music").setLooping(true);
    res.getMusic("music").setVolume(0.3f);
    res.getMusic("music").play();
    
    sb = new SpriteBatch();
    cam = new BoundedCamera(); //Camera set to follow player
    cam.setToOrtho(false, V_WIDTH, V_HEIGHT);

    //Camera Stationary, set for displaying on screen stationary items.
    hudCam = new OrthographicCamera(); 
    hudCam.setToOrtho(false, V_WIDTH , V_HEIGHT);

    //Initiate new GameStateManager
    gsm = new GameStateManager(this);
  }

  /*
   * (non-Javadoc)
   * @see com.badlogic.gdx.ApplicationAdapter#render()
   */
  @Override
  public void render()
  {
    accum += Gdx.graphics.getDeltaTime();

    while (accum >= STEP)
    {
      accum -= STEP;
      gsm.update(STEP);
      gsm.render();
      MyInput.update();
    }

  }

  /*
   * (non-Javadoc)
   * @see com.badlogic.gdx.ApplicationAdapter#dispose()
   * 
   * disposes all reources once game is exited.
   */
  public void dispose()
  {
    res.removeAll();
  }

  public void resize(int w, int h)
  {
  }

  public void pause()
  {
  }

  public void resume()
  {
  }
  
  public SpriteBatch getSpriteBatch()
  {
    return sb;
  }

  public OrthographicCamera getCamera()
  {
    return cam;
  }

 public OrthographicCamera getHUDCamera()
  {
    return hudCam;
  }
}
