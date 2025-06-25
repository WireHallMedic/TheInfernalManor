package TheInfernalManor.Map;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Engine.*;
import WidlerSuite.*;
import java.util.*;

public class BSPZoneMapFactory extends ZoneMapFactory implements MapConstants, GUIConstants
{

   
   public static ZoneMap generate(int w, int h, int min, int max, double connChance, double connRatio)
   {
      TIMBinarySpacePartitioning.setPartitionChance(.5);
      Vector<TIMRoom> roomList = TIMBinarySpacePartitioning.partition(w, h, min, max);
      return generate(roomList, connChance, connRatio);
   }
   
   
   public static ZoneMap generate(Vector<TIMRoom> roomList, double connChance, double connRatio)
   {
      ZoneMap z = new ZoneMap(roomList.elementAt(0).size.x, roomList.elementAt(0).size.y);
      for(Room r : roomList)
      {
         if(!r.isParent)
         {
            for(int x = r.origin.x + 1; x < r.origin.x + r.size.x - 1; x++)
            for(int y = r.origin.y + 1; y < r.origin.y + r.size.y - 1; y++)
            {
               z.getTileMap()[x][y] = MapCellFactory.getMapCell(MapCellBase.CLEAR);
            }
         }
      }
      addDoors(z, roomList);
      increaseConnectivity(z, roomList, connChance, connRatio);
      z.updateAllMaps();
      z.setRoomList(TIMRoom.removeParents(roomList));
      return z;
   }
   public static ZoneMap generate(int w, int h, int min, int max)
   {
      return generate(w, h, min, max, .5, .5);
   }
   
   public static ZoneMap generateBSPCatacombs(Vector<TIMRoom> roomList, int minRoomSize, int maxRoomSize, double connChance, double connRatio)
   {
      Vector<TIMRoom> newRoomList = new Vector<TIMRoom>();
      return null;
   }

   
   protected static void increaseConnectivity(ZoneMap z, Vector<TIMRoom> roomList, double connectionChance, double affectedRooms)
   {
      roomList = TIMRoom.removeParents(roomList);
      Vector<Coord> prospectList = new Vector<Coord>();
      TIMRoom curRoom;
      Collections.sort(roomList);
      Collections.reverse(roomList);
      for(int i = 0; i < (int)(roomList.size() * affectedRooms); i++)
      {
         curRoom = roomList.elementAt(i);
         curRoom.setConnections(z);
         if(!curRoom.connectsNorth && RNG.nextDouble() <= connectionChance)
         {
            prospectList = getHorizontalDoorProspects(z, curRoom.origin.x + 1, 
                           curRoom.origin.x + curRoom.size.x - 1, curRoom.origin.y);
            if(prospectList.size() > 0)
            {
               Coord target = prospectList.elementAt(RNG.nextInt(prospectList.size()));
               z.getTileMap()[target.x][target.y] = MapCellFactory.getDoor();
            }
         }
         if(!curRoom.connectsSouth && RNG.nextDouble() <= connectionChance)
         {
            prospectList = getHorizontalDoorProspects(z, curRoom.origin.x + 1, 
                           curRoom.origin.x + curRoom.size.x - 1, curRoom.origin.y + curRoom.size.y - 1);
            if(prospectList.size() > 0)
            {
               Coord target = prospectList.elementAt(RNG.nextInt(prospectList.size()));
               z.getTileMap()[target.x][target.y] = MapCellFactory.getDoor();
            }
         }
         if(!curRoom.connectsWest && RNG.nextDouble() <= connectionChance)
         {
            prospectList = getVerticalDoorProspects(z, curRoom.origin.x, 
                           curRoom.origin.y + 1, curRoom.origin.y + curRoom.size.y - 1);
            if(prospectList.size() > 0)
            {
               Coord target = prospectList.elementAt(RNG.nextInt(prospectList.size()));
               z.getTileMap()[target.x][target.y] = MapCellFactory.getDoor();
            }
         }
         if(!curRoom.connectsEast && RNG.nextDouble() <= connectionChance)
         {
            prospectList = getVerticalDoorProspects(z, curRoom.origin.x + curRoom.size.x - 1, 
                           curRoom.origin.y + 1, curRoom.origin.y + curRoom.size.y - 1);
            if(prospectList.size() > 0)
            {
               Coord target = prospectList.elementAt(RNG.nextInt(prospectList.size()));
               z.getTileMap()[target.x][target.y] = MapCellFactory.getDoor();
            }
         }
      }
   }

}