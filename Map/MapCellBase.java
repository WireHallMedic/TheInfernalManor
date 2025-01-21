package TheInfernalManor.Map;

import StrictCurses.*;

public enum MapCellBase
{
   CLEAR          (SCConstants.SMALL_BULLET_TILE, true, true, true),
   WALL           ('#', false, false, false),
   LOW_WALL       ('=', false, true, true),
   SHALLOW_LIQUID ('~', false, true, true),
   DEEP_LIQUID    (SCConstants.ALMOST_EQUAL_TO_TILE, false, true, true);
   
   public int iconIndex;
   public boolean lowPassable;
   public boolean highPassable;
   public boolean transparent;
   
   private MapCellBase(int icon, boolean lowPass, boolean highPass, boolean transp)
   {
      iconIndex = icon;
      lowPassable = lowPass;
      highPassable = highPass;
      transparent = transp;
   }
}