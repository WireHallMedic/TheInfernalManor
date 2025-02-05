package TheInfernalManor.AI;

import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import WidlerSuite.Coord;

public class ActionPlan
{
   public static final int NO_TARGET = -100;
   
	private ActionType actionType;
	private Direction direction;
   private int index;
   private int targetX;
   private int targetY;


	public ActionType getActionType(){return actionType;}
	public Direction getDirection(){return direction;}
   public int getIndex(){return index;}
   public int getTargetX(){return targetX;}
   public int getTargetY(){return targetY;}


	public void setActionType(ActionType a){actionType = a;}
	public void setDirection(Direction d){direction = d;}
   public void setIndex(int i){index = i;}
   public void setTargetX(int x){targetX = x;}
   public void setTargetY(int y){targetY = y;}

   
   public ActionPlan(ActionType at, Direction dir)
   {
      actionType = at;
      direction = dir;
      index = -1;
      targetX = NO_TARGET;
      targetY = NO_TARGET;
   }
   
   public ActionPlan(ActionType at, int ind)
   {
      this(at, Direction.ORIGIN);
      index = ind;
   }
   
   public ActionPlan(ActionType at)
   {
      this(at, Direction.ORIGIN);
   }
   
   public ActionPlan(ActionType at, Coord t)
   {
      this(at);
      targetX = t.x;
      targetY = t.y;
   }
   
   public boolean hasXYTarget()
   {
      return targetX != NO_TARGET && targetY != NO_TARGET;
   }
}