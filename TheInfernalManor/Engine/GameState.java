package TheInfernalManor.Engine;

import TheInfernalManor.Actor.*;
import TheInfernalManor.Map.*;

public class GameState
{
	private static Actor playerCharacter = null;
	private static ZoneMap curZone = null;


	public static Actor getPlayerCharacter(){return playerCharacter;}
	public static ZoneMap getCurZone(){return curZone;}


	public static void setPlayerCharacter(Actor p){playerCharacter = p;}
	public static void setCurZone(ZoneMap c){curZone = c;}

   public static void setTestValues()
   {
      setPlayerCharacter(ActorFactory.getTestPlayer());
      setCurZone(MapFactory.getTestMap1());
   }
}