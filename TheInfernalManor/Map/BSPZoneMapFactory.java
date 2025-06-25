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
   
   public static ZoneMap generateDungeon(Vector<TIMRoom> roomList, int minRoomSize, int maxRoomSize, double connChance, double connRatio)
   {
      Vector<TIMRoom> newRoomList = new Vector<TIMRoom>();
      for(int i = 0; i < roomList.size(); i++)
         if(roomList.elementAt(i).isParent)
            newRoomList.add(roomList.elementAt(i));
         else
            newRoomList.add(generateSubroom(roomList.elementAt(i), minRoomSize, maxRoomSize));
      roomList = newRoomList;
      ZoneMap z = new ZoneMap(roomList.elementAt(0).size.x, roomList.elementAt(0).size.y);
      for(TIMRoom r : roomList)
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
      addTunnels(z, roomList);
      z.updateAllMaps();
      z.setRoomList(TIMRoom.removeParents(roomList));
      return z;
   }
   
   protected static TIMRoom generateSubroom(TIMRoom original, int minSize, int maxSize)
   {
      TIMRoom newRoom = new TIMRoom(original);
      int sizeVariability = maxSize - minSize;
      Coord newSize = new Coord(minSize + RNG.nextInt(sizeVariability + 1), minSize + RNG.nextInt(sizeVariability + 1));
      if(newSize.x > original.size.x)
         newSize.x = original.size.x;
      if(newSize.y > original.size.y)
         newSize.y = original.size.y;
      newRoom.size = newSize;
      int xSpace = original.size.x - newRoom.size.x;
      int ySpace = original.size.y - newRoom.size.y;
      newRoom.origin.x += RNG.nextInt(xSpace + 1);
      newRoom.origin.y += RNG.nextInt(ySpace + 1);
      return newRoom;
   }
      
   protected static void addDoors(ZoneMap z, Vector<TIMRoom> roomList)
   {
      for(int i = 1; i < roomList.size(); i += 2)
      {
         Vector<Coord> prospectList = new Vector<Coord>();
         TIMRoom curRoom = roomList.elementAt(i);
         if(curRoom.isHorizontallyAdjacent(roomList.elementAt(i + 1)))
         {
            prospectList = getVerticalDoorProspects(z, curRoom.origin.x + curRoom.size.x - 1, 
                           curRoom.origin.y + 1, curRoom.origin.y + curRoom.size.y - 1);
         }
         else // vertically adjacent
         {
            prospectList = getHorizontalDoorProspects(z, curRoom.origin.x + 1, 
                           curRoom.origin.x + curRoom.size.x - 1, curRoom.origin.y + curRoom.size.y - 1);
         }
         if(prospectList.size() > 0)
         {
            Coord target = prospectList.elementAt(RNG.nextInt(prospectList.size()));
            z.getTileMap()[target.x][target.y] = MapCellFactory.getDoor();
         }
      }
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
   
   protected static void addTunnels(ZoneMap z, Vector<TIMRoom> roomList)
   {
      for(int i = 1; i < roomList.size(); i += 2)
      {
         TIMRoom a = roomList.elementAt(i);
         TIMRoom b = roomList.elementAt(i + 1);
         // horizontally adjacent
         if(a.contains(b.origin.x - 1, b.origin.y + 1))
            addHorizontalTunnel(z, a, b);
         else
            addVerticalTunnel(z, a, b);
      }
   }
   
   public static void addVerticalTunnel(ZoneMap z, TIMRoom a, TIMRoom b)
   {
      Vector<Coord> aList = new Vector<Coord>();
      for(int i = 0; aList.size() == 0; i++)
         aList = getHorizontalDoorProspects(z, a.origin.x + 1, a.origin.x + a.size.x - 2, a.origin.y + a.size.y - (2 + i));
      Vector<Coord> bList = new Vector<Coord>();
      for(int i = 0; bList.size() == 0; i++)
         bList = getHorizontalDoorProspects(z, b.origin.x + 1, b.origin.x + b.size.x - 2, b.origin.y + 1 + i);
      Coord start = null;
      Coord end = null;
      // try and make a straight line
      if(RNG.nextInt(2) == 1)
      {
         Vector<Coord> verifiedStartList = new Vector<Coord>();
         Vector<Coord> verifiedEndList = new Vector<Coord>();
         for(Coord st : aList)
         for(Coord en : bList)
         {
            if(st.x == en.x)
            {
               verifiedStartList.add(st);
               verifiedEndList.add(en);
            }
         }
         if(verifiedStartList.size() > 0)
         {
            int index = RNG.nextInt(verifiedStartList.size());
            start = verifiedStartList.elementAt(index);
            end = verifiedEndList.elementAt(index);
            setLine(z, start, end, MapCellBase.CLEAR);
         }
      }
      if(start == null) // no straight tunnel built, build angled tunnel
      {
         start = pickCoordFromList(aList);
         end = pickCoordFromList(bList);
         Coord median1 = new Coord(start.x, (start.y + end.y) / 2);
         Coord median2 = new Coord(end.x, (start.y + end.y) / 2);
         setLine(z, start, median1, MapCellBase.CLEAR);
         setLine(z, median1, median2, MapCellBase.CLEAR);
         setLine(z, median2, end, MapCellBase.CLEAR);
      }
   }

   
   public static void addHorizontalTunnel(ZoneMap z, TIMRoom a, TIMRoom b)
   {
      Vector<Coord> aList = new Vector<Coord>();
      for(int i = 0; aList.size() == 0; i++)
         aList = getVerticalDoorProspects(z, a.origin.x + a.size.x - (2 + i), a.origin.y + 1, a.origin.y + a.size.y - 2);
      Vector<Coord> bList = new Vector<Coord>();
      for(int i = 0; bList.size() == 0; i++)
         bList = getVerticalDoorProspects(z, b.origin.x + 1 + i, b.origin.y + 1, b.origin.y + b.size.y - 2);
      Coord start = null;
      Coord end = null;
      // try and make a straight line
      if(RNG.nextInt(2) == 1)
      {
         Vector<Coord> verifiedStartList = new Vector<Coord>();
         Vector<Coord> verifiedEndList = new Vector<Coord>();
         for(Coord st : aList)
         for(Coord en : bList)
         {
            if(st.y == en.y)
            {
               verifiedStartList.add(st);
               verifiedEndList.add(en);
            }
         }
         if(verifiedStartList.size() > 0)
         {
            int index = RNG.nextInt(verifiedStartList.size());
            start = verifiedStartList.elementAt(index);
            end = verifiedEndList.elementAt(index);
            setLine(z, start, end, MapCellBase.CLEAR);
         }
      }
      if(start == null) // no straight tunnel built, build angled tunnel
      {
         start = pickCoordFromList(aList);
         end = pickCoordFromList(bList);
         Coord median1 = new Coord((start.x + end.x) / 2, start.y);
         Coord median2 = new Coord((start.x + end.x) / 2, end.y);
         setLine(z, start, median1, MapCellBase.CLEAR);
         setLine(z, median1, median2, MapCellBase.CLEAR);
         setLine(z, median2, end, MapCellBase.CLEAR);
      }
   }
}