/**
 * @author Dan Kestell
 * 
 * Framework based off of Tutorial by ForeignGuyMike
 * https://www.youtube.com/playlist?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK
 */

package states;

import static handlers.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import entities.HUD;
import entities.Player;
import entities.Snowflake;
import entities.WinFlag;
import handlers.B2DVars;
import handlers.Background;
import handlers.BoundedCamera;
import handlers.GameStateManager;
import handlers.MyContactListener;
import handlers.MyInput;

import com.stacksmash.SnowPrincess.Game;

/*
 * Play is where the vast majority of game logic is processed. It is where we 
 * set up the world and render the player in order to play the main portion
 * of the game.
 */
public class Play extends GameState
{
  private World world;
  private Box2DDebugRenderer b2dr;

  private BoundedCamera b2dCam;

  private MyContactListener cl;

  private TiledMap tileMap;
  private int tileMapWidth;
  private int tileMapHeight;
  private float tileSize;
  private OrthogonalTiledMapRenderer tmr;

  private Player player;
  private Array<Snowflake> snowflakes;
  private WinFlag flag;

  private Background bkgrnds[];

  private HUD hud;

  public static int level;
  private float camHeight;
  
  private int snowflakeGoal;

  public Play(GameStateManager gsm)
  {
    super(gsm);
    setSnowflakeGoal();
    // set up box2d stuff
    world = new World(new Vector2(0, -9.81f), true);
    cl = new MyContactListener();
    world.setContactListener(cl);
    b2dr = new Box2DDebugRenderer();
    camHeight = Game.V_HEIGHT / 2;

    // create player
    createPlayer();

    // create tiles
    createTiles();

    // create snowflakes
    createSnowflakes();
    player.setSnowflakesGoal(snowflakeGoal);
    
    createFlag();

    // background
    Texture bg = Game.res.getTexture("mountains");
    TextureRegion mountains = new TextureRegion(bg, 0, -50, 562, 360);
    Texture cloud = Game.res.getTexture("cloud");
    TextureRegion clouds = new TextureRegion(cloud, 0, 0, 1280, 1280);
    bkgrnds = new Background[2];

    bkgrnds[0] = new Background(clouds, cam, 0f);
    bkgrnds[1] = new Background(mountains, cam, 0.2f);

    // set up b2dcam
    b2dCam = new BoundedCamera();
    b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
    b2dCam.setBounds(0, (tileMapWidth * tileSize) / PPM, 0,
        (tileMapHeight * tileSize) / PPM);
    
    // set up hud
    hud = new HUD(player);
  }

  /*
   * Sets the number of snowflakes needed to be collected in order to pass
   * a given level.
   */
  private void setSnowflakeGoal()
  {
    switch(level)
    {
      case 1: snowflakeGoal = 1; break;
      case 2: snowflakeGoal = 16; break;
      case 3: snowflakeGoal = 17; break;
      case 4: snowflakeGoal = 16; break;
      case 5: snowflakeGoal = 26; break;
      default: break;
    }
    
  }

  /*
   * Add force to player to cause a jump only if the player is on the ground.
   */
  private void playerJump()
  {
    if (cl.isPlayerOnGround())
    {
      
      player.getBody().applyForceToCenter(0, 600, true);
      Game.res.getSound("jump").play();
    }
  }

  /*
   * Listen to mouse/touch and keyboard events to control the character in the
   * game.
   */
  @Override
  public void handleInput()
  {
    // player jump desktop
    if (MyInput.isPressed(MyInput.BUTTON1))
    {
      playerJump();
    }

    // player Jump with touch
    if (MyInput.isPressed())
    {
      playerJump();
    }

  }

  /*
   * Update all the events in the world. This includes:
   *  1) Snowflake collection.
   *  2) Animation Rendering.
   *  3) Flag detection
   */
  @Override
  public void update(float dt)
  {
    handleInput();

    world.step(dt, 6, 2);

    // remove snowflakes
    Array<Body> bodies = cl.getBodiesToRemove();
    for (int i = 0; i < bodies.size; i++)
    {
      Body b = bodies.get(i);
      snowflakes.removeValue((Snowflake) b.getUserData(), true);
      world.destroyBody(b);
      player.collectSnowflake();
      Game.res.getSound("snowflake").play();
    }

    bodies.clear();

    player.update(dt);

    for (int i = 0; i < snowflakes.size; i++)
    {
      snowflakes.get(i).update(dt);
    }
    
    flag.update(dt);
  }

  /*
   * Draw all of the images on the game, update the camera, render the backgrounds
   * etc.
   */
  @Override
  public void render()
  {
    // clear screen
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    sb.setProjectionMatrix(hudCam.combined);
    for (int i = 0; i < bkgrnds.length; i++)
    {
      bkgrnds[i].render(sb);
    }

    // set camera to follow player
    cam.position.set(player.getPosition().x * PPM + Game.V_WIDTH / 4, camHeight, 0);
    if(camHeight < player.getPosition().y * PPM - 100) camHeight++;
    if(camHeight > player.getPosition().y * PPM && camHeight > Game.V_HEIGHT / 2) camHeight = player.getPosition().y * PPM;

    cam.update();
    if (player.getBody().getLinearVelocity().x < 0.1)
    {
      player.getBody().setLinearVelocity(0.8f, player.getBody().getLinearVelocity().y);
    }
    // draw tile map
    tmr.setView(cam);
    tmr.render();

    // draw player
    sb.setProjectionMatrix(cam.combined);
    player.render(sb);

    // draw snowflakes
    for (int i = 0; i < snowflakes.size; i++)
    {
      snowflakes.get(i).render(sb);
    }
    
    //draw winFlag
    flag.render(sb);
    
    // draw HUD
    sb.setProjectionMatrix(hudCam.combined);
    hud.render(sb);
    
    // draw box2d world
    // b2dr.render(world, b2dCam.combined);
    
    //Detect if player completed a level
    if(cl.atFlag)
    {
      if(player.getNumSnowflakes() >= snowflakeGoal)
      {
        Game.res.getSound("success").play();
        //Change to build a snowman every third level
        if(level % 3 == 0)
        {
          gsm.setState(GameStateManager.SNOWMAN);
        }
        else gsm.setState(GameStateManager.LEVEL_SELECT);
      }
      else //Player failed to get enough snowflakes. :(
      {
        Game.res.getSound("failure").play();
        gsm.setState(GameStateManager.MENU);
      }
    }

    // check player failed
    if (player.getBody().getPosition().y * PPM < -64)
    {
      Game.res.getSound("fail").play();
      gsm.setState(GameStateManager.MENU);
    }
  }

  @Override
  public void dispose()
  {

  }

  /*
   * Create and initiate a new Box2D player. Set the initial states of the body.
   */
  public void createPlayer()
  {

    BodyDef bdef = new BodyDef();
    FixtureDef fdef = new FixtureDef();
    PolygonShape shape = new PolygonShape();

    // create player
    bdef.position.set(0f / PPM, 100f / PPM);
    bdef.type = BodyType.DynamicBody;
    bdef.linearVelocity.set(0.8f, 0);
    Body body = world.createBody(bdef);

    shape.setAsBox(12f / PPM, 26f / PPM);
    fdef.shape = shape;
    fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
    fdef.filter.maskBits = B2DVars.BIT_GROUND | B2DVars.BIT_SNOWFLAKE;
    body.createFixture(fdef).setUserData("player");

    // create foot sensor
    shape.setAsBox(12f / PPM, 4f / PPM, new Vector2(0, -25f / PPM), 0);
    fdef.shape = shape;
    fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
    fdef.filter.maskBits = B2DVars.BIT_GROUND;
    fdef.isSensor = true;
    body.createFixture(fdef).setUserData("foot");

    // create player
    player = new Player(body);

    body.setUserData(player);

  }

  /*
   * Initate information for the current level and call to have their collidable
   * bodies rendered in game.
   */
  private void createTiles()
  {
    // load tile map
    try
    {
      tileMap = new TmxMapLoader().load("tiles/level" + level + ".tmx");
    } catch (Exception e)
    {
      System.out.println("Cannot find file: tiles/level" + level + ".tmx");
      Gdx.app.exit();
    }
    tmr = new OrthogonalTiledMapRenderer(tileMap);
    //Set tileMap properties for the current level.
    tileMapWidth = (int) tileMap.getProperties().get("width", Integer.class);
    tileMapHeight = (int) tileMap.getProperties().get("height", Integer.class);
    tileSize = (int) tileMap.getProperties().get("tilewidth", Integer.class);

    TiledMapTileLayer layer;
    layer = (TiledMapTileLayer) tileMap.getLayers().get("collisionlayer");
    createLayer(layer, B2DVars.BIT_GROUND);
  }

  /*
   * Iterate through the given layer and create Box2D bodies for them. This
   * allows for collision detection.
   */
  public void createLayer(TiledMapTileLayer layer, short bits)
  {
    BodyDef bdef = new BodyDef();
    FixtureDef fdef = new FixtureDef();

    // go through all the cells in the layer
    for (int row = 0; row < layer.getHeight(); row++)
    {
      for (int col = 0; col < layer.getWidth(); col++)
      {
        // get cell
        Cell cell = layer.getCell(col, row);

        // check for cell
        if (cell == null)
          continue;
        if (cell.getTile() == null)
          continue;

        // create body + fixture from cell
        bdef.type = BodyType.StaticBody;
        bdef.position.set((float) ((col + 0.5f) * tileSize / PPM),
            (float) ((row + 0.5f) * tileSize / PPM));

        ChainShape cs = new ChainShape();
        Vector2[] v = new Vector2[3];
        v[0] = new Vector2(-tileSize / 2.0f / PPM, -tileSize / 2.0f / PPM);
        v[1] = new Vector2(-tileSize / 2.0f / PPM, tileSize / 2.0f / PPM);
        v[2] = new Vector2(tileSize / 2.0f / PPM, tileSize / 2.0f / PPM);

        cs.createChain(v);

        fdef.friction = 0;
        fdef.shape = cs;
        fdef.filter.categoryBits = bits;
        fdef.filter.maskBits = B2DVars.BIT_PLAYER;
        fdef.isSensor = false;

        world.createBody(bdef).createFixture(fdef);
      }
    }
  }
  
  /*
   * Set the winFlag at the end of the level.
   */
  private void createFlag()
  {
    flag = new WinFlag(null);
    MapLayer layer = tileMap.getLayers().get("flag");
    
    BodyDef bdef = new BodyDef();
    FixtureDef fdef = new FixtureDef();
    for (MapObject mo: layer.getObjects())
    {
      bdef.type = BodyType.StaticBody;
      float x = (float) mo.getProperties().get("x", Float.class) / PPM;
      float y = (float) mo.getProperties().get("y", Float.class) / PPM;
      
      bdef.position.set(x,y);
      
      PolygonShape shape = new PolygonShape();
      shape.setAsBox(10 / PPM, 66f / PPM);
   
      fdef.shape = shape;
      fdef.isSensor = true;
      fdef.filter.categoryBits = B2DVars.BIT_SNOWFLAKE;
      fdef.filter.maskBits = B2DVars.BIT_PLAYER;

      Body body = world.createBody(bdef);
      body.createFixture(fdef).setUserData("flag");

      flag = new WinFlag(body);

      body.setUserData(flag);
    }
  }
  
  /*
   * Locate, initiate, and render all of the snowflakes in the current level.
   */
  private void createSnowflakes()
  {
    snowflakes = new Array<Snowflake>();

    //Locate all snowflakes
    MapLayer layer = tileMap.getLayers().get("snowflakes");

    BodyDef bdef = new BodyDef();
    FixtureDef fdef = new FixtureDef();

    //Get all the snowflakes, set their body and fixture definitions and add 
    // them to the Box2D world.
    for (MapObject mo : layer.getObjects())
    {
      bdef.type = BodyType.StaticBody;
      float x = (float) mo.getProperties().get("x", Float.class) / PPM;
      float y = (float) mo.getProperties().get("y", Float.class) / PPM;

      bdef.position.set(x, y);

      CircleShape cshape = new CircleShape();
      cshape.setRadius(12.5f / PPM);

      fdef.shape = cshape;
      fdef.isSensor = true;
      fdef.filter.categoryBits = B2DVars.BIT_SNOWFLAKE;
      fdef.filter.maskBits = B2DVars.BIT_PLAYER;

      Body body = world.createBody(bdef);
      body.createFixture(fdef).setUserData("snowflake");

      Snowflake s = new Snowflake(body);
      snowflakes.add(s);

      body.setUserData(s);
    }
  }
}
