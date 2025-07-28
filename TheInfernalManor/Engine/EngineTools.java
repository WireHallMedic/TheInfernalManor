package TheInfernalManor.Engine;

import java.util.*;
import WidlerSuite.*;
import TheInfernalManor.TIMMain;
import TheInfernalManor.Map.*;
import TheInfernalManor.Actor.*;
import java.io.*;

public class EngineTools implements EngineConstants
{
   private static final Coord[][] SHELL_LIST = generateShellList();
   public static final int UNCHECKED = 0;
   public static final int CHECKED_TRUE = 1;
   public static final int CHECKED_FALSE = 2;
   
   public static Vector<Coord> getPointTarget(int xOrigin, int yOrigin, int xTarget, int yTarget, int maxLen)
   {
      Coord origin = new Coord(xOrigin, yOrigin);
      Coord target = new Coord(xTarget, yTarget);
      Vector<Coord> line = StraightLine.findLine(origin, target, StraightLine.REMOVE_ORIGIN);
      Vector<Coord> targetList = new Vector<Coord>();
      for(int i = 0; i < line.size(); i++)
      {
         Coord c = line.elementAt(i);
         if(!GameState.getCurZone().isPermeable(c) ||
            GameState.isActorAt(c) || 
            WSTools.getAngbandMetric(origin, c) >= maxLen)
         {
            targetList.add(c);
            return targetList;
         }
      }
      targetList.add(target);
      return targetList;
   }
   public static Vector<Coord> getPointTarget(Coord origin, Coord target, int maxLen){return getPointTarget(origin.x, origin.y, target.x, target.y, maxLen);}
   
   // return the tiles that will be affected
   public static Vector<Coord> getLineTargets(int xOrigin, int yOrigin, int xTarget, int yTarget, int maxLen)
   {
      Coord origin = new Coord(xOrigin, yOrigin);
      Coord target = new Coord(xTarget, yTarget);
      target.subtract(origin);
      Vect vect = new Vect(target);
      vect.magnitude = maxLen;
      target = new Coord(vect);
      target.add(origin);
      Vector<Coord> line = StraightLine.findLine(origin, target, StraightLine.REMOVE_ORIGIN);
      Vector<Coord> actualLine = new Vector<Coord>();
      for(int i = 0; i < line.size(); i++)
      {
         Coord c = line.elementAt(i);
         actualLine.add(c);
         if(WSTools.getAngbandMetric(origin, c) > maxLen ||
            !GameState.getCurZone().isPermeable(c))
            break;
      }
      return actualLine;
   }
   public static Vector<Coord> getLineTargets(Coord origin, Coord target, int maxLen){return getLineTargets(origin.x, origin.y, target.x, target.y, maxLen);}


   // return a shell of passed radius, centered on origin
   public static Vector<Coord> getShellList(Coord origin, int radius)
   {
      Vector<Coord> val = new Vector<Coord>();
      for(int i = 0; i < SHELL_LIST[radius].length; i++)
      {
         Coord c = new Coord(SHELL_LIST[radius][i]);
         c.add(origin);
         val.add(c);
      }
      return val;
   }
   public static Vector<Coord> getShellList(int x, int y, int radius){return getShellList(new Coord(x, y), radius);}
   
   
   public static Vector<Coord> getConeTargets(Coord origin, Coord target, int length, int radius)
   {
      if(target.equals(origin))
         return new Vector<Coord>();
      int diameter = (length * 2) + 1;
      Coord midpoint = new Coord(diameter / 2, diameter / 2);
      Coord shellOrigin = new Coord(target);
      shellOrigin.subtract(origin);
      Vect v = new Vect(shellOrigin);
      v.magnitude = length - radius;
      shellOrigin = new Coord(v);
      shellOrigin.add(midpoint);
      int[][] passMap = new int[diameter][diameter];
      
      Vector<Coord> shellList = getShellList(shellOrigin, radius);
      Vector<Coord> lineList;
      for(Coord endPoint : shellList)
      {
         lineList = StraightLine.findLine(midpoint, endPoint, StraightLine.REMOVE_ORIGIN);
         int checkVal = CHECKED_TRUE;
         for(Coord tile : lineList)
         {
            if(passMap[tile.x][tile.y] == UNCHECKED)
               passMap[tile.x][tile.y] = checkVal;
            if(!GameState.getCurZone().isPermeable(tile.x + origin.x - midpoint.x, tile.y + origin.y - midpoint.y))
               checkVal = CHECKED_FALSE;
         }
      }
      Vector<Coord> targetList = new Vector<Coord>();
      for(int x = 0; x < diameter; x++)
      for(int y = 0; y < diameter; y++)
      {
         if(passMap[x][y] == CHECKED_TRUE)
            targetList.add(new Coord(x + origin.x - midpoint.x, y + origin.y - midpoint.y));
      }
      return targetList;
   }
   
   // blasts originate one tile short of impacting a wall, or on an actor, or at max distance.
   private static Coord getBlastOriginTile(int xOrigin, int yOrigin, int xTarget, int yTarget, int maxLen)
   {
      Coord origin = new Coord(xOrigin, yOrigin);
      Coord target = new Coord(xTarget, yTarget);
      Vector<Coord> line = StraightLine.findLine(origin, target);
      Vector<Coord> targetList = new Vector<Coord>();
      for(int i = 1; i < line.size(); i++)
      {
         Coord c = line.elementAt(i);
         if(!GameState.getCurZone().isPermeable(c))
            return line.elementAt(i - 1);
         if(GameState.isActorAt(c) || 
            WSTools.getAngbandMetric(origin, c) >= maxLen)
            return c;
      }
      return target;
   }
   public static Coord getBlastOriginTile(Coord origin, Coord target, int maxLen){return getBlastOriginTile(origin.x, origin.y, target.x, target.y, maxLen);}
   
   
   public static Vector<Coord> getBlastTargets(int xOrigin, int yOrigin, int xTarget, int yTarget, int maxLen, int radius)
   {
      Coord trueTarget = getBlastOriginTile(xOrigin, yOrigin, xTarget, yTarget, maxLen);
      
      int diameter = (radius * 2) + 1;
      Coord midpoint = new Coord(diameter / 2, diameter / 2);
      Coord shellOrigin = new Coord(midpoint);
      int[][] passMap = new int[diameter][diameter];
      
      Vector<Coord> shellList = getShellList(shellOrigin, radius);
      Vector<Coord> lineList;
      for(Coord endPoint : shellList)
      {
         lineList = StraightLine.findLine(midpoint, endPoint, StraightLine.REMOVE_ORIGIN);
         int checkVal = CHECKED_TRUE;
         for(Coord tile : lineList)
         {
            if(passMap[tile.x][tile.y] == UNCHECKED)
               passMap[tile.x][tile.y] = checkVal;
            if(!GameState.getCurZone().isPermeable(tile.x + trueTarget.x - midpoint.x, tile.y + trueTarget.y - midpoint.y))
               checkVal = CHECKED_FALSE;
         }
      }
      Vector<Coord> targetList = new Vector<Coord>();
      targetList.add(trueTarget);
      for(int x = 0; x < diameter; x++)
      for(int y = 0; y < diameter; y++)
      {
         if(passMap[x][y] == CHECKED_TRUE)
            targetList.add(new Coord(x + trueTarget.x - midpoint.x, y + trueTarget.y - midpoint.y));
      }
      
      return targetList;
   }
   public static Vector<Coord> getBlastTargets(Coord origin, Coord target, int maxLen, int radius){return getBlastTargets(origin.x, origin.y, target.x, target.y, maxLen, radius);}
   
   
   public static Vector<Coord> getAuraTargets(Coord origin, int radius)
   {
      Vector<Coord> targetList = getBlastTargets(origin, origin, 0, radius);
      for(int i = 0; i < targetList.size(); i++)
      {
         if(targetList.elementAt(i).equals(origin))
         {
            targetList.removeElementAt(i);
            i--;
         }
      }
      return targetList;
   }
   public static Vector<Coord> getAuraTargets(int xOrigin, int yOrigin, int radius){return getAuraTargets(new Coord(xOrigin, yOrigin), radius);}
   
   
   // a shell is all the Coords of a specific Angband metric, relative to zero. filling in the corners if AM > 1 to make sure
   // we don't miss any tiles.
   private static Coord[][] generateShellList()
   {
      int maxSize = 10;
      int diameter = (2 * maxSize) + 1;
      int center = diameter / 2;
      // generate big box of Angband metrics
      int[][] metricArray = new int[diameter][diameter];
      for(int x = 0; x < diameter; x++)
      for(int y = 0; y < diameter; y++)
      {
         metricArray[x][y] = WSTools.getAngbandMetric(x, y, center, center);
      }
      
      Coord[][] shellList = new Coord[maxSize + 1][];
      shellList[0] = new Coord[1];
      shellList[0][0] = new Coord(0, 0);
      
      for(int i = 1; i <= maxSize; i++)
      {
         Vector<Coord> cList = new Vector<Coord>();
         for(int x = 0; x < diameter; x++)
         for(int y = 0; y < diameter; y++)
         {
            // add matching locations
            if(metricArray[x][y] == i)
            {
               cList.add(new Coord(x - center, y - center));
            }
            // also add interior corner locations
            else if(metricArray[x][y] == i - 1 && countNeighbors(metricArray, x, y, i) == 2)
            {
               cList.add(new Coord(x - center, y - center));
            }
         }
         shellList[i] = new Coord[cList.size()];
         for(int j = 0; j < cList.size(); j++)
            shellList[i][j] = cList.elementAt(j);
      }
      return shellList;
   }
   
   
   private static int countNeighbors(int[][] angbandMap, int x, int y, int searchInt)
   {
      int count = 0;
      if(x - 1 > 0 && angbandMap[x - 1][y] == searchInt)
         count++;
      if(x + 1 < angbandMap.length && angbandMap[x + 1][y] == searchInt)
         count++;
      if(y - 1 > 0 && angbandMap[x][y - 1] == searchInt)
         count++;
      if(y + 1 < angbandMap[0].length && angbandMap[x][y + 1] == searchInt)
         count++;
      return count;
   }
   
   
   public static Rollable roll(Rollable[] items)
   {
      int maxWeight = 0;
      for(Rollable item : items)
         maxWeight += item.getWeight();
      int roll = RNG.nextInt(maxWeight);
      for(Rollable item : items)
      {
         if(roll < item.getWeight())
            return item;
         roll -= item.getWeight();
      }
      return null;
   }
   
   
   public static Rollable roll(Rollable[] items, int level)
   {
      return roll(filterRollable(items, level));
   }
   
   
   public static Rollable[] filterRollable(Rollable[] original, int level)
   {
      Vector<Rollable> newList = new Vector<Rollable>();
      for(Rollable item : original)
      {
         if(item.getMinLevel() <= level &&
            item.getMaxLevel() >= level &&
            item.getWeight() > 0)
            newList.add(item);
      }
      return newList.toArray(new Rollable[newList.size()]);
   }
   
   public static void printBoolMap(boolean[][] map)
   {
      for(int y = 0; y < map[0].length; y++)
      {
         for(int x = 0; x < map.length; x++)
            if(map[x][y])
               System.out.print(".");
            else
               System.out.print("#");
         System.out.println();
      }
      System.out.println();
   }
   
   // File I/O
   public static BufferedReader getTextReader(String fileName)
   {
      BufferedReader bReader = null;
      try
      {
         InputStream is = TIMMain.class.getResourceAsStream(fileName);
         bReader = new BufferedReader(new InputStreamReader(is));
      }
      catch(Exception ex)
      {
         System.out.println(String.format("Unable to load file %s: %s\n%s", fileName, ex.toString(),
            "If running as a .jar, make sure the file's location is listed in the manifest file."));
      }
      return bReader;
   }
   
   public static String cleanSerializationString(String str)
   {
      str = str.replace("’", "'");
      return str;
   }
}