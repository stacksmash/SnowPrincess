/**
 * @author Dan Kestell
 * 
 * Framework based off of Tutorial by ForeignGuyMike
 * https://www.youtube.com/playlist?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK
 */

package handlers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/*
 * Handles animations of Box2D sprites in the Box2D world. This includes setting
 * the animation regions and updating each frame of the animation to the next.
 */
public class Animation
{
  private TextureRegion[] frames;
  private float time;
  private float delay;
  private int currentFrame;
  private int timesPlayed;

  public Animation()
  {

  }

  public Animation(TextureRegion[] frames)
  {
    this(frames, 1 / 12f);
  }

  public Animation(TextureRegion[] frames, float delay)
  {
    this.frames = frames;
    this.delay = delay;
    time = 0;
    currentFrame = 0;
  }

  public void setDelay(float f)
  {
    delay = f;
  }

  public void setCurrentFrame(int i)
  {
    if (i < frames.length)
      currentFrame = i;
  }

  public void setFrames(TextureRegion[] frames, float delay)
  {
    this.frames = frames;
    this.delay = delay;
    time = 0;
    currentFrame = 0;
    timesPlayed = 0;
  }

  /*
   * Update game based on chang in time dt.
   */
  public void update(float dt)
  {
    if (delay <= 0)
      return;
    time += dt;
    while (time >= delay)
    {
      step();
    }
  }

  /*
   * step to the next frame in the animation.
   */
  public void step()
  {
    time -= delay;
    currentFrame++;
    //loop to beginning
    if (currentFrame == frames.length)
    {
      currentFrame = 0;
      timesPlayed++;
    }
  }

  //Returns the current frame in the animation.
  public TextureRegion getFrame()
  {
    return frames[currentFrame];
  }

  //Returns the number of times an animation has played.
  public int getTimesPlayed()
  {
    return timesPlayed;
  }

  //Returns true if the animation has played only once.
  public boolean hasPlayedOnce()
  {
    return timesPlayed > 0;
  }
}
