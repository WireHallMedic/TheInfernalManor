package TheInfernalManor.Engine;

import java.util.*;
import WidlerSuite.*;
import TheInfernalManor.Map.*;
import TheInfernalManor.Actor.*;

public class EngineTools implements EngineConstants
{
   private static final Coord[][] SHELL_LIST = generateShellList();
   
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
            !GameState.getCurZone().isHighPassable(c))
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
      boolean[][] passMap = new boolean[diameter][diameter];
      
      Vector<Coord> shellList = getShellList(shellOrigin, radius);
//       for(Coord c: shellList)
//       {
//          passMap[c.x][c.y] = true;
//       }
      
      
      Vector<Coord> lineList;
      for(Coord endPoint : shellList)
      {
         lineList = StraightLine.findLine(midpoint, endPoint, StraightLine.REMOVE_ORIGIN);
         for(Coord tile : lineList)
         {
            passMap[tile.x][tile.y] = true;
            if(!GameState.getCurZone().isHighPassable(tile.x + origin.x - midpoint.x, tile.y + origin.y - midpoint.y))
               break;
         }
      }
      Vector<Coord> targetList = new Vector<Coord>();
      for(int x = 0; x < diameter; x++)
      for(int y = 0; y < diameter; y++)
      {
         if(passMap[x][y])
            targetList.add(new Coord(x + origin.x - midpoint.x, y + origin.y - midpoint.y));
      }
      return targetList;
   }
   
   
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
}