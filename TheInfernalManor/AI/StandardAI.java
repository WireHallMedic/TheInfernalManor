package TheInfernalManor.AI;

import TheInfernalManor.Ability.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import TheInfernalManor.Map.*;
import java.util.*;
import WidlerSuite.*;

public class StandardAI extends BaseAI
{


   public StandardAI(Actor s)
   {
      super(s);
   }
   
   
   @Override
   public void plan()
   {
      // default action
      ActionPlan ap = new ActionPlan(ActionType.DELAY, Direction.ORIGIN);
      Actor target = getClosestVisibleEnemy();
      if(target != null)
      {
         // adjacent, attack
         if(self.isAdjacent(target))
         {
            
            Direction dirTo = Direction.getDirectionTo(self.getLocation(), target.getLocation());
            ap = new ActionPlan(ActionType.BASIC_ATTACK, dirTo);
         }
         else if(self.distanceTo(target) <= MAX_PATHING_DIST)// not adjacent, do some planning
         {
            Vector<Coord> path = getPathTowards(target);
            // has path to
            if(path.size() != 0)
            {
               Coord nextStep = path.elementAt(0);
               // can step
               if(GameState.getCurZone().canStep(nextStep, self))
                  ap = new ActionPlan(ActionType.STEP, nextStep);
               // open door
               else if(GameState.getCurZone().getTile(nextStep) instanceof Door && usesDoors)
                  ap = new ActionPlan(ActionType.USE, nextStep);
            }
         }
         // no path or too far away, do dumbstep
         if(ap.getActionType() == ActionType.DELAY)
         {
            Direction dir = getDumbstep(target);
            if(dir != null)
               ap = new ActionPlan(ActionType.STEP, dir);
         }
      }
      // else do nothing
      setPendingAction(ap);
   }
   
}