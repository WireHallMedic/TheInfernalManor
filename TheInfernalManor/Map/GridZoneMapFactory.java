package TheInfernalManor.Map;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Engine.*;
import WidlerSuite.*;
import java.util.*;

public class GridZoneMapFactory extends ZoneMapFactory implements MapConstants, GUIConstants
{
   public static ZoneMap generate(RoomTemplate[][] templateGrid, boolean addExits)
   {
      int roomsWide = templateGrid.length;
      int roomsTall = templateGrid[0].length;
      int widthOfRoom = templateGrid[0][0].getWidth();
      int heightOfRoom = templateGrid[0][0].getHeight();
      int totalMapWidth = ((widthOfRoom - 1) * roomsWide) + 1;
      int totalMapHeight = ((heightOfRoom - 1) * roomsTall) + 1;
      ZoneMap zm = new ZoneMap(totalMapWidth, totalMapHeight);
      clear(zm);
      for(int x = 0; x < roomsWide; x++)
      for(int y = 0; y < roomsTall; y++)
      {
         placeTemplate(zm, templateGrid[x][y].resolveRandomTiles(), x * (widthOfRoom - 1), y * (heightOfRoom - 1));
         if(templateGrid[x][y].getConnectionType() != ConnectionType.ISOLATED)
         {
            TIMRoom room = new TIMRoom();
            room.origin = new Coord(x * (widthOfRoom - 1), y * (heightOfRoom - 1));
            room.size = new Coord(widthOfRoom, heightOfRoom);
            zm.getRoomList().add(room);
         }
      }
      if(addExits)
         addEntranceAndExit(zm, Direction.WEST);
      setConnections(zm, zm.getRoomList());
      addChests(zm);
      zm.updateAllMaps();
      return zm;
   }
   public static ZoneMap generate(RoomTemplate[][] templateGrid){return generate(templateGrid, true);}
   
   
   // generate map for island-style
   public static ZoneMap generate(GridOfMapGrids upper, int separation)
   {
      separation = Math.max(separation, 5);
      int roomsWide = upper.getWidth() * upper.getLowerWidth();
      int roomsTall = upper.getHeight() * upper.getLowerHeight();
      int widthOfRoom = upper.getLowerGrid(0, 0).getTemplateMap()[0][0].getWidth();
      int heightOfRoom = upper.getLowerGrid(0, 0).getTemplateMap()[0][0].getHeight();
      int islandWidth = (widthOfRoom * upper.getLowerWidth()) + separation;
      int islandHeight = (heightOfRoom * upper.getLowerHeight()) + separation;
      int totalMapWidth = islandWidth * upper.getWidth();
      int totalMapHeight = islandHeight * upper.getHeight();
      ZoneMap zm = new ZoneMap(totalMapWidth, totalMapHeight);
      clear(zm);
      for(int x = 0; x < upper.getWidth(); x++)
      for(int y = 0; y < upper.getHeight(); y++)
      {
         ZoneMap submap = generate(upper.getLowerGrid(x, y).getTemplateMap(), false);
         int maxXInset = islandWidth - submap.getWidth() - 4;
         int maxYInset = islandHeight - submap.getHeight() - 4;
         overlay(submap, zm, (x * islandWidth) + RNG.nextInt(maxXInset + 3), (y * islandHeight) + RNG.nextInt(maxYInset + 3));
      }
      addBridges(zm, upper.getUpperGrid(), widthOfRoom, heightOfRoom, islandWidth, islandHeight);
      fillNulls(zm);
      zm.updateAllMaps();
      addEntranceAndExit(zm, Direction.WEST);
      
      return zm;
   }
   
   public static ZoneMap generate(GridOfMapGrids upper)
   {
      return generate(upper, 3);
   }
   
   protected static void addBridges(ZoneMap z, MapGrid connMap, int widthOfRoom, int heightOfRoom, 
                             int islandWidth, int islandHeight)
   {
      RoomTemplate[][] rt = connMap.getTemplateMap();
      for(int x = 0; x < rt.length - 1; x++)
      for(int y = 0; y < rt[0].length; y++)
      {
         if(rt[x][y].connectsEast())
         {
            Coord start = null;
            Coord end = null;
            // chance to try and make straight bridge
            if(RNG.nextInt(2) == 1)
            {
               Vector<Coord> startList = getEastCoords(z, x * islandWidth, y * islandHeight, islandWidth, islandHeight);
               Vector<Coord> endList = getWestCoords(z, (x + 1) * islandWidth, y * islandHeight, islandWidth, islandHeight);
               Vector<Coord> verifiedStartList = new Vector<Coord>();
               Vector<Coord> verifiedEndList = new Vector<Coord>();
               // make list of linear pairs
               for(Coord st : startList)
               for(Coord en : endList)
               {
                  if(st.y == en.y)
                  {
                     verifiedStartList.add(st);
                     verifiedEndList.add(en);
                  }
               }
               // if at least one pair exists, choose a pair and make a bridge
               if(verifiedStartList.size() > 0)
               {
                  int index = RNG.nextInt(verifiedStartList.size());
                  start = verifiedStartList.elementAt(index);
                  end = verifiedEndList.elementAt(index);
                  setLine(z, start, end, MapCellBase.CLEAR);
               }
            }
            // if no bridge has been made, make a non-linear bridge
            if(start == null)
            {
               start = getEastCoord(z, x * islandWidth, y * islandHeight, islandWidth, islandHeight);
               end = getWestCoord(z, (x + 1) * islandWidth, y * islandHeight, islandWidth, islandHeight);
               if(start != null && end != null)
               {
                  Coord median1 = new Coord((start.x + end.x) / 2, start.y);
                  Coord median2 = new Coord((start.x + end.x) / 2, end.y);
                  setLine(z, start, median1, MapCellBase.CLEAR);
                  setLine(z, median1, median2, MapCellBase.CLEAR);
                  setLine(z, median2, end, MapCellBase.CLEAR);
               }
            }
         }
      }
      for(int x = 0; x < rt.length; x++)
      for(int y = 0; y < rt[0].length - 1; y++)
      {
         if(rt[x][y].connectsSouth())
         {
            Coord start = null;
            Coord end = null;
            // chance to try and make straight bridge
            if(RNG.nextInt(2) == 1)
            {
               Vector<Coord> startList = getSouthCoords(z, x * islandWidth, y * islandHeight, islandWidth, islandHeight);
               Vector<Coord> endList = getNorthCoords(z, (x + 1) * islandWidth, y * islandHeight, islandWidth, islandHeight);
               Vector<Coord> verifiedStartList = new Vector<Coord>();
               Vector<Coord> verifiedEndList = new Vector<Coord>();
               // make list of linear pairs
               for(Coord st : startList)
               for(Coord en : endList)
               {
                  if(st.x == en.x)
                  {
                     verifiedStartList.add(st);
                     verifiedEndList.add(en);
                  }
               }
               // if at least one pair exists, choose a pair and make a bridge
               if(verifiedStartList.size() > 0)
               {
                  int index = RNG.nextInt(verifiedStartList.size());
                  start = verifiedStartList.elementAt(index);
                  end = verifiedEndList.elementAt(index);
                  setLine(z, start, end, MapCellBase.CLEAR);
               }
            }
            // if no bridge has been made, make a non-linear bridge
            if(start == null)
            {
               start = getSouthCoord(z, x * islandWidth, y * islandHeight, islandWidth, islandHeight);
               end = getNorthCoord(z, x * islandWidth, (y + 1) * islandHeight, islandWidth, islandHeight);
               if(start != null && end != null)
               {
                  Coord median1 = new Coord(start.x, (start.y + end.y) / 2);
                  Coord median2 = new Coord(end.x, (start.y + end.y) / 2);
                  setLine(z, start, median1, MapCellBase.CLEAR);
                  setLine(z, median1, median2, MapCellBase.CLEAR);
                  setLine(z, median2, end, MapCellBase.CLEAR);
               }
            }
         }
      }
   }

   
   protected static Vector<Coord> getWestCoords(ZoneMap zm, int xStart, int yStart, int islandWidth, int islandHeight)
   {
      Vector<Coord> cellList = new Vector<Coord>();
      for(int x = 0; x < islandWidth && cellList.size() == 0; x++)
      {
         for(int y = 0; y < islandHeight; y++)
         {
            if(zm.getTile(xStart + x, yStart + y) != null &&
               (zm.getTile(xStart + x, yStart + y).isLowPassable() || 
                zm.getTile(xStart + x, yStart + y) instanceof ToggleTile))
            {
               cellList.add(new Coord(xStart + x, yStart + y));
            }
         }
      }
      return cellList;
   }
   
   protected static Coord getWestCoord(ZoneMap zm, int xStart, int yStart, int islandWidth, int islandHeight)
   {
      return pickCoordFromList(getWestCoords(zm, xStart, yStart, islandWidth, islandHeight));
   }
   
   protected static Vector<Coord> getEastCoords(ZoneMap zm, int xStart, int yStart, int islandWidth, int islandHeight)
   {
      Vector<Coord> cellList = new Vector<Coord>();
      for(int x = islandWidth - 1; x >= 0 && cellList.size() == 0; x--)
      {
         for(int y = 0; y < islandHeight; y++)
         {
            if(zm.getTile(xStart + x, yStart + y) != null &&
               (zm.getTile(xStart + x, yStart + y).isLowPassable() || 
                zm.getTile(xStart + x, yStart + y) instanceof ToggleTile))
            {
               cellList.add(new Coord(xStart + x, yStart + y));
            }
         }
      }
      return cellList;
   }
   
   protected static Coord getEastCoord(ZoneMap zm, int xStart, int yStart, int islandWidth, int islandHeight)
   {
      return pickCoordFromList(getEastCoords(zm, xStart, yStart, islandWidth, islandHeight));
   }
   
   protected static Vector<Coord> getNorthCoords(ZoneMap zm, int xStart, int yStart, int islandWidth, int islandHeight)
   {
      Vector<Coord> cellList = new Vector<Coord>();
      for(int y = 0; y < islandHeight && cellList.size() == 0; y++)
      {
         for(int x = 0; x < islandWidth; x++)
         {
            if(zm.getTile(xStart + x, yStart + y) != null &&
               (zm.getTile(xStart + x, yStart + y).isLowPassable() || 
                zm.getTile(xStart + x, yStart + y) instanceof ToggleTile))
            {
               cellList.add(new Coord(xStart + x, yStart + y));
            }
         }
      }
      return cellList;
   }
   
   protected static Coord getNorthCoord(ZoneMap zm, int xStart, int yStart, int islandWidth, int islandHeight)
   {
      return pickCoordFromList(getNorthCoords(zm, xStart, yStart, islandWidth, islandHeight));
   }
   
   protected static Vector<Coord> getSouthCoords(ZoneMap zm, int xStart, int yStart, int islandWidth, int islandHeight)
   {
      Vector<Coord> cellList = new Vector<Coord>();
      for(int y = islandHeight - 1; y >= 0 && cellList.size() == 0; y--)
      {
         for(int x = 0; x < islandWidth; x++)
         {
            if(zm.getTile(xStart + x, yStart + y) != null &&
               (zm.getTile(xStart + x, yStart + y).isLowPassable() || 
                zm.getTile(xStart + x, yStart + y) instanceof ToggleTile))
            {
               cellList.add(new Coord(xStart + x, yStart + y));
            }
         }
      }
      return cellList;
   }
   
   protected static Coord getSouthCoord(ZoneMap zm, int xStart, int yStart, int islandWidth, int islandHeight)
   {
      return pickCoordFromList(getSouthCoords(zm, xStart, yStart, islandWidth, islandHeight));
   }



}