package TheInfernalManor.AI;

import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;

public class ActionPlan
{
	private ActionType actionType;
	private Direction direction;


	public ActionType getActionType(){return actionType;}
	public Direction getDirection(){return direction;}


	public void setActionType(ActionType a){actionType = a;}
	public void setDirection(Direction d){direction = d;}

   
   public ActionPlan(ActionType at, Direction dir)
   {
      actionType = at;
      direction = dir;
   }
   
   public ActionPlan(ActionType at)
   {
      this(at, Direction.ORIGIN);
   }
}