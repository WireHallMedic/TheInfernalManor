package TheInfernalManor.AI;

import TheInfernalManor.Ability.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import java.util.*;
import WidlerSuite.*;

public class BaseAI
{
	protected Actor self;
	protected Vector<ActionPlan> pendingAction;
	protected Vector<ActionPlan> previousAction;
   protected boolean playerControlled;
   protected boolean usesDoors;
   protected Team team;


	public Actor getSelf(){return self;}
	public Vector<ActionPlan> getPendingAction(){return pendingAction;}
	public Vector<ActionPlan> getPreviousAction(){return previousAction;}
   public boolean isPlayerControlled(){return playerControlled;}
   public boolean getUsesDoors(){return usesDoors;}
   public Team getTeam(){return team;}


	public void setSelf(Actor s){self = s;}
   public void setPlayerControlled(boolean pc){playerControlled = pc;}
   public void setTeam(Team t){team = t;}
   public void setUsesDoors(boolean u){usesDoors = u;}

   public BaseAI(Actor s)
   {
      self = s;
      pendingAction = new Vector<ActionPlan>();
      previousAction = new Vector<ActionPlan>();
      playerControlled = false;
      usesDoors = true;
      team = Team.ENEMY;
   }
   
   public boolean hasPlan()
   {
      return pendingAction.size() > 0;
   }
   
   public void setPendingAction(ActionPlan ap)
   {
      pendingAction.clear();
      pendingAction.add(ap);
   }
   
   public void setPendingAction(Vector<ActionPlan> apList)
   {
      pendingAction.clear();
      for(ActionPlan ap : apList)
         pendingAction.add(ap);
   }
   
   private void cleanUp()
   {
      previousAction = pendingAction;
      pendingAction.clear();
   }
   
   public void clearPlan()
   {
      pendingAction.clear();
   }
   
   // override in child classes
   public void plan()
   {
      if(!playerControlled)
      {
         setPendingAction(new ActionPlan(ActionType.DELAY));
      }
   }
   
   public Actor getClosestVisibleEnemy()
   {
      Actor prospect = null;
      int dist = 100;
      Vector<Actor> characterList = GameState.getActorList();
      for(int i = 0; i < characterList.size(); i++)
      {
         Actor a = characterList.elementAt(i);
         if(self.isEnemy(a) &&
            self.canSee(a))
         {
            int d = WSTools.getAngbandMetric(self.getXLocation(), self.getYLocation(),
                                             a.getXLocation(), a.getYLocation());
            if(d < dist)
            {
               dist = d;
               prospect = a;
            }
         }
      }
      return prospect;
   }
   
   public Vector<Coord> getPathTowards(Coord c)
   {
      int radius = 13;
      int minX = self.getXLocation() - radius;
      int maxX = self.getXLocation() + radius;
      int minY = self.getYLocation() - radius;
      int maxY = self.getYLocation() + radius;
      boolean[][] passMap = GameState.getCurZone().getPathingMap(self, radius);
      // block off actors
      for(Actor a : GameState.getActorList())
      {
         if(a != self)
         {
            if(a.getXLocation() >= minX &&
               a.getXLocation() <= maxX &&
               a.getYLocation() >= minY &&
               a.getYLocation() <= maxY)
            passMap[a.getXLocation() - self.getXLocation() + radius][a.getYLocation() - self.getYLocation() + radius] = false;
         }
      }
      // set target as passable
      passMap[c.x - self.getXLocation() + radius][c.y - self.getYLocation() + radius] = true;
      
      // generate path
      AStar aStar = new AStar();
      Vector<Coord> path = aStar.path(passMap, radius, radius, c.x - self.getXLocation() + radius, c.y - self.getYLocation() + radius);
      // adjust for offset
      Coord offset = new Coord(self.getXLocation() - radius, self.getYLocation() - radius);
      for(Coord step : path)
         step.add(offset);
      return path;
   }
   public Vector<Coord> getPathTowards(Actor a){return getPathTowards(new Coord(a.getXLocation(), a.getYLocation()));}
   
   
   public void act()
   {
      for(ActionPlan plan : pendingAction)
      {
         int x = self.getXLocation();
         int y = self.getYLocation();
         if(plan.getDirection() != null)
         {
            x += plan.getDirection().x;
            y += plan.getDirection().y;
         }
         if(plan.hasXYTarget())
         {
            x = plan.getTargetX();
            y = plan.getTargetY();
         }
         
         if(plan.getActionType() == ActionType.DELAY)
         {
            self.takeStep(Direction.ORIGIN);
            self.discharge(self.getMoveSpeed());
         }
         if(plan.getActionType() == ActionType.STEP)
         {
            if(plan.hasXYTarget())
               self.takeStep(new Coord(x, y));
            else
               self.takeStep(plan.getDirection());
            self.discharge(self.getMoveSpeed());
         }
         if(plan.getActionType() == ActionType.USE)
         {
            if(plan.hasXYTarget())
               self.doToggle(new Coord(x, y));
            else
               self.doToggle(plan.getDirection());
            self.discharge(self.getInteractSpeed());
         }
         if(plan.getActionType() == ActionType.BASIC_ATTACK)
         {
            self.doAttack(self.getBasicAttack(), x, y);
            self.discharge(self.getBasicAttack().getSpeed());
         }
         if(plan.getActionType() == ActionType.ABILITY)
         {
            Ability ability = self.getAbility(plan.getIndex());
            if(ability instanceof Attack)
            {
               Attack attack = (Attack)ability;
               self.doAttack(attack, x, y);
               self.discharge(attack.getSpeed());
            }
         }
         if(plan.getActionType() == ActionType.PICK_UP)
         {
            self.pickUp();
            self.discharge(self.getInteractSpeed());
         }
         if(plan.getActionType() == ActionType.DROP)
         {
            self.dropFromInventory(plan.getIndex());
            self.discharge(self.getInteractSpeed());
         }
         if(plan.getActionType() == ActionType.EQUIP)
         {
            self.equipFromInventory(plan.getIndex());
            self.discharge(self.getInteractSpeed());
         }
         if(plan.getActionType() == ActionType.REMOVE)
         {
            self.unequipItem(plan.getIndex());
            self.discharge(self.getInteractSpeed());
         }
      }
      cleanUp();
   }
}