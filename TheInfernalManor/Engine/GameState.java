package TheInfernalManor.Engine;

import TheInfernalManor.Actor.*;
import TheInfernalManor.Map.*;
import java.util.*;

public class GameState implements EngineConstants
{
	private static Actor playerCharacter = null;
	private static ZoneMap curZone = null;
   private static Vector<Actor> actorList = new Vector<Actor>();
   private static int gameMode = PREGAME_MODE;


	public static Actor getPlayerCharacter(){return playerCharacter;}
	public static ZoneMap getCurZone(){return curZone;}
   public static Vector<Actor> getActorList(){return actorList;}
   public static int getGameMode(){return gameMode;}


	public static void setPlayerCharacter(Actor p){playerCharacter = p;}
	public static void setCurZone(ZoneMap c){curZone = c;}
   public static void setActorList(Vector<Actor> al){actorList = al;}
   public static void setGameMode(int m){gameMode = m;}

   public static void setTestValues()
   {
      setPlayerCharacter(ActorFactory.getTestPlayer());
      actorList.add(playerCharacter);
      setCurZone(MapFactory.getTestMap1());
      actorList.add(ActorFactory.getTestEnemy(5, 5));
      actorList.add(ActorFactory.getTestEnemy(7, 5));
      gameMode = ADVENTURE_MODE;
   }
}