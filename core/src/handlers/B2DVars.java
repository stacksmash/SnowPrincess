/**
 * @author Dan Kestell
 * 
 * Framework based off of Tutorial by ForeignGuyMike
 * https://www.youtube.com/playlist?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK
 */

package handlers;

/*
 * Keeps track of the variable needed in our Box2D world including the Pixels
 * Per Meter conversion factor and the bit values of each Box2D object in the
 * world. Collision is based on bit filtering, so each value must be a power of
 * two.
 */
public class B2DVars
{
  //Pixels per meter
  public static final int PPM = 100;
  
  //category bits
  public static final short BIT_PLAYER = 2;
  public static final short BIT_GROUND = 4;
  public static final short BIT_SNOWFLAKE = 8;
  
}
