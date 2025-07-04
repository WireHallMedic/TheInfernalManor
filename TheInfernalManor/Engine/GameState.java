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
   private static ShadowFoVRect enemyFoV;


	public static Actor getPlayerCharacter(){return playerCharacter;}
	public static ZoneMap getCurZone(){return curZone;}
   public static Vector<Actor> getActorList(){return actorList;}
   public static int getGameMode(){return gameMode;}
   public static boolean getRunFlag(){return runF;}
   public static ShadowFoVRect getFoV(){return fov;}
   public static ShadowFoVRect getEnemyFoV(){return enemyFoV;}


	public static void setPlayerCharacter(Actor p){playerCharacter = p;}
   public static void setActorList(Vector<Actor> al){actorList = al;}
   public static void setGameMode(int m){gameMode = m;}
   public static void setRunFlag(boolean rf){runF = rf;}

   public static void setTestValues()
   {
      fov = null;
      setPlayerCharacter(ActorFactory.getTestPlayer());
      actorList.add(playerCharacter);
      actorList.add(ActorFactory.getTestEnemy(4, 15));
      Actor a = ActorFactory.getTestEnemy(9, 15);
      //a.setMainHand(WeaponFactory.getSling());
      a.getAI().setUsesDoors(false);
      actorList.add(a);
      Actor b = ActorFactory.getTestZombie(14, 15);
      b.setOffHand(OffHandFactory.getShield());
      actorList.add(b);
      actorList.add(ActorFactory.getTestWizard(17, 15));
      setCurZone(ZoneMapFactory.getTestMap1());
      runF = true;
   }
   
   
	public static void setCurZone(ZoneMap c)
   {
      curZone = c;
      fov = new ShadowFoVRect(curZone.getTransparentMap());
      enemyFoV = new ShadowFoVRect(curZone.getTransparentMap());
      calcFoV();
   }
   
   public static void calcFoV()
   {
      if(playerCharacter != null && fov != null)
      {
         fov.calcFoV(playerCharacter.getXLocation(), playerCharacter.getYLocation(), playerCharacter.getVision());
      }
   }
   
   public static void calcEnemyFoV(Actor a)
   {
      enemyFoV.calcFoV(a.getXLocation(), a.getYLocation(), a.getVision());
   }
   
   public static boolean playerCanSee(int x, int y)
   {
      if(fov != null)
         return fov.isVisible(x, y);
      return false;
   }
   public static boolean playerCanSee(Coord c){return playerCanSee(c.x, c.y);}
   
   public static boolean playerCanSee(Actor a)
   {
      return playerCanSee(a.getXLocation(), a.getYLocation());
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
         // break stuff
         if(curZone.getTile(targetList.elementAt(i)).isBreakable())
         {
            curZone.breakTile(targetList.elementAt(i));
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
   
   
   public static void notifyOfDeath(Actor a)
   {
   
   }
   
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
                  calcFoV();
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
            if(i <= initiativeIndex)
               initiativeIndex--;
            i--;
         }
      }
   }
   
   
}