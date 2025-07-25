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
      // no longer need sibiling pairs, can sort to add connections with preference for bigger rooms
      roomList = TIMRoom.removeParents(roomList);
      Collections.sort(roomList);
      Collections.reverse(roomList);
      increaseConnectivity(z, roomList, connChance, connRatio);
      z.updateAllMaps();
      setConnections(z, roomList);
      z.setRoomList(roomList);
      addChests(z);
      addEntranceAndExit(z, Direction.WEST);
      return z;
   }
   public static ZoneMap generate(int w, int h, int min, int max)
   {
      return generate(w, h, min, max, .5, .5);
   }
   
   public static ZoneMap generateVillage(Vector<TIMRoom> roomList, int minRoomSize, int maxRoomSize, double connChance, double connRatio)
   {
      Vector<TIMRoom> newRoomList = new Vector<TIMRoom>();
      for(int i = 0; i < roomList.size(); i++)
         if(roomList.elementAt(i).isParent)
            newRoomList.add(roomList.elementAt(i));
         else
            newRoomList.add(generateSubroom(roomList.elementAt(i), minRoomSize, maxRoomSize));
      ZoneMap z = new ZoneMap(roomList.elementAt(0).size.x, roomList.elementAt(0).size.y);
      for(int x = 0; x < z.getWidth(); x++)
      for(int y = 0; y < z.getHeight(); y++)
         z.getTileMap()[x][y] = MapCellFactory.getMapCell(MapCellBase.CLEAR);
         
      for(TIMRoom r : newRoomList)
      {
         if(!r.isParent)
         {
            for(int x = r.origin.x; x < r.origin.x + r.size.x; x++)
            {
               z.getTileMap()[x][r.origin.y] = MapCellFactory.getMapCell(MapCellBase.WALL);
               z.getTileMap()[x][r.origin.y + r.size.y - 1] = MapCellFactory.getMapCell(MapCellBase.WALL);
            }
            for(int y = r.origin.y; y < r.origin.y + r.size.y; y++)
            {
               z.getTileMap()[r.origin.x][y] = MapCellFactory.getMapCell(MapCellBase.WALL);
               z.getTileMap()[r.origin.x + r.size.x - 1][y] = MapCellFactory.getMapCell(MapCellBase.WALL);
            }
         }
      }
      // no longer need sibiling pairs, can sort to add connections with preference for bigger rooms
      newRoomList = TIMRoom.removeParents(newRoomList);
      addRandomDoors(z, newRoomList);
      for(TIMRoom r : newRoomList)
         r.setAllPaletteVariations(z, MapPalette.ALTERNATE);
      Collections.sort(newRoomList);
      Collections.reverse(newRoomList);
      z.setRoomList(newRoomList);
      ZoneMap biggerZoneMap = new ZoneMap(z.getWidth() + 2, z.getHeight() + 2);
      overlay(z, biggerZoneMap, 1, 1);
      z = biggerZoneMap;
      addEntranceAndExit(z, Direction.WEST);
      z.updateAllMaps();
      setConnections(z, newRoomList);
      return z;
   }
   
   public static ZoneMap generateDungeon(Vector<TIMRoom> roomList, int minRoomSize, int maxRoomSize, double connChance, double connRatio)
   {
      Vector<TIMRoom> newRoomList = new Vector<TIMRoom>();
      for(int i = 0; i < roomList.size(); i++)
         if(roomList.elementAt(i).isParent)
            newRoomList.add(roomList.elementAt(i));
         else
            newRoomList.add(generateSubroom(roomList.elementAt(i), minRoomSize, maxRoomSize));
      ZoneMap z = new ZoneMap(roomList.elementAt(0).size.x, roomList.elementAt(0).size.y);
      for(TIMRoom r : newRoomList)
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
      addTunnels(z, roomList, newRoomList); // use low walls instead of clear to avoid misidentifying paths as rooms
      replaceAll(z, MapCellBase.LOW_WALL, MapCellBase.CLEAR);
      // no longer need sibiling pairs, can sort to add connections with preference for bigger rooms
      newRoomList = TIMRoom.removeParents(newRoomList);
      Collections.sort(newRoomList);
      Collections.reverse(newRoomList);
      increaseDungeonConnectivity(z, newRoomList, connChance, connRatio);
      z.updateAllMaps();
      setConnections(z, newRoomList);
      z.setRoomList(TIMRoom.removeParents(newRoomList));
      addChests(z);
      addEntranceAndExit(z, Direction.WEST);
      return z;
   }
   
   // generated rooms are at least one tile smaller than the original, with a buffer in the north and west
   protected static TIMRoom generateSubroom(TIMRoom original, int minSize, int maxSize)
   {
      TIMRoom newRoom = new TIMRoom(original);
      int sizeVariability = maxSize - minSize;
      Coord newSize = new Coord(minSize + RNG.nextInt(sizeVariability + 1), minSize + RNG.nextInt(sizeVariability + 1));
      if(newSize.x > original.size.x - 2)
         newSize.x = original.size.x - 2;
      if(newSize.y > original.size.y - 2)
         newSize.y = original.size.y - 2;
      newRoom.size = newSize;
      int xSpace = (original.size.x - 2) - newRoom.size.x;
      int ySpace = (original.size.y - 2) - newRoom.size.y;
      newRoom.origin.x += RNG.nextInt(xSpace + 1) + 1;
      newRoom.origin.y += RNG.nextInt(ySpace + 1) + 1;
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
            prospectList = searchVerticallyForDoorProspects(z, curRoom.origin.x + curRoom.size.x - 1, 
                           curRoom.origin.y + 1, curRoom.origin.y + curRoom.size.y - 1);
         }
         else // vertically adjacent
         {
            prospectList = searchHorizontallyForDoorProspects(z, curRoom.origin.x + 1, 
                           curRoom.origin.x + curRoom.size.x - 1, curRoom.origin.y + curRoom.size.y - 1);
         }
         if(prospectList.size() > 0)
         {
            Coord target = prospectList.elementAt(RNG.nextInt(prospectList.size()));
            z.getTileMap()[target.x][target.y] = MapCellFactory.getDoor();
         }
      }
   }
      
   protected static void addRandomDoors(ZoneMap z, Vector<TIMRoom> roomList)
   {
      for(TIMRoom room : roomList)
      {
         if(!room.isParent)
         {
            int x = room.origin.x + 1 + RNG.nextInt(room.size.x - 2);
            int y = room.origin.y + 1 + RNG.nextInt(room.size.y - 2);
            int roll = RNG.nextInt(4);
            // rooms near edge should not point door towards that edge
            switch(roll)
            {
               // west
               case 0 : if(!z.isInBounds(room.origin.x - 10, y))
                           roll = 1;
                        break;
               // east
               case 1 : if(!z.isInBounds(room.origin.x + room.size.x + 9, y))
                           roll = 0;
                        break;
               // north
               case 2 : if(!z.isInBounds(x, room.origin.y - 10))
                           roll = 3;
                        break;
               // south
               case 3 : if(!z.isInBounds(x, room.origin.y + room.size.y + 9))
                           roll = 2;
                        break;
            }
            switch(roll)
            {
               case 0 : x = room.origin.x; break;
               case 1 : x = room.origin.x + room.size.x - 1; break;
               case 2 : y = room.origin.y; break;
               case 3 : y = room.origin.y + room.size.y - 1; break;
            }
            z.getTileMap()[x][y] = MapCellFactory.getDoor();
         }
      }
   }
   
   protected static void increaseConnectivity(ZoneMap z, Vector<TIMRoom> roomList, double connectionChance, double affectedRooms)
   {
      Vector<Coord> prospectList = new Vector<Coord>();
      TIMRoom curRoom;
      for(int i = 0; i < (int)(roomList.size() * affectedRooms); i++)
      {
         curRoom = roomList.elementAt(i);
         curRoom.setConnections(z);
         if(!curRoom.connectsNorth && RNG.nextDouble() <= connectionChance)
         {
            prospectList = searchHorizontallyForDoorProspects(z, curRoom.origin.x + 1, 
                           curRoom.origin.x + curRoom.size.x - 1, curRoom.origin.y);
            if(prospectList.size() > 0)
            {
               Coord target = prospectList.elementAt(RNG.nextInt(prospectList.size()));
               z.getTileMap()[target.x][target.y] = MapCellFactory.getDoor();
            }
         }
         if(!curRoom.connectsSouth && RNG.nextDouble() <= connectionChance)
         {
            prospectList = searchHorizontallyForDoorProspects(z, curRoom.origin.x + 1, 
                           curRoom.origin.x + curRoom.size.x - 1, curRoom.origin.y + curRoom.size.y - 1);
            if(prospectList.size() > 0)
            {
               Coord target = prospectList.elementAt(RNG.nextInt(prospectList.size()));
               z.getTileMap()[target.x][target.y] = MapCellFactory.getDoor();
            }
         }
         if(!curRoom.connectsWest && RNG.nextDouble() <= connectionChance)
         {
            prospectList = searchVerticallyForDoorProspects(z, curRoom.origin.x, 
                           curRoom.origin.y + 1, curRoom.origin.y + curRoom.size.y - 1);
            if(prospectList.size() > 0)
            {
               Coord target = prospectList.elementAt(RNG.nextInt(prospectList.size()));
               z.getTileMap()[target.x][target.y] = MapCellFactory.getDoor();
            }
         }
         if(!curRoom.connectsEast && RNG.nextDouble() <= connectionChance)
         {
            prospectList = searchVerticallyForDoorProspects(z, curRoom.origin.x + curRoom.size.x - 1, 
                           curRoom.origin.y + 1, curRoom.origin.y + curRoom.size.y - 1);
            if(prospectList.size() > 0)
            {
               Coord target = prospectList.elementAt(RNG.nextInt(prospectList.size()));
               z.getTileMap()[target.x][target.y] = MapCellFactory.getDoor();
            }
         }
      }
   }

   
   protected static void increaseDungeonConnectivity(ZoneMap z, Vector<TIMRoom> roomList, double connectionChance, double affectedRooms)
   {
      for(int i = 0; i < (int)(roomList.size() * affectedRooms); i++)
      {
         TIMRoom room = roomList.elementAt(i);
         room.setConnections(z);
         // north
         if(!room.connectsNorth && RNG.nextDouble() <= connectionChance)
         {
            Vector<Coord> prospectList = new Vector<Coord>();
            for(int x = room.origin.x + 1; x < room.origin.x + room.size.x - 2; x++)
            {
               int y = room.origin.y;
               boolean foundF = false;
               while(z.isInBounds(x, y) && !foundF)
               {
                  if(z.getTile(x, y - 1).isLowPassable() ||
                     z.getTile(x + 1, y).isLowPassable() ||
                     z.getTile(x - 1, y).isLowPassable())
                     foundF = true;
                  else
                     y--;
               }
               if(foundF)
                  prospectList.add(new Coord(x, y));
            }
            if(prospectList.size() > 0)
            {
               Coord target = pickCoordFromList(prospectList);
               setLine(z, new Coord(target.x, room.origin.y), target, MapCellBase.CLEAR);
            }
         }
         // south
         if(!room.connectsSouth && RNG.nextDouble() <= connectionChance)
         {
            Vector<Coord> prospectList = new Vector<Coord>();
            for(int x = room.origin.x + 1; x < room.origin.x + room.size.x - 2; x++)
            {
               int y = room.origin.y + room.size.y - 1;
               boolean foundF = false;
               while(z.isInBounds(x, y) && !foundF)
               {
                  if(z.getTile(x, y + 1).isLowPassable() ||
                     z.getTile(x + 1, y).isLowPassable() ||
                     z.getTile(x - 1, y).isLowPassable())
                     foundF = true;
                  else
                     y++;
               }
               if(foundF)
                  prospectList.add(new Coord(x, y));
            }
            if(prospectList.size() > 0)
            {
               Coord target = pickCoordFromList(prospectList);
               setLine(z, new Coord(target.x, room.origin.y + room.size.y - 1), target, MapCellBase.CLEAR);
            }
         }
         // east
         if(!room.connectsEast && RNG.nextDouble() <= connectionChance)
         {
            Vector<Coord> prospectList = new Vector<Coord>();
            for(int y = room.origin.y + 1; y < room.origin.y + room.size.y - 2; y++)
            {
               int x = room.origin.x + room.size.x - 1;
               boolean foundF = false;
               while(z.isInBounds(x, y) && !foundF)
               {
                  if(z.getTile(x + 1, y).isLowPassable() ||
                     z.getTile(x, y + 1).isLowPassable() ||
                     z.getTile(x, y - 1).isLowPassable())
                     foundF = true;
                  else
                     x++;
               }
               if(foundF)
                  prospectList.add(new Coord(x, y));
            }
            if(prospectList.size() > 0)
            {
               Coord target = pickCoordFromList(prospectList);
               setLine(z, new Coord(room.origin.x + room.size.x - 1, target.y), target, MapCellBase.CLEAR);
            }
         }
         // west
         if(!room.connectsWest && RNG.nextDouble() <= connectionChance)
         {
            Vector<Coord> prospectList = new Vector<Coord>();
            for(int y = room.origin.y + 1; y < room.origin.y + room.size.y - 2; y++)
            {
               int x = room.origin.x;
               boolean foundF = false;
               while(z.isInBounds(x, y) && !foundF)
               {
                  if(z.getTile(x - 1, y).isLowPassable() ||
                     z.getTile(x, y + 1).isLowPassable() ||
                     z.getTile(x, y - 1).isLowPassable())
                     foundF = true;
                  else
                     x--;
               }
               if(foundF)
                  prospectList.add(new Coord(x, y));
            }
            if(prospectList.size() > 0)
            {
               Coord target = pickCoordFromList(prospectList);
               setLine(z, new Coord(room.origin.x, target.y), target, MapCellBase.CLEAR);
            }
         }
      }
   }
   
   protected static void addTunnels(ZoneMap z, Vector<TIMRoom> bigRoomList, Vector<TIMRoom> smallRoomList)
   {
      for(int i = 1; i < smallRoomList.size(); i += 2)
      {
         TIMRoom bigA = bigRoomList.elementAt(i);
         TIMRoom bigB = bigRoomList.elementAt(i + 1);
         TIMRoom smallA = smallRoomList.elementAt(i);
         TIMRoom smallB = smallRoomList.elementAt(i + 1);
         // horizontally adjacent
         if(bigA.contains(bigB.origin.x - 1, bigB.origin.y + 1))
            addHorizontalTunnel(z, smallA, smallB);
         // vertically adjacent
         else
            addVerticalTunnel(z, smallA, smallB);
      }
   }
   
   public static void addVerticalTunnel(ZoneMap z, TIMRoom a, TIMRoom b)
   {
      Vector<Coord> aList = new Vector<Coord>();
      for(int i = 0; aList.size() == 0; i++)
         aList = searchHorizontallyForTunnelProspects(z, a.origin.x + 1, a.origin.x + a.size.x - 2, a.origin.y + a.size.y - 1 - i);
      Vector<Coord> bList = new Vector<Coord>();
      for(int i = 0; bList.size() == 0; i++)
         bList = searchHorizontallyForTunnelProspects(z, b.origin.x + 1, b.origin.x + b.size.x - 2, b.origin.y + i);
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
            start.y++;
            end = verifiedEndList.elementAt(index);
            end.y--;
            setLine(z, start, end, MapCellBase.LOW_WALL);
         }
      }
      if(start == null) // no straight tunnel built, build angled tunnel
      {
         start = pickCoordFromList(aList);
            start.y++;
         end = pickCoordFromList(bList);
            end.y--;
         Coord median1 = new Coord(start.x, (start.y + end.y) / 2);
         Coord median2 = new Coord(end.x, (start.y + end.y) / 2);
         setLine(z, start, median1, MapCellBase.LOW_WALL);
         setLine(z, median1, median2, MapCellBase.LOW_WALL);
         setLine(z, median2, end, MapCellBase.LOW_WALL);
      }
   }

   
   public static void addHorizontalTunnel(ZoneMap z, TIMRoom a, TIMRoom b)
   {
      Vector<Coord> aList = new Vector<Coord>();
      for(int i = 0; aList.size() == 0; i++)
         aList = searchVerticallyForTunnelProspects(z, a.origin.x + a.size.x - (2 + i), a.origin.y + 1, a.origin.y + a.size.y - 2);
      Vector<Coord> bList = new Vector<Coord>();
      for(int i = 0; bList.size() == 0; i++)
         bList = searchVerticallyForTunnelProspects(z, b.origin.x + 1 + i, b.origin.y + 1, b.origin.y + b.size.y - 2);
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
            start.x++;
            end = verifiedEndList.elementAt(index);
            end.x--;
            setLine(z, start, end, MapCellBase.LOW_WALL);
         }
      }
      if(start == null) // no straight tunnel built, build angled tunnel
      {
         start = pickCoordFromList(aList);
         start.x++;
         end = pickCoordFromList(bList);
         end.x--;
         Coord median1 = new Coord((start.x + end.x) / 2, start.y);
         Coord median2 = new Coord((start.x + end.x) / 2, end.y);
         setLine(z, start, median1, MapCellBase.LOW_WALL);
         setLine(z, median1, median2, MapCellBase.LOW_WALL);
         setLine(z, median2, end, MapCellBase.LOW_WALL);
      }
   }
   
   public static TIMRoom getRoom(Coord c, Vector<TIMRoom> roomList)
   {
      for(TIMRoom room : roomList)
      {
         if(!room.isParent && room.contains(c))
            return room;
      }
      return null;
   }
   public static TIMRoom getRoom(int x, int y, Vector<TIMRoom> roomList){return getRoom(new Coord(x, y), roomList);}
}