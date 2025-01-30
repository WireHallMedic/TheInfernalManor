package TheInfernalManor.Engine;

import WidlerSuite.Coord;

public enum Direction
{
   ORIGIN      (0, 0), 
   NORTH       (0, -1), 
   NORTHEAST   (1, -1), 
   EAST        (1, 0), 
   SOUTHEAST   (1, 1), 
   SOUTH       (0, 1), 
   SOUTHWEST   (-1, 1), 
   WEST        (-1, 0), 
   NORTHWEST   (-1, -1);
   
   public int x;
   public int y;
   
   private Direction(int _x, int _y)
   {
      x = _x;
      y = _y;
   }
   
   public static Direction getDirectionTo(int xOrigin, int yOrigin, int xTarget, int yTarget)
   {
      int newX = Math.max(-1, Math.min(1, (xTarget - xOrigin)));
      int newY = Math.max(-1, Math.min(1, (yTarget - yOrigin)));
      for(Direction dir : Direction.values())
      {
         if(dir.x == newX && dir.y == newY)
            return dir;
      }
      return null;
   }
   public static Direction getDirectionTo(Coord origin, Coord target){return getDirectionTo(origin.x, origin.y, target.x, target.y);}
}