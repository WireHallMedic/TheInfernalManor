package TheInfernalManor.AI;

import TheInfernalManor.Ability.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import java.util.*;
import WidlerSuite.*;

public class ZombieAI extends BaseAI
{


   public ZombieAI(Actor s)
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
         Direction dirTo = Direction.getDirectionTo(self.getXLocation(), self.getYLocation(),
                                                    target.getXLocation(), target.getYLocation());
         // adjacent, attack
         if(self.isAdjacent(target))
         {
            ap = new ActionPlan(ActionType.BASIC_ATTACK, dirTo);
         }
         else if(self.canStep(self.getXLocation() + dirTo.x, self.getYLocation() + dirTo.y, GameState.getCurZone()))
         // not adjacent but can step, step
         {
            ap = new ActionPlan(ActionType.STEP, dirTo);
         }
         else if(self.canStep(self.getXLocation() + dirTo.clockwise().x, self.getYLocation() + dirTo.clockwise().y, GameState.getCurZone()))
         // not adjacent but can step clockwise, step clockwise
         {
            ap = new ActionPlan(ActionType.STEP, dirTo.clockwise());
         }
         else if(self.canStep(self.getXLocation() + dirTo.counterClockwise().x, self.getYLocation() + dirTo.counterClockwise().y, GameState.getCurZone()))
         // not adjacent but can step counter-clockwise, step counterclockwise
         {
            ap = new ActionPlan(ActionType.STEP, dirTo.counterClockwise());
         }
      }
      // else do nothing
      setPendingAction(ap);
   }
   
}