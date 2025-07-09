package TheInfernalManor.GUI;

import StrictCurses.*;
import WidlerSuite.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;

public class VisualEffectFactory implements GUIConstants, SCConstants
{
   
   public static void registerMovementEcho(Actor a)
   {
      int len = 18;
      int xOrigin = a.getXLocation();
      int yOrigin = a.getYLocation();
      int[] fgArr = new int[len];
      int[] iconArr = new int[len];
      int startColor = a.getColor();
      int endColor = GameState.getCurZone().getTile(xOrigin, yOrigin).getBGColor();
      int[] colorGradient = GUITools.getGradient(startColor, endColor, len);
      for(int i = 0; i < len; i++)
      {
         iconArr[i] = a.getIconIndex();
         fgArr[i] = colorGradient[i];
      }
      VisualEffect ve = new VisualEffect(iconArr, fgArr, null);
      ve.setTicksPerFrame(1);
      ve.setXLocation(xOrigin);
      ve.setYLocation(yOrigin);
      AnimationManager.addGroundEffect(ve);
   }


   public static void registerExplosion(int xOrigin, int yOrigin)
   {
      int len = 12;
      int[] fgArr = new int[len];
      int[] iconArr = new int[len];
      for(int i = 0; i < len; i++)
      {
         if(i < len / 3)
            fgArr[i] = YELLOW;
         else if(i < (len * 2) / 3)
            fgArr[i] = ORANGE;
         else
            fgArr[i] = RED;
      }
      for(int i = 0; i < len; i++)
      {
         if((i / 2) % 2 == 0)
         {
            iconArr[i] = '*';
         }
         else
         {
            iconArr[i] = ROTATED_ASTERIX_TILE;
         }
      }
      VisualEffect ve = new VisualEffect(iconArr, fgArr, null);
      ve.setTicksPerFrame(1);
      ve.setXLocation(xOrigin);
      ve.setYLocation(yOrigin);
      AnimationManager.addLocking(ve);
   }
   public static void registerExplosion(Coord c){registerExplosion(c.x, c.y);}
   
   public static void registerExplosion(int xOrigin, int yOrigin, int radius)
   {
      for(int x = xOrigin - radius; x < xOrigin + radius + 1; x++)
      for(int y = yOrigin - radius; y < yOrigin + radius + 1; y++)
      {
         if(WSTools.getAngbandMetric(x, y, xOrigin, yOrigin) <= radius)
            registerExplosion(x, y);
      }
   }
   public static void registerExplosion(Coord c, int radius){registerExplosion(c.x, c.y, radius);}
   
   
   public static void registerLightning(int xOrigin, int yOrigin, Direction dirToSource)
   {
      int[] colorArr = GUITools.getGradient(WHITE, YELLOW, 12);
      int indexVal = -1;
      switch(dirToSource)
      {
         case Direction.NORTH :
         case Direction.SOUTH :     indexVal = '|'; break;
         case Direction.EAST :
         case Direction.WEST :      indexVal = '-'; break;
         case Direction.NORTHEAST :
         case Direction.SOUTHWEST : indexVal = '/'; break;
         case Direction.SOUTHEAST :
         case Direction.NORTHWEST : indexVal = '\\'; break;
         case Direction.ORIGIN :    indexVal = SCConstants.BOLT_TILE; break;
      }
      VisualEffect ve = new VisualEffect(getArrayOfInt(indexVal, 12), colorArr, null);
      ve.setTicksPerFrame(1);
      ve.setXLocation(xOrigin);
      ve.setYLocation(yOrigin);
      AnimationManager.addLocking(ve);
   }
   public static void registerLightning(Coord c, Direction dirToSource){registerLightning(c.x, c.y, dirToSource);}
   
   private static int[] getArrayOfInt(int val, int len)
   {
      int[] result = new int[len];
      for(int i = 0; i < len; i++)
         result[i] = val;
      return result;
   }
}