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
      boolean[][] basePassable = GameState.getCurZone().getLowPassMap();
      boolean[][] passable = new boolean[basePassable.length][basePassable[0].length];
      for(Actor a : GameState.getActorList())
      {
         passable[a.getXLocation()][a.getYLocation()] = false;
      }
      AStar aStar = new AStar();
      Vector<Coord> path = aStar.path(passable, self.getXLocation(), self.getYLocation(), c.x, c.y);
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
            self.takeStep(plan.getDirection());
            self.discharge(self.getMoveSpeed());
         }
         if(plan.getActionType() == ActionType.USE)
         {
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