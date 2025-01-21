package TheInfernalManor.Engine;

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
}