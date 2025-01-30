package TheInfernalManor.Engine;

import java.util.*;
import WidlerSuite.*;
import TheInfernalManor.Map.*;
import TheInfernalManor.Actor.*;

public class EngineTools implements EngineConstants
{
   // return the tiles that will be affected
   public static Vector<Coord> getLineTargets(int xOrigin, int yOrigin, int xTarget, int yTarget, int maxLen)
   {
      Coord origin = new Coord(xOrigin, yOrigin);
      Vector<Coord> line = StraightLine.findLine(origin, new Coord(xTarget, yTarget), 
                                                 StraightLine.REMOVE_ORIGIN);
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
}