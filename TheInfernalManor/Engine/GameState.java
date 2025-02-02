package TheInfernalManor.Engine;

import TheInfernalManor.Actor.*;
import TheInfernalManor.Ability.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Map.*;
import TheInfernalManor.GUI.*;
import WidlerSuite.Coord;
import WidlerSuite.ShadowFoVRect;
import java.util.*;

public class GameState implements EngineConstants, Runnable
{
	private static Actor playerCharacter = null;
	private static ZoneMap curZone = null;
   private static Vector<Actor> actorList = new Vector<Actor>();
   private static int gameMode = PREGAME_MODE;
   private static boolean runF;
   private static int initiativeIndex;
   private static ShadowFoVRect fov;


	public static Actor getPlayerCharacter(){return playerCharacter;}
	public static ZoneMap getCurZone(){return curZone;}
   public static Vector<Actor> getActorList(){return actorList;}
   public static int getGameMode(){return gameMode;}
   public static boolean getRunFlag(){return runF;}
   public static ShadowFoVRect getFoV(){return fov;}


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
      actorList.add(ActorFactory.getTestEnemy(9, 5));
      Actor b = ActorFactory.getTestEnemy(7, 5);
      b.setOffHand(OffHandFactory.getShield());
      actorList.add(b);
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
   public static Actor getActorAt(Coord c){return getActorAt(c.x, c.y);}
   
   public static boolean isActorAt(int x, int y)
   {
      return getActorAt(x, y) != null;
   }
   public static boolean isActorAt(Coord c){return isActorAt(c.x, c.y);}
   
   public static void resolveAttack(Actor attacker, Attack attack, int targetX, int targetY)
   {
      Vector<Coord> targetList = new Vector<Coord>();
      Coord origin = new Coord(attacker.getXLocation(), attacker.getYLocation());
      int range = attack.getRange();
      if(range == AbilityConstants.USE_WEAPON_RANGE)
         range = attacker.getWeapon().getRange();
      if(attack.getShape() == AbilityConstants.EffectShape.POINT)
         targetList.add(new Coord(targetX, targetY));
      if(attack.getShape() == AbilityConstants.EffectShape.BEAM)
      {
         Coord target = new Coord(targetX, targetY);
         targetList = EngineTools.getLineTargets(origin, target, range);
      }
      if(attack.getShape() == AbilityConstants.EffectShape.BLAST)
      {
         Coord target = new Coord(targetX, targetY);
         targetList = EngineTools.getBlastTargets(origin, target, range, attack.getRadius());
      }
      if(attack.getShape() == AbilityConstants.EffectShape.CONE)
      {
         Coord target = new Coord(targetX, targetY);
         targetList = EngineTools.getConeTargets(origin, target, range, attack.getRadius());
      }
      if(attack.getShape() == AbilityConstants.EffectShape.AURA)
      {
         Coord target = new Coord(targetX, targetY);
         targetList = EngineTools.getAuraTargets(origin, attack.getRadius());
      }
      for(int i = 0; i < targetList.size(); i++)
      {
         // resolve attack
         Actor defender = GameState.getActorAt(targetList.elementAt(i));
         if(defender != null)
         {
            Combat.resolveAttack(attacker, defender, attack);
         }
         // visual effects
         if(attack.getShape() == AbilityConstants.EffectShape.BEAM)
         {
            Coord t = targetList.elementAt(i);
            Coord o = origin;
            if(i > 0)
               o = targetList.elementAt(i - 1);
            VisualEffectFactory.registerLightning(t, Direction.getDirectionTo(t, o));
         }
         if(attack.getShape() == AbilityConstants.EffectShape.CONE ||
            attack.getShape() == AbilityConstants.EffectShape.AURA ||
            attack.getShape() == AbilityConstants.EffectShape.BLAST)
         {
            for(Coord c : targetList)
               VisualEffectFactory.registerExplosion(c);
         }
      }
   }
   public static void resolveAttack(Actor attacker, Attack attack, Coord c){resolveAttack(attacker, attack, c.x, c.y);}
   
   
   // adventure mode loop
   public void run()
   {
      Actor curActor;
      while(true)
      {
         if(runF && !AnimationManager.hasBlockingVisualEffect())
         {
            curActor = actorList.elementAt(initiativeIndex);
            if(curActor.isCharged())
            {
               curActor.startTurn();
               // charged and has plan, do action
               if(curActor.hasPlan())
               {
                  curActor.act();
                  curActor.endTurn();
                  incrementInitiative();
               }
               // charged no plan, make plan
               else
               {
                  curActor.plan();
               }
            }
            // not charged, charge
            else
            {
               curActor.charge();
               incrementInitiative();
            }
            cleanUp();
         }
         Thread.yield();
      }
   }
   
   private void incrementInitiative()
   {
      initiativeIndex++;
      if(initiativeIndex >= actorList.size())
         initiativeIndex = 0;
   }
   
   private void cleanUp()
   {
      for(int i = 0; i < actorList.size(); i++)
      {
         Actor a = actorList.elementAt(i);
         if(a.isDead())
         {
            a.die();
            actorList.removeElementAt(i);
            i--;
            if(i <= initiativeIndex)
               initiativeIndex--;
         }
      }
   }
   
   
}