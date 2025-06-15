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
      int totalMapWidth = widthOfRoom * roomsWide;//((widthOfRoom - 1) * roomsWide) + 1;
      int totalMapHeight = heightOfRoom * roomsTall;//((heightOfRoom - 1) * roomsTall) + 1;
      ZoneMap zm = new ZoneMap(totalMapWidth, totalMapHeight);
      for(int x = 0; x < roomsWide; x++)
      for(int y = 0; y < roomsTall; y++)
      {
         placeTemplate(zm, templateGrid[x][y], x * widthOfRoom, y * heightOfRoom);
      }
      zm.updateAllMaps();
      return zm;
   }
   
   private static void placeTemplate(ZoneMap zm, RoomTemplate rt, int xOrigin, int yOrigin)
   {
      MapCell[][] tileMap = zm.getTileMap();
      for(int x = 0; x < rt.getWidth(); x++)
      for(int y = 0; y < rt.getHeight(); y++)
         tileMap[x + xOrigin][y + yOrigin] = MapCellFactory.getMapCell(rt.getCellMapping(x, y));
   }
   
   private static MapCell resolveOverwrite(MapCell c1, MapCell c2)
   {
      // doors have highest priority
      if(c1 instanceof Door && c2 instanceof Door)
         if(RNG.nextBoolean())
            return c1;
         else
            return c2;
      if(c1 instanceof Door)
         return c1;
      if(c2 instanceof Door)
         return c2;
      // low passable has second priority
      if(c1.isLowPassable() && c2.isLowPassable())
         if(RNG.nextBoolean())
            return c1;
         else
            return c2;
      if(c1.isLowPassable())
         return c1;
      if(c2.isLowPassable())
         return c2;
      // all others are equal
      if(RNG.nextBoolean())
         return c1;
      else
         return c2;
   }
}