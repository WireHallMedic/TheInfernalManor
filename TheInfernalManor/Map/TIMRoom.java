/*

Adds a bit more functionality to WidlerSuite.Room

*/
package TheInfernalManor.Map;

import WidlerSuite.*;
import java.util.*;
import TheInfernalManor.Engine.*;

public class TIMRoom extends Room implements Comparable<TIMRoom>
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
      this.origin = new Coord(that.origin);
      this.size = new Coord(that.size);
      this.iteration = that.iteration;
      this.isParent = that.isParent;
   }
   
   public boolean isTerminal()
   {
      return numOfConnections == 1;
   }
   
   // for sorting
   public int compareTo(TIMRoom that)
   {
      return this.getArea() - that.getArea();
   }
   
   public int getArea()
   {
      return size.x * size.y;
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
   
   // returns true if the right boundary of this room and the left boundary of that room are the same.
   // find sibiling then checks the opposite (right of that adjacent to left of this)
   @Override
   public boolean isHorizontallyAdjacent(Room that, boolean findSibiling)
   {
      if(this.origin.x + this.size.x - 1 == that.origin.x)
         return true;
      
      if(findSibiling == false)
      if(that.origin.x + that.size.x - 1 == this.origin.x)
         return true;
      
      return false;
   }
   public boolean isHorizontallyAdjacent(Room that){return isHorizontallyAdjacent(that, false);}
   
   // returns true if the bottom boundary of this room and the top boundary of that room are the same.
   // find sibiling then checks the opposite (bottom of that adjacent to top of this)
   @Override
   public boolean isVerticallyAdjacent(Room that, boolean findSibiling)
   {
      if(this.origin.y + this.size.y - 1 == that.origin.y)
         return true;
      
      if(findSibiling == false)
      if(that.origin.y + that.size.y - 1 == this.origin.y)
         return true;
      
      return false;
   }
   public boolean isVerticallyAdjacent(Room that){return isVerticallyAdjacent(that, false);}
   
   // returns a random cell from the room
   @Override
   public Coord getRandomCell()
   {
      Coord cell = new Coord(origin);
      
      cell.x += RNG.nextInt(size.x);
      cell.y += RNG.nextInt(size.y);
      
      return cell;
   }
   
   public void setAllPaletteVariations(ZoneMap z, int variation)
   {
      for(int x = origin.x; x < origin.x + size.x; x++)
      for(int y = origin.y; y < origin.y + size.y; y++)
         z.getTileMap()[x][y].setPaletteVariation(variation);
   }
   
   public static Vector<TIMRoom> removeParents(Vector<TIMRoom> fullList)
   {
      Vector<TIMRoom> newList = new Vector<TIMRoom>();
      for(TIMRoom room : fullList)
         if(!room.isParent)
            newList.add(room);
      return newList;
   }
}