package TheInfernalManor.AI;

import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;

public class ActionPlan
{
	private ActionType actionType;
	private Direction direction;
   private int index;


	public ActionType getActionType(){return actionType;}
	public Direction getDirection(){return direction;}
   public int getIndex(){return index;}


	public void setActionType(ActionType a){actionType = a;}
	public void setDirection(Direction d){direction = d;}
   public void setIndex(int i){index = i;}

   
   public ActionPlan(ActionType at, Direction dir)
   {
      actionType = at;
      direction = dir;
      index = -1;
   }
   
   public ActionPlan(ActionType at, int ind)
   {
      this(at, null);
      index = ind;
   }
   
   public ActionPlan(ActionType at)
   {
      this(at, Direction.ORIGIN);
   }
}