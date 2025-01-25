package TheInfernalManor.AI;

import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import java.util.*;

public class BaseAI
{
	protected Actor self;
	protected Vector<ActionPlan> pendingAction;
	protected Vector<ActionPlan> previousAction;
   protected boolean playerControlled;


	public Actor getSelf(){return self;}
	public Vector<ActionPlan> getPendingAction(){return pendingAction;}
	public Vector<ActionPlan> getPreviousAction(){return previousAction;}
   public boolean isPlayerControlled(){return playerControlled;}


	public void setSelf(Actor s){self = s;}
   public void setPlayerControlled(boolean pc){playerControlled = pc;}

   public BaseAI(Actor s)
   {
      self = s;
      pendingAction = new Vector<ActionPlan>();
      previousAction = new Vector<ActionPlan>();
      playerControlled = false;
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
         if(plan.getActionType() == ActionType.DELAY)
            self.takeStep(Direction.ORIGIN);
         if(plan.getActionType() == ActionType.STEP)
            self.takeStep(plan.getDirection());
         if(plan.getActionType() == ActionType.USE)
            self.doToggle(plan.getDirection());
         if(plan.getActionType() == ActionType.BASIC_ATTACK)
            self.doAttack(self.getBasicAttack(), x, y);
         if(plan.getActionType() == ActionType.PICK_UP)
            self.pickUp();
         if(plan.getActionType() == ActionType.DROP)
            self.dropFromInventory(plan.getIndex());
      }
      cleanUp();
   }
}