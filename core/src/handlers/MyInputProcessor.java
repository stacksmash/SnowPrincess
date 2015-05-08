/**
 * @author Dan Kestell
 * 
 * Framework based off of Tutorial by ForeignGuyMike
 * https://www.youtube.com/playlist?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK
 */

package handlers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

/*
 * Processes all input from touch, keyboard, and mouse. Also handles which
 * keys or actions are used as controls in the game.
 */
public class MyInputProcessor extends InputAdapter
{
  public boolean mouseMoved(int x, int y)
  {
    MyInput.x = x;
    MyInput.y = y;
    return true;
  }
  
  public boolean touchDragged (int x, int y, int pointer)
  {
    MyInput.x = x;
    MyInput.y = y;
    MyInput.down = true;
    MyInput.dragged = true;
    return true;
  }
  
  public boolean touchDown(int x , int y, int pointer, int button)
  {
    MyInput.x = x;
    MyInput.y = y;
    MyInput.down = true;
    return true;
  }
  
  public boolean touchUp (int x, int y, int pointer, int button)
  {
    MyInput.x = x;
    MyInput.y = y;
    MyInput.down = false;
    MyInput.dragged = false;
    return true;
  }
  
  public boolean keyDown(int k)
  {
    if(k == Keys.SPACE)
    {
      MyInput.setKey(MyInput.BUTTON1, true);
      
    }
    if(k == Keys.X)
    {
      MyInput.setKey(MyInput.BUTTON2, true);
    }
    return true;
  }
  
  public boolean keyUp(int k)
  {
    if(k == Keys.SPACE)
    {
      MyInput.setKey(MyInput.BUTTON1, false);
      
    }
    if(k == Keys.X)
    {
      MyInput.setKey(MyInput.BUTTON2, false);
    }
    
    return true;
  }
}
