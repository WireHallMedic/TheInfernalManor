/*
   An AI that will occasionally take a step.
*/

package TheInfernalManor.AI;

import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import java.util.*;

public class WanderAI extends BaseAI
{
   private double wanderChance;


	public double getWanderChance(){return wanderChance;}


	public void setWanderChance(double w){wanderChance = w;}


   public WanderAI(Actor s)
   {
      this(s, .5);
   }

   public WanderAI(Actor s, double chance)
   {
      super(s);
      wanderChance = chance;
   }
   
   public void plan()
   {
      if(RNG.nextDouble() <= wanderChance)
      {
         Vector<Direction> dirList = new Vector<Direction>();
         for(Direction dir : Direction.values())
            if(self.canStep(dir, GameState.getCurZone()))
               dirList.add(dir);
         if(dirList.size() > 0)
         {
            setPendingAction(new ActionPlan(ActionType.STEP, dirList.elementAt(RNG.nextInt(dirList.size()))));
         }
         else
            setPendingAction(new ActionPlan(ActionType.DELAY, Direction.ORIGIN));
      }
      else
      {
         setPendingAction(new ActionPlan(ActionType.DELAY, Direction.ORIGIN));
      }
   }
}