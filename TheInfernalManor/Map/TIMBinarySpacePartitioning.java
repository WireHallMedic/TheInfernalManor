/*******************************************************************************************
Copied from WidlerSuite, altered to use TIM RNG.

Additionally, WS BSP rooms do not overlap; TIM ones do.

*******************************************************************************************/

package TheInfernalManor.Map;

import java.util.*;
import WidlerSuite.*;
import TheInfernalManor.Engine.*;

public class TIMBinarySpacePartitioning
{
   protected static double partitionChance = .5;     // how likely a room is to split if it's below
                                                   // max size but could still split into legal rooms
    
   public static void setPartitionChance(double pc){partitionChance = pc;}
    
   // the main function
   public static Vector<TIMRoom> partition(int x, int y, int minRoomDiameter, int maxRoomDiameter)
   {
      Vector<TIMRoom> roomList = new Vector<TIMRoom>();
      TIMRoom[] addRooms;
   
      // The max has to be at least twice the min
      if(maxRoomDiameter < 2 * minRoomDiameter)
         maxRoomDiameter = 2 * minRoomDiameter;
   
      TIMRoom startRoom = new TIMRoom();
      startRoom.origin = new Coord(0, 0);
      startRoom.size = new Coord(x, y);
      startRoom.iteration = 0;
      roomList.add(startRoom);
   
      for(int i = 0; i < roomList.size(); i++)
      {
         // rooms that are larger than the max are always split
         if(roomList.elementAt(i).size.x > maxRoomDiameter ||
            roomList.elementAt(i).size.y > maxRoomDiameter)
         {
            addRooms = divide(roomList.elementAt(i), minRoomDiameter);
            roomList.add(addRooms[0]);
            roomList.add(addRooms[1]);
         }
         else // rooms which can be, but don't need to be split, are handled here
         {
            if(roomList.elementAt(i).size.x >= minRoomDiameter * 2 ||
               roomList.elementAt(i).size.y >= minRoomDiameter * 2)
            if(RNG.nextDouble() < partitionChance)
            {
               addRooms = divide(roomList.elementAt(i), minRoomDiameter);
               roomList.add(addRooms[0]);
               roomList.add(addRooms[1]);
            }
         }
      }
      return roomList;
   }
   public static Vector<TIMRoom> partition(Coord size, int minRoomDiameter, int maxRoomDiameter)
   {
      return partition(size.x, size.y, minRoomDiameter, maxRoomDiameter);
   }
   
   // the main work method. Splits a room and returns its children
   protected static TIMRoom[] divide(Room r, int minRoomDiameter)
   {
      TIMRoom a = new TIMRoom();
      TIMRoom b = new TIMRoom();
      a.iteration = r.iteration + 1;
      b.iteration = r.iteration + 1;
      r.isParent = true;
      int diameterVarianceX = r.size.x - (2 * minRoomDiameter);
      int diameterVarianceY = r.size.y - (2 * minRoomDiameter);
   
      if(r.size.x > r.size.y) // wider; divide vertically
      {
         a.origin = new Coord(r.origin);
         a.size.x = minRoomDiameter + (int)(diameterVarianceX * RNG.nextDouble());
         a.size.y = r.size.y;
   
         b.origin.x = a.origin.x + a.size.x - 1;
         b.origin.y = a.origin.y;
         b.size.x = r.size.x - a.size.x + 1;
         b.size.y = a.size.y;
      }
      else // taller; divide horizontally
      {
         a.origin = new Coord(r.origin);
         a.size.x = r.size.x;
         a.size.y = minRoomDiameter + (int)(diameterVarianceY * RNG.nextDouble());
   
         b.origin.x = a.origin.x;
         b.origin.y = a.origin.y + a.size.y - 1;
         b.size.x = a.size.x;
         b.size.y = r.size.y - a.size.y + 1;
      }
      return new TIMRoom[] {a, b};
   }
}