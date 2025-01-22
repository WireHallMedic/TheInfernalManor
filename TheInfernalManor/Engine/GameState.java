package TheInfernalManor.Engine;

import TheInfernalManor.Actor.*;
import TheInfernalManor.Map.*;
import java.util.*;

public class GameState implements EngineConstants, Runnable
{
	private static Actor playerCharacter = null;
	private static ZoneMap curZone = null;
   private static Vector<Actor> actorList = new Vector<Actor>();
   private static int gameMode = PREGAME_MODE;
   private static boolean runF;
   private static int initiativeIndex;


	public static Actor getPlayerCharacter(){return playerCharacter;}
	public static ZoneMap getCurZone(){return curZone;}
   public static Vector<Actor> getActorList(){return actorList;}
   public static int getGameMode(){return gameMode;}
   public static boolean getRunFlag(){return runF;}


	public static void setPlayerCharacter(Actor p){playerCharacter = p;}
	public static void setCurZone(ZoneMap c){curZone = c;}
   public static void setActorList(Vector<Actor> al){actorList = al;}
   public static void setGameMode(int m){gameMode = m;}
   public static void setRunFlag(boolean rf){runF = rf;}

   public static void setTestValues()
   {
      setPlayerCharacter(ActorFactory.getTestPlayer());
      actorList.add(playerCharacter);
      setCurZone(MapFactory.getTestMap1());
      actorList.add(ActorFactory.getTestEnemy(5, 5));
      actorList.add(ActorFactory.getTestEnemy(7, 5));
      runF = true;
   }
   
   public GameState()
   {
      Thread thread = new Thread(this);
      runF = false;
      initiativeIndex = 0;
      thread.start();
   }
   
   public static void runGameLoop()
   {
      initiativeIndex = 0;
      runF = true;
   }
   
   public static void stopGameLoop()
   {
      runF = false;
   }
   
   public static Actor getActorAt(int x, int y)
   {
      for(int i = 0; i < actorList.size(); i++)
      {
         if(actorList.elementAt(i).getXLocation() == x &&
            actorList.elementAt(i).getYLocation() == y)
         {
            return actorList.elementAt(i);
         }
      }
      return null;
   }
   
   public static boolean isActorAt(int x, int y)
   {
      return getActorAt(x, y) != null;
   }
   
   // adventure mode loop
   public void run()
   {
      Actor curActor;
      while(true)
      {
         if(runF)
         {
            curActor = actorList.elementAt(initiativeIndex);
            if(curActor.hasPlan())
            {
               curActor.act();
               initiativeIndex++;
               if(initiativeIndex >= actorList.size())
                  initiativeIndex = 0;
            }
            else
            {
               curActor.plan();
            }
         }
         Thread.yield();
      }
   }
}