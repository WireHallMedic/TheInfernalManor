package TheInfernalManor.Map;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Engine.*;

public class ZoneMapFactory implements MapConstants
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
      int widthOfRoom = upper.getGrid(0, 0).getTemplateMap()[0][0].getWidth();
      int heightOfRoom = upper.getGrid(0, 0).getTemplateMap()[0][0].getHeight();
      int totalMapWidth = (widthOfRoom + 3) * roomsWide;
      int totalMapHeight = (heightOfRoom + 3) * roomsTall;
      ZoneMap zm = new ZoneMap(totalMapWidth, totalMapHeight);
      for(int x = 0; x < upper.getWidth(); x++)
      for(int y = 0; y < upper.getHeight(); y++)
      {
         for(int x2 = 0; x2 < upper.getLowerWidth(); x2++)
         for(int y2 = 0; y2 < upper.getLowerHeight(); y2++)
         {
            RoomTemplate rt = upper.getGrid(x, y).getTemplateMap()[x2][y2].resolveRandomTiles();
            int xOrigin = x * ((upper.getWidth() * upper.getLowerWidth()) + 3);
            xOrigin += upper.getLowerWidth() * x2;
            int yOrigin = y * ((upper.getWidth() * upper.getLowerWidth()) + 3);
            yOrigin += upper.getLowerWidth() * y2;
            placeTemplate(zm, rt, xOrigin, yOrigin);
         }
      }
      zm.updateAllMaps();
      return zm;
   }
   
   private static void clear(ZoneMap zm)
   {
      MapCell[][] tileMap = zm.getTileMap();
      for(int x = 0; x < tileMap.length; x++)
      for(int y = 0; y < tileMap[0].length; y++)
         tileMap[x][y] = null;
   }
   
   private static void placeTemplate(ZoneMap zm, RoomTemplate rt, int xOrigin, int yOrigin)
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
}