package TheInfernalManor.Map;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Engine.*;
import WidlerSuite.*;
import java.util.*;
import java.io.*;

public class ZoneMapFactory implements MapConstants, GUIConstants
{
   private static RoomTemplateDeck genericTiles = loadRoomTemplates("/TheInfernalManor/DataFiles/GenericMapTiles.ttd");
   private static RoomTemplateDeck forestTiles = loadRoomTemplates("/TheInfernalManor/DataFiles/ForestMapTiles.ttd");
   
   public static ZoneMap generateZoneMap(MapType type, MapSize size)
   {
      ZoneMap z = null;
      
      switch(type)
      {
         case ROAD     : z = generateDungeon(size); break;
         case FIELD    : z = generateDungeon(size); break;
         case FOREST   : z = generateForest(size); break;
         case CAVERN   : z = generateDungeon(size); break;
         case SWAMP    : z = generateSwamp(size); break;
         case CATACOMB : z = generateDungeon(size); break;
         case MOUNTAIN : z = generateDungeon(size); break;
         case BUILDING : z = generateDungeon(size); break;
         case VILLAGE  : z = generateDungeon(size); break;
         case DUNGEON  : z = generateDungeon(size); break;
      }
      
      return z;
   }
   
   public static ZoneMap generateForest(MapSize size)
   {
      double minConnectivity = .66;
      double minRoomRatio = .75;
      int roomDiameter = 5;
      switch(size)
      {
         case SMALL :   roomDiameter = 3; break;
         case LARGE :   roomDiameter = 7; break;
      }
      MapGrid mapGrid = new MapGrid(roomDiameter, roomDiameter, minConnectivity, forestTiles, minRoomRatio);
      ZoneMap z = GridZoneMapFactory.generate(mapGrid.getTemplateMap());
      replaceAll(z, MapCellBase.DEFAULT_IMPASSABLE, MapCellBase.WALL);
      z.applyPalette(MapPalette.getBasePalette());
      return z;
   }
   
   public static ZoneMap generateSwamp(MapSize size)
   {
      double upperMinConnectivity = .66;
      double upperMinRoomRatio = .75;
      double lowerMinConnectivity = .5;
      double lowerMinRoomRatio = .5;
      int upperRoomDiameter = 4;
      int lowerRoomDiameter = 2;
      switch(size)
      {
         case SMALL :   upperRoomDiameter = 3; break;
         case LARGE :   upperRoomDiameter = 5; break;
      }
      GridOfMapGrids gridOfGrids = new GridOfMapGrids(upperRoomDiameter, upperRoomDiameter, upperMinConnectivity, genericTiles, upperMinRoomRatio);
      gridOfGrids.setLowerConnectivity(lowerMinConnectivity);
      gridOfGrids.setLowerMinRatio(lowerMinRoomRatio);
      gridOfGrids.setLowerWidth(lowerRoomDiameter);
      gridOfGrids.setLowerHeight(lowerRoomDiameter);
      gridOfGrids.rollLowers();
      
      ZoneMap z = GridZoneMapFactory.generate(gridOfGrids, 7);
      replaceAll(z, MapCellBase.DEFAULT_IMPASSABLE, MapCellBase.DEEP_LIQUID);
      z.applyPalette(MapPalette.getSwampPalette());
      return z;
   }
   
   public static ZoneMap generateDungeon(MapSize size)
   {
      int upperMin = 15;
      int upperMax = 25;
      int tilesDiameter = 80;
      switch(size)
      {
         case SMALL :   tilesDiameter = 60; break;
         case LARGE :   tilesDiameter = 100; break;
      }
      TIMBinarySpacePartitioning.setPartitionChance(.5);
      Vector<TIMRoom> roomList = TIMBinarySpacePartitioning.partition(tilesDiameter, tilesDiameter, upperMin, upperMax);
      ZoneMap z = BSPZoneMapFactory.generateDungeon(roomList, 7, 13, .5, .5);
      replaceAll(z, MapCellBase.DEFAULT_IMPASSABLE, MapCellBase.WALL);
      z.applyPalette(MapPalette.getDungeonPalette());
      return z;
   }
   
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
            tileMap[x][y] = MapCellFactory.getMapCell(MapCellBase.DEFAULT_IMPASSABLE, WHITE, BLACK);
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
      for(TIMRoom room : top.getRoomList())
      {
         room.origin.x += xOrigin;
         room.origin.y += yOrigin;
         bottom.getRoomList().add(room);
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
   
   protected static MapCell resolveOverwrite(MapCell c1, MapCell c2)
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
   
   protected static MapCell resolveOverwriteCorner(MapCell c1, MapCell c2)
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
   
   protected static MapCell resolveDoors(MapCell c1, MapCell c2)
   {
      if(c1 instanceof Door && c2 instanceof Door)
         return randomPick(c1, c2);
      if(c1 instanceof Door)
         return c1;
      if(c2 instanceof Door)
         return c2;
      return null;
   }
   
   protected static MapCell resolveLowPassable(MapCell c1, MapCell c2)
   {
      if(c1.isLowPassable() && c2.isLowPassable())
        return randomPick(c1, c2);
      if(c1.isLowPassable())
         return c1;
      if(c2.isLowPassable())
         return c2;
      return null;
   }
   
   protected static MapCell resolveHighImpassable(MapCell c1, MapCell c2)
   {
      if(!c1.isHighPassable() && !c2.isHighPassable())
         return randomPick(c1, c2);
      if(!c1.isHighPassable())
         return c1;
      if(!c2.isHighPassable())
         return c2;
      return null;
   }
   
   protected static MapCell randomPick(MapCell c1, MapCell c2)
   {
      if(RNG.nextBoolean())
         return c1;
      else
         return c2;
   }
   
   protected static void addChests(ZoneMap z)
   {
      for(TIMRoom r : z.getRoomList())
      {
         if(r.isTerminal() && RNG.nextDouble() <= .75)
            addChest(z, r);
         else if(RNG.nextDouble() <= .20)
            addChest(z, r);
      }
   }
   
   protected static void addChest(ZoneMap z, TIMRoom r)
   {
      Coord target = null;
      Vector<Coord> prospectList = new Vector<Coord>();
      for(int x = 0; x < r.size.x - 2; x++)
      for(int y = 0; y < r.size.y - 2; y++)
         if(z.isLowPassable(x + r.origin.x + 1, y + r.origin.y + 1))
            prospectList.add(new Coord(x + r.origin.x + 1, y + r.origin.y + 1));
      if(prospectList.size() > 0)
      {
         target = pickCoordFromList(prospectList);
         z.setTile(target.x, target.y, MapCellFactory.getChest());
      }
   }
   
   protected static void addRoomEntrance(ZoneMap z, Direction fromDir)
   {
      TIMRoom r = null;
      Coord target = null;
      Direction[] dirList = new Direction[3];
      switch(RNG.nextInt(3))
      {
         case 0 : dirList[0] = fromDir;
                  dirList[1] = fromDir.clockwise();
                  dirList[2] = fromDir.counterClockwise();
                  break;
         case 1 : dirList[0] = fromDir.clockwise();
                  dirList[1] = fromDir.counterClockwise();
                  dirList[2] = fromDir;
                  break;
         default: dirList[0] = fromDir.counterClockwise();
                  dirList[1] = fromDir;
                  dirList[2] = fromDir.clockwise();
                  break;
      }
      for(int i = 0; i < dirList.length && r == null; i++)
         r = getRoomInSector(z, dirList[i]);
      if(r != null)
      {
         Vector<Coord> prospectList = new Vector<Coord>();
         for(int x = 0; x < r.size.x - 4; x++)
         for(int y = 0; y < r.size.y - 4; y++)
            if(z.isLowPassable(x + r.origin.x + 2, y + r.origin.y + 2))
               prospectList.add(new Coord(x + r.origin.x + 2, y + r.origin.y + 2));
         if(prospectList.size() > 0)
         {
            target = pickCoordFromList(prospectList);
            z.setTile(target.x, target.y, MapCellFactory.getEntrance());
         }
      }
   }
   
   // exit can be opposite or adjacent to opposite
   protected static void addRoomExit(ZoneMap z, Direction fromDir)
   {
      TIMRoom r = null;
      Direction toDir = Direction.getDirectionTo(fromDir.x, fromDir.y, 0, 0);
      Direction[] dirList = new Direction[3];
      switch(RNG.nextInt(3))
      {
         case 0 : dirList[0] = toDir;
                  dirList[1] = toDir.clockwise();
                  dirList[2] = toDir.counterClockwise();
                  break;
         case 1 : dirList[0] = toDir.clockwise();
                  dirList[1] = toDir.counterClockwise();
                  dirList[2] = toDir;
                  break;
         default: dirList[0] = toDir.counterClockwise();
                  dirList[1] = toDir;
                  dirList[2] = toDir.clockwise();
                  break;
      }
      r = null;
      for(int i = 0; i < dirList.length && r == null; i++)
         r = getRoomInSector(z, dirList[i]);
      if(r != null)
      {
         Vector<Coord> prospectList = new Vector<Coord>();
         for(int x = 0; x < r.size.x - 4; x++)
         for(int y = 0; y < r.size.y - 4; y++)
            if(z.isLowPassable(x + r.origin.x + 2, y + r.origin.y + 2))
               prospectList.add(new Coord(x + r.origin.x + 2, y + r.origin.y + 2));
         if(prospectList.size() > 0)
         {
            Coord target = pickCoordFromList(prospectList);
            z.setTile(target.x, target.y, MapCellFactory.getExit());
         }
      }

   
   }
   
   protected static void addEntranceAndExit(ZoneMap z, Direction fromDir)
   {
      addRoomEntrance(z, fromDir);
      addRoomExit(z, fromDir);   
   }
   
   protected static TIMRoom getRoomInSector(ZoneMap z, Direction dir)
   {
      Vector<TIMRoom> roomList = getRoomsInSector(z, dir);
      if(roomList.size() == 0)
         return null;
      return roomList.elementAt(RNG.nextInt(roomList.size()));
   }
   
   protected static Vector<TIMRoom> getRoomsInSector(ZoneMap z, Direction dir)
   {
      Vector<TIMRoom> roomList = new Vector<TIMRoom>();
      Coord min = new Coord(0, 0);
      Coord max = new Coord(0, 0);
      int width = z.getWidth();
      int height = z.getHeight();
      switch(dir)
      {
         case NORTH :      min.x = width / 3;
                           max.x = (width * 2) / 3;
                           min.y = 0;
                           max.y = height / 3;
                           break;
         case NORTHEAST :  min.x = (width * 2) / 3;
                           max.x = width - 1;
                           min.y = 0;
                           max.y = height / 3;
                           break;
         case EAST :       min.x = (width * 2) / 3;
                           max.x = width - 1;
                           min.y = height / 3;
                           max.y = (height * 2) / 3;
                           break;
         case SOUTHEAST :  min.x = (width * 2) / 3;
                           max.x = width;
                           min.y = (height * 2) / 3;
                           max.y = height - 1;
                           break;
         case SOUTH :      min.x = width / 3;
                           max.x = (width * 2) / 3;
                           min.y = (height * 2) / 3;
                           max.y = height - 1;
                           break;
         case SOUTHWEST :  min.x = 0;
                           max.x = width / 3;
                           min.y = (height * 2) / 3;
                           max.y = height - 1;
                           break;
         case WEST :       min.x = 0;
                           max.x = width / 3;
                           min.y = height / 3;
                           max.y = (height * 2) / 3;
                           break;
         case NORTHWEST :  min.x = 0;
                           max.x = width / 3;
                           min.y = 0;
                           max.y = height / 3;
                           break;
         case ORIGIN :     min.x = width / 3;
                           max.x = (width * 2) / 3;
                           min.y = height / 3;
                           max.y = (height * 2) / 3;
                           break;
      }
      for(TIMRoom curRoom : z.getRoomList())
      {
         Coord c = curRoom.getCenter();
         if(c.x >= min.x && c.x <= max.x &&
            c.y >= min.y && c.y <= max.y)
            roomList.add(curRoom);
      }
      return roomList;
   }
   
   
   
   // removes high-impassable rows and columns beyond the first in contact with low-impassable
   protected static ZoneMap getTrimmed(ZoneMap original)
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
   
   private static RoomTemplateDeck loadRoomTemplates(String fileName)
   {
      BufferedReader bReader = EngineTools.getTextReader(fileName);
		Vector<String> strList = new Vector<String>();
      try
      {
         String str = bReader.readLine();
         while(str != null)
         {
            str = str.replace("\n", "");
            strList.add(str);
            str = bReader.readLine();
         }
      }
      catch(Exception ex)
      {
         System.out.println("Exception while loading " + fileName + ": " + ex.toString());
      }
		return new RoomTemplateDeck(strList);
   }
   
   public static ZoneMap getTestMap1()
   {
      int w = 20;
      int h = 20;
      ZoneMap map = new ZoneMap(w, h);
      for(int x = 1; x < w - 1; x++)
      for(int y = 1; y < h - 1; y++)
         map.setTile(x, y, MapCellFactory.getMapCell(MapCellBase.CLEAR, LIGHT_GREY, DARK_GREY));
      map.setTile(2, 2, MapCellFactory.getMapCell(MapCellBase.WALL, LIGHT_GREY, DARK_GREY));
      map.setTile(w - 3, h - 3, MapCellFactory.getMapCell(MapCellBase.WALL, LIGHT_GREY, DARK_GREY));
      map.setTile(w - 3, 2, MapCellFactory.getMapCell(MapCellBase.WALL, LIGHT_GREY, DARK_GREY));
      map.setTile(2, h - 3, MapCellFactory.getMapCell(MapCellBase.WALL, LIGHT_GREY, DARK_GREY));
      ItemContainer ic = MapCellFactory.getBarrel();
      ic.addItem(new Gold(10));
      map.setTile(10, 3, ic);
      map.setTile(10, 5, MapCellFactory.getDoor());
      Chest c = MapCellFactory.getChest();
      c.addItem(new Gold(10));
      c.addItem(new Gold(10));
      c.addItem(new Gold(10));
      map.setTile(10, 7, c);
      map.setItemAt(1, 2, new Armor("Filthy Rags"));
      
      MapCell fragilePillar = MapCellFactory.getMapCell(MapCellBase.WALL, BROWN, DARK_GREY);
      fragilePillar.setBreakable(true);
      map.setTile(10, 9, new MapCell(fragilePillar));
      map.setTile(10, 10, new MapCell(fragilePillar));
      map.setTile(11, 9, new MapCell(fragilePillar));
      map.setTile(11, 10, new MapCell(fragilePillar));
      
      for(int x = 1; x < 19; x++)
         map.setTile(x, 12, MapCellFactory.getMapCell(MapCellBase.LOW_WALL, LIGHT_GREY, DARK_GREY));
      for(int y = 13; y < 19; y++)
      {
         map.setTile(5, y, MapCellFactory.getMapCell(MapCellBase.LOW_WALL, LIGHT_GREY, DARK_GREY));
         map.setTile(10, y, MapCellFactory.getMapCell(MapCellBase.LOW_WALL, LIGHT_GREY, DARK_GREY));
         map.setTile(15, y, MapCellFactory.getMapCell(MapCellBase.LOW_WALL, LIGHT_GREY, DARK_GREY));
      }
      map.setTile(2, 12, MapCellFactory.getGate());
      map.setTile(7, 12, MapCellFactory.getGate());
      map.setTile(12, 12, MapCellFactory.getGate());
      map.setTile(17, 12, MapCellFactory.getGate());
      
      map.setItemAt(2, 3, new Gold(10));
      map.applyPalette(MapPalette.getBasePalette());
      return map;
   }
}