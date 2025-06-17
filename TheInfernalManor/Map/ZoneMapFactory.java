package TheInfernalManor.Map;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Engine.*;

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
   
   public static ZoneMap generate(GridOfMapGrids upper)
   {
      int roomsWide = upper.getWidth() * upper.getLowerWidth();
      int roomsTall = upper.getHeight() * upper.getLowerHeight();
      int widthOfRoom = upper.getLowerGrid(0, 0).getTemplateMap()[0][0].getWidth();
      int heightOfRoom = upper.getLowerGrid(0, 0).getTemplateMap()[0][0].getHeight();
      int islandWidth = (widthOfRoom * upper.getLowerWidth()) + 3;
      int islandHeight = (heightOfRoom * upper.getLowerHeight()) + 3;
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
      fillNulls(zm);
      zm = getTrimmed(zm);
      replaceAll(zm, MapCellBase.WALL, MapCellBase.DEEP_LIQUID);
      zm.updateAllMaps();
      
      return zm;
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