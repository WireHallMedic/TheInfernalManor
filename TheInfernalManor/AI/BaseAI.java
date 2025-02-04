package TheInfernalManor.AI;

import TheInfernalManor.Ability.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import java.util.*;

public class BaseAI
{
	protected Actor self;
	protected Vector<ActionPlan> pendingAction;
	protected Vector<ActionPlan> previousAction;
   protected boolean playerControlled;
   protected Team team;


	public Actor getSelf(){return self;}
	public Vector<ActionPlan> getPendingAction(){return pendingAction;}
	public Vector<ActionPlan> getPreviousAction(){return previousAction;}
   public boolean isPlayerControlled(){return playerControlled;}
   public Team getTeam(){return team;}


	public void setSelf(Actor s){self = s;}
   public void setPlayerControlled(boolean pc){playerControlled = pc;}
   public void setTeam(Team t){team = t;}

   public BaseAI(Actor s)
   {
      self = s;
      pendingAction = new Vector<ActionPlan>();
      previousAction = new Vector<ActionPlan>();
      playerControlled = false;
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