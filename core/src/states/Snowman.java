/**
 * @author Dan Kestell
 */
package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.stacksmash.SnowPrincess.Game;
import handlers.Background;
import handlers.GameButton;
import handlers.GameStateManager;
import handlers.MyInput;

/**
 * Snowman is a game state that is called after successfully completing every
 * third level. This state allows you to use touch gestures to piece together
 * images to create a snowman.
 */
public class Snowman extends GameState
{
  //Forward and back buttons to choose which item you are currently moving
  private GameButton fbutton; 
  private GameButton bbutton;  
  
  private Background bg;
  private Background bg2;
  
  private Texture[] textures = new Texture[8];
  private TextureRegion[] snowman = new TextureRegion[8];
  
  private int partTracker = 0;
  
  private int[] snowmanX = new int[8];
  private int[] snowmanY = new int[8];
  
  private float currentX;
  private float currentY;
  
  Vector3 vec;

  public Snowman(GameStateManager gsm)
  {
    super(gsm);
    Texture tex = Game.res.getTexture("mountains");
    Texture tex2 = Game.res.getTexture("cloud");

    bg = new Background(new TextureRegion(tex), cam, 0f); //Set the background
    bg2 = new Background(new TextureRegion(tex2), cam, 0.7f);

    bg2.setVector(-20, 0); //Move the backgrounds

    //Set up the buttons that select which part you are modifying.
    tex = Game.res.getTexture("forward");
    fbutton = new GameButton(new TextureRegion(tex, 0, 0, 75, 75), 440, 40, cam);
    
    tex = Game.res.getTexture("backward");
    bbutton = new GameButton(new TextureRegion(tex, 0, 0, 75, 75), 40, 40, cam);
    
    initiateSnowman(); //Set up all images to build the snowman.
    
    vec = new Vector3();
    cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
  }

  private void initiateSnowman()
  {
    //import images
    textures[0] = Game.res.getTexture("bottom");
    textures[1] = Game.res.getTexture("middle");
    textures[2] = Game.res.getTexture("head");
    textures[3] = Game.res.getTexture("eyes");
    textures[4] = Game.res.getTexture("mouth");
    textures[5] = Game.res.getTexture("nose");
    textures[6] = Game.res.getTexture("l-arm");
    textures[7] = Game.res.getTexture("r-arm");
   
    //assign images to regions
    snowman[0] = new TextureRegion(textures[0]);
    snowman[1] = new TextureRegion(textures[1]);
    snowman[2] = new TextureRegion(textures[2]);
    snowman[3] = new TextureRegion(textures[3]);
    snowman[4] = new TextureRegion(textures[4]);
    snowman[5] = new TextureRegion(textures[5]);
    snowman[6] = new TextureRegion(textures[6]);
    snowman[7] = new TextureRegion(textures[7]);
    
    //set starting point
    for (int i = 0; i < snowmanX.length; i++)
    {
      snowmanX[i] = 90;
      snowmanY[i] = 80;
    }
  }

  /*
   * Sense touch gestures in order to move snowman parts on-screen. Also
   * register button presses to draw and move onto next snowman part.
   */
  @Override
  public void handleInput()
  {
    if(MyInput.isDragged())
    {
      snowmanX[partTracker] = (int) currentX;
      snowmanY[partTracker] = (int) currentY;
    }
    
    if (fbutton.isClicked())
    {
      Game.res.getSound("snowflake").play();
      partTracker++;
      if(partTracker >= 8) 
      {
        Game.res.getSound("success").play();
        gsm.setState(GameStateManager.LEVEL_SELECT); 
      }
    }
    
    if (bbutton.isClicked() && partTracker > 0)
    {
      Game.res.getSound("snowflake").play();
      partTracker--;
    }
  }

  /*
   * Update items to the current state of the game.
   */
  @Override
  public void update(float dt)
  {
    vec.set(MyInput.x, MyInput.y, 0);
    cam.unproject(vec);
    
    currentX = vec.x;
    currentY = vec.y;
    
    handleInput();

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    bg.update(dt);
    bg2.update(dt);
    
    fbutton.update(dt);
    bbutton.update(dt);
  }

  /*
   * Draw all the images up to the current image being worked with.
   */
  @Override
  public void render()
  {

    sb.setProjectionMatrix(hudCam.combined);

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    bg2.render(sb); //Draw the Backgrounds.
    bg.render(sb);
    
    sb.begin();
    
    //draw each part of the snowman that has been placed.
    for (int i = 0; i <= partTracker; i++)
    {
      int offsetX = snowman[i].getRegionWidth()/2;
      int offsetY = snowman[i].getRegionHeight()/2;
      
      sb.draw(snowman[i], snowmanX[i] - offsetX, snowmanY[i] - offsetY);
    }
    sb.end();
    
    fbutton.render(sb); //draw the buttons.
    bbutton.render(sb);
  }

  @Override
  public void dispose()
  {
    
  }
}
