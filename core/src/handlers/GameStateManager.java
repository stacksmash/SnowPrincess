/**
 * @author Dan Kestell
 * 
 * Framework based off of Tutorial by ForeignGuyMike
 * https://www.youtube.com/playlist?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK
 */

package handlers;

import java.util.Stack;

import states.GameState;
import states.LevelSelect;
import states.Menu;
import states.Play;
import states.Snowman;

import com.stacksmash.SnowPrincess.Game;

/*
 * GameStateManager is responsible for changing to and from each mode in the 
 * game. This allows to change from the menu to the level select to the game 
 * etc.
 */
public class GameStateManager
{
   private Game game;
   
   private Stack<GameState> gameStates;
   
   //These numbers are arbitrary. I could have used a different identifier
   // but this was easiest.
   public static final int MENU = 908047;
   public static final int PLAY = 2310;
   public static final int LEVEL_SELECT = -8482939;
   public static final int SNOWMAN = 42799238;
   
   public GameStateManager(Game game)
   {
     this.game = game;
     gameStates = new Stack<GameState>();
     pushState(MENU);
   }
   
   public Game game() {return game;} 
   
   //update the GameState and switch if neccessary.
   public void update(float dt)
   {
     gameStates.peek().update(dt);
   }
   
   //Draw current game state.
   public void render()
   {
     gameStates.peek().render();
   }
   
   //Returns a gameState allowing to switch from current state to new state.
   private GameState getState(int state)
   {
     if(state == MENU) return new Menu(this);
     if(state == PLAY) return new Play(this);
     if(state == LEVEL_SELECT) return new LevelSelect(this);
     if(state == SNOWMAN) return new Snowman(this);
     
     return null;
   }
   
   //Set a new game state.
   public void setState(int state)
   {
     popState();
     pushState(state);
   }
   
   public void pushState(int state)
   {
     gameStates.push(getState(state));
   }
   
   public void popState()
   {
     GameState gs = gameStates.pop();
     gs.dispose();
   }
}
