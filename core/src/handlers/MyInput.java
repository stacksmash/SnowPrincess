/**
 * @author Dan Kestell
 * 
 * Framework based off of Tutorial by ForeignGuyMike
 * https://www.youtube.com/playlist?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK
 */
package handlers;

/*
 * Custom handler used for keyboard and mouse events. This is used for both 
 * the desktop and Android versions of the game.
 */
public class MyInput
{
  public static int x;
  public static int y;
  public static boolean down;
  public static boolean pdown;
  public static boolean dragged;
  
  public static boolean[] keys;
  public static boolean[] pkeys;
  
  public static final int NUM_KEYS = 2;
  public static final int BUTTON1 = 0;
  public static final int BUTTON2 = 1;
  
  static
  {
    keys = new boolean[NUM_KEYS];
    pkeys = new boolean[NUM_KEYS];
  }
  
  //Update the state of the key or mouse presses.
  public static void update()
  {
    pdown = down;
    for( int i = 0; i < NUM_KEYS; i++)
    {
      pkeys[i] = keys[i];
    }
  }
  
  /*
   * returns true if key or mouse or touch is currently pressed.
   */
  public static boolean isDown() 
  {
    return down;
  }
  
  /*
   * returns true if key is pressed
   */
  public static boolean isPressed()
  {
    return down && !pdown;
  }
  
  /*
   * returns true to notify when action is finished.
   */
  public static boolean isReleased()
  {
    return !down && pdown;
  }

  //Set condition of a given key.
  public static void setKey(int i, boolean b)
  {
    keys[i] = b;
  }
 
  public static boolean isDown(int i)
  {
    return keys[i];
  }
  
  public static boolean isPressed(int i)
  {
    return keys[i] && !pkeys[i];
  }
  
  public static boolean isDragged()
  {
    return dragged;
  }
}
