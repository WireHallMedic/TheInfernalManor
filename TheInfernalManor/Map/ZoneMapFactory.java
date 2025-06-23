package TheInfernalManor.Map;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Engine.*;
import WidlerSuite.*;
import java.util.*;

public class ZoneMapFactory implements MapConstants, GUIConstants
{
   public static ZoneMap generate(RoomTemplate[][] templateGrid)
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
      }
      zm.updateAllMaps();
      return zm;
   }
   
   
   // generate map for island-style
   public static ZoneMap generate(GridOfMapGrids upper, int separation)
   {
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
         ZoneMap submap = new ZoneMap(islandWidth, islandHeight);
         clear(submap);
         for(int x2 = 0; x2 < upper.getLowerWidth(); x2++)
         for(int y2 = 0; y2 < upper.getLowerHeight(); y2++)
         {
            RoomTemplate rt = upper.getLowerGrid(x, y).getTemplateMap()[x2][y2].resolveRandomTiles();
            int xOrigin = (widthOfRoom - 1) * x2;
            int yOrigin = (heightOfRoom - 1) * y2;
            placeTemplate(submap, rt, xOrigin, yOrigin);
         }
         fillNulls(submap);
         submap = getTrimmed(submap);
         if(submap != null)
         {
            int maxXInset = islandWidth - submap.getWidth();
            int maxYInset = islandHeight - submap.getHeight();
            overlay(submap, zm, (x * islandWidth) + RNG.nextInt(maxXInset + 1), (y * islandHeight) + RNG.nextInt(maxYInset + 1));
         }
      }
      addBridges(zm, upper.getUpperGrid(), widthOfRoom, heightOfRoom, islandWidth, islandHeight);
      fillNulls(zm);
      replaceAll(zm, MapCellBase.WALL, MapCellBase.DEEP_LIQUID);
      zm.updateAllMaps();
      
      return zm;
   }
   
   public static ZoneMap generate(GridOfMapGrids upper)
   {
      return generate(upper, 3);
   }
   
   protected static void setLine(ZoneMap z, Coord start, Coord end, MapCellBase base)
   {
      Vector<Coord> line = StraightLine.findLine(start, end);
      for(Coord c : line)
      {
         z.getTileMap()[c.x][c.y] = MapCellFactory.getMapCell(base);
      }
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
            Coord start = getEastCoord(z, x * islandWidth, y * islandHeight, islandWidth, islandHeight);
            Coord end = getWestCoord(z, (x + 1) * islandWidth, y * islandHeight, islandWidth, islandHeight);
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
      for(int x = 0; x < rt.length; x++)
      for(int y = 0; y < rt[0].length - 1; y++)
      {
         if(rt[x][y].connectsSouth())
         {
            Coord start = getSouthCoord(z, x * islandWidth, y * islandHeight, islandWidth, islandHeight);
            Coord end = getNorthCoord(z, x * islandWidth, (y + 1) * islandHeight, islandWidth, islandHeight);
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
   
   protected static Coord getWestCoord(ZoneMap zm, int xStart, int yStart, int islandWidth, int islandHeight)
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
      if(cellList.size() > 0)
      {
         return cellList.elementAt(RNG.nextInt(cellList.size()));
      }
      return null;
   }
   
   protected static Coord getEastCoord(ZoneMap zm, int xStart, int yStart, int islandWidth, int islandHeight)
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
      if(cellList.size() > 0)
      {
         return cellList.elementAt(RNG.nextInt(cellList.size()));
      }
      return null;
   }
   
   protected static Coord getNorthCoord(ZoneMap zm, int xStart, int yStart, int islandWidth, int islandHeight)
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
      if(cellList.size() > 0)
      {
         return cellList.elementAt(RNG.nextInt(cellList.size()));
      }
      return null;
   }
   
   protected static Coord getSouthCoord(ZoneMap zm, int xStart, int yStart, int islandWidth, int islandHeight)
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
      if(cellList.size() > 0)
      {
         return cellList.elementAt(RNG.nextInt(cellList.size()));
      }
      return null;
   }
   
   protected static void clear(ZoneMap zm)
   {
      MapCell[][] tileMap = zm.getTileMap();
      for(int x = 0; x < tileMap.length; x++)
      for(int y = 0; y < tileMap[0].length; y++)
         tileMap[x][y] = null;
   }
   
   protected static void fillNulls(ZoneMap zm)
   {
      MapCell[][] tileMap = zm.getTileMap();
      for(int x = 0; x < tileMap.length; x++)
      for(int y = 0; y < tileMap[0].length; y++)
         if(tileMap[x][y] == null)
            tileMap[x][y] = MapCellFactory.getMapCell(MapCellBase.WALL, WHITE, BLACK);
   }
   
   protected static void replaceAll(ZoneMap zm, MapCellBase find, MapCellBase replace)
   {
      MapCell[][] tileMap = zm.getTileMap();
      for(int x = 0; x < tileMap.length; x++)
      for(int y = 0; y < tileMap[0].length; y++)
         if(tileMap[x][y].getIconIndex() == find.iconIndex)
            tileMap[x][y] = MapCellFactory.getMapCell(replace, WHITE, BLACK);
   }
   
   protected static void overlay(ZoneMap top, ZoneMap bottom, int xOrigin, int yOrigin)
   {
      MapCell[][] topMap = top.getTileMap();
      MapCell[][] bottomMap = bottom.getTileMap();
      for(int x = 0; x < top.getWidth(); x++)
      for(int y = 0; y < top.getHeight(); y++)
      {
         if(bottom.isInBounds(x + xOrigin, y + yOrigin))
         {
            bottomMap[x + xOrigin][y + yOrigin] = topMap[x][y];
         }
      }
   }
   
   protected static void placeTemplate(ZoneMap zm, RoomTemplate rt, int xOrigin, int yOrigin)
   {
      MapCell[][] tileMap = zm.getTileMap();
      for(int x = 0; x < rt.getWidth(); x++)
      for(int y = 0; y < rt.getHeight(); y++)
      {
         if(tileMap[x + xOrigin][y + yOrigin] == null)
            tileMap[x + xOrigin][y + yOrigin] = MapCellFactory.getMapCell(rt.getCellMapping(x, y));
         else
         {  // resolve overwrites
            MapCell newCell = MapCellFactory.getMapCell(rt.getCellMapping(x, y));
            if((x == 0 || x == rt.getWidth() - 1) &&
               (y == 0 || y == rt.getHeight() - 1))
               newCell = resolveOverwriteCorner(newCell, tileMap[x + xOrigin][y + yOrigin]);
            else
               newCell = resolveOverwrite(newCell, tileMap[x + xOrigin][y + yOrigin]);
            tileMap[x + xOrigin][y + yOrigin] = newCell;
         }
      }
   }
   
   private static MapCell resolveOverwrite(MapCell c1, MapCell c2)
   {
      MapCell newCell = null;
      // doors have highest priority
      newCell = resolveDoors(c1, c2);
      // low passable has second priority
      if(newCell == null)
         newCell = resolveLowPassable(c1, c2);
      // all others are equal
      if(newCell == null)
         newCell = randomPick(c1, c2);
      return newCell;
   }
   
   private static MapCell resolveOverwriteCorner(MapCell c1, MapCell c2)
   {
      MapCell newCell = null;
      // doors have highest priority
      newCell = resolveDoors(c1, c2);
      // high passable has second priority
      if(newCell == null)
         newCell = resolveHighImpassable(c1, c2);
      // all others are equal
      if(newCell == null)
         newCell = randomPick(c1, c2);
      return newCell;
   }
   
   private static MapCell resolveDoors(MapCell c1, MapCell c2)
   {
      if(c1 instanceof Door && c2 instanceof Door)
         return randomPick(c1, c2);
      if(c1 instanceof Door)
         return c1;
      if(c2 instanceof Door)
         return c2;
      return null;
   }
   
   private static MapCell resolveLowPassable(MapCell c1, MapCell c2)
   {
      if(c1.isLowPassable() && c2.isLowPassable())
        return randomPick(c1, c2);
      if(c1.isLowPassable())
         return c1;
      if(c2.isLowPassable())
         return c2;
      return null;
   }
   
   private static MapCell resolveHighImpassable(MapCell c1, MapCell c2)
   {
      if(!c1.isHighPassable() && !c2.isHighPassable())
         return randomPick(c1, c2);
      if(!c1.isHighPassable())
         return c1;
      if(!c2.isHighPassable())
         return c2;
      return null;
   }
   
   private static MapCell randomPick(MapCell c1, MapCell c2)
   {
      if(RNG.nextBoolean())
         return c1;
      else
         return c2;
   }
   
   // removes high-impassable rows and columns beyond the first in contact with low-impassable
   private static ZoneMap getTrimmed(ZoneMap original)
   {
      int xLeading = 0;
      int yLeading = 0;
      int xTrailing = 0;
      int yTrailing = 0;
      boolean continueF = true;
      for(int x = 0; x < original.getWidth() && continueF; x++)
      {
         for(int y = 0; y < original.getHeight(); y++)
         {
            if(original.getTile(x, y).isHighPassable() || original.getTile(x, y) instanceof ToggleTile)
            {
               continueF = false;
               break;
            }
         }
         if(continueF)
            xLeading++;
      }
      continueF = true;
      for(int x = original.getWidth() - 1; x >= 0 && continueF; x--)
      {
         for(int y = 0; y < original.getHeight(); y++)
         {
            if(original.getTile(x, y).isHighPassable() || original.getTile(x, y) instanceof ToggleTile)
            {
               continueF = false;
               break;
            }
         }
         if(continueF)
            xTrailing++;
      }
      continueF = true;
      for(int y = 0; y < original.getHeight() && continueF; y++)
      {
         for(int x = 0; x < original.getWidth(); x++)
         {
            if(original.getTile(x, y).isHighPassable() || original.getTile(x, y) instanceof ToggleTile)
            {
               continueF = false;
               break;
            }
         }
         if(continueF)
            yLeading++;
      }
      continueF = true;
      for(int y = original.getHeight() - 1; y >= 0 && continueF; y--)
      {
         for(int x = 0; x < original.getWidth(); x++)
         {
            if(original.getTile(x, y).isHighPassable() || original.getTile(x, y) instanceof ToggleTile)
            {
               continueF = false;
               break;
            }
         }
         if(continueF)
            yTrailing++;
      }
      xLeading = Math.max(0, xLeading - 1);
      yLeading = Math.max(0, yLeading - 1);
      xTrailing = Math.max(0, xTrailing - 1);
      yTrailing = Math.max(0, yTrailing - 1);
      
      // solid tile
      if(original.getWidth() - 1 == xLeading)
         return null;
         
      ZoneMap trimmed = new ZoneMap(original.getWidth() - (xLeading + xTrailing), original.getHeight() - (yLeading + yTrailing));
      MapCell[][] oMap = original.getTileMap();
      MapCell[][] tMap = trimmed.getTileMap();
      for(int x = 0; x < trimmed.getWidth(); x++)
      for(int y = 0; y < trimmed.getHeight(); y++)
      {
         tMap[x][y] = oMap[x + xLeading][y + yLeading];
      }
      return trimmed;
   }
}