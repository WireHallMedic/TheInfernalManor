package TheInfernalManor.Engine;

import TheInfernalManor.Actor.*;
import TheInfernalManor.Quest.*;
import TheInfernalManor.Ability.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Map.*;
import TheInfernalManor.GUI.*;
import WidlerSuite.Coord;
import WidlerSuite.ShadowFoVRect;
import WidlerSuite.SpiralSearch;
import java.util.*;

public class GameState implements EngineConstants, Runnable
{
	private static Actor playerCharacter = null;
	private static ZoneMap curZone = null;
   private static Vector<Actor> actorList = new Vector<Actor>();
   private static Quest curQuest = null;
   private static int gameMode = PREGAME_MODE;
   private static boolean runF;
   private static int initiativeIndex;
   private static ShadowFoVRect fov;
   private static ShadowFoVRect enemyFoV;


	public static Actor getPlayerCharacter(){return playerCharacter;}
	public static ZoneMap getCurZone(){return curZone;}
   public static Vector<Actor> getActorList(){return actorList;}
   public static Quest getCurQuest(){return curQuest;}
   public static int getGameMode(){return gameMode;}
   public static boolean getRunFlag(){return runF;}
   public static ShadowFoVRect getFoV(){return fov;}
   public static ShadowFoVRect getEnemyFoV(){return enemyFoV;}


	public static void setPlayerCharacter(Actor p){playerCharacter = p;}
   public static void setActorList(Vector<Actor> al){actorList = al;}
   public static void setCurQuest(Quest cq){curQuest = cq;}
   public static void setGameMode(int m){gameMode = m;}
   public static void setRunFlag(boolean rf){runF = rf;}

   public static void setTestValues()
   {
      fov = null;
      setPlayerCharacter(ActorFactory.getTestPlayer());
      setCurQuest(Quest.mock());
      setActiveQuestSegment(1, true);
      calcFoV();
      runF = true;
   }
   
   
	public static void setCurZone(ZoneMap c)
   {
      curZone = c;
      fov = new ShadowFoVRect(curZone.getTransparentMap());
      enemyFoV = new ShadowFoVRect(curZone.getTransparentMap());
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
         targetList = EngineTools.getPointTarget(origin, new Coord(targetX, targetY), range);
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
      // ranged attack line visual effects
      if(attack.getShape() == AbilityConstants.EffectShape.POINT ||
         attack.getShape() == AbilityConstants.EffectShape.BLAST)
      {
         Coord stopTile = EngineTools.getPointTarget(origin, new Coord(targetX, targetY), range).elementAt(0);
         VisualEffectFactory.registerAttackLine(origin, new Coord(targetX, targetY), stopTile);
      }
      
      // process each target
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
         // aoe visual effects
         switch(attack.getShape())
         {
            case BLAST :   
            case CONE :    
            case AURA :    VisualEffectFactory.registerExplosion(targetList.elementAt(i));
                           break;
            case BEAM :    Coord t = targetList.elementAt(i);
                           Coord o = origin;
                           if(i > 0)
                              o = targetList.elementAt(i - 1);
                           VisualEffectFactory.registerLightning(t, Direction.getDirectionTo(t, o));
                           break;
         }
      }
   }
   public static void resolveAttack(Actor attacker, Attack attack, Coord c){resolveAttack(attacker, attack, c.x, c.y);}
   
   
   public static void notifyOfDeath(Actor a)
   {
   
   }
   
   // adds actor as near as possible to the target location
   public static void addActorAt(Actor a, int x, int y)
   {
      if(curZone.canStep(x, y, a))
      {
         if(!isActorAt(x, y))
         {
            a.setLocation(x, y);
            actorList.add(a);
         }
         else
         {
            SpiralSearch search = new SpiralSearch(curZone.getLowPassMap(), x, y);
            Coord target = null;
            while(target == null)
            {
               target = search.getNext();
               if(target == null)
                  break;
               if(isActorAt(target))
               {
                  target = null;
               }
            }
            if(target != null)
            {
               a.setLocation(target);
               actorList.add(a);
            }
         }
      }
      // can't step at passed location; find nearest steppable and rerun
      else
      {
         int radius = 5;
         int diameter = (radius * 2) + 1;
         boolean[][] searchGrid = new boolean[diameter][diameter];
         for(int xx = 0; xx < diameter; xx++)
         for(int yy = 0; yy < diameter; yy++)
            searchGrid[xx][yy] = true;
         SpiralSearch search = new SpiralSearch(searchGrid, radius, radius);
         Coord target = null;
         while(target == null)
         {
            target = search.getNext();
            if(target == null)
               break;
            target.x += x - radius;
            target.y += y - radius;
            if(!curZone.isLowPassable(target.x, target.y))
               target = null;
         }
         if(target != null)
            addActorAt(a, target);
      }
   }
   public static void addActorAt(Actor a, Coord c){addActorAt(a, c.x, c.y);}
   
   public static void useExit(Exit exit)
   {
      setActiveQuestSegment(exit.getTargetZone(), exit.getBase() == MapCellBase.EXIT);
   }
      
   public static void setActiveQuestSegment(int segment, boolean movingForward)
   {
      runF = false;
      if(segment == MapConstants.QUEST_EXIT)
      {
         ; //TODO
      }
      else
      {
         curQuest.clean();
         setCurZone(curQuest.getZoneMap(segment));
         actorList = new Vector<Actor>();
         if(movingForward)
            addActorAt(playerCharacter, curZone.getEntranceLoc());
         else
            addActorAt(playerCharacter, curZone.getExitLoc());
         for(Actor a : curQuest.getActorList(segment))
            addActorAt(a, a.getLocation());
         initiativeIndex = 0;
         calcFoV();
         runF = true;
      }
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
      if(initiativeIndex < 0)
         initiativeIndex = 0;
   }
   
   
}