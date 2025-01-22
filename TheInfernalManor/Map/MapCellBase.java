package TheInfernalManor.Map;

import StrictCurses.*;

public enum MapCellBase
{
   CLEAR             (SCConstants.BULLET_TILE, true, true, true),
   ROUGH             (SCConstants.CENTERED_COMMA_TILE, true, true, true),
   WALL              ('#', false, false, false),
   LOW_WALL          ('=', false, true, true),
   BARS              (':', false, false, true),
   SHALLOW_LIQUID    ('~', false, true, true),
   DEEP_LIQUID       (SCConstants.ALMOST_EQUAL_TO_TILE, false, true, true),
   CHEST_CLOSED      ('?', false, true, true),
   CHEST_OPEN        (SCConstants.INVERTED_QUESTION_TILE, false, true, true),
   DOOR_CLOSED       ('|', false, false, false),
   DOOR_OPEN         ('-', true, true, true),
   TOGGLE_UNFLIPPED  ('!', false, false, true),
   TOGGLE_FLIPPED    (SCConstants.INVERTED_EXCLAMATION_TILE, false, false, true),
   ENTRANCE          ('<', true, true, true),
   EXIT              ('>', true, true, true);
   
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