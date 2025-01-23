/*
   An AI that will occasionally take a step.
*/

package TheInfernalManor.AI;

import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import java.util.*;

public class WanderAI
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
      if(!playerControlled)
      {
         setPendingAction(new ActionPlan(ActionType.DELAY, Direction.ORIGIN));
      }
   }
}