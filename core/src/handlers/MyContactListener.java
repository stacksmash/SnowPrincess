/**
 * @author Dan Kestell
 * 
 * Framework based off of Tutorial by ForeignGuyMike
 * https://www.youtube.com/playlist?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK
 */
package handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

/*
 * Contact Listener is responsible for sensing collisions to be used for 
 * updating the players state. This is used in collection of snowflakes as well
 * as most of the game physics.
 */
public class MyContactListener implements ContactListener
{
  private int numFootContacts;
  private Array<Body> bodiesToRemove;
  public Boolean atFlag = false;
  
  public MyContactListener()
  {
    super();
    
    bodiesToRemove = new Array<Body>();
  }
  
  //called when 2 fixtures start to collide
  @Override
  public void beginContact(Contact c)
  {
    Fixture fa = c.getFixtureA();
    Fixture fb = c.getFixtureB();
    
    if(fa.getUserData() != null && fa.getUserData().equals("foot"))
    {
      numFootContacts++;
    }
    if(fb.getUserData() != null && fb.getUserData().equals("foot"))
    {
      numFootContacts++;
    }
    if(fa.getUserData() != null && fa.getUserData().equals("snowflake"))
    {
      //remove snowflake
      bodiesToRemove.add(fa.getBody());

    }
    if(fb.getUserData() != null && fb.getUserData().equals("snowflake"))
    {
      //remove snowflake
      bodiesToRemove.add(fb.getBody());
      
    }
    //Detect if user finished the level
    if(fa.getUserData() != null && fa.getUserData().equals("flag"))
    {
      atFlag = true;
    }
    if(fb.getUserData() != null && fb.getUserData().equals("flag"))
    {
      atFlag = true;
    }
    
    
  }

  //called when two fixtures no longer collide
  @Override
  public void endContact(Contact c)
  {
    Fixture fa = c.getFixtureA();
    Fixture fb = c.getFixtureB();
    
    if(fa == null || fb == null) return;
    
    if(fa.getUserData() != null && fa.getUserData().equals("foot"))
    {
      numFootContacts--;
    }
    if(fb.getUserData() != null && fb.getUserData().equals("foot"))
    {
      numFootContacts--;
    }
  }
  
  public boolean isPlayerOnGround()
  {
    return numFootContacts > 0;
  }
  
  public Array<Body> getBodiesToRemove()
  {
    return bodiesToRemove;
  }

  @Override
  public void preSolve(Contact contact, Manifold oldManifold)
  {
  }

  @Override
  public void postSolve(Contact contact, ContactImpulse impulse)
  {
  }
}
