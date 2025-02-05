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
            dirTo = getDumbstep(target);
            if(dirTo != null)
               ap = new ActionPlan(ActionType.STEP, dirTo);
         }
      }
      // else do nothing
      setPendingAction(ap);
   }
   
}