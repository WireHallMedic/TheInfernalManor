package TheInfernalManor.Map;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Engine.*;
import WidlerSuite.*;
import java.util.*;

public class ZoneMapFactory implements MapConstants, GUIConstants
{   
   
   protected static Vector<Coord> searchVerticallyForDoorProspects(ZoneMap z, int x, int yStart, int yEnd)
   {
      Vector<Coord> prospectList = new Vector<Coord>();
      for(int y = yStart; y < yEnd; y++)
      {
         if(isValidHorizontalDoorLocation(z, x, y))
            prospectList.add(new Coord(x, y));
      }
      return prospectList;
   }
   
   protected static Vector<Coord> searchHorizontallyForDoorProspects(ZoneMap z, int xStart, int xEnd, int y)
   {
      Vector<Coord> prospectList = new Vector<Coord>();
      for(int x = xStart; x < xEnd; x++)
      {
         if(isValidVerticalDoorLocation(z, x, y))
            prospectList.add(new Coord(x, y));
      }
      return prospectList;
   }
   protected static Vector<Coord> searchVerticallyForTunnelProspects(ZoneMap z, int x, int yStart, int yEnd)
   {
      Vector<Coord> prospectList = new Vector<Coord>();
      for(int y = yStart; y <= yEnd; y++)
      {
         if(isValidTunnelStartLocation(z, x, y))
            prospectList.add(new Coord(x, y));
      }
      return prospectList;
   }
   
   protected static Vector<Coord> searchHorizontallyForTunnelProspects(ZoneMap z, int xStart, int xEnd, int y)
   {
      Vector<Coord> prospectList = new Vector<Coord>();
      for(int x = xStart; x <= xEnd; x++)
      {
         if(isValidTunnelStartLocation(z, x, y))
            prospectList.add(new Coord(x, y));
      }
      return prospectList;
   }
   
   protected static boolean isValidVerticalDoorLocation(ZoneMap z, int x, int y)
   {               
      return z.getTile(x, y - 1).isLowPassable() &&
             z.getTile(x, y + 1).isLowPassable() &&
             !(z.getTile(x - 1, y) instanceof Door) &&
             !(z.getTile(x + 1, y) instanceof Door);
   }
   
   protected static boolean isValidHorizontalDoorLocation(ZoneMap z, int x, int y)
   {               
      return z.getTile(x - 1, y).isLowPassable() &&
             z.getTile(x + 1, y).isLowPassable() &&
             !(z.getTile(x, y - 1) instanceof Door) &&
             !(z.getTile(x, y + 1) instanceof Door);
   }
   
   protected static boolean isValidTunnelStartLocation(ZoneMap z, int x, int y)
   {
      return z.getTile(x, y).isLowPassable() &&
             !(z.getTile(x, y) instanceof Door);
   }
      
   protected static void setLine(ZoneMap z, Coord start, Coord end, MapCellBase base)
   {
      Vector<Coord> line = StraightLine.findLine(start, end);
      for(Coord c : line)
      {
         z.getTileMap()[c.x][c.y] = MapCellFactory.getMapCell(base);
      }
   }
      
   protected static Coord pickCoordFromList(Vector<Coord> list)
   {
      if(list.size() > 0)
      {
         return list.elementAt(RNG.nextInt(list.size()));
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
   
   protected static void setConnections(ZoneMap z, Vector<TIMRoom> roomList)
   {
      for(TIMRoom room : roomList)
         room.setConnections(z);
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