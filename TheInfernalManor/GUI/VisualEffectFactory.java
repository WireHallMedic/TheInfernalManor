package TheInfernalManor.GUI;

import StrictCurses.*;
import WidlerSuite.*;

public class VisualEffectFactory implements GUIConstants, SCConstants
{
   public static void registerExplosion(int xOrigin, int yOrigin)
   {
      int len = 12;
      int[] fgArr = new int[len];
      int[] iconArr = new int[len];
      for(int i = 0; i < len; i++)
      {
         if(i < len / 3)
            fgArr[i] = BRIGHT_YELLOW;
         else if(i < (len * 2) / 3)
            fgArr[i] = BRIGHT_ORANGE;
         else
            fgArr[i] = BRIGHT_RED;
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
   
   public static void registerExplosion(int xOrigin, int yOrigin, int radius)
   {
      for(int x = xOrigin - radius; x < xOrigin + radius + 1; x++)
      for(int y = yOrigin - radius; y < yOrigin + radius + 1; y++)
      {
         if(WSTools.getAngbandMetric(x, y, xOrigin, yOrigin) <= radius)
            registerExplosion(x, y);
      }
   }
}