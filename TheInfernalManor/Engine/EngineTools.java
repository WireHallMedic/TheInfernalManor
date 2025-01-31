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
            if(metricArray[x][y] == i)
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
   
   
   // return a shell of passed radius, centered on origin
   public static Coord[] getShellList(Coord origin, int radius)
   {
      Coord[] val = new Coord[SHELL_LIST[radius].length];
      for(int i = 0; i < val.length; i++)
      {
         Coord c = new Coord(SHELL_LIST[radius][i]);
         c.add(origin);
         val[i] = c;
      }
      return val;
   }
   public static Coord[] getShellList(int x, int y, int radius){return getShellList(new Coord(x, y), radius);}
}