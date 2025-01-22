package TheInfernalManor.AI;

import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import java.util.*;

public class BaseAI
{
	private Actor self;
	private Vector<ActionPlan> pendingAction;
	private Vector<ActionPlan> previousAction;
   private boolean playerControlled;


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
   
   // override in child classes
   public void plan()
   {
      if(!playerControlled)
      {
         setPendingAction(new ActionPlan(ActionType.DELAY, Direction.ORIGIN));
      }
   }
   
   public void act()
   {
      for(ActionPlan plan : pendingAction)
      {
         if(plan.getActionType() == ActionType.DELAY || plan.getActionType() == ActionType.STEP)
            takeStep(plan.getDirection());
         
      }
      cleanUp();
   }
   
   // execute action
   private void takeStep(Direction dir)
   {
      int xLoc = self.getXLocation() + dir.x;
      int yLoc = self.getYLocation() + dir.y;
      self.setLocation(xLoc, yLoc);
   }
}