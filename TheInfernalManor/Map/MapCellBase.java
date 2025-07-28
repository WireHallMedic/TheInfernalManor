package TheInfernalManor.Map;

import StrictCurses.*;

public enum MapCellBase
{
   CLEAR             (SCConstants.BULLET_TILE, true, true, true),
   ROUGH             (SCConstants.CENTERED_COMMA_TILE, true, true, true),
   WALL              ('#', false, false, false),
   LOW_WALL          ('=', false, true, true),
   BARS              (':', false, false, true),
   SHALLOW_LIQUID    ('~', true, true, true),
   DEEP_LIQUID       (SCConstants.ALMOST_EQUAL_TO_TILE, false, true, true),
   PIT               (' ', false, true, true),
   CONTAINER         ('0', false, true, true),
   CHEST_CLOSED      ('?', false, true, true),
   CHEST_OPEN        (SCConstants.INVERTED_QUESTION_TILE, false, true, true),
   DOOR_CLOSED       ('|', false, false, false, true),
   DOOR_OPEN         ('/', true, true, true),
   GATE_CLOSED       ('|', false, true, true, true),
   GATE_OPEN         ('/', true, true, true),
   TOGGLE_UNFLIPPED  ('!', false, false, true),
   TOGGLE_FLIPPED    (SCConstants.INVERTED_EXCLAMATION_TILE, false, false, true),
   ENTRANCE          ('<', true, true, true),
   EXIT              ('>', true, true, true),
   DEFAULT_IMPASSABLE('#', false, false, false);
   
   public int iconIndex;
   public boolean lowPassable;
   public boolean highPassable;
   public boolean transparent;
   public boolean pathingPassable;  // if should be considered passable for mapping and searching
   
   private MapCellBase(int icon, boolean lowPass, boolean highPass, boolean transp)
   {
      this(icon, lowPass, highPass, transp, lowPass);
   }
   
   private MapCellBase(int icon, boolean lowPass, boolean highPass, boolean transp, boolean pathPass)
   {
      iconIndex = icon;
      lowPassable = lowPass;
      highPassable = highPass;
      transparent = transp;
      pathingPassable = pathPass;
   }
}