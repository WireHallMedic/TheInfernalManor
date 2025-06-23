/*

Adds a bit more functionality to WidlerSuite.Room

*/
package TheInfernalManor.Map;

import WidlerSuite.*;

public class TIMRoom extends Room
{
   public boolean connectsNorth;
   public boolean connectsEast;
   public boolean connectsSouth;
   public boolean connectsWest;
   public int numOfConnections;
   
   public TIMRoom()
   {
      super();
      numOfConnections = 0;
   }
   
   public TIMRoom(Room that)
   {
      this();
      this.origin = that.origin;
      this.size = that.size;
      this.iteration = that.iteration;
      this.isParent = that.isParent;
   }
   
   public boolean isTerminal()
   {
      return numOfConnections == 1;
   }
   
   // because WidlerSuite rooms don't overlap, and TIM rooms do.
   public void increaseSizeByOne()
   {
      size.x += 1;
      size.y += 1;
   }
   
   public void setConnections(ZoneMap z)
   {
      numOfConnections = 0;
      connectsNorth = false;
      connectsSouth = false;
      connectsWest = false;
      connectsEast = false;
      for(int x = origin.x; x < origin.x + size.x; x++)
      {
         if(z.getTile(x, origin.y).isLowPassable() || z.getTile(x, origin.y) instanceof Door)
            connectsNorth = true;
         if(z.getTile(x, origin.y + size.y - 1).isLowPassable() || z.getTile(x, origin.y + size.y - 1) instanceof Door)
            connectsSouth = true;
      }
      for(int y = origin.y; y < origin.y + size.y; y++)
      {
         if(z.getTile(origin.x, y).isLowPassable() || z.getTile(origin.x, y) instanceof Door)
            connectsWest = true;
         if(z.getTile(origin.x + size.x - 1, y).isLowPassable() || z.getTile(origin.x + size.x - 1, y) instanceof Door)
            connectsEast = true;
      }
      if(connectsNorth) numOfConnections++;
      if(connectsEast) numOfConnections++;
      if(connectsSouth) numOfConnections++;
      if(connectsWest) numOfConnections++;
   }
   
   public boolean overlaps(TIMRoom that)
   {
      return intervalOverlap(this.origin.x + this.size.x, that.origin.x + this.size.x, this.origin.x, that.origin.x) &&
         intervalOverlap(this.origin.y + this.size.y, that.origin.y + this.size.y, this.origin.y, that.origin.y);
   }
   
   private boolean intervalOverlap(int max1, int max2, int min1, int min2)
   {
      return (max1 >= min2) & (max2 >= min1);
   }
}